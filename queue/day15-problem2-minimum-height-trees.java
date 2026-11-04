// Day 15 - Problem 2: Minimum Height Trees (LC 310) - Medium
// Topic: Queue (leaf-trimming / topological BFS)
//
// PROBLEM (Hinglish):
// Ek tree diya hai (n nodes, n-1 edges). Aisa root chuno jisse tree
// ki height minimum ho. Saare aise roots return karo.
// Example: n = 4, edges = [[1,0],[1,2],[1,3]] -> [1]
// Example: n = 6, edges = [[3,0],[3,1],[3,2],[3,4],[5,4]] -> [3,4]
//
// APPROACH (Hinglish):
// Tree ko bahar se andar chheelo: leaves (degree 1) ko queue me
// daalo aur ek layer karke hatao. Har leaf hatane par neighbour
// ki degree kam hoti hai; neighbour leaf ban jaye to queue me
// daalo. Aakhir me jo 1 ya 2 nodes bachenge, wahi centers hain -
// aur wahi minimum height roots hain.
//
// TIME COMPLEXITY: O(n) - har node aur edge ek baar process hoti hai
// SPACE COMPLEXITY: O(n) - adjacency list + queue
//
// KEY LEARNING:
// Minimum height root hamesha tree ke center par hota hai, aur
// center nikalne ka sabse seedha tareeka leaf-trimming BFS hai.

import java.util.*;

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) {
            return Arrays.asList(0);
        }
        // adjacency list + degree
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        int[] degree = new int[n];
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
            degree[e[0]]++;
            degree[e[1]]++;
        }
        // shuru me saare leaves queue me
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                q.offer(i);
            }
        }
        int remaining = n;
        while (remaining > 2) {
            // ek layer ke saare leaves ek saath hatao
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int leaf = q.poll();
                remaining--;
                for (int nb : adj.get(leaf)) {
                    degree[nb]--;
                    if (degree[nb] == 1) {
                        q.offer(nb);
                    }
                }
            }
        }
        // queue me jo bache, wahi centers hain
        return new ArrayList<>(q);
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findMinHeightTrees(4,
            new int[][]{{1, 0}, {1, 2}, {1, 3}})); // Expected: [1]
        System.out.println(s.findMinHeightTrees(6,
            new int[][]{{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}})); // Expected: [3, 4]
        System.out.println(s.findMinHeightTrees(1, new int[][]{})); // Expected: [0]
    }
}
