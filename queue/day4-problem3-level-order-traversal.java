// Day 4 - Problem 3: Binary Tree Level Order Traversal (LC 102) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Binary tree ka level-order traversal return karo, har level
// ek alag list me: [[3],[9,20],[15,7]]
//
// APPROACH (Hinglish):
// Queue se BFS. Har level ke liye queue ka size snapshot lo,
// utne nodes pop karo, values level-list me daalo, children queue
// me daalo, phir level-list result me daalo.
// Snapshot zaroori hai kyunki loop ke andar queue badh raha hai
// (children add ho rahe hain) - bina snapshot ke levels mix ho jayenge.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - w = sabse chaude level ki width + result
//
// KEY LEARNING:
// BFS ka standard pattern: queue + level-size snapshot.
// Yehi pattern Problem 1 me bhi tha, bas wahan average nikaala tha.
// Interviews me ye sabse zyada poochha jaane wala tree pattern hai.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelSize = q.size(); // snapshot: is level ke nodes
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            result.add(level);
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
        System.out.println(new Solution().levelOrder(root));
        // Expected: [[3], [9, 20], [15, 7]]
    }
}
