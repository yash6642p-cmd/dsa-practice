// Day 9 - Problem 3: Kth Largest Sum in a Binary Tree (LC 2583) - Medium
// Topic: Queue (BFS - level sums + sorting)
//
// PROBLEM (Hinglish):
// Binary tree aur ek number k diya hai. Har level ke nodes ka sum
// nikaalo (level sum), phir unme se k-th LARGEST level sum wapas
// karo. Agar tree me k se kam levels hain to -1 do.
// Example: root = [5,8,9,2,1,3,7,4,6], k = 2
//   Level sums: [5, 17, 13, 10] -> 2nd largest = 13
//
// APPROACH (Hinglish):
// BFS se har level ka sum ek list me jama karo. Phir list ko
// descending order me sort karo aur (k-1)th index wala element lo.
// Pehle check karo: levels < k ho to -1.
//
// TIME COMPLEXITY: O(n + L log L) - BFS O(n), sort O(L log L)
// SPACE COMPLEXITY: O(n) - queue + level sums list
//
// KEY LEARNING:
// BFS se level-wise data nikaalna + phir us par sorting/kth
// statistic lagana - yeh combination interview me bahut aata hai.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public long kthLargestLevelSum(TreeNode root, int k) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        List<Long> levelSums = new ArrayList<>();
        while (!q.isEmpty()) {
            long levelSum = 0;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                levelSum += node.val;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            levelSums.add(levelSum);
        }
        if (levelSums.size() < k) {
            return -1; // itne levels hi nahi hain
        }
        Collections.sort(levelSums, Collections.reverseOrder());
        return levelSums.get(k - 1);
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
        System.out.println(s.kthLargestLevelSum(buildTree(
            new Integer[]{5,8,9,2,1,3,7,4,6}), 2)); // Expected: 13
        System.out.println(s.kthLargestLevelSum(buildTree(
            new Integer[]{1,2,null,3}), 1)); // Expected: 3
        System.out.println(s.kthLargestLevelSum(buildTree(
            new Integer[]{1,2,null,3}), 5)); // Expected: -1
    }
}
