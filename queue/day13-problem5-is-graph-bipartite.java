// Day 13 - Problem 5: Is Graph Bipartite? (LC 785) - Medium
// Topic: Queue (BFS coloring)
//
// PROBLEM (Hinglish):
// Ek undirected graph diya hai (adjacency list form me). Check karo ki
// kya graph bipartite hai - matlab nodes ko do groups A aur B me aise
// baanta ja sakta hai ki har edge A ke node ko B ke node se jode.
// Example: graph = [[1,3],[0,2],[1,3],[0,2]] -> True
// Example: graph = [[1,2,3],[0,2],[0,1,3],[0,2]] -> False
//
// APPROACH (Hinglish):
// Har node ko do colors me se ek do (0 ya 1), BFS se. Ek node ka color
// jo ho, uske saare neighbours ko ulta color do. Agar kabhi kisi
// neighbour ka color current node jaisa mile, to graph bipartite nahi
// hai. Graph disconnected ho sakta hai, isliye har unvisited node se
// BFS shuru karo.
//
// TIME COMPLEXITY: O(V + E) - har node aur edge ek baar visit hoti hai
// SPACE COMPLEXITY: O(V) - color array aur queue ke liye
//
// KEY LEARNING:
// Bipartite check = 2-coloring check. Odd-length cycle (jaise triangle)
// kabhi bipartite nahi hota.

import java.util.*;

class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1); // -1 = abhi color nahi mila
        for (int start = 0; start < n; start++) {
            if (color[start] != -1) continue; // is component ko color mil chuka hai
            color[start] = 0;
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            while (!q.isEmpty()) {
                int node = q.poll();
                for (int nxt : graph[node]) {
                    if (color[nxt] == -1) {
                        color[nxt] = 1 - color[node]; // neighbour ko ulta color do
                        q.offer(nxt);
                    } else if (color[nxt] == color[node]) {
                        return false; // same color ka neighbour = bipartite nahi
                    }
                }
            }
        }
        return true;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isBipartite(new int[][]{{1, 3}, {0, 2}, {1, 3}, {0, 2}})); // Expected: true
        System.out.println(s.isBipartite(new int[][]{{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}})); // Expected: false
        System.out.println(s.isBipartite(new int[][]{{1, 2}, {0, 2}, {0, 1}})); // Expected: false (triangle)
        System.out.println(s.isBipartite(new int[][]{{1}, {0}})); // Expected: true
    }
}
