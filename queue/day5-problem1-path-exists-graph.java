// Day 5 - Problem 1: Find if Path Exists in Graph (LC 1971) - Easy
// ============================================================
// PROBLEM (Hinglish):
// Ek bi-directional graph diya hai: n vertices (0 se n-1) aur edges ki list.
// Batao kya source se destination tak koi valid path hai? True/False do.
// Example: n=3, edges=[[0,1],[1,2],[2,0]], source=0, destination=2 -> True
//
// APPROACH (Hinglish):
// 1. Adjacency list banao: edge [u, v] ke liye dono taraf neighbour daalo.
// 2. Source se BFS: queue me source daalo, visited[] me mark karo.
// 3. Node nikaalo, destination mila to True; warna unvisited neighbours
//    ko queue me daalo. Queue khatam ho to False.
// Visited ke bina cycle me infinite loop lag jayega, isliye zaroori hai.
//
// TIME COMPLEXITY: O(V + E) - har vertex aur edge ek baar
// SPACE COMPLEXITY: O(V + E) - adjacency list + queue + visited
//
// KEY LEARNING:
// Graph par BFS = queue + visited. Reachability ke sawaal me
// destination milte hi return kar do, poora graph ghoomne ki zaroorat nahi.

import java.util.*;

class Solution {
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        // Step 1: adjacency list (dono taraf, kyunki undirected graph hai)
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        // Step 2: BFS
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];
        q.offer(source);
        visited[source] = true;
        while (!q.isEmpty()) {
            int node = q.poll();
            if (node == destination) return true; // destination mil gaya
            for (int neighbour : graph.get(node)) {
                if (!visited[neighbour]) {
                    visited[neighbour] = true;
                    q.offer(neighbour);
                }
            }
        }
        return false;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.validPath(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}, 0, 2));
        // Expected: true
        System.out.println(s.validPath(6, new int[][]{{0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3}}, 0, 5));
        // Expected: false
        System.out.println(s.validPath(1, new int[][]{}, 0, 0));
        // Expected: true
    }
}
