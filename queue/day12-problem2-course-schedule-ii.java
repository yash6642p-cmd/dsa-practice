// Day 12 - Problem 2: Course Schedule II (LC 210) - Medium
// Topic: Queue (Kahn's algorithm - topological sort with BFS)
//
// PROBLEM (Hinglish):
// numCourses courses hain (0 se numCourses-1 tak). prerequisites me har
// pair [a, b] ka matlab hai: course a lene se pehle course b lena zaroori hai.
// Aisi koi bhi valid order return karo jisme saare courses ho sakein.
// Agar koi valid order possible nahi (cycle hai), to [] return karo.
// Example: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
// -> output [0,1,2,3] (ya [0,2,1,3], dono sahi hain)
//
// APPROACH (Hinglish):
// Kahn's algorithm: har course ka indegree gino aur adjacency list banao.
// Jinke indegree 0 hai, unhe queue me daalo. Queue se course nikalo,
// order me daalo, aur uske baad wale courses ka indegree 1 ghatao -
// jo 0 ho jaye use queue me daal do. Ant me agar order ki length
// numCourses ke barabar nahi hai, matlab cycle thi, to [] do.
//
// TIME COMPLEXITY: O(V + E) - har course aur har prerequisite ek baar
// SPACE COMPLEXITY: O(V + E) - adjacency list + queue + indegree array
//
// KEY LEARNING:
// "Kis order me kaam karne hain jab dependencies hon" = topological sort.
// Kahn's BFS version me indegree 0 wale nodes hamesha pehle aate hain.

import java.util.*;

class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            int a = p[0], b = p[1];
            adj.get(b).add(a);
            indegree[a]++; // a ka ek prerequisite aur badh gaya
        }
        Queue<Integer> q = new LinkedList<>();
        for (int c = 0; c < numCourses; c++) {
            if (indegree[c] == 0) q.offer(c);
        }
        int[] order = new int[numCourses];
        int idx = 0;
        while (!q.isEmpty()) {
            int course = q.poll();
            order[idx++] = course;
            for (int nxt : adj.get(course)) {
                indegree[nxt]--;
                if (indegree[nxt] == 0) q.offer(nxt);
            }
        }
        // saare courses order me nahi aaye = cycle hai
        if (idx != numCourses) return new int[0];
        return order;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(s.findOrder(2, new int[][]{{1, 0}})));
        // Expected: [0, 1]
        System.out.println(Arrays.toString(
            s.findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));
        // Expected: [0, 1, 2, 3]
        System.out.println(Arrays.toString(
            s.findOrder(2, new int[][]{{1, 0}, {0, 1}})));
        // Expected: []
        System.out.println(Arrays.toString(s.findOrder(1, new int[][]{})));
        // Expected: [0]
    }
}
