// Day 23 - Problem 3: Count Sub Islands (LC 1905) - Medium
// Topic: Queue (BFS flood fill - do grids compare karo)
//
// PROBLEM (Hinglish):
// Do m x n binary matrices grid1 aur grid2 di gayi hain (0 = paani,
// 1 = land). grid2 ka ek island SUB-ISLAND hai agar us island ke
// SAARE cells grid1 me bhi land hon (matlab grid1 ka koi island use
// poora cover karta ho). grid2 me kitne sub-islands hain, gino.
// Example 1:
// grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]]
// grid2 = [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
// -> output 3
// Example 2:
// grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]]
// grid2 = [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
// -> output 2
//
// APPROACH (Hinglish):
// grid2 ke har island pe BFS chalao (LC 200 Number of Islands jaisa).
// BFS ke dauraan ek flag rakho: agar island ka koi bhi cell grid1 me
// paani (0) hai, to ye island sub-island NAHI hai. Poora island ghumne
// ke baad flag abhi bhi true hai to count +1. Visited cells ko grid2
// me 0 kar do taaki dobara na ghumo.
//
// TIME COMPLEXITY: O(m*n) - har cell ek baar visit hota hai
// SPACE COMPLEXITY: O(m*n) - BFS queue (visited grid2 me hi mark hota hai)
//
// KEY LEARNING:
// Do grids wale problems me ek grid pe BFS chalao aur dusri grid ko
// "condition check" ki tarah use karo. Island ke saare cells ek saath
// check karne hote hain - ek cell bhi fail hua to poora island fail.

import java.util.*;

class Solution {
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid2[0].length;
        int count = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // grid2 me nayi land mili -> naya island shuru
                if (grid2[i][j] == 1) {
                    // BFS se poora island ghumo
                    boolean isSub = true;
                    Queue<int[]> q = new LinkedList<>();
                    q.offer(new int[]{i, j});
                    grid2[i][j] = 0; // visited mark
                    while (!q.isEmpty()) {
                        int[] cell = q.poll();
                        int r = cell[0], c = cell[1];
                        // grid1 me yahan paani hai -> sub-island nahi
                        if (grid1[r][c] == 0) {
                            isSub = false;
                        }
                        for (int[] d : dirs) {
                            int nr = r + d[0], nc = c + d[1];
                            if (nr >= 0 && nr < m && nc >= 0 && nc < n
                                    && grid2[nr][nc] == 1) {
                                grid2[nr][nc] = 0;
                                q.offer(new int[]{nr, nc});
                            }
                        }
                    }
                    // poora island grid1 ke andar hai -> sub-island
                    if (isSub) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        int[][] g1a = {{1,1,1,0,0},{0,1,1,1,1},{0,0,0,0,0},{1,0,0,0,0},{1,1,0,1,1}};
        int[][] g2a = {{1,1,1,0,0},{0,0,1,1,1},{0,1,0,0,0},{1,0,1,1,0},{0,1,0,1,0}};
        System.out.println(s.countSubIslands(g1a, g2a)); // Expected: 3
        // Example 2 (LeetCode)
        int[][] g1b = {{1,0,1,0,1},{1,1,1,1,1},{0,0,0,0,0},{1,1,1,1,1},{1,0,1,0,1}};
        int[][] g2b = {{0,0,0,0,0},{1,1,1,1,1},{0,1,0,1,0},{0,1,0,1,0},{1,0,0,0,1}};
        System.out.println(s.countSubIslands(g1b, g2b)); // Expected: 2
        // single cell sub-island
        System.out.println(s.countSubIslands(
            new int[][]{{1}}, new int[][]{{1}})); // Expected: 1
        // grid1 me paani, grid2 me land -> sub-island nahi
        System.out.println(s.countSubIslands(
            new int[][]{{0}}, new int[][]{{1}})); // Expected: 0
        // grid2 me koi land hi nahi
        System.out.println(s.countSubIslands(
            new int[][]{{1,1}}, new int[][]{{0,0}})); // Expected: 0
    }
}
