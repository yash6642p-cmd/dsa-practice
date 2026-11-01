// Day 12 - Problem 5: Populating Next Right Pointers in Each Node (LC 116) - Medium
// Topic: Queue (BFS level order)
//
// PROBLEM (Hinglish):
// Ek perfect binary tree diya hai (har parent ke 2 children, saare leaves
// ek hi level par). Har node ka next pointer uske same level wale next
// right node ki taraf set karo. Agar koi next right node nahi hai, to
// next = null rehne do. Root wapas return karo.
// Example: root = [1,2,3,4,5,6,7] -> level 2: 2->3, level 3: 4->5->6->7
//
// APPROACH (Hinglish):
// Level order BFS: queue me root daalo. Har level me nodes ko ek-ek karke
// nikalo - pichhle node ka next current node se jod do (prev.next = node),
// phir uske children queue me daal do. Level khatam hote hi prev reset
// kar do, taaki agle level ka pehla node pichhle level se na jud jaye.
//
// TIME COMPLEXITY: O(n) - har node ek baar visit hota hai
// SPACE COMPLEXITY: O(w) - queue me ek level ke nodes (worst case O(n))
//
// KEY LEARNING:
// Level ke andar nodes ko aapas me jodna ho to BFS me "prev" variable
// ka pattern yaad rakho: prev.next = current, phir prev = current.

import java.util.*;

class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;
    public Node() {}
    public Node(int _val) { val = _val; }
    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
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
                if (prev != null) {
                    prev.next = node; // pichhle node ko current se jodo
                }
                prev = node;
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            // level ka aakhri node ka next pehle se null hai
        }
        return root;
    }

    // TEST: next pointers se levels print karo
    public static void main(String[] args) {
        Solution s = new Solution();
        Node t1 = new Node(1,
            new Node(2, new Node(4), new Node(5), null),
            new Node(3, new Node(6), new Node(7), null), null);
        Node r = s.connect(t1);
        Node levelStart = r;
        while (levelStart != null) {
            Node cur = levelStart;
            while (cur != null) {
                System.out.print(cur.val + " ");
                cur = cur.next;
            }
            System.out.println();
            levelStart = levelStart.left;
        }
        // Expected:
        // 1
        // 2 3
        // 4 5 6 7
        System.out.println(s.connect(null)); // Expected: null
    }
}
