// Day 24 - Problem 5: Number of Ways to Arrive at Destination (LC 1976) - Medium
// Topic: Queue (priority queue / Dijkstra - shortest time + uske raaste gino)
//
// PROBLEM (Hinglish):
// n intersections (0 se n-1) hain, roads[i] = [ui, vi, timei] ka
// matlab ui aur vi ke beech road hai jo timei minute leti hai.
// Intersection 0 se n-1 tak SHORTEST time me jaane ke kitne tareeke
// hain? Answer modulo 10^9 + 7 return karo.
// Example: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],
//   [6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
//   -> output 4 (shortest time 7 minute, 4 raaste)
// Example: n = 2, roads = [[1,0,10]] -> output 1
//
// APPROACH (Hinglish):
// Dijkstra + counting, ek saath. Priority queue (min-heap) se nodes
// ko shortest distance ke order me nikalo. Har node ke liye:
//   dist[i] = 0 se i tak ka shortest time
//   ways[i] = us shortest time se i tak pahunchne ke tareeke
// Neighbour v ko dekh ke:
//   - naya time chhota hai (dist[u] + w < dist[v]) -> dist update
//     karo aur ways[v] = ways[u] (nayi best distance ke raaste)
//   - naya time barabar hai (== dist[v]) -> ways[v] += ways[u]
//     (ek aur shortest raasta mil gaya)
// Heap se node nikalte waqt purani (stale) entry ho to skip karo.
// Ant me ways[n-1] hi answer hai.
//
// TIME COMPLEXITY: O((V + E) log V) - heap wala Dijkstra
// SPACE COMPLEXITY: O(V + E) - adjacency list + dist/ways arrays
//
// KEY LEARNING:
// Dijkstra sirf shortest distance nahi, uske saath "kitne tareeke"
// bhi gina sakta hai - bas ways array ko distance update ke saath
// update karte raho. Barabar distance mile to add karo, chhota mile
// to replace karo. Modulo har addition par lagana mat bhoolo.

import java.util.*;

class Solution {
    public int countPaths(int n, int[][] roads) {
        final int MOD = 1_000_000_007;
        // adjacency list banao: har entry [neighbour, time]
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] r : roads) {
            graph.get(r[0]).add(new int[]{r[1], r[2]});
            graph.get(r[1]).add(new int[]{r[0], r[2]});
        }

        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        long[] ways = new long[n];
        dist[0] = 0;
        ways[0] = 1;
        // (time, node) min-heap - sabse kam time wala pehle niklega
        PriorityQueue<long[]> heap = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        heap.offer(new long[]{0, 0});
        while (!heap.isEmpty()) {
            long[] cur = heap.poll();
            long d = cur[0];
            int u = (int) cur[1];
            // stale entry hai to skip karo (behtar distance mil chuki)
            if (d > dist[u]) {
                continue;
            }
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                long nd = d + edge[1];
                if (nd < dist[v]) {
                    // naya shortest time mila - ways reset karo
                    dist[v] = nd;
                    ways[v] = ways[u];
                    heap.offer(new long[]{nd, v});
                } else if (nd == dist[v]) {
                    // ek aur shortest raasta mila - ways jod do
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }
        return (int) (ways[n - 1] % MOD);
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        int[][] r1 = {{0, 6, 7}, {0, 1, 2}, {1, 2, 3}, {1, 3, 3}, {6, 3, 3},
                      {3, 5, 1}, {6, 5, 1}, {2, 5, 1}, {0, 4, 5}, {4, 6, 2}};
        System.out.println(s.countPaths(7, r1)); // Expected: 4
        // Example 2 (LeetCode)
        System.out.println(s.countPaths(2, new int[][]{{1, 0, 10}})); // Expected: 1
        // diamond: 0->1->3 aur 0->2->3, dono time 2
        System.out.println(s.countPaths(4, new int[][]{{0, 1, 1}, {0, 2, 1}, {1, 3, 1}, {2, 3, 1}})); // Expected: 2
        // seedha raasta hi ekmatra shortest
        System.out.println(s.countPaths(3, new int[][]{{0, 1, 5}, {1, 2, 5}, {0, 2, 20}})); // Expected: 1
        // do barabar raaste: seedha 0->2 (10) aur 0->1->2 (5+5=10)
        System.out.println(s.countPaths(3, new int[][]{{0, 1, 5}, {1, 2, 5}, {0, 2, 10}})); // Expected: 2
    }
}
