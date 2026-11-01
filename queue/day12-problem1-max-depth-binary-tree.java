// Day 12 - Problem 1: Maximum Depth of Binary Tree (LC 104) - Easy
// Topic: Queue (BFS / level order)
//
// PROBLEM (Hinglish):
// Ek binary tree ka root diya hai. Uski maximum depth return karo.
// Maximum depth = root se sabse door wale leaf tak nodes ki sankhya.
// Example: root = [3,9,20,null,null,15,7] -> output 3
//
// APPROACH (Hinglish):
// Queue me root daalo aur level-by-level traverse karo. Har level poora
// process karne ke baad depth me 1 jodo. Jab queue khaali ho jaye,
// depth hi maximum depth hai. Empty tree ki depth 0 hoti hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst case O(n))
//
// KEY LEARNING:
// Depth = levels ki sankhya. Level order traversal me har level ko
// alag se ginna seekho - yahi pattern aage ke kaafi tree problems me aayega.

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
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 0;
        while (!q.isEmpty()) {
            // ek level ke saare nodes ek saath process karo
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            depth++; // ek level poora ho gaya
        }
        return depth;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode t1 = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(s.maxDepth(t1)); // Expected: 3
        TreeNode t2 = new TreeNode(1, null, new TreeNode(2));
        System.out.println(s.maxDepth(t2)); // Expected: 2
        System.out.println(s.maxDepth(null)); // Expected: 0
    }
}
