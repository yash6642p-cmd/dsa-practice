// Day 22 - Problem 4: Sum of Nodes with Even-Valued Grandparent (LC 1315) - Medium
// Topic: Queue (BFS - har node ke saath parent + grandparent track karo)
//
// PROBLEM (Hinglish):
// Binary tree ka root diya hai. Un nodes ke values ka SUM nikaalo
// jinke GRANDPARENT (dada - parent ka parent) ki value EVEN hai.
// Aisa koi node nahi hai to 0 return karo.
// Example: root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5] -> 18
// (6 even hai, uske grandchildren 2,7,1,3; 8 even hai, uska
//  grandchild 5; total = 2+7+1+3+5 = 18)
// Example: root = [1] -> 0 (koi grandparent hi nahi)
//
// APPROACH (Hinglish):
// BFS queue me sirf node nahi, (node, parent_val, grandparent_val)
// ka triple daalo. Jab node nikalo, agar grandparent_val even hai
// to node.val sum me jodo. Phir bachcho ko daalo - unke liye current
// node parent ban jayega aur current parent grandparent. Root ke
// liye parent aur grandparent dono "koi nahi" (-1) rakho.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(n) - BFS queue (worst case ek level)
//
// KEY LEARNING:
// Tree traverse karte time "upar wale" ki info neeche le jaani ho to
// queue me node ke saath extra info bhi daal do. Grandparent check
// karne ke liye do level upar dekhna padta hai - isliye triple use karo.

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

// queue me ek entry: node + uske parent aur grandparent ki values
class NodeState {
    TreeNode node;
    int parentVal;      // -1 matlab koi parent nahi
    int grandparentVal; // -1 matlab koi grandparent nahi
    NodeState(TreeNode node, int parentVal, int grandparentVal) {
        this.node = node;
        this.parentVal = parentVal;
        this.grandparentVal = grandparentVal;
    }
}

class Solution {
    public int sumEvenGrandparent(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int total = 0;
        Queue<NodeState> q = new LinkedList<>();
        q.offer(new NodeState(root, -1, -1));
        while (!q.isEmpty()) {
            NodeState cur = q.poll();
            TreeNode node = cur.node;
            // grandparent hai aur even hai -> is node ka value jodo
            if (cur.grandparentVal != -1 && cur.grandparentVal % 2 == 0) {
                total += node.val;
            }
            // bachcho ke liye: current node parent, current parent grandparent
            if (node.left != null) {
                q.offer(new NodeState(node.left, node.val, cur.parentVal));
            }
            if (node.right != null) {
                q.offer(new NodeState(node.right, node.val, cur.parentVal));
            }
        }
        return total;
    }

    // TEST helpers
    static TreeNode buildTree(Integer[] values) {
        if (values.length == 0 || values[0] == null) {
            return null;
        }
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
        // Example 1 (LeetCode)
        TreeNode t1 = buildTree(new Integer[]{6, 7, 8, 2, 7, 1, 3, 9, null, 1, 4, null, null, null, 5});
        System.out.println(s.sumEvenGrandparent(t1)); // Expected: 18
        // Example 2 (LeetCode) - single node
        System.out.println(s.sumEvenGrandparent(buildTree(new Integer[]{1}))); // Expected: 0
        // root even hai: grandchildren 4 aur 5 ginne hain -> 9
        System.out.println(s.sumEvenGrandparent(buildTree(new Integer[]{2, 1, 3, 4, 5}))); // Expected: 9
        // root odd hai: koi even grandparent nahi -> 0
        System.out.println(s.sumEvenGrandparent(buildTree(new Integer[]{1, 2, 3, 4, 5, 6, 7}))); // Expected: 0
    }
}
