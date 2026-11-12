// Day 23 - Problem 5: Minimum Number of Operations to Sort a Binary
// Tree by Level (LC 2471) - Medium
// Topic: Queue (BFS level order + minimum swaps)
//
// PROBLEM (Hinglish):
// Binary tree diya hai, saare values UNIQUE hain. Ek operation me
// SAME level ke kisi bhi do nodes ke values swap kar sakte ho.
// Minimum kitne operations chahiye taaki HAR level ke values
// strictly increasing order me aa jaayein? Wo count return karo.
// Example 1: root = [1,4,3,7,6,8,5,null,null,null,null,9,null,10]
// -> output 3
// Example 2: root = [1,3,2,7,6,5,4] -> output 3
// Example 3: root = [1,2,3,4,5,6] -> output 0 (pehle se sorted)
//
// APPROACH (Hinglish):
// 1) BFS (level order) se har level ke values alag-alag collect karo.
// 2) Har level ke liye "minimum swaps to sort array" nikalo: sorted
//    order me har value ki sahi position (rank) nikalo, phir cycle
//    decomposition se gino - ek cycle of length L ko sort karne me
//    L-1 swaps lagte hain. Saare levels ke swaps jod do.
//
// TIME COMPLEXITY: O(n log n) - BFS O(n) + har level sort O(L log L)
// SPACE COMPLEXITY: O(n) - queue + level arrays
//
// KEY LEARNING:
// "Minimum swaps to sort" = cycle decomposition: cycle length L ho
// to L-1 swaps. Ye trick kai problems me kaam aata hai. BFS se level
// alag karna + per-level ye trick = poora solution.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution {
    public int minimumOperations(TreeNode root) {
        int total = 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelSize = q.size();
            List<Integer> vals = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                vals.add(node.val);
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            // is level ko sort karne ke minimum swaps jodo
            total += minSwapsToSort(vals);
        }
        return total;
    }

    // array ko sort karne ke minimum swaps (cycle decomposition)
    private int minSwapsToSort(List<Integer> arr) {
        int n = arr.size();
        List<Integer> sorted = new ArrayList<>(arr);
        Collections.sort(sorted);
        // value -> sorted order me uski position
        Map<Integer, Integer> pos = new HashMap<>();
        for (int i = 0; i < n; i++) {
            pos.put(sorted.get(i), i);
        }
        // perm[i] = arr[i] ki sahi position
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = pos.get(arr.get(i));
        }
        boolean[] visited = new boolean[n];
        int swaps = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i] || perm[i] == i) {
                continue;
            }
            // ek cycle ghumo
            int cycleLen = 0;
            int j = i;
            while (!visited[j]) {
                visited[j] = true;
                j = perm[j];
                cycleLen++;
            }
            // L length wale cycle ko sort karne me L-1 swaps
            swaps += cycleLen - 1;
        }
        return swaps;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        TreeNode r1 = new TreeNode(1,
            new TreeNode(4,
                new TreeNode(7),
                new TreeNode(6)),
            new TreeNode(3,
                new TreeNode(8, new TreeNode(9), null),
                new TreeNode(5, null, new TreeNode(10))));
        System.out.println(s.minimumOperations(r1)); // Expected: 3
        // Example 2 (LeetCode)
        TreeNode r2 = new TreeNode(1,
            new TreeNode(3,
                new TreeNode(7),
                new TreeNode(6)),
            new TreeNode(2,
                new TreeNode(5),
                new TreeNode(4)));
        System.out.println(s.minimumOperations(r2)); // Expected: 3
        // Example 3 (LeetCode) - pehle se sorted
        TreeNode r3 = new TreeNode(1,
            new TreeNode(2,
                new TreeNode(4),
                new TreeNode(5)),
            new TreeNode(3,
                new TreeNode(6),
                null));
        System.out.println(s.minimumOperations(r3)); // Expected: 0
        // single node
        TreeNode r4 = new TreeNode(1);
        System.out.println(s.minimumOperations(r4)); // Expected: 0
        // level 1 unsorted: [5,4,3] -> 1 swap
        TreeNode r5 = new TreeNode(5,
            new TreeNode(4),
            new TreeNode(3));
        System.out.println(s.minimumOperations(r5)); // Expected: 1
    }
}
