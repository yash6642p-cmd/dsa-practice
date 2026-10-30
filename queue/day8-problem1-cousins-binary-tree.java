// Day 8 - Problem 1: Cousins in Binary Tree (LC 993) - Easy
// ============================================================
// PROBLEM (Hinglish):
// Do nodes x aur y cousins hain agar depth SAME ho lekin parent
// ALAG ho. Root ki depth 0.
// Example: [1,2,3,null,4,null,5], x=5, y=4 -> True
//
// APPROACH (Hinglish):
// BFS level by level: queue me (node, parent) ka pair rakho. Har
// level ke baad check karo - dono isi level me mile to parent
// compare karo, sirf ek mila to seedha False (doosra same depth
// pe nahi hoga). Siblings cousins NAHI hote, ye mat bhoolna.
//
// TIME COMPLEXITY: O(n)
// SPACE COMPLEXITY: O(w) - ek level ke nodes

import java.util.*;

// Node ke saath parent bhi rakhne ke liye chhota pair class
class NodeParent {
    TreeNode node;
    TreeNode parent;
    NodeParent(TreeNode node, TreeNode parent) {
        this.node = node;
        this.parent = parent;
    }
}

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public boolean isCousins(TreeNode root, int x, int y) {
        Queue<NodeParent> q = new LinkedList<>();
        q.offer(new NodeParent(root, null));
        while (!q.isEmpty()) {
            int size = q.size();
            TreeNode xParent = null, yParent = null;
            boolean xFound = false, yFound = false;
            for (int i = 0; i < size; i++) {
                NodeParent curr = q.poll();
                TreeNode node = curr.node;
                if (node.val == x) { xParent = curr.parent; xFound = true; }
                if (node.val == y) { yParent = curr.parent; yFound = true; }
                if (node.left != null) q.offer(new NodeParent(node.left, node));
                if (node.right != null) q.offer(new NodeParent(node.right, node));
            }
            if (xFound && yFound) return xParent != yParent;
            if (xFound || yFound) return false; // sirf ek isi level me
        }
        return false;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(5);
        System.out.println(s.isCousins(root, 5, 4)); // Expected: true
        System.out.println(s.isCousins(root, 2, 3)); // Expected: false (siblings)
    }
}
