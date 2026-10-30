// Day 9 - Problem 2: Deepest Leaves Sum (LC 1302) - Medium
// Topic: Queue (BFS - level by level)
//
// PROBLEM (Hinglish):
// Binary tree ki sabse gehraai (deepest) wali leaves ka sum nikaalo.
// Leaf = woh node jiske koi child nahi. Sabse neeche wale level ki
// saari leaves ka sum chahiye.
// Example: root = [1,2,3,4,5,null,6,7,null,null,null,null,8] -> 15
//
// APPROACH (Hinglish):
// BFS level by level chalao. Har level shuru hone par sum ko 0 se
// reset karo, phir us level ke saare nodes ka sum jodo. Queue khaali
// hone par jo sum bacha, wahi LAST (deepest) level ka sum hai -
// kyunki BFS levels ko order me process karta hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (w = max width)
//
// KEY LEARNING:
// "Har level ke baad reset karo, aakhri wahi answer" - yeh trick
// deepest level wale har problem me kaam aati hai.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public int deepestLeavesSum(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int levelSum = 0;
        while (!q.isEmpty()) {
            levelSum = 0; // naya level = sum reset
            int size = q.size(); // sirf isi level ke nodes
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                levelSum += node.val;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return levelSum; // aakhri level ka sum = deepest leaves ka sum
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
        System.out.println(s.deepestLeavesSum(buildTree(
            new Integer[]{1,2,3,4,5,null,6,7,null,null,null,null,8}))); // Expected: 15
        System.out.println(s.deepestLeavesSum(buildTree(
            new Integer[]{6,7,8,2,7,1,3,9,null,1,4,null,null,null,5}))); // Expected: 19
        System.out.println(s.deepestLeavesSum(buildTree(new Integer[]{1}))); // Expected: 1
    }
}
