// Day 5 - Problem 3: Nearest Exit from Entrance in Maze (LC 1926) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Maze grid hai: '.' = khaali rasta, '+' = deewar. Entrance se border
// (kinare) par kisi khaali cell tak minimum kitne steps lagenge?
// Entrance ko exit NAHI maante. Koi exit na mile to -1.
// Example: maze=[["+","+",".","+"],[".",".",".","+"],
//                ["+","+","+","."]], entrance=[1,2] -> 1
//
// APPROACH (Hinglish):
// Unweighted grid me minimum steps = BFS, kyunki BFS distance ke
// order me cells visit karta hai (pehle 1 kadam door, phir 2...).
// 1. Queue me entrance, visited me mark, steps = 0.
// 2. Har level = 1 step: us level ki cells ke 4 neighbours check karo.
// 3. Neighbour andar ho, '.' ho, unvisited ho:
//      - border par hai to steps+1 return karo (ye sabse nazdeek exit hai).
//      - warna queue me daalo, visited mark karo.
// 4. Queue khatam to -1.
// Exit check sirf neighbours par karte hain, taaki entrance ko
// galti se exit na maan liya jaye.
//
// TIME COMPLEXITY: O(m * n) - har cell ek baar
// SPACE COMPLEXITY: O(m * n) - queue + visited
//
// KEY LEARNING:
// "Nearest / minimum steps" + har kadam barabar cost = BFS.
// Level-wise BFS me steps ginna easy hai: har level = +1 step.

import java.util.*;

class Solution {
    public int nearestExit(char[][] maze, int[] entrance) {
        int rows = maze.length, cols = maze[0].length;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // up, down, left, right
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        q.offer(entrance);
        visited[entrance[0]][entrance[1]] = true;
        int steps = 0;
        while (!q.isEmpty()) {
            steps++; // agla level = ek kadam aage
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] cell = q.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    // grid ke bahar, deewar, ya visited cell skip karo
                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) continue;
                    if (maze[nr][nc] == '+' || visited[nr][nc]) continue;
                    // border par khaali cell = sabse nazdeek exit
                    if (nr == 0 || nr == rows - 1 || nc == 0 || nc == cols - 1) {
                        return steps;
                    }
                    visited[nr][nc] = true;
                    q.offer(new int[]{nr, nc});
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
        char[][] m1 = {{'+', '+', '.', '+'}, {'.', '.', '.', '+'}, {'+', '+', '+', '.'}};
        System.out.println(s.nearestExit(m1, new int[]{1, 2})); // Expected: 1
        char[][] m2 = {{'+', '+', '+'}, {'.', '.', '.'}, {'+', '+', '+'}};
        System.out.println(s.nearestExit(m2, new int[]{1, 0})); // Expected: 2
        char[][] m3 = {{'.', '+'}};
        System.out.println(s.nearestExit(m3, new int[]{0, 0})); // Expected: -1
    }
}
