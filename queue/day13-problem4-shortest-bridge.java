// Day 13 - Problem 4: Shortest Bridge (LC 934) - Medium
// Topic: Queue (multi-source BFS)
//
// PROBLEM (Hinglish):
// Ek n x n grid me exactly do islands hain (1 = land, 0 = water).
// Kuch 0 ko 1 me badalkar dono islands ko jodna hai. Minimum kitne
// 0 flip karne padenge, woh return karo.
// Example: grid = [[0,1],[1,0]] -> output 1
//
// APPROACH (Hinglish):
// Step 1: Pehli island dhoondho, uske saare cells ko 2 mark karo aur
// BFS queue me daalo.
// Step 2: Multi-source BFS chalao - pehli island ke saare cells ek
// saath failte hain. Jab kisi doosri island ke cell (1) tak pahunche,
// us waqt ke steps hi minimum flips hain.
//
// TIME COMPLEXITY: O(n^2) - har cell ek baar visit hota hai
// SPACE COMPLEXITY: O(n^2) - queue ke liye (worst case)
//
// KEY LEARNING:
// Multi-source BFS: ek se zyada starting points ek saath queue me
// daalne se shortest distance sabse nazdeeki source se milti hai.

import java.util.*;

class Solution {
    public int shortestBridge(int[][] grid) {
        int n = grid.length;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<int[]> q = new LinkedList<>();

        // Step 1: pehli island dhoondho, mark karo, queue me daalo
        boolean found = false;
        for (int r = 0; r < n && !found; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    Deque<int[]> stack = new ArrayDeque<>();
                    stack.push(new int[]{r, c});
                    grid[r][c] = 2;
                    while (!stack.isEmpty()) {
                        int[] cell = stack.pop();
                        q.offer(cell);
                        for (int[] d : dirs) {
                            int nr = cell[0] + d[0], nc = cell[1] + d[1];
                            if (nr >= 0 && nr < n && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                                grid[nr][nc] = 2;
                                stack.push(new int[]{nr, nc});
                            }
                        }
                    }
                    found = true;
                    break;
                }
            }
        }

        // Step 2: multi-source BFS - jab doosri island (1) mile, steps return karo
        int steps = 0;
        while (!q.isEmpty()) {
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                int[] cell = q.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
                        if (grid[nr][nc] == 1) return steps; // doosri island mil gayi
                        if (grid[nr][nc] == 0) {
                            grid[nr][nc] = 2; // visit kar liya
                            q.offer(new int[]{nr, nc});
                        }
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.shortestBridge(new int[][]{{0, 1}, {1, 0}})); // Expected: 1
        System.out.println(s.shortestBridge(new int[][]{
            {0, 1, 0}, {0, 0, 0}, {0, 0, 1}})); // Expected: 2
    }
}
