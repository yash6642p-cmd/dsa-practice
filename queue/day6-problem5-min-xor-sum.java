// Day 6 - Problem 5: Minimum XOR Sum of Two Arrays (LC 1879) - Hard (stretch)
// Topic: Bitmask DP (aaj ka stretch problem)
//
// PROBLEM (Hinglish): nums2 ko rearrange karke sum (nums1[i] XOR nums2[perm[i]])
// minimum karo.
//
// APPROACH (Hinglish): mask = kaun se nums2 use ho chuke. dp[mask] = pehle
// popcount(mask) nums1 elements ki minimum cost. Har unused k ke liye
// dp[mask | (1<<k)] ko update karo.
//
// TIME: O(n * 2^n)  |  SPACE: O(2^n)
// KEY LEARNING: Matching/assignment mein bitmask DP: mask = used set,
// popcount = agla index.

import java.util.Arrays;

class Solution {
    public int minimumXORSum(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            int i = Integer.bitCount(mask); // nums1 ka agla index
            if (i >= n) continue;
            for (int k = 0; k < n; k++) {
                if ((mask & (1 << k)) == 0) { // nums2[k] free hai
                    int nmask = mask | (1 << k);
                    dp[nmask] = Math.min(dp[nmask], dp[mask] + (nums1[i] ^ nums2[k]));
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.minimumXORSum(new int[]{1, 2}, new int[]{2, 3}));    // 2
        System.out.println(s.minimumXORSum(new int[]{1, 0, 3}, new int[]{5, 3, 4})); // 8
    }
}
