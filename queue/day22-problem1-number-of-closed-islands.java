// Day 22 - Problem 1: Number of Closed Islands (LC 1254) - Medium
// Topic: Queue (BFS - boundary se judi land ko pehle hatao)
//
// PROBLEM (Hinglish):
// Grid me 0 = land aur 1 = paani hai. Island = 4-direction se jude
// hue 0s ka group. CLOSED island wo hai jo chaaro taraf se paani se
// ghira ho - matlab uska koi bhi cell grid ki BOUNDARY ko touch na
// karta ho. Aise closed islands gin kar return karo.
// Example: grid = [[1,1,1],[1,0,1],[1,1,1]] -> output 1
// Example: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]] -> output 1
//
// APPROACH (Hinglish):
// Har unvisited land cell se BFS chalao. BFS ke dauraan check karo
// ki island ka koi cell boundary pe hai ya nahi. Boundary touch hui
// to wo island open hai, warna closed - count badhao.
//
// TIME COMPLEXITY: O(m*n) - har cell ek baar visit hota hai
// SPACE COMPLEXITY: O(m*n) - visited array + BFS queue
//
// KEY LEARNING:
// "Closed" ka matlab boundary se door. Boundary wali land ko pehle
// hata dena problem ko simple bana deta hai - bachi hui land sirf
// gini jaati hai. Ye LC 1020 (Number of Enclaves) ka ulta hai.

import java.util.*;

class Solution {
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int closed = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // land mili jo abhi visit nahi hui -> naya island
                if (grid[i][j] == 0 && !visited[i][j]) {
                    // BFS se poora island ghumo, boundary touch hui ya nahi
                    boolean touchesBoundary = false;
                    Queue<int[]> q = new LinkedList<>();
                    q.offer(new int[]{i, j});
                    visited[i][j] = true;
                    while (!q.isEmpty()) {
                        int[] cell = q.poll();
                        int r = cell[0], c = cell[1];
                        // kinare pe hai to ye island closed NAHI hai
                        if (r == 0 || r == m - 1 || c == 0 || c == n - 1) {
                            touchesBoundary = true;
                        }
                        for (int[] d : dirs) {
                            int nr = r + d[0], nc = c + d[1];
                            if (nr >= 0 && nr < m && nc >= 0 && nc < n
                                    && grid[nr][nc] == 0 && !visited[nr][nc]) {
                                visited[nr][nc] = true;
                                q.offer(new int[]{nr, nc});
                            }
                        }
                    }
                    // boundary touch nahi hui -> closed island
                    if (!touchesBoundary) {
                        closed++;
                    }
                }
            }
        }
        return closed;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        int[][] g1 = {{1,1,1,1,1,1,1,0},{1,0,0,0,0,1,1,0},{1,0,1,0,1,1,1,0},
                      {1,0,0,0,0,1,0,1},{1,1,1,1,1,1,1,0}};
        System.out.println(s.closedIsland(g1)); // Expected: 2
        // Example 2 (LeetCode)
        int[][] g2 = {{0,0,1,0,0},{0,1,0,1,0},{0,1,1,1,0}};
        System.out.println(s.closedIsland(g2)); // Expected: 1
        // single beech wala cell
        int[][] g3 = {{1,1,1},{1,0,1},{1,1,1}};
        System.out.println(s.closedIsland(g3)); // Expected: 1
        // saari land boundary pe hai - koi closed nahi
        int[][] g4 = {{0,0,0},{0,0,0},{0,0,0}};
        System.out.println(s.closedIsland(g4)); // Expected: 0
        // sirf paani - koi island nahi
        int[][] g5 = {{1,1},{1,1}};
        System.out.println(s.closedIsland(g5)); // Expected: 0
    }
}
