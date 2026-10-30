// Day 7 - Problem 5: 01 Matrix (LC 542) - Medium
// ============================================================
// PROBLEM (Hinglish):
// 0/1 matrix me har cell ke liye sabse nazdeek 0 ki distance
// (up/down/left/right move = 1).
// Example: [[0,0,0],[0,1,0],[1,1,1]] -> [[0,0,0],[0,1,0],[1,2,1]]
//
// APPROACH (Hinglish):
// Har 1 se BFS slow hoga. Ulta: saare 0 cells ek saath queue me
// daalo (multi-source BFS), distance 0. BFS distance ke order me
// failta hai, to jo cell pehli baar visit hoga uski distance
// minimum hi hogi. Neighbour = current + 1.
//
// TIME COMPLEXITY: O(m * n)
// SPACE COMPLEXITY: O(m * n) - queue + distance matrix
//
// KEY LEARNING:
// "Har cell ka nearest X" = multi-source BFS. Yehi trick Rotting
// Oranges (LC 994, Day 4) me bhi thi.

import java.util.*;

class Solution {
    public int[][] updateMatrix(int[][] mat) {
        int rows = mat.length, cols = mat[0].length;
        int[][] dist = new int[rows][cols];
        Queue<int[]> q = new LinkedList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (mat[r][c] == 0) {
                    dist[r][c] = 0;
                    q.offer(new int[]{r, c});
                } else {
                    dist[r][c] = -1; // abhi unvisited
                }
            }
        }
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            int r = cell[0], c = cell[1];
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[][] m = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        for (int[] row : sol.updateMatrix(m)) {
            System.out.println(Arrays.toString(row));
        }
        // Expected: [0, 0, 0], [0, 1, 0], [1, 2, 1]
    }
}
