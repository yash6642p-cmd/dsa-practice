// Day 6 - Problem 2: Maximum Candies You Can Get from Boxes (LC 1298) - Hard
// Topic: Queue (BFS)
//
// PROBLEM (Hinglish): Boxes kholo, candies lo. Band box tabhi khulega jab
// uski chabi mile. Initial boxes se shuru karo, maximum candies kitni?
//
// APPROACH (Hinglish): BFS queue. Box tabhi kholo jab (mila hua ho) AUR
// (status open ho ya chabi mili ho). Chabi milte hi pehle mile band boxes
// ko dobara queue mein daalo. Har box sirf ek baar khulta hai.
//
// TIME: O(n + total keys/boxes)  |  SPACE: O(n)
// KEY LEARNING: Band box ko discard mat karo - chabi baad mein mil sakti hai,
// isliye state (have/opened/key) alag-alag track karo.

import java.util.ArrayDeque;
import java.util.Queue;

class Solution {
    public int maxCandies(int[] status, int[] candies, int[][] keys,
                          int[][] containedBoxes, int[] initialBoxes) {
        int n = status.length;
        boolean[] have = new boolean[n];
        boolean[] opened = new boolean[n];
        boolean[] hasKey = new boolean[n];
        Queue<Integer> q = new ArrayDeque<>();
        for (int b : initialBoxes) {
            have[b] = true;
            q.offer(b);
        }
        int total = 0;

        while (!q.isEmpty()) {
            int box = q.poll();
            if (opened[box]) continue;
            if (status[box] == 0 && !hasKey[box]) continue; // chabi ka wait
            opened[box] = true;
            total += candies[box];
            for (int k : keys[box]) {
                if (!hasKey[k]) {
                    hasKey[k] = true;
                    if (have[k] && !opened[k]) q.offer(k);
                }
            }
            for (int nb : containedBoxes[box]) {
                if (!have[nb]) {
                    have[nb] = true;
                    q.offer(nb);
                }
            }
        }
        return total;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maxCandies(new int[]{1, 0, 1, 0}, new int[]{7, 5, 4, 100},
            new int[][]{{}, {}, {1}, {}}, new int[][]{{1, 2}, {3}, {}, {}}, new int[]{0})); // 16
        System.out.println(s.maxCandies(new int[]{1, 0, 0, 0, 0, 0}, new int[]{1, 1, 1, 1, 1, 1},
            new int[][]{{}, {}, {}, {}, {}, {}}, new int[][]{{1, 2, 3, 4, 5}, {}, {}, {}, {}, {}}, new int[]{0})); // 1
    }
}
