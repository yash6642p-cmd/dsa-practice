// Day 10 - Problem 1: Maximum Depth of N-ary Tree (LC 559) - Easy
// Topic: Queue (BFS level-order on an N-ary tree)
//
// PROBLEM (Hinglish):
// Ek N-ary tree diya hai - har node ke 0 ya zyada children ho
// sakte hain. Tree ki maximum depth nikalni hai, yaani root se
// sabse door wale leaf tak kitne nodes ka rasta hai.
// Example: root = [1,null,3,2,4,null,5,6] -> depth 3
// Agar tree khaali hai to depth 0.
//
// APPROACH (Hinglish):
// Queue wali BFS chalao, level by level. Root ko queue me daalo,
// phir jab tak queue khaali na ho: us level ke saare nodes nikaalo,
// unke saare children queue me daal do, aur level counter +1 karo.
// Aakhri level ka count hi maximum depth hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar queue me aata hai
// SPACE COMPLEXITY: O(w) - queue me zyada se zyada ek level ke nodes
//
// KEY LEARNING:
// BFS ka level-size wala loop kisi bhi tree ki depth nikalne ka
// sabse seedha tareeka hai - binary ho ya N-ary.

import java.util.*;

class Node {
    public int val;
    public List<Node> children;
    public Node(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }
    public Node(int val, List<Node> children) {
        this.val = val;
        this.children = children;
    }
}

class Solution {
    public int maxDepth(Node root) {
        if (root == null) {
            return 0; // khaali tree ki depth 0
        }
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        int depth = 0;
        while (!q.isEmpty()) {
            int levelSize = q.size(); // is level ke saare nodes
            for (int i = 0; i < levelSize; i++) {
                Node node = q.poll();
                for (Node child : node.children) {
                    q.offer(child);
                }
            }
            depth++; // ek level poora ho gaya
        }
        return depth;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Tree: 1 -> [3, 2, 4], 3 -> [5, 6]  (LC example 1)
        Node root = new Node(1, Arrays.asList(
            new Node(3, Arrays.asList(new Node(5), new Node(6))),
            new Node(2),
            new Node(4)));
        System.out.println(s.maxDepth(root));
        // Expected: 3
        System.out.println(s.maxDepth(null));
        // Expected: 0
        System.out.println(s.maxDepth(new Node(7)));
        // Expected: 1 (sirf root)
    }
}
