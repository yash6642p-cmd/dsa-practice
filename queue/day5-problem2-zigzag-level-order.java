// Day 5 - Problem 2: Binary Tree Zigzag Level Order Traversal (LC 103) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Binary tree ka zigzag level order do: level 0 left-to-right, level 1
// right-to-left, phir wapas left-to-right... har level direction palat-ti hai.
// Example: [3,9,20,null,null,15,7] -> [[3], [20,9], [15,7]]
//
// APPROACH (Hinglish):
// LC 102 wala BFS hi hai, bas ek leftToRight flag extra hai:
// 1. Har level ke liye queue ka size snapshot lo.
// 2. Node ki value level list me sahi index par rakho:
//    left-to-right ho to index = i, ulti ho to index = size-1-i.
//    Bachon ko hamesha left-phir-right order me queue me daalo.
// 3. Level khatam hote hi flag palat do.
// Isse list ko reverse karne ki zaroorat nahi padti.
//
// TIME COMPLEXITY: O(n) - har node ek baar
// SPACE COMPLEXITY: O(n) - queue + result
//
// KEY LEARNING:
// Level-order problems me BFS skeleton same rehta hai, sirf level ke
// andar ka kaam badalta hai. Ek boolean flag se poora pattern badal gaya.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean leftToRight = true; // har level ke baad paltega
        while (!q.isEmpty()) {
            int size = q.size(); // is level me kitne nodes hain
            List<Integer> level = new ArrayList<>(Collections.nCopies(size, 0));
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                // direction ke hisaab se index chuno
                int idx = leftToRight ? i : size - 1 - i;
                level.set(idx, node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            result.add(level);
            leftToRight = !leftToRight;
        }
        return result;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(new Solution().zigzagLevelOrder(root));
        // Expected: [[3], [20, 9], [15, 7]]
    }
}
