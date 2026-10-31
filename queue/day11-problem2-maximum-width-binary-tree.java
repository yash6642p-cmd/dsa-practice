// Day 11 - Problem 2: Maximum Width of Binary Tree (LC 662) - Medium
// Topic: Queue (BFS + position indexing)
//
// PROBLEM (Hinglish):
// Binary tree ki maximum width nikaalni hai. Ek level ki width = us level ke
// leftmost aur rightmost non-null node ke beech ki doori + 1, jisme beech ke
// null nodes bhi count hote hain (jaise tree complete hota).
// Example: root = [1,3,2,5,3,null,9] -> output 4 (teesra level: 5,3,null,9)
//
// APPROACH (Hinglish):
// BFS karo, lekin har node ke saath uski "position index" bhi rakho -
// jaise complete binary tree me hota hai: root = 0, left = 2*i, right = 2*i+1.
// Har level par width = last_index - first_index + 1.
// Overflow se bachne ke liye har level ke start me index normalize karo
// (sabhi index me se level ka pehla index subtract kar do).
//
// TIME COMPLEXITY: O(n) - har node ek baar visit
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst case O(n))
//
// KEY LEARNING:
// Sirf node count karne se width nahi milti - beech ke null gaps ke liye
// position index chahiye. Index normalize karna overflow se bachata hai.

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

// node + uski position index ka pair
class Pair {
    TreeNode node;
    int idx;
    Pair(TreeNode node, int idx) { this.node = node; this.idx = idx; }
}

class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        int maxWidth = 0;
        Queue<Pair> q = new LinkedList<>();
        q.offer(new Pair(root, 0));
        while (!q.isEmpty()) {
            int levelSize = q.size();
            int firstIdx = q.peek().idx; // is level ka pehla index
            int lastIdx = firstIdx;
            for (int i = 0; i < levelSize; i++) {
                Pair p = q.poll();
                lastIdx = p.idx;
                int idx = p.idx - firstIdx; // normalize (overflow se bachao)
                if (p.node.left != null) q.offer(new Pair(p.node.left, 2 * idx));
                if (p.node.right != null) q.offer(new Pair(p.node.right, 2 * idx + 1));
            }
            maxWidth = Math.max(maxWidth, lastIdx - firstIdx + 1);
        }
        return maxWidth;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // [1,3,2,5,3,null,9]
        TreeNode t1 = new TreeNode(1,
            new TreeNode(3, new TreeNode(5), new TreeNode(3)),
            new TreeNode(2, null, new TreeNode(9)));
        System.out.println(s.widthOfBinaryTree(t1));
        // Expected: 4
        // [1,3,null,5,3]
        TreeNode t2 = new TreeNode(1,
            new TreeNode(3, new TreeNode(5), new TreeNode(3)), null);
        System.out.println(s.widthOfBinaryTree(t2));
        // Expected: 2
        // [1,3,2,5]
        TreeNode t3 = new TreeNode(1,
            new TreeNode(3, new TreeNode(5), null), new TreeNode(2));
        System.out.println(s.widthOfBinaryTree(t3));
        // Expected: 2
    }
}
