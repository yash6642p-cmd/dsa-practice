// Day 11 - Problem 1: Invert Binary Tree (LC 226) - Easy
// Topic: Queue (BFS version)
//
// PROBLEM (Hinglish):
// Ek binary tree diya hai. Use invert (mirror) karo - matlab har node ke
// left aur right child ko swap kar do, poore tree me. Root wapas return karo.
// Example: root = [4,2,7,1,3,6,9] -> output [4,7,2,9,6,3,1]
//
// APPROACH (Hinglish):
// Queue me root daalo (BFS / level order). Har node ko queue se nikalo,
// uske left-right swap karo, phir dono children ko queue me daal do.
// Jab queue khaali ho jaye, tree ulta ho chuka hoga.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst case O(n))
//
// KEY LEARNING:
// Tree ko level-by-level process karne ke liye queue best hoti hai.
// Swap karte waqt temp variable chahiye kyunki Java me tuple swap nahi hai.

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
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            // left-right swap (mirror step)
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        return root;
    }

    // TEST (level-order print)
    static void printLevelOrder(TreeNode root) {
        if (root == null) { System.out.println("[]"); return; }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        List<Integer> out = new ArrayList<>();
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) { out.add(null); continue; }
            out.add(node.val);
            q.offer(node.left);
            q.offer(node.right);
        }
        while (!out.isEmpty() && out.get(out.size() - 1) == null) out.remove(out.size() - 1);
        System.out.println(out);
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode t1 = new TreeNode(4,
            new TreeNode(2, new TreeNode(1), new TreeNode(3)),
            new TreeNode(7, new TreeNode(6), new TreeNode(9)));
        printLevelOrder(s.invertTree(t1));
        // Expected: [4, 7, 2, 9, 6, 3, 1]
        TreeNode t2 = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        printLevelOrder(s.invertTree(t2));
        // Expected: [2, 3, 1]
        printLevelOrder(s.invertTree(null));
        // Expected: []
    }
}
