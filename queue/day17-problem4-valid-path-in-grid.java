// Day 17 - Problem 4: Check if There is a Valid Path in a Grid (LC 1391) - Medium
// Topic: Queue (BFS - street connections ko follow karte hue)
//
// PROBLEM (Hinglish):
// m x n grid hai, har cell ek street hai (type 1 se 6):
//   1: left <-> right        2: up <-> down
//   3: left <-> down         4: right <-> down
//   5: left <-> up           6: right <-> up
// (0, 0) se (m-1, n-1) tak rasta hai ya nahi? Rasta SIRF
// streets ko follow karke banna chahiye - do cells tabhi judte
// hain jab dono ek dusre ki taraf khulte hon.
// Example: grid = [[2,4,3],[6,5,2]] -> true
//
// APPROACH (Hinglish):
// BFS (0, 0) se shuru. Har cell ke liye uski street type se
// pata karo kin directions me ja sakte ho. Padosi cell me tabhi
// jao jab padosi ki street ULTI direction me khulti ho. Dono
// taraf ka connection check karna hi is problem ki jaan hai.
//
// TIME COMPLEXITY: O(m * n) - har cell ek baar visit hota hai
// SPACE COMPLEXITY: O(m * n) - visited + queue
//
// KEY LEARNING:
// Grid graph me edge tabhi banta hai jab DONO taraf se connection
// ho - sirf apni taraf dekhna kaafi nahi, padosi ki taraf bhi
// check karo.

import java.util.*;

class Solution {
    public boolean hasValidPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // direction: 0 = up, 1 = down, 2 = left, 3 = right
        int[][] DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[] OPP = {1, 0, 3, 2}; // up ka opposite down, left ka right, ...
        // har street type kin directions me khulti hai (index = type)
        int[][] STREET = {
            {},          // 0: koi street nahi
            {2, 3},      // 1: left, right
            {0, 1},      // 2: up, down
            {2, 1},      // 3: left, down
            {3, 1},      // 4: right, down
            {2, 0},      // 5: left, up
            {3, 0}       // 6: right, up
        };
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});
        visited[0][0] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            if (x == m - 1 && y == n - 1) {
                return true;
            }
            for (int d : STREET[grid[x][y]]) {
                int nx = x + DIRS[d][0], ny = y + DIRS[d][1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    // padosi ki street ulti direction me khulti hai?
                    boolean backOpen = false;
                    for (int nd : STREET[grid[nx][ny]]) {
                        if (nd == OPP[d]) backOpen = true;
                    }
                    if (backOpen) {
                        visited[nx][ny] = true;
                        q.offer(new int[]{nx, ny});
                    }
                }
            }
        }
        return false;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.hasValidPath(new int[][]{{2, 4, 3}, {6, 5, 2}})); // Expected: true
        System.out.println(s.hasValidPath(new int[][]{{1, 2, 1}, {1, 2, 1}})); // Expected: false
        System.out.println(s.hasValidPath(new int[][]{{1, 1, 1, 1, 1, 1, 3}})); // Expected: true
        System.out.println(s.hasValidPath(new int[][]{{2}, {2}, {2}, {2}, {2}, {2}, {6}})); // Expected: true
        System.out.println(s.hasValidPath(new int[][]{{1}})); // Expected: true
    }
}
