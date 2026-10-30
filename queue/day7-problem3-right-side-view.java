// Day 7 - Problem 3: Binary Tree Right Side View (LC 199) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Tree ko right side se dekho to kaun se nodes dikhenge? Har level
// ka sabse right wala node, upar se neeche.
// Example: [1,2,3,null,5,null,4] -> [1,3,4]
//
// APPROACH (Hinglish):
// Level-order BFS, har level ka size snapshot lo, us level ka
// aakhri node result me daalo. Bachon ko left-phir-right order me
// queue me daalo taaki level ka order sahi rahe.
//
// TIME COMPLEXITY: O(n)
// SPACE COMPLEXITY: O(w) - ek level ke nodes
//
// KEY LEARNING:
// "Har level se ek node" = BFS + level size snapshot. Right view =
// har level ka last node, left view hota to first node.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (i == size - 1) result.add(node.val); // level ka aakhri
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
        }
        return result;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);
        System.out.println(new Solution().rightSideView(root)); // Expected: [1, 3, 4]
        System.out.println(new Solution().rightSideView(null)); // Expected: []
    }
}
