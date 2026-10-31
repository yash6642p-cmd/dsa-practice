// Day 11 - Problem 4: Snakes and Ladders (LC 909) - Medium
// Topic: Queue (BFS = minimum moves)
//
// PROBLEM (Hinglish):
// n x n board hai, cells 1 se n^2 tak numbered hain - neeche-left se
// shuru, har row me direction alternate (boustrophedon style). Har move me
// dice se 1-6 aage badh sakte ho. Agar landing cell par snake/ladder hai
// (board value != -1), to us destination par jaana MUST hai (sirf ek baar).
// Square n^2 tak pahunchne ke minimum dice rolls batao, warna -1.
// Example: 6x6 board (example 1) -> output 4
//
// APPROACH (Hinglish):
// Har square ek node hai, dice roll edges hain (1-6 aage). Minimum moves
// chahiye = unweighted shortest path = BFS. Queue me (square, moves) rakho.
// Square number se (row, col) nikaalne ke liye helper banao - neeche se
// row ginni jaati hai aur alternate rows me column ulta hota hai.
// Visited set se dobara visit roko. Jaise hi n^2 mile, moves+1 return.
//
// TIME COMPLEXITY: O(n^2) - har square ek baar, har square se max 6 edges
// SPACE COMPLEXITY: O(n^2) - visited set + queue
//
// KEY LEARNING:
// "Minimum moves/steps" dikhe to turant BFS socho - BFS level-by-level
// chalta hai isliye pehli baar target milte hi answer shortest hota hai.
// Boustrophedon mapping hi is problem ka asli trick hai.

import java.util.*;

class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int target = n * n;
        boolean[] visited = new boolean[target + 1];
        visited[1] = true;
        Queue<int[]> q = new LinkedList<>(); // {square, moves}
        q.offer(new int[]{1, 0});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int square = cur[0], moves = cur[1];
            for (int dice = 1; dice <= 6; dice++) {
                int nxt = square + dice;
                if (nxt > target) break;
                // square number -> (row, col): neeche se row, alternate col
                int rFromBottom = (nxt - 1) / n;
                int row = n - 1 - rFromBottom;
                int col = (nxt - 1) % n;
                if (rFromBottom % 2 == 1) col = n - 1 - col;
                // snake/ladder ho to destination par jump (sirf ek baar)
                if (board[row][col] != -1) nxt = board[row][col];
                if (nxt == target) return moves + 1;
                if (!visited[nxt]) {
                    visited[nxt] = true;
                    q.offer(new int[]{nxt, moves + 1});
                }
            }
        }
        return -1;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        int[][] board1 = {{-1,-1,-1,-1,-1,-1},
                          {-1,-1,-1,-1,-1,-1},
                          {-1,-1,-1,-1,-1,-1},
                          {-1,35,-1,-1,13,-1},
                          {-1,-1,-1,-1,-1,-1},
                          {-1,15,-1,-1,-1,-1}};
        System.out.println(s.snakesAndLadders(board1));
        // Expected: 4
        System.out.println(s.snakesAndLadders(new int[][]{{-1,-1},{-1,3}}));
        // Expected: 1
    }
}
