// Day 21 - Problem 2: Check Completeness of a Binary Tree (LC 958) - Medium
// Topic: Queue (BFS - level order me gap check karo)
//
// PROBLEM (Hinglish):
// Binary tree COMPLETE hai ya nahi, check karo. Complete matlab:
// saare levels (last ko chhod kar) poori tarah bhare hon, aur last
// level ke nodes LEFT se bhare hon, beech me koi gap na ho.
// Example: root = [1,2,3,4,5,6] -> true (last level left se bhara)
// Example: root = [1,2,3,4,5,null,7] -> false (6 ki jagah gap hai,
// phir bhi 7 hai - gap ke baad node nahi hona chahiye)
//
// APPROACH (Hinglish):
// BFS level order me chalao, aur NULL nodes ko bhi queue me daalo.
// Jaise hi pehla NULL dikhe, ek flag laga do. Uske BAAD agar koi
// real node dikh gaya to tree complete NAHI hai (gap ke baad node
// matlab last level left se bhara nahi). Aakhir tak koi node na
// dikhe to complete hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst O(n))
//
// KEY LEARNING:
// "NULL ko bhi queue me daalo" - complete tree check karne ke liye
// khaali jagah ko bhi dekhna padta hai. Gap ke baad node = incomplete.

import java.util.*;

class Solution {
    public boolean isCompleteTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean seenNull = false; // kya pehla khaali (null) node dikh chuka?
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                // pehla gap mil gaya
                seenNull = true;
            } else {
                // gap ke baad node mila = incomplete tree
                if (seenNull) {
                    return false;
                }
                q.offer(node.left);
                q.offer(node.right);
            }
        }
        return true;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 - complete
        TreeNode t1 = new TreeNode(1,
            new TreeNode(2, new TreeNode(4), new TreeNode(5)),
            new TreeNode(3, new TreeNode(6), null));
        System.out.println(s.isCompleteTree(t1)); // Expected: true
        // Example 2 - gap ke baad 7 hai
        TreeNode t2 = new TreeNode(1,
            new TreeNode(2, new TreeNode(4), new TreeNode(5)),
            new TreeNode(3, null, new TreeNode(7)));
        System.out.println(s.isCompleteTree(t2)); // Expected: false
        // single node - complete
        System.out.println(s.isCompleteTree(new TreeNode(1))); // Expected: true
        // perfect tree - complete
        TreeNode t4 = new TreeNode(1,
            new TreeNode(2, new TreeNode(4), new TreeNode(5)),
            new TreeNode(3, new TreeNode(6), new TreeNode(7)));
        System.out.println(s.isCompleteTree(t4)); // Expected: true
        // last level me beech me gap
        TreeNode t5 = new TreeNode(1,
            new TreeNode(2, new TreeNode(4), null),
            new TreeNode(3, new TreeNode(6), null));
        System.out.println(s.isCompleteTree(t5)); // Expected: false
    }
}
