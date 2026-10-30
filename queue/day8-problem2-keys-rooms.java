// Day 8 - Problem 2: Keys and Rooms (LC 841) - Medium
// ============================================================
// PROBLEM (Hinglish):
// n rooms, sirf room 0 khula hai. rooms[i] me un rooms ki keys
// hain jo room i se khulenge. Room 0 se kya saare rooms visit
// ho sakte hain?
// Example: [[1],[2],[3],[]] -> true
// Example: [[1,3],[3,0,1],[2],[0]] -> false
//
// APPROACH (Hinglish):
// Graph BFS: room 0 queue me daalo aur turant visited mark karo.
// Queue se room nikaalo, uski keys me se unvisited rooms queue
// me daalo. Aakhir me visited count == n ho to true. visited
// daalte waqt hi mark karo, warna cycle me loop lag jayega.
//
// TIME COMPLEXITY: O(n + e) - har room aur har key ek baar
// SPACE COMPLEXITY: O(n) - queue + visited

import java.util.*;

class Solution {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int n = rooms.size();
        boolean[] visited = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        visited[0] = true;
        q.offer(0);
        int count = 1; // kitne rooms visit ho gaye
        while (!q.isEmpty()) {
            int room = q.poll();
            for (int key : rooms.get(room)) {
                if (!visited[key]) {
                    visited[key] = true;
                    count++;
                    q.offer(key);
                }
            }
        }
        return count == n;
    }
}

// TEST
class Main {
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.canVisitAllRooms(Arrays.asList(
            Arrays.asList(1), Arrays.asList(2),
            Arrays.asList(3), Arrays.asList()))); // Expected: true
        System.out.println(s.canVisitAllRooms(Arrays.asList(
            Arrays.asList(1, 3), Arrays.asList(3, 0, 1),
            Arrays.asList(2), Arrays.asList(0)))); // Expected: false
    }
}
