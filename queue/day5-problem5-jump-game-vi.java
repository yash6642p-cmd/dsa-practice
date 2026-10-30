// Day 5 - Problem 5: Jump Game VI (LC 1696) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Array nums, index 0 se aakhri index tak jana hai. Ek move me aage
// zyada se zyada k steps jump kar sakte ho. Score = visit kiye hue
// indices ke nums ka sum. Maximum score kitna?
// Example: nums=[1,-1,-2,4,-7,3], k=2 -> 7 (path: 1, -1, 4, 3)
//
// APPROACH (Hinglish) - DP + Monotonic Deque:
// dp[i] = index i tak ka maximum score.
// dp[i] = nums[i] + (pichhle k indices me sabse bada dp).
// Window ka maximum har baar scan karke nikaalna O(n*k) hoga,
// isliye deque rakhte hain jisme dp values decreasing order me hon:
// 1. Front se window se bahar indices hatao (i - front > k).
// 2. dp[i] = nums[i] + dp[front] (window ka maximum).
// 3. Back se dp <= dp[i] wale hatao (ye kabhi maximum nahi banenge),
//    phir i ko back me daalo.
// Har index ek baar aata aur ek baar jaata hai, isliye O(n).
// Ye wahi trick hai jo Sliding Window Maximum (LC 239, Day 3) me thi.
//
// TIME COMPLEXITY: O(n)
// SPACE COMPLEXITY: O(n) - dp array + deque
//
// KEY LEARNING:
// "Pichhle k me maximum + DP" dikhe to monotonic deque yaad karo:
// front = window ka best, back se chhote hatao, bahar wale front se hatao.

import java.util.*;

class Solution {
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        Deque<Integer> dq = new ArrayDeque<>(); // indices, dp decreasing order me
        dq.offer(0);
        for (int i = 1; i < n; i++) {
            // 1. Window se bahar indices front se hatao
            while (!dq.isEmpty() && i - dq.peekFirst() > k) {
                dq.pollFirst();
            }
            // 2. dp[i] = nums[i] + window ka maximum
            dp[i] = nums[i] + dp[dq.peekFirst()];
            // 3. Back se chhote/barabar dp wale hatao
            while (!dq.isEmpty() && dp[dq.peekLast()] <= dp[i]) {
                dq.pollLast();
            }
            dq.offerLast(i);
        }
        return dp[n - 1];
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maxResult(new int[]{1, -1, -2, 4, -7, 3}, 2)); // Expected: 7
        System.out.println(s.maxResult(new int[]{10, -5, -2, 4, 0, 3}, 3)); // Expected: 17
        System.out.println(s.maxResult(new int[]{1, -5, -20, 4, -1, 3, -6, -3}, 2)); // Expected: 0
    }
}
