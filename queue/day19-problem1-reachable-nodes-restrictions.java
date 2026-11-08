// Day 19 - Problem 1: Reachable Nodes With Restrictions (LC 2368) - Medium
// Topic: Queue (BFS - tree me 0 se kitne nodes tak pahunch sakte ho)
//
// PROBLEM (Hinglish):
// `n` nodes wala undirected tree hai (nodes 0 se n-1). `edges` me
// tree ke edges hain. Kuch nodes `restricted` array me hain - un
// nodes par jaana MANA hai. Node 0 se start karke, bina kisi
// restricted node par jaaye, maximum kitne nodes tak pahunch
// sakte ho? Count return karo. Node 0 kabhi restricted nahi hota.
// Example: n = 7, edges = [[0,1],[1,2],[3,1],[4,0],[0,5],[5,6]],
// restricted = [4,5] -> output 4 (nodes 0,1,2,3)
//
// APPROACH (Hinglish):
// Tree ko adjacency list banao. Restricted nodes ko ek set me
// daalo (O(1) check ke liye). Node 0 se BFS chalao (queue use
// karo): har node ke neighbours dekho, jo na restricted ho aur
// na visited ho, use queue me daalo aur count badhao.
// Restricted node aate hi us taraf jaana band - uske aage ka
// subtree bhi block ho jaata hai.
//
// TIME COMPLEXITY: O(n) - har node aur edge ek baar visit hota hai
// SPACE COMPLEXITY: O(n) - adjacency list + visited set + queue
//
// KEY LEARNING:
// "Restricted/blocked nodes se bachke traversal" - BFS/DFS me
// sirf ek extra condition lagti hai: neighbour ko queue me
// daalne se pehle check karo ki wo restricted to nahi. Baaki
// traversal bilkul normal rehta hai.

import java.util.*;

class Solution {
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        // adjacency list banao
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        // restricted nodes ka set (fast check ke liye)
        Set<Integer> blocked = new HashSet<>();
        for (int r : restricted) {
            blocked.add(r);
        }

        // BFS node 0 se
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        int count = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            count++;
            for (int nb : graph.get(node)) {
                // restricted ya visited node ko skip karo
                if (!blocked.contains(nb) && !visited.contains(nb)) {
                    visited.add(nb);
                    queue.offer(nb);
                }
            }
        }

        return count;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.reachableNodes(7, new int[][]{{0,1},{1,2},{3,1},{4,0},{0,5},{5,6}}, new int[]{4,5})); // Expected: 4
        // Example 2
        System.out.println(s.reachableNodes(7, new int[][]{{0,1},{0,2},{0,5},{0,4},{3,2},{6,5}}, new int[]{4,2,1})); // Expected: 3
        // koi restriction nahi - saare nodes reachable
        System.out.println(s.reachableNodes(3, new int[][]{{0,1},{1,2}}, new int[]{})); // Expected: 3
        // node 1 restricted - sirf 0 reachable
        System.out.println(s.reachableNodes(3, new int[][]{{0,1},{1,2}}, new int[]{1})); // Expected: 1
    }
}
