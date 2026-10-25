// Day 4 - Problem 1: Average of Levels in Binary Tree (LC 637) - Easy
// ============================================================
// PROBLEM (Hinglish):
// Ek binary tree diya hai. Har level ke nodes ka average nikaalo,
// aur averages ki list return karo (level 0 se shuru karke).
// Example: [3,9,20,15,7] -> [3.0, 14.5, 11.0]
//
// APPROACH (Hinglish):
// Queue se BFS karo. Har level ke liye queue ka current size lo,
// utne nodes pop karo, sum jodo, children queue me daalo,
// phir average = sum / count.
// Queue FIFO order me level-wise traversal deta hai, isliye perfect hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - w = sabse chaude level ki width
//
// KEY LEARNING:
// Level-wise kaam = BFS = Queue. Level boundary ke liye har baar
// queue ka size snapshot lo, usi ko loop limit banao.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelSize = q.size(); // is level me kitne nodes hain
            long levelSum = 0;
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                levelSum += node.val;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            result.add(levelSum / (double) levelSize);
        }
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
        System.out.println(new Solution().averageOfLevels(root));
        // Expected: [3.0, 14.5, 11.0]
    }
}
