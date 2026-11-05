// Day 16 - Problem 3: Reorder Routes to Make All Paths Lead to City Zero (LC 1466) - Medium
// Topic: Queue (BFS on undirected graph)
//
// PROBLEM (Hinglish):
// n cities aur n-1 directed roads. connections[i] = [a, b] matlab
// road a se b ki taraf jaati hai. Kam se kam kitni roads palatni
// hongi taaki har city se city 0 tak pahuncha ja sake?
// Example: n = 6, [[0,1],[1,3],[2,3],[4,0],[4,5]] -> 3
//
// APPROACH (Hinglish):
// Graph ko undirected bana ke har edge ke saath cost rakho:
// edge a -> b ke liye a se b jaane ki cost 1 hai (yeh road 0 se
// door ja rahi hai, palatna padega) aur b se a jaane ki cost 0
// hai (pehle se 0 ki taraf). City 0 se BFS karo aur traverse
// kiye edges ki cost jodo - wahi minimum reorders hain.
//
// TIME COMPLEXITY: O(n) - har city aur road ek baar visit hoti hai
// SPACE COMPLEXITY: O(n) - adjacency list + queue + visited
//
// KEY LEARNING:
// Directed edge ko undirected graph me cost ke saath rakhna ek
// powerful trick hai - BFS khud bata deta hai kaunsi edge galat
// direction me hai.

import java.util.*;

class Solution {
    public int minReorder(int n, int[][] connections) {
        // adj[u] = (v, cost) - u se v jaane par cost reorders
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] e : connections) {
            int a = e[0], b = e[1];
            adj.get(a).add(new int[]{b, 1}); // a -> b: 0 se door
            adj.get(b).add(new int[]{a, 0}); // b -> a: 0 ki taraf
        }
        boolean[] visited = new boolean[n];
        visited[0] = true;
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        int reorders = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int[] edge : adj.get(u)) {
                int v = edge[0], cost = edge[1];
                if (!visited[v]) {
                    visited[v] = true;
                    reorders += cost;
                    q.offer(v);
                }
            }
        }
        return reorders;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.minReorder(6, new int[][]{{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}})); // Expected: 3
        System.out.println(s.minReorder(5, new int[][]{{1, 0}, {1, 2}, {3, 2}, {3, 4}})); // Expected: 2
        System.out.println(s.minReorder(3, new int[][]{{1, 0}, {2, 0}})); // Expected: 0
    }
}
