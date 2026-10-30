// Day 8 - Problem 3: N-ary Tree Level Order Traversal (LC 429) - Medium
// ============================================================
// PROBLEM (Hinglish):
// N-ary tree ka level order: har node ke kitne bhi children ho
// sakte hain. Levels ko alag-alag lists me return karo.
// Example: [1,null,3,2,4,null,5,6] -> [[1],[3,2,4],[5,6]]
//
// APPROACH (Hinglish):
// Binary level-order jaisa hi, bas left/right ki jagah
// node.children ki poori loop queue me daalo. Har level ke liye
// queue-size snapshot lo - wahi level ki boundary hai.
//
// TIME COMPLEXITY: O(n)
// SPACE COMPLEXITY: O(w) - ek level ke nodes + result

import java.util.*;

// LeetCode ka Node definition
class Node {
    public int val;
    public List<Node> children;
    public Node() {}
    public Node(int val) { this.val = val; }
    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }
}

class Solution {
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size(); // is level me kitne nodes hain
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node node = q.poll();
                level.add(node.val);
                if (node.children != null) {
                    for (Node child : node.children) {
                        q.offer(child);
                    }
                }
            }
            result.add(level);
        }
        return result;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Node root = new Node(1, Arrays.asList(
            new Node(3, Arrays.asList(new Node(5), new Node(6))),
            new Node(2), new Node(4)));
        System.out.println(new Solution().levelOrder(root)); // Expected: [[1], [3, 2, 4], [5, 6]]
        System.out.println(new Solution().levelOrder(null)); // Expected: []
    }
}
