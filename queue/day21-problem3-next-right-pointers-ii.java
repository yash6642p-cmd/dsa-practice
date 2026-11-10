// Day 21 - Problem 3: Populating Next Right Pointers in Each Node II (LC 117) - Medium
// Topic: Queue (BFS level order - har level ke nodes ko jodo)
//
// PROBLEM (Hinglish):
// Binary tree ka har node ek extra `next` pointer rakhta hai.
// Har node ka `next` uske RIGHT wale neighbour (same level) ko
// point karna chahiye. Level ka aakhri node ka `next` = NULL.
// Tree PERFECT nahi hai - kisi bhi node ke 0, 1 ya 2 children
// ho sakte hain.
// Example: root = [1,2,3,4,5,null,7]
// Level 1: 1 -> NULL
// Level 2: 2 -> 3 -> NULL
// Level 3: 4 -> 5 -> 7 -> NULL
//
// APPROACH (Hinglish):
// BFS level order: queue me ek level ke nodes ek saath lo
// (level size = queue.size()). Level ke andar pehle node ko yaad
// rakho (prev), har agle node par prev.next = node kar do.
// Level khatam hote hi prev reset. Aakhri node ka next waise hi
// NULL rehta hai.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst O(n))
//
// KEY LEARNING:
// Level-order BFS me "ek level ek saath process karo" ka pattern
// (for loop queue.size() tak) bahut kaam aata hai - yahan usi se
// same-level neighbours mil jaate hain.

import java.util.*;

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;
    public Node() {}
    public Node(int val) { this.val = val; }
    public Node(int val, Node left, Node right, Node next) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.next = next;
    }
}

class Solution {
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelSize = q.size();
            Node prev = null; // is level ka pichhla node
            for (int i = 0; i < levelSize; i++) {
                Node node = q.poll();
                // pichhle node ka next = current node
                if (prev != null) {
                    prev.next = node;
                }
                prev = node;
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            // level ka aakhri node ka next waise hi null rehta hai
        }
        return root;
    }

    // TEST - next pointers se levels print karo
    public static void printLevels(Node root) {
        Node levelStart = root;
        while (levelStart != null) {
            Node node = levelStart;
            Node nextLevelStart = null;
            StringBuilder sb = new StringBuilder();
            while (node != null) {
                sb.append(node.val).append(" ");
                if (nextLevelStart == null) {
                    nextLevelStart = node.left != null ? node.left : node.right;
                }
                node = node.next;
            }
            System.out.println(sb.toString().trim());
            levelStart = nextLevelStart;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 - levels: 1 | 2 3 | 4 5 7
        Node t1 = new Node(1,
            new Node(2, new Node(4), new Node(5), null),
            new Node(3, null, new Node(7), null), null);
        s.connect(t1);
        printLevels(t1); // Expected: 1 / 2 3 / 4 5 7
        // single node
        Node t3 = new Node(1);
        s.connect(t3);
        printLevels(t3); // Expected: 1
        // skewed tree - har level me ek node
        Node t4 = new Node(1,
            new Node(2, new Node(3, new Node(4), null, null), null, null),
            null, null);
        s.connect(t4);
        printLevels(t4); // Expected: 1 / 2 / 3 / 4
    }
}
