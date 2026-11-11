// Day 22 - Problem 3: Complete Binary Tree Inserter (LC 919) - Medium
// Topic: Queue (design - queue se incomplete nodes track karo)
//
// PROBLEM (Hinglish):
// Complete binary tree matlab: har level poora bhara hai, sirf aakhri
// level adhura ho sakta hai, aur aakhri level ke nodes bilkul LEFT se
// bhare hote hain. Ek class banao:
// - CBTInserter(root): diye hue complete tree se start karo
// - insert(v): naya node daalo taaki tree complete hi rahe, aur uske
//   PARENT ka value return karo
// - get_root(): tree ka root return karo
// Example: root = [1,2], insert(3) -> 1 (3, 1 ka right child bana)
//          insert(4) -> 2 (4, 2 ka left child bana)
//
// APPROACH (Hinglish):
// Complete tree me insert hamesha pehle us node ke neeche hota hai
// jiska koi child missing hai - aur wo node BFS (level order) me
// sabse pehle aata hai. Isliye ek queue me saare "incomplete" nodes
// (jinke left ya right child missing hai) rakho. Insert karte time
// queue ke pehle node ke neeche daalo: left khaali ho to left, warna
// right. Agar dono bhar gaye to use queue se hatao, aur naya node
// queue me daalo (wo bhi incomplete hai). Constructor me BFS se
// queue bharo - O(n) ek baar, phir har insert O(1).
//
// TIME COMPLEXITY: constructor O(n), insert O(1), get_root O(1)
// SPACE COMPLEXITY: O(n) - queue me incomplete nodes
//
// KEY LEARNING:
// Design problems me "kaunsa node next hai" ye pehle se track karke
// rakho - har baar poora tree scan karna waste hai. Queue yahan
// "kaam ki list" hai: jiska kaam bacha hai, wo queue me hai.

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

class CBTInserter {
    private TreeNode root;
    // queue me sirf incomplete nodes (kisi ka child missing hai)
    private Queue<TreeNode> q;

    public CBTInserter(TreeNode root) {
        this.root = root;
        q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.peek();
            if (node.left != null) {
                q.offer(node.left);
            }
            if (node.right != null) {
                q.offer(node.right);
            }
            // dono child hain to ye node poora hai - queue se hatao
            if (node.left != null && node.right != null) {
                q.poll();
            } else {
                break;
            }
        }
    }

    public int insert(int val) {
        // queue ke pehle node ke neeche daalo
        TreeNode parent = q.peek();
        TreeNode newNode = new TreeNode(val);
        if (parent.left == null) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
            // ab parent ke dono child bhar gaye - queue se hatao
            q.poll();
        }
        // naya node bhi incomplete hai - queue me daalo
        q.offer(newNode);
        return parent.val;
    }

    public TreeNode get_root() {
        return root;
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

    static List<Integer> treeToList(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                out.add(null);
                continue;
            }
            out.add(node.val);
            q.offer(node.left);
            q.offer(node.right);
        }
        // peeche wale null hatao
        while (!out.isEmpty() && out.get(out.size() - 1) == null) {
            out.remove(out.size() - 1);
        }
        return out;
    }

    // TEST
    public static void main(String[] args) {
        // Example 1 (LeetCode): root = [1,2]
        CBTInserter ins = new CBTInserter(buildTree(new Integer[]{1, 2}));
        System.out.println(ins.insert(3)); // Expected: 1
        System.out.println(ins.insert(4)); // Expected: 2
        System.out.println(treeToList(ins.get_root())); // Expected: [1, 2, 3, 4]
        // bada tree: [1,2,3,4,5,6] me insert karo
        CBTInserter ins2 = new CBTInserter(buildTree(new Integer[]{1, 2, 3, 4, 5, 6}));
        System.out.println(ins2.insert(7)); // Expected: 3
        System.out.println(ins2.insert(8)); // Expected: 4
        System.out.println(treeToList(ins2.get_root())); // Expected: [1, 2, 3, 4, 5, 6, 7, 8]
        // single node tree
        CBTInserter ins3 = new CBTInserter(buildTree(new Integer[]{10}));
        System.out.println(ins3.insert(20)); // Expected: 10
        System.out.println(ins3.insert(30)); // Expected: 10
        System.out.println(treeToList(ins3.get_root())); // Expected: [10, 20, 30]
    }
}
