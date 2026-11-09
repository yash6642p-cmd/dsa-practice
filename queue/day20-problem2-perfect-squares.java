// Day 20 - Problem 2: Perfect Squares (LC 279) - Medium
// Topic: Queue (BFS - shortest path soch ke solve karo)
//
// PROBLEM (Hinglish):
// Tumhe ek integer `n` diya hai. Minimum kitne perfect square numbers
// (1, 4, 9, 16, 25...) ka sum `n` ke barabar hota hai? Count return karo.
// Example: n = 12 -> output 3 (4 + 4 + 4)
// Example: n = 13 -> output 2 (4 + 9)
//
// APPROACH (Hinglish):
// Ise ek SHORTEST PATH problem samjho: number 0 se start karo, har
// step me ek perfect square add karo. Pehli baar jab `n` tak pahunche,
// wahi minimum steps hain - kyunki BFS level-by-level jaata hai.
// Queue me current sum rakho, visited set se dobara visit roko.
// Har level = ek square aur. Level count hi answer hai.
// (DP wala tareeka bhi hai, lekin BFS yahan Queue topic se match karta hai.)
//
// TIME COMPLEXITY: O(n * sqrt(n)) - har number se sqrt(n) squares try hote hain
// SPACE COMPLEXITY: O(n) - queue + visited set
//
// KEY LEARNING:
// "Har minimum-steps problem BFS hai" - jab har move ki cost 1 ho,
// toh BFS ka pehla mila hua answer hi sabse chhota hota hai. Numbers
// ko graph ke nodes samajhna ek powerful trick hai.

import java.util.*;

class Solution {
    public int numSquares(int n) {
        // perfect squares ki list banao (1, 4, 9 ... <= n)
        List<Integer> squares = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            squares.add(i * i);
        }

        // BFS: queue me current sum rakho, steps alag se track karo
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        Set<Integer> visited = new HashSet<>();
        visited.add(0);
        int steps = 0;

        while (!queue.isEmpty()) {
            steps++;
            int levelSize = queue.size();
            for (int k = 0; k < levelSize; k++) {
                int cur = queue.poll();
                // har perfect square add karke dekho
                for (int sq : squares) {
                    int nxt = cur + sq;
                    // n tak pahunch gaye - yahi minimum steps hain
                    if (nxt == n) {
                        return steps;
                    }
                    if (nxt > n) {
                        break; // squares sorted hain, aage sab bade honge
                    }
                    if (!visited.contains(nxt)) {
                        visited.add(nxt);
                        queue.offer(nxt);
                    }
                }
            }
        }

        return n; // fallback (n baar 1 add karna hamesha possible hai)
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.numSquares(12)); // Expected: 3
        // Example 2
        System.out.println(s.numSquares(13)); // Expected: 2
        // n = 1 - khud perfect square
        System.out.println(s.numSquares(1)); // Expected: 1
        // n = 6 -> 4 + 1 + 1
        System.out.println(s.numSquares(6)); // Expected: 3
    }
}
