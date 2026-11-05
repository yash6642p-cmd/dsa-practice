// Day 16 - Problem 1: Path Sum (LC 112) - Easy
// Topic: Queue (BFS)
//
// PROBLEM (Hinglish):
// Binary tree ka root aur targetSum diya hai. Check karo ki root
// se kisi LEAF tak ka aisa path hai ya nahi jiska sum targetSum ho.
// Example: [5,4,8,11,null,13,4,7,2,null,null,null,1], target 22
// -> true (path 5 -> 4 -> 11 -> 2 ka sum 22 hai)
//
// APPROACH (Hinglish):
// Queue me (node, ab tak ka sum) jode daalo. Har node ko nikalo:
// agar woh leaf hai aur sum targetSum hai to true. Warna bachon
// ko unke naye sum ke saath queue me daalo. Khaali tree me koi
// path nahi, isliye root null ho to false (chahe target 0 ho).
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst O(n))
//
// KEY LEARNING:
// Path wale problems me node ke saath chalta hua state (yahan
// ab tak ka sum) bhi queue me rakhna padta hai.

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
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        // queue me (node, root se yahan tak ka sum) rakho
        Queue<Object[]> q = new LinkedList<>();
        q.offer(new Object[]{root, root.val});
        while (!q.isEmpty()) {
            Object[] item = q.poll();
            TreeNode node = (TreeNode) item[0];
            int total = (int) item[1];
            // leaf aur sum match = path mil gaya
            if (node.left == null && node.right == null && total == targetSum) {
                return true;
            }
            if (node.left != null) {
                q.offer(new Object[]{node.left, total + node.left.val});
            }
            if (node.right != null) {
                q.offer(new Object[]{node.right, total + node.right.val});
            }
        }
        return false;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode t1 = new TreeNode(5,
            new TreeNode(4, new TreeNode(11, new TreeNode(7), new TreeNode(2)), null),
            new TreeNode(8, new TreeNode(13), new TreeNode(4, null, new TreeNode(1))));
        System.out.println(s.hasPathSum(t1, 22)); // Expected: true
        System.out.println(s.hasPathSum(new TreeNode(1, new TreeNode(2), new TreeNode(3)), 5)); // Expected: false
        System.out.println(s.hasPathSum(null, 0)); // Expected: false
        System.out.println(s.hasPathSum(new TreeNode(1, new TreeNode(2), null), 1)); // Expected: false
    }
}
