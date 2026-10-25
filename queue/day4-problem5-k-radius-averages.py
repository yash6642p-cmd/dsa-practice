# Day 4 - Problem 5: K Radius Subarray Averages (LC 2090) - Medium
# ============================================================
# PROBLEM (Hinglish):
# Array nums aur integer k diye hain. Har index i ke liye uska
# k-radius average nikaalo: matlab subarray nums[i-k ... i+k]
# (total 2k+1 elements) ka average. Agar i-k < 0 ya i+k >= n ho
# (poora window nahi banta) to answer -1.
# Example: nums=[7,4,3,9,1,8,5,2,6], k=3
#   -> [-1,-1,-1,5,4,4,-1,-1,-1]
#   (i=3: (7+4+3+9+1+8+5)/7 = 37/7 = 5, integer division)
#
# APPROACH (Hinglish):
# Sliding window (queue jaisa window jo aage khisakta hai):
#   1. Pehle window [0 ... 2k] ka sum nikaalo.
#   2. Har center i ke liye average = window_sum // (2k+1).
#   3. Window ko ek step aage khiskao: left element ka sum ghatao,
#      right element ka sum jodo.
# Har element sirf ek baar add aur ek baar remove hota hai, isliye
# O(n) me ho jata hai - har baar poora window dobara sum karna O(n*k)
# hota jo slow hai.
#
# TIME COMPLEXITY: O(n) - sliding window, har element ek baar
# SPACE COMPLEXITY: O(1) extra (answer array ke alawa)
#
# KEY LEARNING:
# Fixed-size window ke sum/average ke liye sliding window use karo:
# window khiskao, dobara calculate mat karo. Yehi idea Day 3 ke
# Sliding Window Maximum (LC 239) me deque ke saath tha, yahan
# simple sum kaafi hai kyunki average ke liye poora sum chahiye.

from typing import List


class Solution:
    def getAverages(self, nums: List[int], k: int) -> List[int]:
        n = len(nums)
        ans = [-1] * n
        window = 2 * k + 1
        if window > n:
            return ans  # kahin bhi poora window nahi banega
        # Pehla window [0 ... 2k] ka sum
        window_sum = sum(nums[:window])
        for i in range(k, n - k):
            # i center hai, window [i-k ... i+k] hai
            ans[i] = window_sum // window
            # Window ek step aage: nums[i-k] bahar, nums[i+k+1] andar
            if i + k + 1 < n:
                window_sum = window_sum - nums[i - k] + nums[i + k + 1]
        return ans


# TEST
if __name__ == "__main__":
    s = Solution()
    print(s.getAverages([7, 4, 3, 9, 1, 8, 5, 2, 6], 3))
    # Expected: [-1, -1, -1, 5, 4, 4, -1, -1, -1]
    print(s.getAverages([100000], 0))  # Expected: [100000]
    print(s.getAverages([8], 100000))  # Expected: [-1]
