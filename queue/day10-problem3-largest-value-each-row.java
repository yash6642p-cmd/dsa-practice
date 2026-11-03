// Day 10 - Problem 3: Find Largest Value in Each Tree Row (LC 515) - Medium
// Topic: Queue (BFS level-order on a binary tree)
//
// PROBLEM (Hinglish):
// Binary tree ke HAR level ka sabse bada value nikalna hai, aur
// unhe ek list me level ke order me wapas karna hai (level 0 se shuru).
// Example 1: root = [1,3,2,5,3,null,9] -> [1,3,9]
// Example 2: root = [1,2,3] -> [1,3]
// Agar tree khaali hai to khaali list wapas karo.
//
// APPROACH (Hinglish):
// Wahi level-order BFS: har level ke saare nodes ko ek saath
// process karo, us level ka maximum track karo, level khatam hote
// hi max ko answer list me daal do.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me zyada se zyada ek level ke nodes
//
// KEY LEARNING:
// Level-order BFS ka template ek baar pakka ho gaya to "har level
// ka X" wale saare problems sirf ek line ke farak se ban jaate hain.

import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class Solution {
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans; // khaali tree -> khaali list
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelMax = Integer.MIN_VALUE;
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                levelMax = Math.max(levelMax, node.val);
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            ans.add(levelMax);
        }
        return ans;
    }

    // Level-order list (null = missing node) se tree banao - sirf test ke liye
    static TreeNode buildTree(Integer[] values) {
        if (values.length == 0 || values[0] == null) return null;
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        int i = 1;
        while (!q.isEmpty() && i < values.length) {
            TreeNode node = q.poll();
            if (i < values.length && values[i] != null) {
                node.left = new TreeNode(values[i]);
                q.offer(node.left);
            }
            i++;
            if (i < values.length && values[i] != null) {
                node.right = new TreeNode(values[i]);
                q.offer(node.right);
            }
            i++;
        }
        return root;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.largestValues(
            buildTree(new Integer[]{1, 3, 2, 5, 3, null, 9})));
        // Expected: [1, 3, 9]
        System.out.println(s.largestValues(buildTree(new Integer[]{1, 2, 3})));
        // Expected: [1, 3]
        System.out.println(s.largestValues(buildTree(new Integer[]{})));
        // Expected: []
    }
}
