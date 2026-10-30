// Day 5 - Problem 4: Course Schedule (LC 207) - Medium
// ============================================================
// PROBLEM (Hinglish):
// numCourses courses (0 se numCourses-1). prerequisites pair [a, b]
// ka matlab: a se pehle b karna zaroori hai. Kya saare courses
// khatam karna possible hai?
// Example: numCourses=2, prerequisites=[[1,0]] -> true
//          numCourses=2, prerequisites=[[1,0],[0,1]] -> false (cycle)
//
// APPROACH (Hinglish) - Kahn's Algorithm (BFS topological sort):
// 1. Graph banao: b -> a edge, aur har course ka indegree gino.
// 2. Indegree 0 wale courses queue me daalo (ye abhi liye ja sakte hain).
// 3. Queue se course nikaalo, count badhao, uske aage walon ka
//    indegree ghatao; jiska 0 ho jaye use queue me daalo.
// 4. count == numCourses to true, warna cycle hai to false.
// Cycle wale courses ka indegree kabhi 0 nahi hota, isliye wo
// queue me kabhi nahi aate - yahi cycle detection hai.
//
// TIME COMPLEXITY: O(V + E)
// SPACE COMPLEXITY: O(V + E)
//
// KEY LEARNING:
// Dependencies wale sawaal = topological sort. Indegree 0 se shuru
// karo, queue se process karo, count se cycle pakdo.

import java.util.*;

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // Step 1: graph (prereq -> course) aur indegree
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
        int[] indegree = new int[numCourses];
        for (int[] p : prerequisites) {
            graph.get(p[1]).add(p[0]); // pehle p[1], phir p[0]
            indegree[p[0]]++;
        }

        // Step 2: bina prerequisite wale courses queue me
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) q.offer(i);
        }

        // Step 3: BFS
        int finished = 0;
        while (!q.isEmpty()) {
            int course = q.poll();
            finished++;
            for (int nxt : graph.get(course)) {
                indegree[nxt]--;
                if (indegree[nxt] == 0) q.offer(nxt);
            }
        }
        // Step 4: saare hue to true, warna cycle
        return finished == numCourses;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.canFinish(2, new int[][]{{1, 0}})); // Expected: true
        System.out.println(s.canFinish(2, new int[][]{{1, 0}, {0, 1}})); // Expected: false
        System.out.println(s.canFinish(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}));
        // Expected: true
    }
}
