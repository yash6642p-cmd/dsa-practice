// Day 18 - Problem 1: Last Stone Weight (LC 1046) - Easy
// Topic: Queue (Priority Queue / Max-Heap - har baar do sabse bade patthar uthao)
//
// PROBLEM (Hinglish):
// `stones` array hai, har stone ka ek weight hai. Har turn me do
// SABSE BHAARI stones uthao (x aur y, x <= y): agar x == y to dono
// khatam, nahi to (y - x) weight ka tukda wapas aa jaata hai.
// Jab tak 1 ya 0 stones bache, ye chalao. Last stone ka weight
// return karo, ya 0 agar kuch na bache.
// Example: stones = [2,7,4,1,8,1] -> output 1
//
// APPROACH (Hinglish):
// Har baar do sabse bade chahiye - iske liye MAX-HEAP perfect hai.
// Java me PriorityQueue default min-heap hota hai, to
// Collections.reverseOrder() se max-heap banao. Jab tak heap me
// 2+ stones hain: do bade nikalo, agar barabar nahi to farak
// (y - x) wapas daal do. End me heap khaali ho to 0.
//
// TIME COMPLEXITY: O(n log n) - har stone ek baar heap me jaata
//   hai aur nikala jaata hai (heap operation O(log n))
// SPACE COMPLEXITY: O(n) - heap me saare stones
//
// KEY LEARNING:
// "Har baar sabse bada element chahiye" sunte hi Priority Queue
// (heap) yaad aana chahiye. Java me max-heap ke liye
// Collections.reverseOrder() use karo.

import java.util.*;

class Solution {
    public int lastStoneWeight(int[] stones) {
        // max-heap banao (sabse bada element upar rahega)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for (int s : stones) {
            maxHeap.offer(s);
        }

        while (maxHeap.size() > 1) {
            // do sabse bade stones nikalo
            int y = maxHeap.poll();
            int x = maxHeap.poll();
            // agar barabar nahi, to bacha hua tukda wapas daalo
            if (x != y) {
                maxHeap.offer(y - x);
            }
        }

        // heap khaali ho to 0, nahi to akhri stone ka weight
        return maxHeap.isEmpty() ? 0 : maxHeap.poll();
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1})); // Expected: 1
        // single stone
        System.out.println(s.lastStoneWeight(new int[]{1})); // Expected: 1
        // dono barabar - dono khatam
        System.out.println(s.lastStoneWeight(new int[]{2, 2})); // Expected: 0
        // [3, 5, 2] -> 5-3=2, phir 2-2=0
        System.out.println(s.lastStoneWeight(new int[]{3, 5, 2})); // Expected: 0
        // [10, 4, 5] -> 10-5=5, phir 5-4=1
        System.out.println(s.lastStoneWeight(new int[]{10, 4, 5})); // Expected: 1
    }
}
