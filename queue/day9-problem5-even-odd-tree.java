// Day 9 - Problem 5: Even Odd Tree (LC 1609) - Medium
// Topic: Queue (BFS - level-wise rule check)
//
// PROBLEM (Hinglish):
// Binary tree "Even-Odd" hai agar: root level 0 par hai, aur
// - EVEN level (0, 2, 4...) par saare values ODD hon aur left se
//   right STRICTLY INCREASING hon
// - ODD level (1, 3, 5...) par saare values EVEN hon aur left se
//   right STRICTLY DECREASING hon
// Tree Even-Odd hai to true, warna false.
// Example: root = [1,10,4,3,null,7,9,12,8,6,null,null,2] -> true
//
// APPROACH (Hinglish):
// BFS level by level. Har level ke liye pichhli value (prev) yaad
// rakho: even level par prev = -infinity se shuru, odd level par
// prev = +infinity se. Har node par do check: (1) parity sahi hai?
// (2) order strictly sahi hai? Koi bhi check fail ho to seedha
// false. Saare levels pass ho jaayein to true.
//
// TIME COMPLEXITY: O(n) - har node ek baar
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (w = max width)
//
// KEY LEARNING:
// BFS me sirf traversal nahi, level ke andar ORDER bhi milta hai -
// queue se nodes left-to-right hi nikalte hain, isliye prev se
// compare karke monotonicity check karna ekdum natural hai.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;
        while (!q.isEmpty()) {
            // Even level: odd + increasing, Odd level: even + decreasing
            int prev = (level % 2 == 0) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (level % 2 == 0) {
                    // Odd value + strictly increasing chahiye
                    if (node.val % 2 == 0 || node.val <= prev) return false;
                } else {
                    // Even value + strictly decreasing chahiye
                    if (node.val % 2 == 1 || node.val >= prev) return false;
                }
                prev = node.val;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            level++;
        }
        return true;
    }

    // TEST: level-order list se tree banane ka helper
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

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.isEvenOddTree(buildTree(
            new Integer[]{1,10,4,3,null,7,9,12,8,6,null,null,2}))); // Expected: true
        System.out.println(s.isEvenOddTree(buildTree(
            new Integer[]{5,4,2,3,3,7}))); // Expected: false
        System.out.println(s.isEvenOddTree(buildTree(
            new Integer[]{5,9,1,3,5,7}))); // Expected: false
        System.out.println(s.isEvenOddTree(buildTree(new Integer[]{1}))); // Expected: true
    }
}
