// Day 10 - Problem 5: As Far from Land as Possible (LC 1162) - Medium
// Topic: Queue (Multi-source BFS on a 2D grid)
//
// PROBLEM (Hinglish):
// Ek n x n grid hai, 1 = land, 0 = water. Aisi water cell dhoondhni
// hai jo apne sabse nazdeek wale land se sabse DOOR ho, aur woh
// distance (Manhattan distance) wapas karni hai.
// Example 1: [[1,0,1],[0,0,0],[1,0,1]] -> 2
// Example 2: [[1,0,0],[0,0,0],[0,0,0]] -> 4
// Agar grid me sirf land ho ya sirf water ho to -1.
//
// APPROACH (Hinglish):
// Yeh "multi-source BFS" hai - Day 9 ke 01 Matrix (LC 542) ka twin.
// Har water cell se land dhoondhne ki bajay, saari land cells ko
// EK SAATH queue me daal do aur BFS ki leher ki tarah failao. Jo
// water cell sabse aakhir me milegi, wahi sabse door wali hai -
// kyunki BFS hamesha shortest distance pehle pahunchta hai.
//
// TIME COMPLEXITY: O(n * n) - har cell zyada se zyada ek baar
// SPACE COMPLEXITY: O(n * n) - queue me saari cells aa sakti hain
//
// KEY LEARNING:
// "Sabse nazdeek X" wale problems me saare X ko ek saath source
// banao - multi-source BFS. Isse O(n^4) wala brute force O(n^2)
// ho jaata hai.

import java.util.*;

class Solution {
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        Queue<int[]> q = new LinkedList<>(); // har entry: {row, col, distance}
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    q.offer(new int[]{r, c, 0}); // saari land cells = sources
                }
            }
        }
        // Edge cases: na land ho, na water ho
        if (q.isEmpty() || q.size() == n * n) {
            return -1;
        }

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int maxDist = -1;
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int r = cell[0], c = cell[1], dist = cell[2];
            maxDist = dist; // BFS order me aakhri = sabse bada
            for (int[] d : directions) {
                int nr = r + d[0], nc = c + d[1];
                // Boundary check + sirf unvisited water
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                    grid[nr][nc] = 1; // visit mark karo
                    q.offer(new int[]{nr, nc, dist + 1});
                }
            }
        }
        return maxDist;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maxDistance(new int[][]{
            {1,0,1},{0,0,0},{1,0,1}}));
        // Expected: 2
        System.out.println(s.maxDistance(new int[][]{
            {1,0,0},{0,0,0},{0,0,0}}));
        // Expected: 4
        System.out.println(s.maxDistance(new int[][]{
            {1,1},{1,1}}));
        // Expected: -1 (koi water nahi)
        System.out.println(s.maxDistance(new int[][]{
            {0,0},{0,0}}));
        // Expected: -1 (koi land nahi)
    }
}
