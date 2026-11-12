// Day 23 - Problem 4: Single-Threaded CPU (LC 1834) - Medium
// Topic: Queue (Priority Queue / Min-Heap - task scheduling)
//
// PROBLEM (Hinglish):
// n tasks hain, tasks[i] = [enqueueTime, processingTime]. Matlab
// task i enqueueTime pe available hoti hai aur processingTime tak
// chalti hai. Single-threaded CPU ek time pe ek hi task karta hai:
// - CPU idle ho aur koi available task na ho -> idle rehta hai
// - CPU idle ho aur tasks available hon -> SABSE CHHOTI processingTime
//   wala task chuno (tie ho to sabse chhota index)
// - Task shuru ho gaya to bina ruke poora chalega
// Tasks kis order me process honge, wo order (indices) return karo.
// Example 1: tasks = [[1,2],[2,4],[3,2],[4,1]] -> output [0,2,3,1]
// Example 2: tasks = [[7,10],[7,12],[7,5],[7,4],[7,2]]
//            -> output [4,3,2,0,1]
//
// APPROACH (Hinglish):
// Pehle tasks ko enqueueTime ke hisaab se sort karo (index saath me
// rakho). Phir current time pe jo tasks arrive ho chuke hain, unhe
// MIN-HEAP me daalo - heap ka order (processingTime, index) se.
// Heap khaali ho to time ko agle task ke enqueueTime tak badhao (CPU
// idle wait). Heap se top nikalo, order me daalo, time += uski
// processingTime. Har task ek baar heap me jaata hai, ek baar nikalta
// hai - isliye O(n log n).
//
// TIME COMPLEXITY: O(n log n) - sort + har task ek heap push/pop
// SPACE COMPLEXITY: O(n) - sorted list + heap + answer
//
// KEY LEARNING:
// "Available tasks me se best chuno" = min-heap pattern. Aur "time
// aage badhao jab kuch available na ho" = simulation pattern. Dono
// saath me aayein to pehle sort karo, phir pointer se heap bharte jao.

import java.util.*;

class Solution {
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        // [enqueueTime, processingTime, index] - enqueueTime se sort
        int[][] indexed = new int[n][3];
        for (int i = 0; i < n; i++) {
            indexed[i][0] = tasks[i][0];
            indexed[i][1] = tasks[i][1];
            indexed[i][2] = i;
        }
        Arrays.sort(indexed, (a, b) -> Integer.compare(a[0], b[0]));
        // min-heap: [processingTime, index]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });
        int[] order = new int[n];
        long time = 0; // time bada ho sakta hai, long use karo
        int ptr = 0;   // sorted list me pointer
        int filled = 0;
        while (filled < n) {
            // jitne tasks ab tak arrive ho chuke, heap me daalo
            while (ptr < n && indexed[ptr][0] <= time) {
                pq.offer(new int[]{indexed[ptr][1], indexed[ptr][2]});
                ptr++;
            }
            if (!pq.isEmpty()) {
                // sabse chhoti processingTime wala task chalao
                int[] cur = pq.poll();
                order[filled++] = cur[1];
                time += cur[0];
            } else {
                // koi task available nahi - agle task ke aane tak wait
                time = indexed[ptr][0];
            }
        }
        return order;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        int[][] t1 = {{1,2},{2,4},{3,2},{4,1}};
        System.out.println(Arrays.toString(s.getOrder(t1))); // [0, 2, 3, 1]
        // Example 2 (LeetCode)
        int[][] t2 = {{7,10},{7,12},{7,5},{7,4},{7,2}};
        System.out.println(Arrays.toString(s.getOrder(t2))); // [4, 3, 2, 0, 1]
        // NeetCode example
        int[][] t3 = {{1,4},{3,3},{2,1}};
        System.out.println(Arrays.toString(s.getOrder(t3))); // [0, 2, 1]
        // single task
        int[][] t4 = {{5,3}};
        System.out.println(Arrays.toString(s.getOrder(t4))); // [0]
        // gap: CPU ko wait karna padta hai
        int[][] t5 = {{1,2},{10,1}};
        System.out.println(Arrays.toString(s.getOrder(t5))); // [0, 1]
    }
}
