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
    public int amountOfTime(TreeNode root, int start) {
        Map<TreeNode, TreeNode> parent = new HashMap<>();
        TreeNode startNode = null;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        parent.put(root, null);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.val == start) {
                startNode = node;
            }
            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }
            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }
        Queue<TreeNode> q = new LinkedList<>();
        Map<TreeNode, Integer> time = new HashMap<>();
        q.offer(startNode);
        time.put(startNode, 0);
        int maxTime = 0;
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            int t = time.get(node);
            maxTime = Math.max(maxTime, t);
            TreeNode[] neighbours = {node.left, node.right, parent.get(node)};
            for (TreeNode nb : neighbours) {
                if (nb != null && !time.containsKey(nb)) {
                    time.put(nb, t + 1);
                    q.offer(nb);
                }
            }
        }
        return maxTime;
    }
}
