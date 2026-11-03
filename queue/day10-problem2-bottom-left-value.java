// Day 10 - Problem 2: Find Bottom Left Tree Value (LC 513) - Medium
// Topic: Queue (BFS level-order on a binary tree)
//
// PROBLEM (Hinglish):
// Ek binary tree diya hai. Uske sabse neeche wale level (last row)
// ka sabse left wala node ka value nikalna hai.
// Example 1: root = [2,1,3] -> 1
// Example 2: root = [1,2,3,4,null,5,6,null,null,7] -> 7
// (yeh "left child" nahi, "last row ka pehla node" hai!)
// Tree me kam se kam ek node hamesha hoga.
//
// APPROACH (Hinglish):
// Level-order BFS chalao. Har level ka PEHLA node (i == 0 wala)
// ek variable `ans` me save karte jao. Jab BFS khatam hogi, `ans`
// me aakhri level ka pehla node hoga - wahi answer hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me zyada se zyada ek level ke nodes
//
// KEY LEARNING:
// Har level ka pehla node overwrite karte jao - aakhri overwrite
// hi answer hai. Yeh pattern LC 515 (har level ka max) ka bhai hai.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public int findBottomLeftValue(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int ans = root.val;
        while (!q.isEmpty()) {
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                if (i == 0) {
                    ans = node.val; // is level ka leftmost node
                }
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return ans;
    }

    // Level-order list (null = missing node) se tree banao - sirf test ke liye
    static TreeNode buildTree(Integer[] values) {
        if (values.length == 0 || values[0] == null) return null;
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < values.length) {
            TreeNode node = q.poll();
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                q.offer(node.left);
            }
            i++;
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                q.offer(node.right);
            }
            i++;
        }
        return root;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findBottomLeftValue(buildTree(new Integer[]{2, 1, 3})));
        // Expected: 1
        System.out.println(s.findBottomLeftValue(
            buildTree(new Integer[]{1, 2, 3, 4, null, 5, 6, null, null, 7})));
        // Expected: 7
        System.out.println(s.findBottomLeftValue(buildTree(new Integer[]{1})));
        // Expected: 1
    }
}
