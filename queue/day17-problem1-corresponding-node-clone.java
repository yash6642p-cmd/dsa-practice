// Day 17 - Problem 1: Find a Corresponding Node of a Binary Tree in a Clone of That Tree (LC 1379) - Easy
// Topic: Queue (BFS - dono trees ko saath-saath traverse karo)
//
// PROBLEM (Hinglish):
// Ek binary tree `original` aur uski exact copy `cloned` di hai.
// `target` original tree ka ek node hai. Cloned tree me usi
// position wala node dhoondh kar return karo. VALUE se match mat
// karo, POSITION se match karo (duplicate values bhi ho sakti hain).
// Example: tree = [7,4,3,null,null,6,19], target = 3 -> output 3
// (cloned tree wala node, original wala nahi)
//
// APPROACH (Hinglish):
// Dono trees ki structure ek jaisi hai, to dono ko SAATH-SAATH
// BFS se traverse karo. Queue me (original_node, cloned_node)
// ka pair daalo. Jab original_node == target (reference se
// compare) mile, to saath wala cloned_node hi answer hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(n) - queue me ek level ke nodes (worst case O(n))
//
// KEY LEARNING:
// Jab do same-structure trees ko compare karna ho, to dono ko
// parallel traverse karo - pair queue me daalo. Reference (==)
// se compare karo, value se nahi.

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
    public TreeNode getTargetCopy(TreeNode original, TreeNode cloned, TreeNode target) {
        // queue me (original ka node, cloned ka node) ka pair
        Queue<TreeNode[]> q = new LinkedList<>();
        q.offer(new TreeNode[]{original, cloned});
        while (!q.isEmpty()) {
            TreeNode[] pair = q.poll();
            TreeNode origNode = pair[0], cloneNode = pair[1];
            // reference se compare - yahi asli pehchan hai
            if (origNode == target) {
                return cloneNode;
            }
            if (origNode.left != null) {
                q.offer(new TreeNode[]{origNode.left, cloneNode.left});
            }
            if (origNode.right != null) {
                q.offer(new TreeNode[]{origNode.right, cloneNode.right});
            }
        }
        return null;
    }

    // TEST
    // tree ki gehri copy banao (test ke liye)
    static TreeNode cloneTree(TreeNode node) {
        if (node == null) return null;
        TreeNode copy = new TreeNode(node.val);
        copy.left = cloneTree(node.left);
        copy.right = cloneTree(node.right);
        return copy;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // tree = [7,4,3,null,null,6,19], target = 3
        TreeNode orig = new TreeNode(7, new TreeNode(4), new TreeNode(3, new TreeNode(6), new TreeNode(19)));
        TreeNode cloned = cloneTree(orig);
        TreeNode target = orig.right; // value 3 wala node
        TreeNode ans = s.getTargetCopy(orig, cloned, target);
        System.out.println(ans.val); // Expected: 3
        System.out.println(ans == cloned.right); // Expected: true (cloned ka node hai)

        // single node tree
        TreeNode orig2 = new TreeNode(7);
        TreeNode cloned2 = cloneTree(orig2);
        System.out.println(s.getTargetCopy(orig2, cloned2, orig2).val); // Expected: 7
    }
}
