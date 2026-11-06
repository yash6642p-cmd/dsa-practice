// Day 17 - Problem 2: Minimum Knight Moves (LC 1197) - Medium
// Topic: Queue (BFS - shortest path, har move ki cost 1 hai)
//
// PROBLEM (Hinglish):
// Ek knight (ghoda) infinite chess board par (0, 0) se shuru
// hota hai. Knight L-shape me chalta hai: 2 square ek direction
// me + 1 square perpendicular (kul 8 possible moves).
// Target square (x, y) tak pahunchne ke liye MINIMUM kitne
// moves chahiye? Answer hamesha exist karta hai.
// Example: x = 2, y = 1 -> 1;  x = 5, y = 5 -> 4
//
// APPROACH (Hinglish):
// Har move ki cost 1 hai, to BFS level-by-level chalegi aur
// pehli baar target milte hi wahi minimum moves hoga.
// Board infinite hai, isliye grid nahi bana sakte - visited ke
// liye SET use karo. Symmetry ka fayda: abs(x), abs(y) lo
// (first quadrant), answer same rahega. Pruning: -2 se neeche
// ya target+2 se upar jaane ka fayda nahi.
//
// TIME COMPLEXITY: O(max(|x|, |y|)^2) - visited area ke hisab se
// SPACE COMPLEXITY: O(max(|x|, |y|)^2) - queue + visited set
//
// KEY LEARNING:
// Infinite board par BFS karte waqt visited SET chahiye, grid
// nahi. Symmetry se search space 4 guna chhota ho jata hai.

import java.util.*;

class Solution {
    public int minKnightMoves(int x, int y) {
        // symmetry: sirf first quadrant dekho, answer same rahega
        x = Math.abs(x);
        y = Math.abs(y);
        // knight ki 8 possible moves
        int[][] moves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                         {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        // queue me (row, col, ab tak ke moves)
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0, 0});
        Set<Long> visited = new HashSet<>();
        visited.add(0L); // (0,0) ko encode karke daalo
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cx = cur[0], cy = cur[1], steps = cur[2];
            if (cx == x && cy == y) {
                return steps;
            }
            for (int[] mv : moves) {
                int nx = cx + mv[0], ny = cy + mv[1];
                // position ko ek long number me encode karo
                long key = ((long) (nx + 1000) << 32) | (ny + 1000);
                // pruning: bahut peeche ya bahut aage jaana bekar hai
                if (!visited.contains(key) && nx >= -2 && nx <= x + 2 && ny >= -2 && ny <= y + 2) {
                    visited.add(key);
                    q.offer(new int[]{nx, ny, steps + 1});
                }
            }
        }
        return -1; // kabhi nahi aayega (answer hamesha exist karta hai)
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.minKnightMoves(2, 1)); // Expected: 1
        System.out.println(s.minKnightMoves(5, 5)); // Expected: 4
        System.out.println(s.minKnightMoves(0, 0)); // Expected: 0
        System.out.println(s.minKnightMoves(1, 1)); // Expected: 2
        System.out.println(s.minKnightMoves(-2, -1)); // Expected: 1
    }
}
