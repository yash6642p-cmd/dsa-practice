// Day 17 - Problem 5: Path with Maximum Probability (LC 1514) - Medium
// Topic: Queue (Priority Queue / Max-Heap - Dijkstra ka ulta version)
//
// PROBLEM (Hinglish):
// n nodes ka undirected graph hai. Har edge [a, b] ke saath ek
// success probability succProb hai. start se end tak woh rasta
// dhoondho jiski TOTAL probability (saare edges ki probability
// ka MULTIPLY) sabse badi ho. Rasta na ho to 0 return karo.
// Example: n = 3, edges = [[0,1],[1,2],[0,2]],
// succProb = [0.5, 0.5, 0.2], start = 0, end = 2 -> 0.25
// (seedha 0->2 = 0.2, par 0->1->2 = 0.5*0.5 = 0.25 bada hai)
//
// APPROACH (Hinglish):
// Ye Dijkstra hai, bas ulta: minimum distance ki jagah MAXIMUM
// probability chahiye. Max-heap (PriorityQueue with ulta
// comparator) me hamesha sabse badi probability wala node pehle
// nikalo. Jab kisi node ko pehli baar heap se nikalo, wahi uski
// best probability hai (probabilities <= 1 hoti hain, aage badhne
// se probability sirf ghatti hai). Padosiyon ki probability =
// current * edgeProb se update karo. end milte hi return karo.
//
// TIME COMPLEXITY: O(E log V) - har edge heap me ja sakta hai
// SPACE COMPLEXITY: O(V + E) - graph + heap + seen array
//
// KEY LEARNING:
// Dijkstra sirf shortest path ke liye nahi hai - jab bhi "best
// pehle" greedy kaam kare, priority queue wala wahi pattern
// chalta hai. Java me max-heap ke liye comparator ulta karo.

import java.util.*;

class Solution {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {
        // adjacency list: graph[u] = [(v, prob), ...]
        List<List<double[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0], v = edges[i][1];
            graph.get(u).add(new double[]{v, succProb[i]});
            graph.get(v).add(new double[]{u, succProb[i]});
        }
        // max-heap: sabse badi probability wala node pehle niklega
        Queue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[0], a[0]));
        pq.offer(new double[]{1.0, start});
        boolean[] seen = new boolean[n];
        while (!pq.isEmpty()) {
            double[] cur = pq.poll();
            double prob = cur[0];
            int u = (int) cur[1];
            if (u == end) {
                return prob;
            }
            if (seen[u]) {
                continue;
            }
            seen[u] = true;
            for (double[] edge : graph.get(u)) {
                int v = (int) edge[0];
                double edgeProb = edge[1];
                if (!seen[v]) {
                    pq.offer(new double[]{prob * edgeProb, v});
                }
            }
        }
        return 0.0;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maxProbability(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, new double[]{0.5, 0.5, 0.2}, 0, 2)); // Expected: 0.25
        System.out.println(s.maxProbability(3, new int[][]{{0, 1}, {1, 2}, {0, 2}}, new double[]{0.5, 0.5, 0.3}, 0, 2)); // Expected: 0.3
        System.out.println(s.maxProbability(3, new int[][]{{0, 1}}, new double[]{0.5}, 0, 2)); // Expected: 0.0
        System.out.println(s.maxProbability(5, new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 4}, {0, 4}}, new double[]{0.9, 0.9, 0.9, 0.9, 0.5}, 0, 4)); // Expected: 0.6561
    }
}
