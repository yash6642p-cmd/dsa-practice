// Day 17 - Problem 3: Map of Highest Peak (LC 1765) - Medium
// Topic: Queue (Multi-source BFS - saare paani cells se ek saath)
//
// PROBLEM (Hinglish):
// isWater matrix di hai: 1 = paani (water), 0 = zameen (land).
// Har cell ko ek height deni hai:
// - paani ki height 0 hogi
// - paas-paas wale cells ki height me farak zyada se zyada 1
// - height kabhi negative nahi
// Aisi height do jisse MAXIMUM height sabse badi ho. Koi bhi
// valid answer chalega, return karo height matrix.
// Example: isWater = [[0,1],[0,0]] -> [[1,0],[2,1]]
//
// APPROACH (Hinglish):
// Cell ki height = uski paani se sabse chhoti doori (distance).
// Multi-source BFS: saare paani cells ko height 0 ke saath
// queue me daalo, phir level-by-level phailo - har naye cell
// ki height = padosi ki height + 1. (LC 542 01 Matrix jaisa)
//
// TIME COMPLEXITY: O(m * n) - har cell ek baar visit hota hai
// SPACE COMPLEXITY: O(m * n) - height matrix + queue
//
// KEY LEARNING:
// Multi-source BFS: ek source ki jagah SAARE sources ko ek saath
// queue me daal do - pehli baar jab cell visit hoga, wahi uski
// nearest source se shortest distance hogi.

import java.util.*;

class Solution {
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length, n = isWater[0].length;
        // -1 = abhi height assign nahi hui
        int[][] height = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(height[i], -1);
        }
        Queue<int[]> q = new LinkedList<>();
        // saare paani cells height 0 ke saath queue me
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    height[i][j] = 0;
                    q.offer(new int[]{i, j});
                }
            }
        }
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];
            for (int[] d : dirs) {
                int nx = x + d[0], ny = y + d[1];
                // bounds check + abhi tak height nahi mili
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && height[nx][ny] == -1) {
                    height[nx][ny] = height[x][y] + 1;
                    q.offer(new int[]{nx, ny});
                }
            }
        }
        return height;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.deepToString(s.highestPeak(new int[][]{{0, 1}, {0, 0}}))); // Expected: [[1, 0], [2, 1]]
        System.out.println(Arrays.deepToString(s.highestPeak(new int[][]{{0, 0, 1}, {1, 0, 0}, {0, 0, 0}}))); // Expected: [[1, 1, 0], [0, 1, 1], [1, 2, 2]]
        System.out.println(Arrays.deepToString(s.highestPeak(new int[][]{{1}}))); // Expected: [[0]]
    }
}
