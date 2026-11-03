// Day 14 - Problem 1: Symmetric Tree (LC 101) - Easy
// Topic: Queue (pair-wise BFS)
//
// PROBLEM (Hinglish):
// Ek binary tree ka root diya hai. Check karo ki tree apne aap ka
// mirror hai ya nahi. Example: [1,2,2,3,4,4,3] -> true,
// [1,2,2,null,3,null,3] -> false.
//
// APPROACH (Hinglish):
// Queue me node ke JODE daalo - ek left side se, ek right side se.
// Har pair me dono None ho to aage badho; ek None ho ya values alag
// ho to false. Warna mirror order me bachon ke jode daalo:
// (left.left, right.right) aur (left.right, right.left).
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst case O(n))
//
// KEY LEARNING:
// Symmetric ka matlab ek side ka mirror doosri side hai, isliye
// nodes ko jode me compare karte hain.

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
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        // queue me mirror hone chahiye wale node-jode daalo
        Queue<TreeNode[]> q = new LinkedList<>();
        q.offer(new TreeNode[]{root.left, root.right});
        while (!q.isEmpty()) {
            TreeNode[] pair = q.poll();
            TreeNode left = pair[0];
            TreeNode right = pair[1];
            // dono khaali ho to yeh joda theek hai
            if (left == null && right == null) {
                continue;
            }
            // ek khaali, doosra nahi, ya values alag -> mirror toota
            if (left == null || right == null || left.val != right.val) {
                return false;
            }
            // mirror order me bachon ke jode daalo
            q.offer(new TreeNode[]{left.left, right.right});
            q.offer(new TreeNode[]{left.right, right.left});
        }
        return true;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // [1,2,2,3,4,4,3] -> true
        TreeNode t1 = new TreeNode(1,
            new TreeNode(2, new TreeNode(3), new TreeNode(4)),
            new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        System.out.println(s.isSymmetric(t1)); // Expected: true
        // [1,2,2,null,3,null,3] -> false
        TreeNode t2 = new TreeNode(1,
            new TreeNode(2, null, new TreeNode(3)),
            new TreeNode(2, null, new TreeNode(3)));
        System.out.println(s.isSymmetric(t2)); // Expected: false
        System.out.println(s.isSymmetric(new TreeNode(1))); // Expected: true
    }
}
