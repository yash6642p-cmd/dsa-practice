// Day 16 - Problem 5: Detonate the Maximum Bombs (LC 2101) - Medium
// Topic: Queue (BFS per starting bomb)
//
// PROBLEM (Hinglish):
// bombs[i] = [x, y, r] - bomb i position (x, y) par hai, blast
// radius r hai. Ek bomb phatne par uske radius ke andar wale
// saare bombs bhi phat jaate hain, chain aage chalti hai. Ek
// bomb ko manually phod kar maximum kitne bombs phode ja sakte?
// Example: [[2,1,3],[6,1,4]] -> 2 (doori 4: pehla dusre tak
// nahi pahunchta, lekin dusra pehle tak pahunch jaata hai)
//
// APPROACH (Hinglish):
// Har bomb se BFS chalao: queue me shuruati bomb, visited rakho.
// Queue se bomb nikalo, baaki har bomb ki doori nikalo - agar
// (x1-x2)^2 + (y1-y2)^2 <= r^2 to woh bhi phatega, queue me
// daalo. (Square root lene ki zaroorat nahi.) Har shuruaat ka
// count nikalo, sabse bada count hi answer hai.
//
// TIME COMPLEXITY: O(n^3) worst case - n shuruaat x BFS x n checks
// (n <= 100 hai, isliye chal jaata hai)
// SPACE COMPLEXITY: O(n) - queue + visited
//
// KEY LEARNING:
// Chain reaction = directed graph ka BFS. Edge sirf ek taraf ho
// sakti hai (bada radius chhote ko chhoo sakta hai, ulta nahi),
// isliye har node se alag BFS chalana padta hai.

import java.util.*;

class Solution {
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;
        int ans = 0;
        for (int start = 0; start < n; start++) {
            boolean[] visited = new boolean[n];
            visited[start] = true;
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            int count = 0;
            while (!q.isEmpty()) {
                int i = q.poll();
                count++;
                long xi = bombs[i][0], yi = bombs[i][1], ri = bombs[i][2];
                for (int j = 0; j < n; j++) {
                    if (visited[j]) {
                        continue;
                    }
                    long dx = xi - bombs[j][0];
                    long dy = yi - bombs[j][1];
                    // doori ka square <= radius ka square
                    if (dx * dx + dy * dy <= ri * ri) {
                        visited[j] = true;
                        q.offer(j);
                    }
                }
            }
            ans = Math.max(ans, count);
        }
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maximumDetonation(new int[][]{{2, 1, 3}, {6, 1, 4}})); // Expected: 2
        System.out.println(s.maximumDetonation(new int[][]{{1, 1, 5}, {10, 10, 5}})); // Expected: 1
        System.out.println(s.maximumDetonation(new int[][]{{1, 2, 3}, {2, 3, 1}, {3, 4, 2}, {4, 5, 3}, {5, 6, 4}})); // Expected: 5
    }
}
