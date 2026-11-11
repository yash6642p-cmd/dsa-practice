// Day 22 - Problem 2: Minimum Jumps to Reach Home (LC 1654) - Medium
// Topic: Queue (BFS - state ke saath shortest path)
//
// PROBLEM (Hinglish):
// Ek bug x-axis pe position 0 se start karta hai, uska ghar position
// x pe hai. Rules:
// - a positions AAGE (right) jump kar sakta hai
// - b positions PEECHE (left) jump kar sakta hai, lekin lagatar
//   do baar peeche NAHI jump kar sakta
// - forbidden positions pe land NAHI kar sakta
// - negative position pe NAHI ja sakta, lekin x se aage ja sakta hai
// Ghar pahunchne ke MINIMUM jumps batao, impossible ho to -1.
// Example: forbidden = [14,4,18,1,15], a = 3, b = 15, x = 9 -> 3
// Example: forbidden = [8,3,16,6,12,20], a = 15, b = 13, x = 11 -> -1
//
// APPROACH (Hinglish):
// Shortest jumps chahiye = BFS. Lekin state sirf position nahi hai -
// "pichla jump peeche tha ya nahi" bhi yaad rakhna hai, kyunki do
// baar peeche jump mana hai. Isliye visited alag-alag rakho: aage se
// aaye hue positions aur peeche se aaye hue positions. Aage kitni
// door ja sakte ho? max(x, max(forbidden)) + a + b se aage jaane ka
// fayda nahi - wahan se wapas aana hi padega.
//
// TIME COMPLEXITY: O(M) - M = max(x, max(forbidden)) + a + b (max ~6000)
// SPACE COMPLEXITY: O(M) - queue + visited sets
//
// KEY LEARNING:
// BFS me kabhi-kabhi sirf position hi state nahi hoti - pichli move
// bhi state ka hissa hoti hai. "Do baar peeche nahi" jaisi condition
// ke liye visited ko (position, direction) ke pair se track karo.

import java.util.*;

class Solution {
    public int minimumJumps(int[] forbidden, int a, int b, int x) {
        // ghar pe hi ho to 0 jumps
        if (x == 0) {
            return 0;
        }
        Set<Integer> forbiddenSet = new HashSet<>();
        int maxForbidden = 0;
        for (int f : forbidden) {
            forbiddenSet.add(f);
            maxForbidden = Math.max(maxForbidden, f);
        }
        // isse aage jaane ka koi fayda nahi
        int limit = Math.max(x, maxForbidden) + a + b;
        // queue me [position, jumps, pichla jump peeche tha? 1/0]
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0, 0});
        // visited alag-alag rakho: aage se aaye ya peeche se
        Set<Integer> visitedForward = new HashSet<>();
        Set<Integer> visitedBackward = new HashSet<>();
        visitedForward.add(0);
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int pos = cur[0], jumps = cur[1];
            boolean wasBack = cur[2] == 1;
            // aage jump - hamesha allowed (limit ke andar, forbidden nahi)
            int nxt = pos + a;
            if (nxt == x) {
                return jumps + 1;
            }
            if (nxt <= limit && !forbiddenSet.contains(nxt)
                    && !visitedForward.contains(nxt)) {
                visitedForward.add(nxt);
                q.offer(new int[]{nxt, jumps + 1, 0});
            }
            // peeche jump - sirf tab jab pichla peeche wala nahi tha
            if (!wasBack) {
                int prev = pos - b;
                if (prev == x) {
                    return jumps + 1;
                }
                if (prev >= 0 && !forbiddenSet.contains(prev)
                        && !visitedBackward.contains(prev)) {
                    visitedBackward.add(prev);
                    q.offer(new int[]{prev, jumps + 1, 1});
                }
            }
        }
        // queue khatam, ghar nahi mila
        return -1;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        System.out.println(s.minimumJumps(new int[]{14, 4, 18, 1, 15}, 3, 15, 9)); // Expected: 3
        // Example 2 (LeetCode) - impossible
        System.out.println(s.minimumJumps(new int[]{8, 3, 16, 6, 12, 20}, 15, 13, 11)); // Expected: -1
        // Example 3 (LeetCode) - 0 -> 16 -> 7
        System.out.println(s.minimumJumps(new int[]{1, 6, 2, 14, 5, 17, 4}, 16, 9, 7)); // Expected: 2
        // peeche jump use karna padega
        System.out.println(s.minimumJumps(new int[]{6, 14, 17}, 3, 1, 13)); // Expected: 7
        // ghar pe hi ho
        System.out.println(s.minimumJumps(new int[]{1, 2}, 2, 1, 0)); // Expected: 0
    }
}
