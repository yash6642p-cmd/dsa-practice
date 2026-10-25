# Day 4 - Problem 2: Lemonade Change (LC 860) - Easy
# ============================================================
# PROBLEM (Hinglish):
# Lemonade 5 dollar ki hai. Customers ek-ek karke aate hain aur
# 5, 10 ya 20 dollar ka note dete hain. Har customer ko sahi change
# de paao to True, warna False. Shuru me tumhare paas koi change nahi.
# Example: bills = [5,5,5,10,20] -> True
#          bills = [5,5,10,10,20] -> False (last 20 ke liye change nahi)
#
# APPROACH (Hinglish):
# Sirf count rakhna hai: kitne 5 ke note aur kitne 10 ke note hain.
# (20 ke note change me kabhi kaam nahi aate, isliye unka count
#  rakhne ki zaroorat nahi.)
#   - 5 mile: five += 1
#   - 10 mile: five >= 1 hona chahiye, five -= 1, ten += 1
#   - 20 mile: pehle try karo 10+5 dena (10 bachana better hai),
#     warna 5+5+5 dena; dono na ho to False.
# Greedy trick: 20 ke liye 10+5 ko priority do, kyunki 5 ke note
# zyada flexible hote hain (10 ke change me bhi lagte hain).
#
# TIME COMPLEXITY: O(n) - ek pass
# SPACE COMPLEXITY: O(1) - sirf 2 counters
#
# KEY LEARNING:
# Queue jaisa order matters hai (customers sequence me aate hain),
# aur greedy choice me flexible resource (5 ka note) ko bachao.
# Har step pe sirf current state (counts) chahiye, poora history nahi.

from typing import List


class Solution:
    def lemonadeChange(self, bills: List[int]) -> bool:
        five = 0
        ten = 0
        for bill in bills:
            if bill == 5:
                five += 1
            elif bill == 10:
                if five == 0:
                    return False
                five -= 1
                ten += 1
            else:  # bill == 20, change 15 dena hai
                if ten > 0 and five > 0:
                    ten -= 1
                    five -= 1
                elif five >= 3:
                    five -= 3
                else:
                    return False
        return True


# TEST
if __name__ == "__main__":
    s = Solution()
    print(s.lemonadeChange([5, 5, 5, 10, 20]))   # Expected: True
    print(s.lemonadeChange([5, 5, 10, 10, 20]))  # Expected: False
    print(s.lemonadeChange([5, 5, 10]))          # Expected: True
