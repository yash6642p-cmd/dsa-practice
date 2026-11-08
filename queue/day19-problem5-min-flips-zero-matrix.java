// Day 19 - Problem 5: Minimum Number of Flips to Convert Binary Matrix to Zero Matrix (LC 1284) - Hard (stretch)
// Topic: Queue (BFS over states + bitmask - state space search)
//
// PROBLEM (Hinglish):
// Tumhe `m x n` binary matrix `mat` di hai (m, n <= 3). Ek step
// me tum koi ek cell choose karke use flip kar sakte ho, aur
// uske chaaron neighbours (up/down/left/right) bhi flip ho
// jaate hain (0 -> 1, 1 -> 0). Minimum kitne steps me poori
// matrix ko ZERO matrix (saare 0) banaya ja sakta hai?
// Agar impossible hai to -1 return karo.
// Example: mat = [[0,0],[0,1]] -> output 3
// Example: mat = [[0]] -> output 0
// Example: mat = [[1,0,0],[1,0,0]] -> output -1
//
// APPROACH (Hinglish):
// Matrix chhoti hai (max 3x3 = 9 cells), to saari possible
// states sirf 2^9 = 512 hain. Har state ko ek INTEGER bitmask
// me encode karo (cell (i,j) on hai to bit (i*n + j) set).
// Phir is state-space par BFS chalao:
// - Start state = mat ka bitmask, target state = 0.
// - Har state se har cell (i,j) ko flip karke nayi state banao
//   (cell + uske neighbours ke bits XOR karo).
// - BFS level-order me chalti hai, to pehli baar 0 state mile
//   to wahi MINIMUM steps hain.
// - Queue khaali ho jaaye aur 0 na mile to answer -1.
//
// TIME COMPLEXITY: O(2^(m*n) * m * n) - max 512 states, har
//   state se m*n flips try karte hain
// SPACE COMPLEXITY: O(2^(m*n)) - visited states
//
// KEY LEARNING:
// Chhoti state space + minimum steps = BFS over states.
// Matrix ko bitmask integer me convert karna ek powerful trick
// hai: flip karna matlab sirf XOR karna, aur visited check
// karna ek set lookup. Yehi "state compression" aage
// bitmask DP wale problems me bhi kaam aayegi.

import java.util.*;

class Solution {
    public int minFlips(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        // matrix ko bitmask integer me convert karo
        int start = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    start |= 1 << (i * n + j);
                }
            }
        }

        if (start == 0) {
            return 0;
        }

        // har cell ko flip karne par kaunse bits toggle hote hain (pehle se compute)
        int[] flipMasks = new int[m * n];
        int[][] dirs = {{0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int mask = 0;
                for (int[] d : dirs) {
                    int ni = i + d[0];
                    int nj = j + d[1];
                    if (ni >= 0 && ni < m && nj >= 0 && nj < n) {
                        mask |= 1 << (ni * n + nj);
                    }
                }
                flipMasks[i * n + j] = mask;
            }
        }

        // BFS over states (queue me state + steps rakho)
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start, 0});
        Set<Integer> visited = new HashSet<>();
        visited.add(start);

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int state = cur[0];
            int steps = cur[1];
            for (int mask : flipMasks) {
                int newState = state ^ mask;
                if (newState == 0) {
                    return steps + 1;
                }
                if (!visited.contains(newState)) {
                    visited.add(newState);
                    queue.offer(new int[]{newState, steps + 1});
                }
            }
        }

        return -1;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.minFlips(new int[][]{{0, 0}, {0, 1}})); // Expected: 3
        // Example 2
        System.out.println(s.minFlips(new int[][]{{0}})); // Expected: 0
        // Example 3
        System.out.println(s.minFlips(new int[][]{{1, 0, 0}, {1, 0, 0}})); // Expected: -1
        // single 1 - ek flip me zero
        System.out.println(s.minFlips(new int[][]{{1}})); // Expected: 1
        // 2x2 all ones
        System.out.println(s.minFlips(new int[][]{{1, 1}, {1, 1}})); // Expected: 4
    }
}
