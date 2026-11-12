// Day 23 - Problem 1: Evaluate Boolean Binary Tree (LC 2331) - Easy
// Topic: Queue (tree ko level order / BFS soch se samjho, evaluate neeche se upar)
//
// PROBLEM (Hinglish):
// Ek FULL binary tree diya hai. Leaf nodes me 0 (False) ya 1 (True)
// hota hai. Non-leaf nodes me 2 (OR) ya 3 (AND) hota hai. Har node
// ki evaluation: leaf ho to uska value hi answer, warna dono bachcho
// ko evaluate karo aur node ke operation (OR/AND) se jodo. Root ki
// evaluation return karo.
// Example: root = [2,1,3,null,null,0,1] -> output true
// Example: root = [0] -> output false
//
// APPROACH (Hinglish):
// Neeche se upar evaluate karna hai - isliye recursion natural hai.
// Leaf check karo: val 0 ya 1 ho to seedha true/false. Warna dono
// bachche evaluate karo; val 2 ho to OR, 3 ho to AND.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(h) - recursion stack, tree ki height (worst O(n))
//
// KEY LEARNING:
// Tree evaluation hamesha bottom-up hoti hai - pehle bachche, phir
// parent. Operator ko number (2=OR, 3=AND) se encode karna common
// interview trick hai.

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
    public boolean evaluateTree(TreeNode root) {
        // leaf node: value hi boolean hai (0 = false, 1 = true)
        if (root.val < 2) {
            return root.val == 1;
        }
        // pehle dono bachche evaluate karo
        boolean left = evaluateTree(root.left);
        boolean right = evaluateTree(root.right);
        // 2 = OR, 3 = AND
        if (root.val == 2) {
            return left || right;
        }
        return left && right;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        TreeNode r1 = new TreeNode(2,
            new TreeNode(1),
            new TreeNode(3, new TreeNode(0), new TreeNode(1)));
        System.out.println(s.evaluateTree(r1)); // Expected: true
        // Example 2 (LeetCode)
        TreeNode r2 = new TreeNode(0);
        System.out.println(s.evaluateTree(r2)); // Expected: false
        // AND root, dono true
        TreeNode r3 = new TreeNode(3, new TreeNode(1), new TreeNode(1));
        System.out.println(s.evaluateTree(r3)); // Expected: true
        // OR root, dono false
        TreeNode r4 = new TreeNode(2, new TreeNode(0), new TreeNode(0));
        System.out.println(s.evaluateTree(r4)); // Expected: false
        // single true leaf
        TreeNode r5 = new TreeNode(1);
        System.out.println(s.evaluateTree(r5)); // Expected: true
    }
}
