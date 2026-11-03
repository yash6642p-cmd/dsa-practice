// Day 14 - Problem 2: Number of Enclaves (LC 1020) - Medium
// Topic: Queue (boundary BFS / flood fill)
//
// PROBLEM (Hinglish):
// m x n grid: 1 = land, 0 = sea. Un land cells ki ginti return karo
// jo boundary tak kabhi nahi pahunch sakte (enclaves).
// Example: [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]] -> 3
//
// APPROACH (Hinglish):
// Jo land boundary se connected hai, woh enclave nahi ho sakta.
// Pehle chaaron kinaron ke land cells se BFS flood fill chalao aur
// unhe duba do (0 kar do). Jo land bach jaye, wahi enclaves hain.
//
// TIME COMPLEXITY: O(m*n) - har cell zyada se zyada ek baar visit hota hai
// SPACE COMPLEXITY: O(m*n) - worst case me queue me saari cells aa sakti hain
//
// KEY LEARNING:
// "Kaun bach sakta hai" ke bajay "kaun bach NAHI sakta" ko hatana
// aksar aasan hota hai - boundary flood fill ke baad bacha hua
// hisaab hi jawab hota hai.

import java.util.*;

class Solution {
    public int numEnclaves(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        Queue<int[]> q = new LinkedList<>();
        // chaaron kinaron ke land cells queue me daalo
        for (int r = 0; r < rows; r++) {
            for (int c : new int[]{0, cols - 1}) {
                if (grid[r][c] == 1) {
                    q.offer(new int[]{r, c});
                    grid[r][c] = 0; // duba diya
                }
            }
        }
        for (int c = 0; c < cols; c++) {
            for (int r : new int[]{0, rows - 1}) {
                if (grid[r][c] == 1) {
                    q.offer(new int[]{r, c});
                    grid[r][c] = 0; // duba diya
                }
            }
        }
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        // BFS: boundary se judi saari land dubao
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            for (int[] d : dirs) {
                int nr = cell[0] + d[0];
                int nc = cell[1] + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == 1) {
                    grid[nr][nc] = 0;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        // jo land bach gayi, wahi enclaves hain
        int count = 0;
        for (int[] row : grid) {
            for (int v : row) {
                count += v;
            }
        }
        return count;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numEnclaves(new int[][]{
            {0, 0, 0, 0}, {1, 0, 1, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}})); // Expected: 3
        System.out.println(s.numEnclaves(new int[][]{
            {0, 1, 1, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 0}})); // Expected: 0
        System.out.println(s.numEnclaves(new int[][]{
            {0, 0, 0}, {0, 1, 0}, {0, 0, 0}})); // Expected: 1
    }
}
