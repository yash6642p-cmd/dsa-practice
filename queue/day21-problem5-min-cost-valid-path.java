// Day 21 - Problem 5: Minimum Cost to Make at Least One Valid Path in a Grid (LC 1368) - Hard (stretch)
// Topic: Queue (0-1 BFS with deque - cost 0 ya 1 wale edges)
//
// PROBLEM (Hinglish):
// m x n grid hai. Har cell me ek ARROW hai (1=right, 2=left,
// 3=down, 4=up) jo batata hai us cell se kahan jaana hai.
// (0,0) se (m-1,n-1) tak pahunchna hai. Kisi cell ka arrow
// badalne ki cost 1 hai (ek cell ka arrow ek hi baar badlo).
// Minimum cost batao jisse kam se kam ek valid path ban jaaye.
// Example: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
// -> output 3 (3 arrows badalne padenge)
// Example: grid = [[1,1,3],[3,2,2],[1,1,4]] -> output 0
// (bina badle hi path hai)
//
// APPROACH (Hinglish):
// Har cell se 4 neighbours tak edge hai: arrow wali direction
// me jaane ki cost 0, baaki 3 directions me cost 1.
// Ye 0-1 weights wala shortest path hai - iske liye 0-1 BFS:
// deque use karo. Cost 0 wala move deque ke FRONT me daalo,
// cost 1 wala BACK me. Isse cells hamesha badhte hue cost ke
// order me process hote hain (Dijkstra jaisa, par O(mn) me).
// dist[m-1][n-1] hi minimum cost hai.
//
// TIME COMPLEXITY: O(m * n) - har cell constant kaam se process hota hai
// SPACE COMPLEXITY: O(m * n) - dist array + deque
//
// KEY LEARNING:
// Weights sirf 0 ya 1 hon to Dijkstra ki heap ki zaroorat nahi -
// deque se 0-1 BFS kaafi hai: 0-cost aage, 1-cost peeche. Ye
// trick 0-1 weights wale har shortest-path problem me kaam aati hai.

import java.util.*;

class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        // direction numbers: 1=right, 2=left, 3=down, 4=up
        int[][] dirs = {{0, 1, 1}, {0, -1, 2}, {1, 0, 3}, {-1, 0, 4}};
        int[][] dist = new int[m][n];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        dist[0][0] = 0;
        Deque<int[]> dq = new ArrayDeque<>();
        dq.offerFirst(new int[]{0, 0});
        while (!dq.isEmpty()) {
            int[] cur = dq.pollFirst();
            int x = cur[0], y = cur[1];
            for (int[] d : dirs) {
                int nx = x + d[0], ny = y + d[1];
                if (nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }
                // arrow wali direction = cost 0, warna cost 1
                int w = (grid[x][y] == d[2]) ? 0 : 1;
                if (dist[x][y] + w < dist[nx][ny]) {
                    dist[nx][ny] = dist[x][y] + w;
                    if (w == 0) {
                        dq.offerFirst(new int[]{nx, ny}); // 0-cost aage
                    } else {
                        dq.offerLast(new int[]{nx, ny}); // 1-cost peeche
                    }
                }
            }
        }
        return dist[m - 1][n - 1];
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        int[][] g1 = {{1, 1, 1, 1}, {2, 2, 2, 2}, {1, 1, 1, 1}, {2, 2, 2, 2}};
        System.out.println(s.minCost(g1)); // Expected: 3
        // Example 2 - bina badle path hai
        int[][] g2 = {{1, 1, 3}, {3, 2, 2}, {1, 1, 4}};
        System.out.println(s.minCost(g2)); // Expected: 0
        // Example 3
        int[][] g3 = {{1, 2}, {4, 3}};
        System.out.println(s.minCost(g3)); // Expected: 1
        // single cell - kahin jaana hi nahi
        System.out.println(s.minCost(new int[][]{{1}})); // Expected: 0
        // seedha right arrow, ek hi row
        System.out.println(s.minCost(new int[][]{{1, 1, 1}})); // Expected: 0
    }
}
