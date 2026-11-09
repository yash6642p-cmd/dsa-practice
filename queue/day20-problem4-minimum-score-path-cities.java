// Day 20 - Problem 4: Minimum Score of a Path Between Two Cities (LC 2492) - Medium
// Topic: Queue (BFS - connected component ka sabse chhota edge)
//
// PROBLEM (Hinglish):
// `n` cities (1 se n) hain aur `roads` me bidirectional roads hain,
// har road ki ek distance hai: roads[i] = [ai, bi, distance].
// Path ka SCORE = us path ke roads ki sabse CHHOTI distance.
// City 1 se city n tak jaane wale kisi bhi path ka MINIMUM possible
// score return karo. (Roads dobara use kar sakte ho, cities revisit
// kar sakte ho.)
// Example: n = 4, roads = [[1,2,9],[2,3,6],[2,4,5],[1,4,7]] -> 5
// (path 1->2->4 ka score min(9,5) = 5)
//
// APPROACH (Hinglish):
// Trick yeh hai: roads dobara use kar sakte ho, toh city 1 ke connected
// component ke andar ka KOI BHI edge apne path me la sakte ho. Isliye
// answer = city 1 ke component ka sabse chhota edge weight.
// City 1 se BFS chalao, saare reachable edges dekho, minimum track karo.
// Shortest path nikalne ki zaroorat hi nahi - sirf component chahiye!
//
// TIME COMPLEXITY: O(n + m) - BFS: har city aur road ek baar
// SPACE COMPLEXITY: O(n + m) - adjacency list + visited + queue
//
// KEY LEARNING:
// "Problem ko dobara padho - constraint hi answer hai" - revisit
// allowed hone se shortest path ka sawal hi khatam ho gaya, sirf
// component ka minimum edge chahiye. Aise hidden simplification ko
// pehchan-na interview me bahut kaam aata hai.

import java.util.*;

class Solution {
    public int minScore(int n, int[][] roads) {
        // adjacency list: node -> list of {neighbour, distance}
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] r : roads) {
            graph.get(r[0]).add(new int[]{r[1], r[2]});
            graph.get(r[1]).add(new int[]{r[0], r[2]});
        }

        // BFS city 1 se - poora component ghumo
        boolean[] visited = new boolean[n + 1];
        visited[1] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        int ans = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int[] edge : graph.get(node)) {
                int nb = edge[0];
                int d = edge[1];
                // component ka sabse chhota edge track karo
                ans = Math.min(ans, d);
                if (!visited[nb]) {
                    visited[nb] = true;
                    queue.offer(nb);
                }
            }
        }

        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.minScore(4, new int[][]{{1, 2, 9}, {2, 3, 6}, {2, 4, 5}, {1, 4, 7}})); // Expected: 5
        // Example 2 - revisit se 2 wala edge bhi use ho sakta hai
        System.out.println(s.minScore(4, new int[][]{{1, 2, 2}, {1, 3, 4}, {3, 4, 7}})); // Expected: 2
        // seedha ek hi road
        System.out.println(s.minScore(2, new int[][]{{1, 2, 10}})); // Expected: 10
    }
}
