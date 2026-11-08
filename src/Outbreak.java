import java.util.*;

public class Outbreak {

    /**
     * Mission 5a. DP on the tree of infections for cheapest tests.
     */
    public static boolean[] cheapestTests(int n, int[][] nb, int[] cost) {
        int[] take = new int[n];
        int[] skip = new int[n];
        int[] parent = new int[n];
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[n];

        // Обход в ширину (BFS) для построения порядка от корня(0) к листьям
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        visited[0] = true;
        parent[0] = -1;

        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : nb[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    q.add(v);
                }
            }
        }

        // DP снизу вверх (от листьев к корню)
        for (int i = n - 1; i >= 0; i--) {
            int u = order.get(i);
            take[u] = cost[u];
            skip[u] = 0;

            for (int v : nb[u]) {
                if (v != parent[u]) {
                    // Если тестируем u, для детей берем минимум
                    take[u] += Math.min(take[v], skip[v]);
                    // Если НЕ тестируем u, обязаны тестировать всех детей (v)
                    skip[u] += take[v];
                }
            }
        }

        // Восстановление ответа сверху вниз (от корня к листьям)
        boolean[] tested = new boolean[n];
        tested[0] = take[0] <= skip[0];

        for (int i = 1; i < n; i++) {
            int u = order.get(i);
            int p = parent[u];
            if (!tested[p]) {
                tested[u] = true; // Родитель не протестирован -> ребенок обязан быть
            } else {
                tested[u] = take[u] <= skip[u]; // Берем вариант дешевле
            }
        }

        return tested;
    }

    /**
     * Mission 5b. The number of links on the longest chain of infections.
     */
    public static int longestChain(int n, int[][] nb) {
        int[] max1 = new int[n];
        int[] max2 = new int[n];
        int[] parent = new int[n];
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[n];

        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        visited[0] = true;
        parent[0] = -1;
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : nb[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    q.add(v);
                }
            }
        }

        int maxChain = 0;
        // Поднимаем две максимальные ветви от листьев к корню
        for (int i = n - 1; i >= 0; i--) {
            int u = order.get(i);
            maxChain = Math.max(maxChain, max1[u] + max2[u]);

            if (parent[u] != -1) {
                int p = parent[u];
                int pathFromChild = max1[u] + 1;

                if (pathFromChild > max1[p]) {
                    max2[p] = max1[p];
                    max1[p] = pathFromChild;
                } else if (pathFromChild > max2[p]) {
                    max2[p] = pathFromChild;
                }
            }
        }
        return maxChain;
    }
}