// Day 4 - Problem 4: Rotting Oranges (LC 994) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Grid me: 0 = khaali, 1 = fresh orange, 2 = rotten orange.
// Har minute me har rotten orange apne 4 neighbours ke fresh
// oranges ko rotten kar deta hai. Sab fresh ko rotten hone me
// kitne minutes? Koi fresh kabhi rotten na ho sake to -1.
// Example: [[2,1,1],[1,1,0],[0,1,1]] -> 4
//
// APPROACH (Hinglish):
// Multi-source BFS! Shuru me SAARE rotten oranges queue me daalo
// (sab ek saath failte hain). Har level = 1 minute. Har level me
// saare rotten cells ke neighbours ko rotten karo, queue me daalo.
// End me fresh bacha ho to -1, warna minutes count.
//
// TIME COMPLEXITY: O(m*n) - har cell ek baar visit hota hai
// SPACE COMPLEXITY: O(m*n) - worst case queue me saare cells
//
// KEY LEARNING:
// Multi-source BFS: kai starting points ek saath fail rahe hon to
// sabko shuru me hi queue me daalo. Har BFS level = 1 time step.
// Ye pattern grid problems me baar-baar aata hai.

import java.util.*;

class Solution {
    public int orangesRotting(int[][] grid) {
        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> q = new LinkedList<>();
        int fresh = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 2) q.offer(new int[]{r, c});
                else if (grid[r][c] == 1) fresh++;
            }
        }
        if (fresh == 0) return 0;

        int minutes = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty() && fresh > 0) {
            minutes++; // ek level = ek minute
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                int[] cell = q.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                            && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        fresh--;
                        q.offer(new int[]{nr, nc});
                    }
                }
            }
        }
        return fresh == 0 ? minutes : -1;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.orangesRotting(new int[][]{
            {2, 1, 1}, {1, 1, 0}, {0, 1, 1}})); // Expected: 4
        System.out.println(s.orangesRotting(new int[][]{
            {2, 1, 1}, {0, 1, 1}, {1, 0, 1}})); // Expected: -1
    }
}
