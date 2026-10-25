// Day 4 - Problem 5: K Radius Subarray Averages (LC 2090) - Medium
// ============================================================
// PROBLEM (Hinglish):
// Array nums aur k diye hain. Har index i ke liye uska k-radius
// average nikaalo: subarray nums[i-k ... i+k] (2k+1 elements) ka
// average. Poora window na bane to answer -1.
// Example: [7,4,3,9,1,8,5,2,6], k=3 -> [-1,-1,-1,5,4,4,-1,-1,-1]
//
// APPROACH (Hinglish):
// Sliding window: pehle window [0 ... 2k] ka sum nikaalo, phir
// har center ke liye average = sum / (2k+1), phir window ek step
// aage khiskao (left element ghatao, right element jodo).
// Har element ek baar add, ek baar remove - isliye O(n).
// Dobara sum karna O(n*k) hota, jo slow hai.
//
// TIME COMPLEXITY: O(n) - sliding window
// SPACE COMPLEXITY: O(1) extra (answer array ke alawa)
//
// KEY LEARNING:
// Fixed-size window ke sum/average ke liye sliding window use karo:
// window khiskao, dobara calculate mat karo. Day 3 ke Sliding Window
// Maximum me bhi yehi khisakne wala window tha (deque ke saath).

import java.util.Arrays;

class Solution {
    public int[] getAverages(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        int window = 2 * k + 1;
        if (window > n) return ans;
        long windowSum = 0;
        for (int i = 0; i < window; i++) windowSum += nums[i];
        for (int i = k; i < n - k; i++) {
            ans[i] = (int) (windowSum / window);
            if (i + k + 1 < n) {
                windowSum = windowSum - nums[i - k] + nums[i + k + 1];
            }
        }
        return ans;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(
            s.getAverages(new int[]{7, 4, 3, 9, 1, 8, 5, 2, 6}, 3)));
        // Expected: [-1, -1, -1, 5, 4, 4, -1, -1, -1]
    }
}
