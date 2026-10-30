// Day 6 - Problem 4: Minimum Operations to Reduce X to Zero (LC 1658) - Medium
// Topic: Queue (Sliding Window / Two Pointers)
//
// PROBLEM (Hinglish): Ends se hatakar x ko 0 banao, minimum operations.
// Na ho sake to -1.
//
// APPROACH (Hinglish): Beech mein sum (total - x) ki LONGEST subarray
// bachao. Positive numbers hain to two-pointer window: right badhao,
// sum zyada ho to left sikodo. Answer = n - maxLen.
//
// TIME: O(n)  |  SPACE: O(1)
// KEY LEARNING: Positive array + subarray sum -> two pointers; "minimum
// hatana" = "maximum bachana" (complement trick).

class Solution {
    public int minOperations(int[] nums, int x) {
        int total = 0;
        for (int v : nums) total += v;
        if (total < x) return -1;
        if (total == x) return nums.length;

        int target = total - x;
        int left = 0, curr = 0, maxLen = -1;
        for (int right = 0; right < nums.length; right++) {
            curr += nums[right];
            while (curr > target && left <= right) {
                curr -= nums[left];
                left++;
            }
            if (curr == target) maxLen = Math.max(maxLen, right - left + 1);
        }
        if (maxLen == -1) return -1;
        return nums.length - maxLen;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.minOperations(new int[]{1, 1, 4, 2, 3}, 5));      // 2
        System.out.println(s.minOperations(new int[]{5, 6, 7, 8, 9}, 4));      // -1
        System.out.println(s.minOperations(new int[]{3, 2, 20, 1, 1, 3}, 10)); // 5
    }
}
