// Day 20 - Problem 3: Graph Valid Tree (LC 261) - Medium
// Topic: Queue (BFS - tree check: connected + no cycle)
//
// PROBLEM (Hinglish):
// Tumhe `n` nodes (0 se n-1) aur undirected `edges` diye hain.
// Batao kya ye edges ek VALID TREE banate hain? True/False return karo.
// Valid tree matlab: (1) saare nodes connected hon, (2) koi cycle na ho.
// Example: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]] -> True
// Example: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]] -> False (cycle hai)
//
// APPROACH (Hinglish):
// Tree ki ek simple pehchan: n nodes ke tree me EXACTLY n-1 edges hote
// hain. Pehle ye check karo - edges != n-1 ho to seedha False.
// Phir node 0 se BFS chalao: parent track karo. Agar koi neighbour
// pehle se visited hai aur wo parent nahi hai, to cycle hai -> False.
// Ant me saare nodes visited hone chahiye (connected check).
//
// TIME COMPLEXITY: O(n) - har node aur edge ek baar visit hota hai
// SPACE COMPLEXITY: O(n) - adjacency list + visited + queue
//
// KEY LEARNING:
// "Tree = connected + acyclic" - dono conditions alag se check karni
// hoti hain. Edge-count trick (n-1) ek condition ko shortcut me badal
// deta hai: n-1 edges + no cycle = connected automatically.

import java.util.*;

class Solution {
    public boolean validTree(int n, int[][] edges) {
        // tree me exactly n-1 edges hote hain - pehla fast check
        if (edges.length != n - 1) {
            return false;
        }

        // adjacency list banao
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        // BFS node 0 se, parent track karo (cycle pakadne ke liye)
        boolean[] visited = new boolean[n];
        visited[0] = true;
        Queue<int[]> queue = new LinkedList<>(); // {node, parent}
        queue.offer(new int[]{0, -1});
        int visitedCount = 1;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int node = cur[0];
            int parent = cur[1];
            for (int nb : graph.get(node)) {
                if (nb == parent) {
                    continue; // jahan se aaye, wapas mat jao
                }
                if (visited[nb]) {
                    return false; // cycle mil gaya!
                }
                visited[nb] = true;
                visitedCount++;
                queue.offer(new int[]{nb, node});
            }
        }

        // saare nodes visited hone chahiye (connected check)
        return visitedCount == n;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 - valid tree
        System.out.println(s.validTree(5, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 4}})); // Expected: true
        // Example 2 - cycle hai (1-2-3-1)
        System.out.println(s.validTree(5, new int[][]{{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}})); // Expected: false
        // disconnected - edges kam hain
        System.out.println(s.validTree(4, new int[][]{{0, 1}, {2, 3}})); // Expected: false
        // sirf 1 node, 0 edges - valid tree
        System.out.println(s.validTree(1, new int[][]{})); // Expected: true
    }
}
