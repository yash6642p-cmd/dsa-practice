// Day 15 - Problem 5: Shortest Path in a Grid with Obstacles Elimination (LC 1293) - Hard (stretch)
// Topic: Queue (BFS with state)
//
// PROBLEM (Hinglish):
// m x n grid diya hai: 0 = khaali cell, 1 = obstacle. Tum (0,0) se
// (m-1,n-1) tak ja sakte ho, up/down/left/right. Tum AT MOST k
// obstacles hata sakte ho. Minimum steps return karo; na pahunche
// to -1.
// Example: grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
// -> 6
//
// APPROACH (Hinglish):
// Normal BFS me state sirf (row, col) hoti hai, yahan state
// (row, col, bacha hua k) hai. Queue me (row, col, k_bacha, steps)
// daalo. Ek cell par dobara tabhi jao jab is baar zyada k bacha
// ho. Isliye visited[row][col] me ab tak ka MAXIMUM bacha hua k
// rakho; naya k usse kam ya barabar ho to skip karo. Pehli baar
// destination mile to wahi shortest hai (BFS ki guarantee).
//
// TIME COMPLEXITY: O(m * n * k) - har cell, har k value ke saath
// SPACE COMPLEXITY: O(m * n) - queue + visited (max k se compress)
//
// KEY LEARNING:
// Jab koi resource (yahan k) raaste par depend kare, to BFS state
// me woh resource bhi daalo. Sirf cell visited mark karna galat
// hoga.

import java.util.*;

class Solution {
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        if (m == 1 && n == 1) {
            return 0;
        }
        // visited[r][c] = is cell par ab tak ka max bacha hua k
        int[][] visited = new int[m][n];
        for (int[] row : visited) {
            Arrays.fill(row, -1);
        }
        int startK = k - grid[0][0];
        if (startK < 0) {
            return -1;
        }
        visited[0][0] = startK;
        // queue: [row, col, bacha hua k, steps]
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0, startK, 0});
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1], rem = cur[2], steps = cur[3];
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }
                int nrem = rem - grid[nr][nc];
                if (nrem < 0) {
                    continue;
                }
                if (nr == m - 1 && nc == n - 1) {
                    return steps + 1;
                }
                // sirf tab aage badho jab is cell par zyada k ke
                // saath pahunche hon (warna yeh raasta bekaar hai)
                if (nrem > visited[nr][nc]) {
                    visited[nr][nc] = nrem;
                    q.offer(new int[]{nr, nc, nrem, steps + 1});
                }
            }
        }
        return -1;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] g1 = {{0, 0, 0}, {1, 1, 0}, {0, 0, 0}, {0, 1, 1}, {0, 0, 0}};
        System.out.println(s.shortestPath(g1, 1)); // Expected: 6
        int[][] g2 = {{0, 1, 1}, {1, 1, 1}, {1, 0, 0}};
        System.out.println(s.shortestPath(g2, 1)); // Expected: -1
        int[][] g3 = {{0, 0}, {0, 0}};
        System.out.println(s.shortestPath(g3, 0)); // Expected: 2
    }
}
