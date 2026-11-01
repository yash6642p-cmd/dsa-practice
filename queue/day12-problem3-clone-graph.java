// Day 12 - Problem 3: Clone Graph (LC 133) - Medium
// Topic: Queue (BFS + HashMap)
//
// PROBLEM (Hinglish):
// Ek connected undirected graph ke kisi ek node ka reference diya hai.
// Poore graph ki deep copy (clone) return karo - naye nodes hone chahiye,
// purane nodes ka reference nahi. Har node me val aur neighbors ki list hai.
// Example: adjList = [[2,4],[1,3],[2,4],[1,3]] -> output wahi adjacency
// (lekin naye nodes ke saath)
//
// APPROACH (Hinglish):
// BFS + map: purana node -> naya node ka map banao. Start node ka clone
// banao, map me daalo, queue me daalo. Queue se node nikalo, uske har
// neighbor ke liye: agar neighbor ka clone abhi nahi bana, to banao aur
// queue me daalo; phir current node ke clone ke neighbors me us neighbor
// ka clone jod do. Map hi cycle se bachata hai - dobara visit nahi hota.
//
// TIME COMPLEXITY: O(V + E) - har node aur har edge ek baar
// SPACE COMPLEXITY: O(V) - map + queue
//
// KEY LEARNING:
// Deep copy me sabse badi galti cycle me phasna hai. Map me node ko
// banate hi daal do (neighbors jodne se pehle) - yahi cycle ka ilaaj hai.

import java.util.*;

class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        // purana node -> naya node
        Map<Node, Node> cloneMap = new HashMap<>();
        cloneMap.put(node, new Node(node.val));
        Queue<Node> q = new LinkedList<>();
        q.offer(node);
        while (!q.isEmpty()) {
            Node cur = q.poll();
            for (Node nb : cur.neighbors) {
                if (!cloneMap.containsKey(nb)) {
                    cloneMap.put(nb, new Node(nb.val));
                    q.offer(nb);
                }
                cloneMap.get(cur).neighbors.add(cloneMap.get(nb));
            }
        }
        return cloneMap.get(node);
    }

    // TEST: clone ka val aur neighbors print karo
    public static void main(String[] args) {
        Solution s = new Solution();
        Node n1 = new Node(1), n2 = new Node(2), n3 = new Node(3), n4 = new Node(4);
        n1.neighbors.addAll(Arrays.asList(n2, n4));
        n2.neighbors.addAll(Arrays.asList(n1, n3));
        n3.neighbors.addAll(Arrays.asList(n2, n4));
        n4.neighbors.addAll(Arrays.asList(n1, n3));
        Node c = s.cloneGraph(n1);
        System.out.println(c.val); // Expected: 1
        System.out.println(c.neighbors.size()); // Expected: 2
        System.out.println(c != n1); // Expected: true (deep copy)
        System.out.println(s.cloneGraph(null)); // Expected: null
    }
}
