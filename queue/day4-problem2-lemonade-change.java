// Day 4 - Problem 2: Lemonade Change (LC 860) - Easy
// ============================================================
// PROBLEM (Hinglish):
// Lemonade 5 dollar ki hai. Customers sequence me aate hain aur
// 5, 10 ya 20 ka note dete hain. Sabko sahi change de paao to true,
// warna false. Shuru me koi change nahi hai.
// Example: [5,5,5,10,20] -> true, [5,5,10,10,20] -> false
//
// APPROACH (Hinglish):
// Sirf 2 counters: kitne 5 ke note, kitne 10 ke note.
//   - 5 mile: five++
//   - 10 mile: five >= 1 chahiye, five--, ten++
//   - 20 mile: pehle 10+5 try karo, warna 5+5+5, dono na ho to false.
// Greedy trick: 20 ke liye 10+5 ko priority do, kyunki 5 ka note
// zyada flexible hai (10 ke change me bhi lagta hai).
//
// TIME COMPLEXITY: O(n) - ek pass
// SPACE COMPLEXITY: O(1) - sirf 2 counters
//
// KEY LEARNING:
// Order matters hai (sequence me customers), aur greedy choice me
// flexible resource ko bachao. Sirf current state chahiye.

class Solution {
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;
            } else { // bill == 20, 15 ka change dena hai
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.lemonadeChange(new int[]{5, 5, 5, 10, 20}));
        // Expected: true
        System.out.println(s.lemonadeChange(new int[]{5, 5, 10, 10, 20}));
        // Expected: false
    }
}
