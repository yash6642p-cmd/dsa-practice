// Day 8 - Problem 5: Shortest Path in Binary Matrix (LC 1091) - Medium
// ============================================================
// PROBLEM (Hinglish):
// n x n grid, 0 = khaali, 1 = blocked. (0,0) se (n-1,n-1) tak
// shortest path ki LENGTH (starting cell bhi gini jaati hai).
// 8 directions me chal sakte ho. Koi path na ho to -1.
// Example: [[0,1],[1,0]] -> 2
// Example: [[0,0,0],[1,1,0],[1,1,0]] -> 4
//
// APPROACH (Hinglish):
// BFS grid pe: queue me (row, col, steps) rakho. Destination
// pehli baar mile = shortest path, kyunki BFS level by level
// chalta hai. Start/end blocked ho to seedha -1. 8 directions
// ki list likhna diagonal bhoolne se bachata hai.
//
// TIME COMPLEXITY: O(n^2) - har cell ek baar
// SPACE COMPLEXITY: O(n^2) - queue + visited

import java.util.*;

class Solution {
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) return -1;
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
                        {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> q = new LinkedList<>();
        visited[0][0] = true;
        q.offer(new int[]{0, 0, 1}); // row, col, steps (start = 1)
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int row = curr[0], col = curr[1], steps = curr[2];
            if (row == n - 1 && col == n - 1) return steps;
            for (int[] d : dirs) {
                int nr = row + d[0], nc = col + d[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n
                        && grid[nr][nc] == 0 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc, steps + 1});
                }
            }
        }
        return -1;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.shortestPathBinaryMatrix(
            new int[][]{{0, 1}, {1, 0}})); // Expected: 2
        System.out.println(s.shortestPathBinaryMatrix(
            new int[][]{{0, 0, 0}, {1, 1, 0}, {1, 1, 0}})); // Expected: 4
        System.out.println(s.shortestPathBinaryMatrix(
            new int[][]{{0}})); // Expected: 1
    }
}
