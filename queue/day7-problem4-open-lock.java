// Day 7 - Problem 4: Open the Lock (LC 752) - Medium
// ============================================================
// PROBLEM (Hinglish):
// 4 wheels wala lock, shuru "0000". Har move me ek wheel ek slot
// aage/peeche (9 ke baad 0, 0 ke pehle 9). Deadends par atak jayega.
// Target tak minimum moves? Na ho sake to -1.
// Example: deadends=["0201","0101","0102","1212","2002"], target="0202" -> 6
//
// APPROACH (Hinglish):
// Har state ek node, har move ek edge - minimum moves = BFS.
// Deadends + "0000" ko visited me daalo. Har state se 4 wheels x
// 2 direction = 8 naye states (modulo 10 se wrap). Target milte hi
// steps+1 return karo, queue khatam to -1.
//
// TIME COMPLEXITY: O(10^4 * 8) - bounded, zyada se zyada 10000 states
// SPACE COMPLEXITY: O(10^4) - queue + visited
//
// KEY LEARNING:
// "Minimum moves" + barabar cost = BFS, chahe problem tree/grid ki
// na lage. State ko node samajhna hi asli pattern hai.

import java.util.*;

class Solution {
    public int openLock(String[] deadends, String target) {
        Set<String> dead = new HashSet<>(Arrays.asList(deadends));
        if (dead.contains("0000")) return -1;
        if (target.equals("0000")) return 0;
        Queue<String> q = new LinkedList<>();
        Set<String> visited = new HashSet<>(dead);
        q.offer("0000");
        visited.add("0000");
        int steps = 0;
        while (!q.isEmpty()) {
            steps++; // agla level = ek move aage
            int size = q.size();
            for (int s = 0; s < size; s++) {
                String state = q.poll();
                for (int i = 0; i < 4; i++) {
                    char[] chars = state.toCharArray();
                    int digit = chars[i] - '0';
                    for (int d : new int[]{-1, 1}) {
                        int nd = (digit + d + 10) % 10; // wrap-around
                        chars[i] = (char) ('0' + nd);
                        String next = new String(chars);
                        if (next.equals(target)) return steps;
                        if (!visited.contains(next)) {
                            visited.add(next);
                            q.offer(next);
                        }
                        chars[i] = state.charAt(i); // wapas original
                    }
                }
            }
        }
        return -1;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.openLock(
            new String[]{"0201", "0101", "0102", "1212", "2002"}, "0202")); // Expected: 6
        System.out.println(sol.openLock(new String[]{"8888"}, "0009")); // Expected: 1
        System.out.println(sol.openLock(new String[]{"0000"}, "8888")); // Expected: -1
    }
}
