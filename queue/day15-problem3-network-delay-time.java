// Day 15 - Problem 3: Network Delay Time (LC 743) - Medium
// Topic: Queue (priority queue / Dijkstra)
//
// PROBLEM (Hinglish):
// n nodes ka directed network diya hai. times[i] = [ui, vi, wi]
// matlab ui se vi tak signal pahunchne me wi time lagta hai.
// Node k se signal bhejo. Saare nodes tak signal pahunchne ka
// minimum time return karo; agar koi node unreachable ho to -1.
// Example: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2 -> 2
//
// APPROACH (Hinglish):
// Dijkstra: priority queue (min-heap) me (time, node) daalo, sabse
// kam time wala node pehle nikalo aur uske edges se neighbours ka
// time update karo agar kam ho. Aakhir me saare shortest times ka
// MAXIMUM hi jawab hai. Koi node unreachable raha to -1.
//
// TIME COMPLEXITY: O(E log V) - har edge heap me ja sakti hai
// SPACE COMPLEXITY: O(V + E) - adjacency list + distance array + heap
//
// KEY LEARNING:
// BFS sirf unweighted graph me shortest path deta hai. Weights
// aate hi priority queue wala Dijkstra chahiye - hamesha sabse
// kam time wale node ko pehle expand karo.

import java.util.*;

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // adjacency list: node -> list of [neighbour, time]
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] t : times) {
            adj.get(t[0]).add(new int[]{t[1], t[2]});
        }
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        // min-heap: [time, node]
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        heap.offer(new int[]{0, k});
        while (!heap.isEmpty()) {
            int[] cur = heap.poll();
            int t = cur[0], u = cur[1];
            // purana entry ho to skip karo
            if (t > dist[u]) {
                continue;
            }
            for (int[] e : adj.get(u)) {
                int v = e[0], w = e[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    heap.offer(new int[]{dist[v], v});
                }
            }
        }
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            ans = Math.max(ans, dist[i]);
        }
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.networkDelayTime(
            new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2)); // Expected: 2
        System.out.println(s.networkDelayTime(
            new int[][]{{1, 2, 1}}, 2, 1)); // Expected: 1
        System.out.println(s.networkDelayTime(
            new int[][]{{1, 2, 1}}, 2, 2)); // Expected: -1
    }
}
