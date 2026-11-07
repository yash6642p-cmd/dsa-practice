// Day 18 - Problem 3: Possible Bipartition (LC 886) - Medium
// Topic: Queue (BFS graph coloring - do groups me baanto, dushman ek group me nahi)
//
// PROBLEM (Hinglish):
// n log hain (1 se n tak). `dislikes` me pairs diye hain:
// [a, b] ka matlab a aur b ek dusre ko pasand NAHI karte.
// Kya in n logo ko DO groups me baanta ja sakta hai, taaki
// koi bhi dislike pair EK HI group me na aaye?
// Agar possible hai to true, nahi to false return karo.
// Example 1: n = 4, dislikes = [[1,2],[1,3],[2,4]] -> true
// Example 2: n = 3, dislikes = [[1,2],[1,3],[2,3]] -> false
//   (triangle hai - 2 groups me nahi baant sakte)
//
// APPROACH (Hinglish):
// Ye graph ko 2-COLOR karne wala problem hai (bipartite check).
// Har person ek node, dislike ek edge. BFS se har node ko 2 me se
// ek color do (0 ya 1). Neighbour ko hamesha ULTA color do. Agar
// kabhi kisi node ka neighbour USI color ka mil jaaye, to baantna
// impossible hai - false. Graph disconnected bhi ho sakta hai,
// isliye har unvisited node se BFS start karo.
//
// TIME COMPLEXITY: O(n + m) - n nodes, m dislike edges, har
//   node/edge ek baar visit hoti hai
// SPACE COMPLEXITY: O(n + m) - adjacency list + color array + queue
//
// KEY LEARNING:
// "Do groups me baanto, dushman saath nahi" = Bipartite Graph.
// BFS coloring se check karo: neighbour ko hamesha ulta color.
// Yehi pattern Is Graph Bipartite? (LC 785, Day 13) me bhi tha -
// wahan 0/1 adjacency matrix thi, yahan dislike edge list hai.

import java.util.*;

class Solution {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        // adjacency list banao (1-indexed log)
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for (int[] d : dislikes) {
            graph.get(d[0]).add(d[1]);
            graph.get(d[1]).add(d[0]);
        }

        // color[i] = 0/1 (group), -1 = abhi color nahi mila
        int[] color = new int[n + 1];
        Arrays.fill(color, -1);

        // graph disconnected ho sakta hai - har node se BFS try karo
        for (int start = 1; start <= n; start++) {
            if (color[start] != -1) continue;
            color[start] = 0;
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            while (!q.isEmpty()) {
                int node = q.poll();
                for (int nei : graph.get(node)) {
                    if (color[nei] == -1) {
                        // neighbour ko ulta color do
                        color[nei] = 1 - color[node];
                        q.offer(nei);
                    } else if (color[nei] == color[node]) {
                        // dushman ek hi group me - baantna impossible
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1: group {1,4} aur {2,3} me baant sakte hain
        System.out.println(s.possibleBipartition(4, new int[][]{{1, 2}, {1, 3}, {2, 4}})); // Expected: true
        // Example 2: triangle - impossible
        System.out.println(s.possibleBipartition(3, new int[][]{{1, 2}, {1, 3}, {2, 3}})); // Expected: false
        // 5-cycle (odd cycle) - impossible
        System.out.println(s.possibleBipartition(5, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}})); // Expected: false
        // koi dislike hi nahi - hamesha possible
        System.out.println(s.possibleBipartition(5, new int[][]{})); // Expected: true
        // 4-cycle (even cycle) - possible
        System.out.println(s.possibleBipartition(4, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 1}})); // Expected: true
    }
}
