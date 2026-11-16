import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        List<int[]> nodes = new ArrayList<>();
        Deque<Object[]> q = new ArrayDeque<>();
        q.offer(new Object[]{root, 0, 0});
        while (!q.isEmpty()) {
            Object[] cur = q.poll();
            TreeNode node = (TreeNode) cur[0];
            int row = (int) cur[1];
            int col = (int) cur[2];
            nodes.add(new int[]{col, row, node.val});
            if (node.left != null) q.offer(new Object[]{node.left, row + 1, col - 1});
            if (node.right != null) q.offer(new Object[]{node.right, row + 1, col + 1});
        }
        nodes.sort((a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            if (a[1] != b[1]) return a[1] - b[1];
            return a[2] - b[2];
        });
        int prevCol = Integer.MIN_VALUE;
        for (int[] t : nodes) {
            if (t[0] != prevCol) { ans.add(new ArrayList<>()); prevCol = t[0]; }
            ans.get(ans.size() - 1).add(t[2]);
        }
        return ans;
    }
}
