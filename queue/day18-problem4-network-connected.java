// Day 18 - Problem 4: Number of Operations to Make Network Connected (LC 1319) - Medium
// Topic: Queue (BFS - kitne alag-alag groups (components) hain, gin lo)
//
// PROBLEM (Hinglish):
// n computers hain (0 se n-1) aur `connections` me cables di hain:
// [a, b] ka matlab computer a aur b ke beech cable hai.
// Ek operation me tum ek cable UKHAAD kar kisi bhi do
// disconnected computers ke beech LAGA sakte ho.
// Minimum kitne operations me saare computers ek network me
// jud jaayenge? Agar possible nahi hai to -1 return karo.
// Example 1: n = 4, connections = [[0,1],[0,2],[1,2]] -> 1
//   (1-2 wali extra cable ukhaad kar 1-3 se jod do)
// Example 2: n = 6, connections = [[0,1],[0,2],[0,3],[1,2]] -> -1
//   (sirf 4 cables hain, 6 computers jodne ke liye 5 chahiye)
//
// APPROACH (Hinglish):
// Pehli baat: n computers ko ek network me jodne ke liye kam se
// kam (n - 1) cables chahiye. Agar cables kam hain to -1.
// Phir BFS se connected COMPONENTS gino: ek component = computers
// ka group jo aapas me jude hain. k components ko jodne ke liye
// exactly (k - 1) operations chahiye (har operation 2 groups ko
// jodta hai). To answer = components - 1.
//
// TIME COMPLEXITY: O(n + m) - n computers, m cables, BFS me har
//   node/edge ek baar visit hoti hai
// SPACE COMPLEXITY: O(n + m) - adjacency list + visited + queue
//
// KEY LEARNING:
// "Kitne groups hain" ginna = connected components ginna, jo BFS
// ya DFS se hota hai. n nodes jodne ke liye n-1 edges chahiye -
// ye spanning tree ka basic rule hai, aur aise cable/connection
// wale problems me pehla check yehi hota hai.

import java.util.*;

class Solution {
    public int makeConnected(int n, int[][] connections) {
        // n computers jodne ke liye kam se kam n-1 cables chahiye
        if (connections.length < n - 1) {
            return -1;
        }

        // adjacency list banao
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        for (int[] c : connections) {
            graph.get(c[0]).add(c[1]);
            graph.get(c[1]).add(c[0]);
        }

        boolean[] visited = new boolean[n];
        int components = 0;

        // har unvisited computer se BFS - ek BFS = ek component
        for (int start = 0; start < n; start++) {
            if (visited[start]) continue;
            components++;
            visited[start] = true;
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            while (!q.isEmpty()) {
                int node = q.poll();
                for (int nei : graph.get(node)) {
                    if (!visited[nei]) {
                        visited[nei] = true;
                        q.offer(nei);
                    }
                }
            }
        }

        // k components jodne ke liye (k - 1) operations chahiye
        return components - 1;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.makeConnected(4, new int[][]{{0, 1}, {0, 2}, {1, 2}})); // Expected: 1
        // Example 2 (LeetCode): 2 operations
        System.out.println(s.makeConnected(6, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}})); // Expected: 2
        // cables kam hain - impossible
        System.out.println(s.makeConnected(6, new int[][]{{0, 1}, {0, 2}, {0, 3}, {1, 2}})); // Expected: -1
        // pehle se hi connected
        System.out.println(s.makeConnected(3, new int[][]{{0, 1}, {1, 2}})); // Expected: 0
        // single computer - kuch jodna hi nahi
        System.out.println(s.makeConnected(1, new int[][]{})); // Expected: 0
    }
}
