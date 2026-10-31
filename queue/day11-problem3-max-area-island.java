// Day 11 - Problem 3: Max Area of Island (LC 695) - Medium
// Topic: Queue (grid par BFS)
//
// PROBLEM (Hinglish):
// m x n binary grid di hai. 1 = land, 0 = paani. Island = 4-direction
// (up/down/left/right) se jude hue 1's ka group. Sabse bade island ka
// area (1's ki count) return karo. Koi island na ho to 0.
//
// APPROACH (Hinglish):
// Poori grid scan karo. Jahan 1 mile, wahan se BFS shuru karo - queue me
// cell daalo, use visited mark karo (0 kar do), aur 4 neighbours ko
// queue me daalte jao. BFS khatam hone par us island ka area mil jayega.
// Har island ka area track karo, max return karo.
//
// TIME COMPLEXITY: O(m * n) - har cell ek baar visit
// SPACE COMPLEXITY: O(m * n) - worst case queue (poori grid ek island ho)
//
// KEY LEARNING:
// Grid problems me BFS ke liye visited mark karna zaroori hai warna
// infinite loop lag jayega. Yahan grid ko hi 0 karke visited mark kiya -
// extra visited array ki zaroorat nahi padi.

import java.util.*;

class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0) return 0;
        int m = grid.length, n = grid[0].length;
        int maxArea = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // 4-direction
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    // Naya island mila - BFS se poora island naapo
                    int area = 0;
                    Queue<int[]> q = new LinkedList<>();
                    q.offer(new int[]{r, c});
                    grid[r][c] = 0; // visited mark
                    while (!q.isEmpty()) {
                        int[] cell = q.poll();
                        area++;
                        for (int[] d : dirs) {
                            int nr = cell[0] + d[0], nc = cell[1] + d[1];
                            if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                                grid[nr][nc] = 0; // visited mark
                                q.offer(new int[]{nr, nc});
                            }
                        }
                    }
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] grid1 = {{0,0,1,0,0,0,0,1,0,0,0,0,0},
                         {0,0,0,0,0,0,0,1,1,1,0,0,0},
                         {0,1,1,0,1,0,0,0,0,0,0,0,0},
                         {0,1,0,0,1,1,0,0,1,0,1,0,0},
                         {0,1,0,0,1,1,0,0,1,1,1,0,0},
                         {0,0,0,0,0,0,0,0,0,0,1,0,0},
                         {0,0,0,0,0,0,0,1,1,1,0,0,0},
                         {0,0,0,0,0,0,0,1,1,0,0,0,0}};
        System.out.println(s.maxAreaOfIsland(grid1));
        // Expected: 6
        System.out.println(s.maxAreaOfIsland(new int[][]{{0,0,0,0,0,0,0,0}}));
        // Expected: 0 (koi island nahi)
        System.out.println(s.maxAreaOfIsland(new int[][]{{1,1},{1,0}}));
        // Expected: 3
    }
}
