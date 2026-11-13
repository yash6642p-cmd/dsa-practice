// Day 24 - Problem 3: All Elements in Two Binary Search Trees (LC 1305) - Medium
// Topic: Queue (tree traversal - inorder se sorted list, phir merge)
//
// PROBLEM (Hinglish):
// Do binary search trees (BST) diye hain, root1 aur root2. Dono
// trees ke saare integers ko ek list me SORTED (ascending) order
// me return karo.
// Example: root1 = [2,1,4], root2 = [1,0,3] -> output [0,1,1,2,3,4]
// Example: root1 = [1,null,8], root2 = [8,1] -> output [1,1,8,8]
//
// APPROACH (Hinglish):
// BST ki khaasiyat: inorder traversal (left -> root -> right) se
// values SORTED order me milti hain. Dono trees ka inorder nikaal
// ke do sorted lists banao. Phir merge sort wali two-pointer trick
// se dono sorted lists ko ek sorted list me merge karo - chhota
// wala pehle lo, pointer aage badhao.
// (Dono lists ko jod ke sort karna bhi chalega, par merge O(n+m)
// me hota hai aur BST property ka sahi use hai.)
//
// TIME COMPLEXITY: O(n + m) - dono trees ke nodes ek baar visit + merge
// SPACE COMPLEXITY: O(n + m) - dono sorted lists store karni padti hain
//
// KEY LEARNING:
// BST + sorted output dikhe to turant inorder traversal yaad karo.
// Do sorted lists ka merge two pointers se linear time me hota hai
// - yahi merge sort ka merge step hai.

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
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        // dono trees ka inorder = dono sorted lists
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        inorder(root1, list1);
        inorder(root2, list2);
        // two pointers se merge karo
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size()) {
            if (list1.get(i) <= list2.get(j)) {
                merged.add(list1.get(i++));
            } else {
                merged.add(list2.get(j++));
            }
        }
        // bachi hui list ko jod do
        while (i < list1.size()) {
            merged.add(list1.get(i++));
        }
        while (j < list2.size()) {
            merged.add(list2.get(j++));
        }
        return merged;
    }

    private void inorder(TreeNode node, List<Integer> out) {
        // left -> root -> right (BST me ye sorted order deta hai)
        if (node == null) {
            return;
        }
        inorder(node.left, out);
        out.add(node.val);
        inorder(node.right, out);
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        TreeNode t1 = new TreeNode(2, new TreeNode(1), new TreeNode(4));
        TreeNode t2 = new TreeNode(1, new TreeNode(0), new TreeNode(3));
        System.out.println(s.getAllElements(t1, t2)); // Expected: [0, 1, 1, 2, 3, 4]
        // Example 2 (LeetCode)
        TreeNode t3 = new TreeNode(1, null, new TreeNode(8));
        TreeNode t4 = new TreeNode(8, new TreeNode(1), null);
        System.out.println(s.getAllElements(t3, t4)); // Expected: [1, 1, 8, 8]
        // ek tree khaali
        TreeNode t5 = new TreeNode(5, new TreeNode(3), new TreeNode(7));
        System.out.println(s.getAllElements(null, t5)); // Expected: [3, 5, 7]
        // dono khaali
        System.out.println(s.getAllElements(null, null)); // Expected: []
    }
}
