// Day 7 - Problem 1: Minimum Depth of Binary Tree (LC 111) - Easy
// ============================================================
// PROBLEM (Hinglish):
// Binary tree ka minimum depth: root se sabse nazdeek leaf tak kitne
// nodes hain? Leaf = na left ho na right.
// Example: [3,9,20,null,null,15,7] -> 2 (3 -> 9)
//
// APPROACH (Hinglish):
// BFS level by level: queue me node + depth rakho, pehla leaf milte
// hi uski depth return kar do - wahi minimum hogi. Sirf ek child
// wale node ko leaf mat samajhna.
//
// TIME COMPLEXITY: O(n)
// SPACE COMPLEXITY: O(w) - ek level ke nodes
//
// KEY LEARNING:
// "Minimum / shortest" + tree/graph = BFS, pehla leaf hi answer hai.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int depth = 1;
        while (!q.isEmpty()) {
            int size = q.size(); // is level me kitne nodes hain
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left == null && node.right == null) {
                    return depth; // pehla leaf = minimum depth
                }
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            depth++;
        }
        return depth;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(new Solution().minDepth(root)); // Expected: 2
        System.out.println(new Solution().minDepth(null)); // Expected: 0
        System.out.println(new Solution().minDepth(new TreeNode(1))); // Expected: 1
    }
}
