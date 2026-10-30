// Day 6 - Problem 1: Constrained Subsequence Sum (LC 1425) - Hard
// Topic: Queue (Monotonic Deque)
//
// PROBLEM (Hinglish):
// Array nums aur k diya hai. Subsequence chuno jisme consecutive chosen
// indices ka difference <= k ho. Sum maximum karo, kam se kam ek element lo.
//
// APPROACH (Hinglish):
// dp[i] = nums[i] + max(0, pichle k dp ka maximum)
// Maximum nikalne ke liye monotonic decreasing deque: front par window ka
// maximum, window se bahar wale front se hatao, chhote dp peeche se hatao.
//
// TIME: O(n)  |  SPACE: O(n)
// KEY LEARNING: "Pichle k mein maximum" -> monotonic deque, O(n) solution.

import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public int constrainedSubsetSum(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        Deque<Integer> dq = new ArrayDeque<>(); // dp decreasing, front = maximum
        int ans = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            // Window se bahar wale hatao
            while (!dq.isEmpty() && dq.peekFirst() < i - k) {
                dq.pollFirst();
            }
            int bestPrev = dq.isEmpty() ? 0 : dp[dq.peekFirst()];
            dp[i] = nums[i] + Math.max(0, bestPrev);
            ans = Math.max(ans, dp[i]);
            // Peeche se chhote dp hatao, phir i daalo
            while (!dq.isEmpty() && dp[dq.peekLast()] <= dp[i]) {
                dq.pollLast();
            }
            dq.offerLast(i);
        }
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.constrainedSubsetSum(new int[]{10, 2, -10, 5, 20}, 2)); // 37
        System.out.println(s.constrainedSubsetSum(new int[]{-1, -2, -3}, 1));        // -1
        System.out.println(s.constrainedSubsetSum(new int[]{10, -2, -10, -5, 20}, 2)); // 23
    }
}
