// Day 15 - Problem 4: Cheapest Flights Within K Stops (LC 787) - Medium
// Topic: Queue (level-wise BFS / frontier expansion)
//
// PROBLEM (Hinglish):
// n cities aur flights (from, to, price) diye hain. src se dst tak
// aisi flight lo jisme AT MOST k stops hon (matlab at most k+1
// flights). Sabse sasti price return karo; aisa route na ho to -1.
// Example: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],
// [2,3,200]], src = 0, dst = 3, k = 1 -> 700 (0 -> 1 -> 3)
//
// APPROACH (Hinglish):
// BFS ko level-wise chalao - har level ek flight hai. Frontier =
// woh nodes jo ab tak ke flights se pahunche ja sakte hain. Har
// level me dist ka SNAPSHOT lo: nayi prices sirf snapshot se
// nikalo, taaki usi level me mili sasti price turant agle edge
// me use na ho (warna stops ki limit toot jayegi). k+1 levels
// tak expand karo. Aakhir me dist[dst] hi jawab hai, warna -1.
//
// TIME COMPLEXITY: O(k * E) - k+1 levels, har level me edges scan
// SPACE COMPLEXITY: O(V + E) - adjacency list + distance + frontier
//
// KEY LEARNING:
// Stops ki limit lagani ho to plain Dijkstra kaafi nahi. Aur
// level-wise BFS me snapshot lena ZAROORI hai: bina snapshot ke
// ek hi level ke andar do edges jud jayenge aur stops ka count
// galat ho jayega.

import java.util.*;

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] f : flights) {
            adj.get(f[0]).add(new int[]{f[1], f[2]});
        }
        int INF = Integer.MAX_VALUE;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        // frontier = is level me expand hone wale nodes
        Queue<Integer> frontier = new LinkedList<>();
        frontier.offer(src);
        // k stops = k+1 flights, isliye k+1 levels
        for (int level = 0; level <= k; level++) {
            // snapshot: isi level ki shuruaat wali dist
            int[] old = dist.clone();
            Queue<Integer> nxt = new LinkedList<>();
            boolean[] seen = new boolean[n];
            int size = frontier.size();
            for (int i = 0; i < size; i++) {
                int u = frontier.poll();
                if (old[u] == INF) {
                    continue;
                }
                for (int[] e : adj.get(u)) {
                    int v = e[0], w = e[1];
                    if (old[u] + w < dist[v]) {
                        dist[v] = old[u] + w;
                        if (!seen[v]) {
                            seen[v] = true;
                            nxt.offer(v);
                        }
                    }
                }
            }
            frontier = nxt;
            if (frontier.isEmpty()) {
                break;
            }
        }
        return dist[dst] == INF ? -1 : dist[dst];
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] flights = {{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}};
        System.out.println(s.findCheapestPrice(4, flights, 0, 3, 1)); // Expected: 700
        int[][] f2 = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        System.out.println(s.findCheapestPrice(3, f2, 0, 2, 1)); // Expected: 200
        System.out.println(s.findCheapestPrice(3, f2, 0, 2, 0)); // Expected: 500
    }
}
