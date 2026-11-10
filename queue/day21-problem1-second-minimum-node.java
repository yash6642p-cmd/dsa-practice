// Day 21 - Problem 1: Second Minimum Node In a Binary Tree (LC 671) - Easy
// Topic: Queue (BFS - level order se second minimum dhundho)
//
// PROBLEM (Hinglish):
// Ek SPECIAL binary tree diya hai: har node ke ya to 2 children hain
// ya 0. Aur har parent ka value = min(left child, right child).
// Matlab root hi sabse CHHOTA value hai. Tumhe tree me dusra sabse
// chhota (second minimum) value nikaalna hai. Agar nahi hai to -1.
// Example: root = [2,2,5,null,null,5,7] -> output 5
// Example: root = [2,2,2] -> output -1 (sirf 2 hi hai)
//
// APPROACH (Hinglish):
// Root ka value hi minimum hai (tree ki property se). Toh BFS queue
// se saare nodes ghumo, aur root.val se BADA sabse chhota value
// track karo. Jo node root.val se bada hai, wo second minimum ka
// candidate hai - usme se minimum lo. Koi candidate nahi mila to -1.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst O(n))
//
// KEY LEARNING:
// Tree ki khaas property (root = minimum) use karo - poora tree
// sort karne ki zaroorat nahi, sirf root se bada minimum chahiye.

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
    public int findSecondMinimumValue(TreeNode root) {
        // root hi minimum hai - usse bada sabse chhota value dhundho
        int minVal = root.val;
        int secondMin = Integer.MAX_VALUE;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            // root se bada value = second minimum ka candidate
            if (node.val > minVal) {
                secondMin = Math.min(secondMin, node.val);
            }
            if (node.left != null) {
                q.offer(node.left);
            }
            if (node.right != null) {
                q.offer(node.right);
            }
        }
        // koi candidate nahi mila to -1
        return secondMin == Integer.MAX_VALUE ? -1 : secondMin;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        TreeNode t1 = new TreeNode(2,
            new TreeNode(2),
            new TreeNode(5, new TreeNode(5), new TreeNode(7)));
        System.out.println(s.findSecondMinimumValue(t1)); // Expected: 5
        // Example 2 - sirf ek hi value hai
        TreeNode t2 = new TreeNode(2, new TreeNode(2), new TreeNode(2));
        System.out.println(s.findSecondMinimumValue(t2)); // Expected: -1
        // single node - second minimum nahi hai
        System.out.println(s.findSecondMinimumValue(new TreeNode(5))); // Expected: -1
        // sab values same
        TreeNode t4 = new TreeNode(1, new TreeNode(1), new TreeNode(1));
        System.out.println(s.findSecondMinimumValue(t4)); // Expected: -1
    }
}
