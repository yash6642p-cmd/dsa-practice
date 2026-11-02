// Day 13 - Problem 3: All Nodes Distance K in Binary Tree (LC 863) - Medium
// Topic: Queue (BFS with parent pointers)
//
// PROBLEM (Hinglish):
// Ek binary tree, ek target node, aur integer k diya hai. Un sab nodes
// ki values return karo jo target se exactly k distance par hain.
// Example: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
// -> output [7,4,1] (kisi bhi order me)
//
// APPROACH (Hinglish):
// Tree me sirf neeche ja sakte hain, upar nahi - isliye pehle har node
// ka parent record karo. Phir target se BFS shuru karo jisme neighbours
// = left, right aur parent teenon hote hain. k levels chalke jo nodes
// milen, wahi answer hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(n) - parent map, queue aur visited set ke liye
//
// KEY LEARNING:
// Tree ko graph ki tarah treat karna seekho: parent pointer jodne se
// upar ki taraf bhi BFS chal sakta hai.
//
// NOTE: LeetCode par TreeNode class pehle se defined hoti hai, isliye
// submit karte waqt sirf Solution class paste karo.

import java.util.*;

// Local testing ke liye TreeNode definition (LeetCode par pre-defined hai)
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
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        // Step 1: har node ka parent record karo
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        buildParent(root, null, parent);

        // Step 2: target se BFS, neighbours = left + right + parent
        Queue<TreeNode> q = new LinkedList<>();
        Set<TreeNode> visited = new HashSet<>();
        q.offer(target);
        visited.add(target);
        int dist = 0;
        while (!q.isEmpty()) {
            if (dist == k) {
                List<Integer> ans = new ArrayList<>();
                for (TreeNode node : q) ans.add(node.val);
                return ans;
            }
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.poll();
                TreeNode[] neighbours = {node.left, node.right, parent.get(node)};
                for (TreeNode nxt : neighbours) {
                    if (nxt != null && !visited.contains(nxt)) {
                        visited.add(nxt);
                        q.offer(nxt);
                    }
                }
            }
            dist++;
        }
        return new ArrayList<>();
    }

    private void buildParent(TreeNode node, TreeNode par, Map<TreeNode, TreeNode> parent) {
        if (node == null) return;
        parent.put(node, par);
        buildParent(node.left, node, parent);
        buildParent(node.right, node, parent);
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        TreeNode n7 = new TreeNode(7), n4 = new TreeNode(4);
        TreeNode n2 = new TreeNode(2, n7, n4);
        TreeNode n5 = new TreeNode(5, new TreeNode(6), n2);
        TreeNode n1 = new TreeNode(1, new TreeNode(0), new TreeNode(8));
        TreeNode root = new TreeNode(3, n5, n1);
        System.out.println(s.distanceK(root, n5, 2)); // Expected: [7, 4, 1] kisi bhi order me
        System.out.println(s.distanceK(root, n5, 0)); // Expected: [5]
        System.out.println(s.distanceK(root, n5, 1)); // Expected: [2, 3, 6] kisi bhi order me (children 6, 2 + parent 3)
    }
}
