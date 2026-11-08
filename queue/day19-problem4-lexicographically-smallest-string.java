// Day 19 - Problem 4: Lexicographically Smallest String After Applying Operations (LC 1625) - Medium
// Topic: Queue (BFS over states - saari reachable strings explore karo)
//
// PROBLEM (Hinglish):
// Tumhe digits ki ek string `s` (even length) aur do integers
// `a` aur `b` diye hain. Tum do operations kitni bhi baar, kisi
// bhi order me laga sakte ho:
// 1. Odd indices (0-indexed) ke saare digits me `a` add karo
//    (digit 9 se aage badhe to 0 se wrap ho jaata hai, mod 10).
// 2. String ko right side `b` positions rotate karo.
// In operations se jo bhi strings ban sakti hain, unme se
// lexicographically SABSE CHHOTI string return karo.
// Example: s = "5525", a = 9, b = 2 -> output "2050"
//
// APPROACH (Hinglish):
// Har string ek "state" hai, aur har operation ek state se dusri
// state tak ka edge hai. Saare reachable states finite hain
// (add karne par digit 10 baar me wapas aa jaata hai, rotate
// karne par string n rotations me wapas aa jaati hai).
// BFS chalao: starting string se queue me daalo, visited set
// rakho taaki ek hi state dobara na explore ho. Har state se
// dono operations lagakar nayi strings banao. Saare states me
// se minimum string hi answer hai.
// (DFS se bhi ho jaata, par BFS/queue wala tareeka states ko
// systematically explore karta hai.)
//
// TIME COMPLEXITY: O(n^2 * 10^2) - states zyada se zyada
//   n rotations * 10 * 10 add-combinations, har state O(n)
// SPACE COMPLEXITY: O(n^2 * 10^2) - visited set me saari states
//
// KEY LEARNING:
// Jab operations se naye-naye states bante hon aur states ki
// sankhya limited ho, to "BFS over states + visited set" ka
// pattern use karo. Visited set bhool gaye to infinite loop
// me phas jaoge - ye is pattern ki sabse important cheez hai.

import java.util.*;

class Solution {
    public String findLexSmallestString(String s, int a, int b) {
        int n = s.length();
        Set<String> visited = new HashSet<>();
        visited.add(s);
        Queue<String> queue = new LinkedList<>();
        queue.offer(s);
        String answer = s;

        while (!queue.isEmpty()) {
            String cur = queue.poll();
            // ab tak ki sabse chhoti string update karo
            if (cur.compareTo(answer) < 0) {
                answer = cur;
            }

            // Operation 1: odd indices par `a` add karo (mod 10)
            char[] chars = cur.toCharArray();
            for (int i = 1; i < n; i += 2) {
                chars[i] = (char) ('0' + ((chars[i] - '0' + a) % 10));
            }
            String added = new String(chars);
            if (!visited.contains(added)) {
                visited.add(added);
                queue.offer(added);
            }

            // Operation 2: right side `b` positions rotate karo
            String rotated = cur.substring(n - b) + cur.substring(0, n - b);
            if (!visited.contains(rotated)) {
                visited.add(rotated);
                queue.offer(rotated);
            }
        }

        return answer;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.findLexSmallestString("5525", 9, 2)); // Expected: "2050"
        // Example 2
        System.out.println(s.findLexSmallestString("74", 5, 1)); // Expected: "24"
        // Example 3
        System.out.println(s.findLexSmallestString("0011", 4, 2)); // Expected: "0011"
        // a = 9 add karne par digits wrap hote hain - "99" se "00" tak pahunch sakte ho
        System.out.println(s.findLexSmallestString("99", 9, 1)); // Expected: "00"
    }
}
