// Day 10 - Problem 4: Number of Islands (LC 200) - Medium
// Topic: Queue (BFS on a 2D grid - connected components)
//
// PROBLEM (Hinglish):
// Ek m x n grid hai, '1' = zameen (land), '0' = paani (water).
// Islands ginne hain - island matlab '1's ka group jo up/down/
// left/right se jude hon (diagonal se jude hon to alag island).
// Example: [["1","1","0","0","0"],["1","1","0","0","0"],
//   ["0","0","1","0","0"],["0","0","0","1","1"]] -> 3
//
// APPROACH (Hinglish):
// Poori grid scan karo. Jab bhi koi aisi '1' mile jo abhi visit
// nahi hui, samjho naya island mila -> count +1, phir us island
// ki poori '1's ko BFS queue se visit karke '0' bana do (taaki
// dobara na gine). Yeh wahi flood-fill pattern hai jo Day 9 ke
// LC 733 me tha, bas yahan poori grid scan karni hai.
//
// TIME COMPLEXITY: O(m * n) - har cell zyada se zyada ek baar
// SPACE COMPLEXITY: O(m * n) worst case - BFS queue
//
// KEY LEARNING:
// "Kitne connected groups hain?" = har unvisited '1' par BFS/DFS
// chalao aur count badhao.

import java.util.*;

class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        int rows = grid.length, cols = grid[0].length;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int islands = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '1') {
                    continue; // paani ya pehle visit ki hui zameen
                }
                // Naya island mila!
                islands++;
                Queue<int[]> q = new LinkedList<>();
                q.offer(new int[]{r, c});
                grid[r][c] = '0'; // visit mark karo
                while (!q.isEmpty()) {
                    int[] cell = q.poll();
                    for (int[] d : directions) {
                        int nr = cell[0] + d[0], nc = cell[1] + d[1];
                        // Boundary check + sirf unvisited land
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                                && grid[nr][nc] == '1') {
                            grid[nr][nc] = '0';
                            q.offer(new int[]{nr, nc});
                        }
                    }
                }
            }
        }
        return islands;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numIslands(new char[][]{
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}}));
        // Expected: 1
        System.out.println(s.numIslands(new char[][]{
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}}));
        // Expected: 3
        System.out.println(s.numIslands(new char[][]{
            {'0','0'},{'0','0'}}));
        // Expected: 0
    }
}
