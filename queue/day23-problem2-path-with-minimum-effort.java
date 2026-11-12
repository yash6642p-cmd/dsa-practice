// Day 23 - Problem 2: Path With Minimum Effort (LC 1631) - Medium
// Topic: Queue (Priority Queue / Dijkstra - minimax path)
//
// PROBLEM (Hinglish):
// heights grid diya hai - har cell ki height. Top-left (0,0) se
// bottom-right (m-1,n-1) tak jaana hai, sirf up/down/left/right.
// Ek route ki EFFORT = us route ke kisi bhi do consecutive cells ke
// beech ka SABSE BADA height difference. Aisi route chahiye jiski
// effort MINIMUM ho - wo minimum effort return karo.
// Example: heights = [[1,2,2],[3,8,2],[5,3,5]] -> output 2
// Example: heights = [[1,2,3],[3,8,4],[5,3,5]] -> output 1
// Example: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],
//                     [1,2,1,2,1],[1,1,1,2,1]] -> output 0
//
// APPROACH (Hinglish):
// Ye "minimax path" hai - path ka cost uske sabse bade edge ka max
// hai. Dijkstra yahan bhi kaam karta hai: cost ko add karne ki jagah
// max() lo. Min-heap (PriorityQueue) me (effort, row, col) rakho.
// Heap se sabse chhoti effort wala cell nikalo - pehli baar jab
// destination nikle, wahi answer hai (Dijkstra guarantee). Har
// neighbour ke liye newEffort = max(currentEffort, |height diff|);
// agar ye us cell ki best effort se kam hai to update karke heap me
// daalo.
//
// TIME COMPLEXITY: O(m*n*log(m*n)) - har cell heap me ek-do baar
// SPACE COMPLEXITY: O(m*n) - effort matrix + heap
//
// KEY LEARNING:
// Dijkstra sirf "sum of weights" ke liye nahi hai - jab bhi path ka
// cost monotonic ho (badhaane se kabhi kam na ho), Dijkstra chalega.
// Yahan cost = max edge, isliye max() se update karte hain.

import java.util.*;

class Solution {
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        // best[r][c] = us cell tak pahunchne ki minimum effort
        int[][] best = new int[m][n];
        for (int[] row : best) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        best[0][0] = 0;
        // min-heap: [effort, row, col] - effort se sort
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0]));
        pq.offer(new int[]{0, 0, 0});
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int effort = cur[0], r = cur[1], c = cur[2];
            // destination pehli baar nikla = minimum effort pakka
            if (r == m - 1 && c == n - 1) {
                return effort;
            }
            // purana entry ho to skip karo
            if (effort > best[r][c]) {
                continue;
            }
            for (int[] d : dirs) {
                int nr = r + d[0], nc = c + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
                    // is edge ka gap
                    int gap = Math.abs(heights[r][c] - heights[nr][nc]);
                    // nayi effort = ab tak ka max aur is gap ka max
                    int newEffort = Math.max(effort, gap);
                    if (newEffort < best[nr][nc]) {
                        best[nr][nc] = newEffort;
                        pq.offer(new int[]{newEffort, nr, nc});
                    }
                }
            }
        }
        return best[m - 1][n - 1];
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        int[][] h1 = {{1,2,2},{3,8,2},{5,3,5}};
        System.out.println(s.minimumEffortPath(h1)); // Expected: 2
        // Example 2 (LeetCode)
        int[][] h2 = {{1,2,3},{3,8,4},{5,3,5}};
        System.out.println(s.minimumEffortPath(h2)); // Expected: 1
        // Example 3 (LeetCode) - zero effort route hai
        int[][] h3 = {{1,2,1,1,1},{1,2,1,2,1},{1,2,1,2,1},
                      {1,2,1,2,1},{1,1,1,2,1}};
        System.out.println(s.minimumEffortPath(h3)); // Expected: 0
        // single cell - koi move nahi, effort 0
        int[][] h4 = {{1}};
        System.out.println(s.minimumEffortPath(h4)); // Expected: 0
        // seedha raasta, gap 5
        int[][] h5 = {{1,6}};
        System.out.println(s.minimumEffortPath(h5)); // Expected: 5
    }
}
