// Day 8 - Problem 4: Maximum Level Sum of a Binary Tree (LC 1161) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Root level 1 hai. Sabse chhota level number return karo jiske
// nodes ka sum sabse zyada ho.
// Example: [1,7,0,7,-8,null,null] -> 2 (sums: 1, 7, -1)
//
// APPROACH (Hinglish):
// BFS level by level, har level ka sum jodo, best sum + level
// yaad rakho. Update sirf tab jab sum STRICTLY bada ho (>) -
// tie hone par chhota level hi answer rahega. Level 1 se gino,
// 0 se nahi.
//
// TIME COMPLEXITY: O(n)
// SPACE COMPLEXITY: O(w) - ek level ke nodes

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 1;
        int bestSum = Integer.MIN_VALUE;
        int bestLevel = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            int levelSum = 0;
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                levelSum += node.val;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            if (levelSum > bestSum) { // strictly bada hi update karo
                bestSum = levelSum;
                bestLevel = level;
            }
            level++;
        }
        return bestLevel;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(7);
        root.right = new TreeNode(0);
        root.left.left = new TreeNode(7);
        root.left.right = new TreeNode(-8);
        System.out.println(new Solution().maxLevelSum(root)); // Expected: 2
        TreeNode tie = new TreeNode(2);
        tie.left = new TreeNode(1);
        tie.right = new TreeNode(1);
        System.out.println(new Solution().maxLevelSum(tie)); // Expected: 1 (tie)
    }
}
