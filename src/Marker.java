import java.util.*;

public class Marker {

    /**
     * Mission 3a. The prefix function of p.
     */
    public static int[] prefixFunction(String p) {
        int n = p.length();
        int[] pi = new int[n];
        for (int i = 1; i < n; i++) {
            int j = pi[i - 1];
            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                j = pi[j - 1];
            }
            if (p.charAt(i) == p.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }

    /**
     * Mission 3b. KMP. All start positions where p occurs in text.
     */
    public static int[] findAll(String text, String p) {
        if (p.isEmpty()) return new int[0];
        int[] pi = prefixFunction(p);
        List<Integer> hits = new ArrayList<>();

        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != p.charAt(j)) {
                j = pi[j - 1];
            }
            if (text.charAt(i) == p.charAt(j)) {
                j++;
            }
            if (j == p.length()) {
                hits.add(i - p.length() + 1);
                j = pi[j - 1]; // Продолжаем поиск с учетом возможных перекрытий
            }
        }

        int[] result = new int[hits.size()];
        for (int i = 0; i < hits.size(); i++) {
            result[i] = hits.get(i);
        }
        return result;
    }

    /**
     * Mission 4. Rabin-Karp. Count shared words.
     */
    public static int sharedWords(String a, String b, int k) {
        if (a.length() < k || b.length() < k) return 0;

        long BASE = 131;
        long MOD = 1_000_000_007L;
        long top = 1;
        for (int i = 1; i < k; i++) {
            top = (top * BASE) % MOD;
        }

        // Хешируем все окна длины k из b и сохраняем их стартовые позиции
        Map<Long, List<Integer>> bHashes = new HashMap<>();
        long hB = 0;
        for (int i = 0; i < k; i++) {
            hB = (hB * BASE + b.charAt(i)) % MOD;
        }
        bHashes.computeIfAbsent(hB, x -> new ArrayList<>()).add(0);

        for (int i = 1; i <= b.length() - k; i++) {
            hB = (hB - b.charAt(i - 1) * top % MOD + MOD) % MOD;
            hB = (hB * BASE + b.charAt(i + k - 1)) % MOD;
            bHashes.computeIfAbsent(hB, x -> new ArrayList<>()).add(i);
        }

        int count = 0;
        long hA = 0;

        // Считаем первоначальный хеш для строки a
        for (int i = 0; i < k; i++) {
            hA = (hA * BASE + a.charAt(i)) % MOD;
        }

        // Проверяем первое окно a
        if (checkMatch(a, b, k, 0, hA, bHashes)) count++;

        // Проходим ролл-хешем по остальным окнам a
        for (int i = 1; i <= a.length() - k; i++) {
            hA = (hA - a.charAt(i - 1) * top % MOD + MOD) % MOD;
            hA = (hA * BASE + a.charAt(i + k - 1)) % MOD;

            if (checkMatch(a, b, k, i, hA, bHashes)) count++;
        }

        return count;
    }

    // Вспомогательный метод для посимвольной сверки при коллизиях хешей
    private static boolean checkMatch(String a, String b, int k, int posA, long hA, Map<Long, List<Integer>> bHashes) {
        if (!bHashes.containsKey(hA)) return false;
        for (int posB : bHashes.get(hA)) {
            boolean match = true;
            for (int m = 0; m < k; m++) {
                if (a.charAt(posA + m) != b.charAt(posB + m)) {
                    match = false;
                    break;
                }
            }
            if (match) return true;
        }
        return false;
    }
}