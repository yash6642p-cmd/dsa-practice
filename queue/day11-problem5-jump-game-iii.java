// Day 11 - Problem 5: Jump Game III (LC 1306) - Medium
// Topic: Queue (array ko graph maan kar BFS)
//
// PROBLEM (Hinglish):
// Array arr aur start index diya hai. Index i par ho to i + arr[i] ya
// i - arr[i] par jump kar sakte ho (bounds ke andar). Batao kya kisi
// aise index tak pahunch sakte ho jahan value 0 ho.
// Example: arr = [4,2,3,0,3,1,2], start = 5 -> true (5->4->1->3)
// Example: arr = [3,0,2,1,2], start = 2 -> false
//
// APPROACH (Hinglish):
// Array ko graph samjho: har index ek node, usse 2 edges (aage-peeche jump).
// BFS karo - queue me start daalo, visited array rakho taaki cycle me na
// phanso (warna 2<->4 jaise loop me atak jaoge). Queue se index nikaalo,
// agar value 0 hai to true. Warna dono jumps try karo, valid + unvisited
// ko queue me daalo. Queue khaali ho gayi to false.
//
// TIME COMPLEXITY: O(n) - har index ek baar visit
// SPACE COMPLEXITY: O(n) - visited array + queue
//
// KEY LEARNING:
// Array par jump wale problems aksar graph traversal hote hain - visited
// set hi cycle se bachata hai, warna infinite loop pakka hai.

import java.util.*;

class Solution {
    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        boolean[] visited = new boolean[n];
        visited[start] = true;
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        while (!q.isEmpty()) {
            int i = q.poll();
            if (arr[i] == 0) return true;
            // dono possible jumps
            int[] nexts = {i + arr[i], i - arr[i]};
            for (int nxt : nexts) {
                if (nxt >= 0 && nxt < n && !visited[nxt]) {
                    visited[nxt] = true;
                    q.offer(nxt);
                }
            }
        }
        return false;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.canReach(new int[]{4,2,3,0,3,1,2}, 5));
        // Expected: true
        System.out.println(s.canReach(new int[]{4,2,3,0,3,1,2}, 0));
        // Expected: true
        System.out.println(s.canReach(new int[]{3,0,2,1,2}, 2));
        // Expected: false
    }
}
