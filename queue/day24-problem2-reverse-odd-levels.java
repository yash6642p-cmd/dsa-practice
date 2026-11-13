// Day 24 - Problem 2: Reverse Odd Levels of Binary Tree (LC 2415) - Medium
// Topic: Queue (level order BFS - odd level ke values ko ulta karo)
//
// PROBLEM (Hinglish):
// Ek PERFECT binary tree diya hai (har parent ke 2 bachche, saare
// leaves same level par). Har ODD level ke node values ko reverse
// karna hai. Root level 0 hai.
// Example: root = [2,3,5,8,13,21,34] -> output [2,5,3,8,13,21,34]
// (level 1 ke 3,5 ulta hoke 5,3 ho gaye)
// Example: root = [7,13,11] -> output [7,11,13]
//
// APPROACH (Hinglish):
// BFS se level-by-level chalo, queue me ek level ke nodes rakho.
// Level number gin-te jao (root = 0). Jab level odd ho, us level
// ke nodes ki values ko do pointers (left/right ends) se swap kar
// ke ulta kar do. Sirf VALUES swap hoti hain, tree ki structure
// same rehti hai. Phir bachcho ko queue me daal ke aage badho.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me sabse chauda level (worst O(n))
//
// KEY LEARNING:
// Level order traversal me ek level ke saare nodes ek saath milte
// hain - isliye level-wise kaam (reverse, sum, swap) BFS se sabse
// aasan hota hai. Values swap karna structure badalne se sasta hai.

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
    public TreeNode reverseOddLevels(TreeNode root) {
        if (root == null) {
            return root;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            // is level ke saare nodes nikaalo
            List<TreeNode> nodes = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                nodes.add(q.poll());
            }
            // odd level ho to values ko ulta karo (two pointers swap)
            if (level % 2 == 1) {
                int l = 0, r = nodes.size() - 1;
                while (l < r) {
                    int temp = nodes.get(l).val;
                    nodes.get(l).val = nodes.get(r).val;
                    nodes.get(r).val = temp;
                    l++;
                    r--;
                }
            }
            // agle level ke bachche queue me daalo
            for (TreeNode node : nodes) {
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            level++;
        }
        return root;
    }

    // TEST (tree ko level order list ki tarah print karta hai)
    static List<Integer> toList(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                out.add(null);
            } else {
                out.add(node.val);
                q.offer(node.left);
                q.offer(node.right);
            }
        }
        while (!out.isEmpty() && out.get(out.size() - 1) == null) {
            out.remove(out.size() - 1);
        }
        return out;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        TreeNode r1 = new TreeNode(2,
            new TreeNode(3, new TreeNode(8), new TreeNode(13)),
            new TreeNode(5, new TreeNode(21), new TreeNode(34)));
        System.out.println(toList(s.reverseOddLevels(r1))); // Expected: [2, 5, 3, 8, 13, 21, 34]
        // Example 2 (LeetCode)
        TreeNode r2 = new TreeNode(7, new TreeNode(13), new TreeNode(11));
        System.out.println(toList(s.reverseOddLevels(r2))); // Expected: [7, 11, 13]
        // single node - koi odd level hi nahi
        TreeNode r3 = new TreeNode(5);
        System.out.println(toList(s.reverseOddLevels(r3))); // Expected: [5]
    }
}
