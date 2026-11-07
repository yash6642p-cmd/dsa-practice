// Day 18 - Problem 2: Surrounded Regions (LC 130) - Medium
// Topic: Queue (BFS - border se judi 'O' safe hai, baaki sab gheri hui hain)
//
// PROBLEM (Hinglish):
// Ek m x n board hai jisme 'X' aur 'O' hain. Jo 'O' region chaaro
// taraf se 'X' se GHIRA hua hai (border tak koi raasta nahi),
// uske saare 'O' ko 'X' me badal do. Jo 'O' border se juda hai
// (ya border wali 'O' se connected hai), wo SAFE hai - use mat chhedo.
// Board ko IN-PLACE modify karna hai.
// Example:
//   X X X X       X X X X
//   X O O X  ->   X X X X
//   X X O X       X X X X
//   X O X X       X O X X
//
// APPROACH (Hinglish):
// Ulta socho: gheri hui 'O' dhoondhne ke bajay SAFE 'O' dhoondho.
// Border par jo 'O' hain, wo safe hain - unse BFS chalao aur saari
// connected 'O' ko temporarily '#' mark kar do (safe ka nishan).
// Phir poora board scan karo: jo 'O' bachi hai wo ghiri hui hai,
// use 'X' kar do; aur '#' ko wapas 'O' kar do. Bas!
//
// TIME COMPLEXITY: O(m * n) - har cell ek baar visit hoti hai
// SPACE COMPLEXITY: O(m * n) - worst case queue me saari cells
//   (agar saara board 'O' ho)
//
// KEY LEARNING:
// Kabhi-kabhi problem ko ULTA solve karna aasan hota hai: "gheri
// hui" dhoondhne ke bajay "safe" (border se judi) dhoondho. BFS
// ko border se start karna is pattern ki pehchan hai - yehi
// trick Number of Enclaves (LC 1020) me bhi kaam aayi thi.

import java.util.*;

class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;
        int m = board.length, n = board[0].length;
        Queue<int[]> q = new LinkedList<>();

        // Step 1: border ki saari 'O' queue me daalo aur '#' mark karo (safe)
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                board[i][0] = '#';
                q.offer(new int[]{i, 0});
            }
            if (board[i][n - 1] == 'O') {
                board[i][n - 1] = '#';
                q.offer(new int[]{i, n - 1});
            }
        }
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                board[0][j] = '#';
                q.offer(new int[]{0, j});
            }
            if (board[m - 1][j] == 'O') {
                board[m - 1][j] = '#';
                q.offer(new int[]{m - 1, j});
            }
        }

        // Step 2: BFS - safe 'O' se judi saari 'O' bhi safe hain
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            for (int[] d : dirs) {
                int nx = cell[0] + d[0], ny = cell[1] + d[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && board[nx][ny] == 'O') {
                    board[nx][ny] = '#';
                    q.offer(new int[]{nx, ny});
                }
            }
        }

        // Step 3: bachi hui 'O' ghiri hui hai -> 'X'; '#' wapas 'O'
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == '#') board[i][j] = 'O';
            }
        }
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        char[][] board1 = {{'X', 'X', 'X', 'X'},
                           {'X', 'O', 'O', 'X'},
                           {'X', 'X', 'O', 'X'},
                           {'X', 'O', 'X', 'X'}};
        s.solve(board1);
        System.out.println(Arrays.deepToString(board1));
        // Expected: [[X, X, X, X], [X, X, X, X], [X, X, X, X], [X, O, X, X]]

        // saari 'O' border se judi hain - kuch nahi badlega
        char[][] board2 = {{'O', 'O'}, {'O', 'O'}};
        s.solve(board2);
        System.out.println(Arrays.deepToString(board2)); // Expected: [[O, O], [O, O]]

        // beech me ek 'O', chaaro taraf 'X' - ghiri hui
        char[][] board3 = {{'X', 'X', 'X'}, {'X', 'O', 'X'}, {'X', 'X', 'X'}};
        s.solve(board3);
        System.out.println(Arrays.deepToString(board3)); // Expected: all X
    }
}
