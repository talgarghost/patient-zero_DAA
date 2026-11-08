public class Strains {

    /**
     * Mission 1. The smallest number of single-letter changes that turn a into b.
     */
    public static int editDistance(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[][] d = new int[n + 1][m + 1];

        // Базовые случаи
        for (int i = 0; i <= n; i++) d[i][0] = i;
        for (int j = 0; j <= m; j++) d[0][j] = j;

        // Заполнение таблицы
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                int delete = d[i - 1][j] + 1;
                int insert = d[i][j - 1] + 1;
                int replace = d[i - 1][j - 1] + cost;

                d[i][j] = Math.min(Math.min(delete, insert), replace);
            }
        }
        return d[n][m];
    }

    /**
     * Mission 2. The length of the longest common subsequence.
     */
    public static int lcsLength(String a, String b) {
        int n = a.length();
        int m = b.length();
        int[][] d = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    d[i][j] = d[i - 1][j - 1] + 1;
                } else {
                    d[i][j] = Math.max(d[i - 1][j], d[i][j - 1]);
                }
            }
        }
        return d[n][m];
    }
}