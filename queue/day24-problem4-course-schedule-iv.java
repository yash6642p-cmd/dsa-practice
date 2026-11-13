// Day 24 - Problem 4: Course Schedule IV (LC 1462) - Medium
// Topic: Queue (topological sort / reachability - kaun kiska prerequisite hai)
//
// PROBLEM (Hinglish):
// Total numCourses courses hain (0 se numCourses-1). prerequisites[i]
// = [ai, bi] ka matlab hai course ai pehle lena hoga course bi se
// pehle. Prerequisites INDIRECT bhi ho sakte hain: a -> b aur b -> c
// ho to a, c ka bhi prerequisite hai.
// queries[j] = [uj, vj] poochta hai: kya course uj, course vj ka
// prerequisite hai (direct ya indirect)? Har query ka True/False
// answer list me return karo.
// Example: numCourses = 2, prerequisites = [[1,0]],
//   queries = [[0,1],[1,0]] -> output [false, true]
// Example: numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]],
//   queries = [[1,0],[1,2]] -> output [true, true]
//
// APPROACH (Hinglish):
// Ye "transitive closure" hai - kaun kisko reach kar sakta hai.
// Floyd-Warshall jaisi soch: reach[i][j] = true agar i se j tak
// raasta hai. Pehle direct prerequisites ko true karo. Phir har
// beech wale node k ke liye: agar i -> k aur k -> j hai to i -> j
// bhi true kar do. Ant me har query ka jawab reach table se padho.
// (n <= 100 hai, isliye O(n^3) bilkul chalega.)
//
// TIME COMPLEXITY: O(n^3) - Floyd-Warshall triple loop
// SPACE COMPLEXITY: O(n^2) - reachability table
//
// KEY LEARNING:
// "Indirect bhi count karo" sunte hi transitive closure yaad karo.
// Chhote n (<= 100) par Floyd-Warshall sabse simple aur safe tareeka
// hai - BFS har query par bhi chalega par table ek baar banao to
// har query O(1) me jawab deti hai.

import java.util.*;

class Solution {
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        // reach[i][j] = true agar i se j tak raasta hai (i, j ka prerequisite)
        boolean[][] reach = new boolean[numCourses][numCourses];
        // direct prerequisites daalo
        for (int[] p : prerequisites) {
            reach[p[0]][p[1]] = true;
        }
        // Floyd-Warshall: beech wale node k se raasta jodo
        for (int k = 0; k < numCourses; k++) {
            for (int i = 0; i < numCourses; i++) {
                if (reach[i][k]) {
                    for (int j = 0; j < numCourses; j++) {
                        if (reach[k][j]) {
                            reach[i][j] = true;
                        }
                    }
                }
            }
        }
        // har query ka jawab table se
        List<Boolean> ans = new ArrayList<>();
        for (int[] q : queries) {
            ans.add(reach[q[0]][q[1]]);
        }
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        System.out.println(s.checkIfPrerequisite(2,
            new int[][]{{1, 0}}, new int[][]{{0, 1}, {1, 0}})); // Expected: [false, true]
        // Example 2 (LeetCode)
        System.out.println(s.checkIfPrerequisite(2,
            new int[][]{}, new int[][]{{1, 0}, {0, 1}})); // Expected: [false, false]
        // Example 3 (LeetCode)
        System.out.println(s.checkIfPrerequisite(3,
            new int[][]{{1, 2}, {1, 0}, {2, 0}}, new int[][]{{1, 0}, {1, 2}})); // Expected: [true, true]
        // lambi chain: 3 -> 2 -> 1 -> 0, to 3 sabka prerequisite
        System.out.println(s.checkIfPrerequisite(4,
            new int[][]{{1, 0}, {2, 1}, {3, 2}}, new int[][]{{0, 1}, {3, 1}})); // Expected: [false, true]
        // diamond: 0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3
        System.out.println(s.checkIfPrerequisite(4,
            new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}}, new int[][]{{0, 3}, {1, 2}})); // Expected: [true, false]
    }
}
