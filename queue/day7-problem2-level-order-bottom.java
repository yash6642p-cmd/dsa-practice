// Day 7 - Problem 2: Binary Tree Level Order Traversal II (LC 107) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Level order do, par ulta: sabse neeche wale level se root tak,
// har level me left-to-right order.
// Example: [3,9,20,null,null,15,7] -> [[15,7],[9,20],[3]]
//
// APPROACH (Hinglish):
// Normal level-order BFS (LC 102 jaisa), aakhir me result ko
// reverse kar do. Har level ke liye queue ka size snapshot lo.
//
// TIME COMPLEXITY: O(n)
// SPACE COMPLEXITY: O(n) - queue + result
//
// KEY LEARNING:
// BFS skeleton same, sirf result jodne ka tareeka badalta hai -
// yahan ek reverse ne poora problem badal diya.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            result.add(level);
        }
        Collections.reverse(result); // neeche se upar
        return result;
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
        System.out.println(new Solution().levelOrderBottom(root));
        // Expected: [[15, 7], [9, 20], [3]]
        System.out.println(new Solution().levelOrderBottom(null)); // Expected: []
    }
}
