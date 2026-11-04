// Day 15 - Problem 1: Sum of Left Leaves (LC 404) - Easy
// Topic: Queue (BFS level order)
//
// PROBLEM (Hinglish):
// Ek binary tree ka root diya hai. Sabhi "left leaves" ke values ka
// sum return karo. Leaf = jiska koi bachcha na ho. Left leaf = jo
// apne parent ka LEFT bachcha ho AUR leaf bhi ho.
// Example: [3,9,20,null,null,15,7] -> 24 (9 + 15; 7 right leaf hai)
//
// APPROACH (Hinglish):
// Queue me (node, isLeft) jode daalo. Har node ko nikalo: agar woh
// leaf hai aur isLeft true hai to value sum me jodo. Bachon ko
// queue me daalo - left ke saath true, right ke saath false.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst case O(n))
//
// KEY LEARNING:
// "Left leaf" ke liye node ke saath yeh flag bhi chahiye ki woh
// left bachcha hai ya right - sirf leaf hona kaafi nahi.

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
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int total = 0;
        // queue me node ke saath isLeft flag rakho
        Queue<Object[]> q = new LinkedList<>();
        q.offer(new Object[]{root, false});
        while (!q.isEmpty()) {
            Object[] item = q.poll();
            TreeNode node = (TreeNode) item[0];
            boolean isLeft = (boolean) item[1];
            // leaf + left bachcha = count karo
            if (node.left == null && node.right == null && isLeft) {
                total += node.val;
            }
            if (node.left != null) {
                q.offer(new Object[]{node.left, true});
            }
            if (node.right != null) {
                q.offer(new Object[]{node.right, false});
            }
        }
        return total;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // [3,9,20,null,null,15,7] -> 24
        TreeNode t1 = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(s.sumOfLeftLeaves(t1)); // Expected: 24
        System.out.println(s.sumOfLeftLeaves(new TreeNode(1))); // Expected: 0
        // [1,2,3,4,5] -> 4 (sirf 4 left leaf hai)
        TreeNode t3 = new TreeNode(1,
            new TreeNode(2, new TreeNode(4), new TreeNode(5)),
            new TreeNode(3));
        System.out.println(s.sumOfLeftLeaves(t3)); // Expected: 4
    }
}
