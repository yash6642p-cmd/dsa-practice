// Day 24 - Problem 1: Univalued Binary Tree (LC 965) - Easy
// Topic: Queue (level order BFS - har node ko root ki value se compare karo)
//
// PROBLEM (Hinglish):
// Ek binary tree diya hai. Tree "uni-valued" hota hai agar har node
// ki value same ho. Root diya hai, return true karo agar tree
// uni-valued hai, warna false.
// Example: root = [1,1,1,1,1,null,1] -> output true
// Example: root = [2,2,2,5,2] -> output false
// (node 5 ki value root 2 se alag hai)
//
// APPROACH (Hinglish):
// Root ki value ko "target" maan lo. Ab queue (BFS) se level order
// me har node ko visit karo. Koi bhi node jiski value target se
// alag ho, milte hi false return karo. Saare nodes check ho jayein
// aur koi alag value na mile to true.
// Early exit: galat value milte hi ruk jao, poora tree ghumne ki
// zaroorat nahi.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst O(n))
//
// KEY LEARNING:
// BFS/queue sirf level order ke liye nahi, kisi bhi "har node check
// karo" wale kaam ke liye use hota hai. Early exit se bekaar kaam
// bachta hai.

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
    public boolean isUnivalTree(TreeNode root) {
        // root ki value hi target hai
        int target = root.val;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            // value alag mili to tree uni-valued nahi hai
            if (node.val != target) {
                return false;
            }
            if (node.left != null) {
                q.offer(node.left);
            }
            if (node.right != null) {
                q.offer(node.right);
            }
        }
        return true;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        TreeNode r1 = new TreeNode(1,
            new TreeNode(1, new TreeNode(1), new TreeNode(1)),
            new TreeNode(1, null, new TreeNode(1)));
        System.out.println(s.isUnivalTree(r1)); // Expected: true
        // Example 2 (LeetCode)
        TreeNode r2 = new TreeNode(2,
            new TreeNode(2, new TreeNode(5), new TreeNode(2)),
            new TreeNode(2));
        System.out.println(s.isUnivalTree(r2)); // Expected: false
        // single node tree hamesha uni-valued
        TreeNode r3 = new TreeNode(7);
        System.out.println(s.isUnivalTree(r3)); // Expected: true
        // root se alag value leaf me
        TreeNode r4 = new TreeNode(1,
            new TreeNode(1, new TreeNode(1), new TreeNode(2)),
            new TreeNode(1));
        System.out.println(s.isUnivalTree(r4)); // Expected: false
        // saare zero
        TreeNode r5 = new TreeNode(0, new TreeNode(0), new TreeNode(0));
        System.out.println(s.isUnivalTree(r5)); // Expected: true
    }
}
