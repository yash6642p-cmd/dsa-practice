// Day 16 - Problem 2: Time Needed to Inform All Employees (LC 1376) - Medium
// Topic: Queue (BFS level order)
//
// PROBLEM (Hinglish):
// n employees, headID boss hai. manager[i] = i ka manager,
// head ka manager -1. informTime[i] = i ko apne direct
// subordinates ko khabar pahunchane me lagne wala time. Head se
// shuru karke sab tak khabar pahunchne me kitna total time?
// Example: n = 6, headID = 2, manager = [2,2,-1,2,2,2],
// informTime = [0,0,1,0,0,0] -> 1
//
// APPROACH (Hinglish):
// Manager array se children ki list banao, phir BFS: queue me
// (employee, ab tak laga time) daalo, head se shuru (time 0).
// Har employee ke bachon ko (time + informTime[employee]) ke
// saath queue me daalo. Answer = sabse bada time jo kisi
// employee tak pahunchne me laga.
//
// TIME COMPLEXITY: O(n) - har employee ek baar visit hota hai
// SPACE COMPLEXITY: O(n) - children list + queue
//
// KEY LEARNING:
// Tree hamesha pointer se nahi banta - kabhi parent array se
// children ki list banani padti hai, phir wahi BFS chalti hai.

import java.util.*;

class Solution {
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        // children[i] = i ke direct subordinates
        List<List<Integer>> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            children.add(new ArrayList<>());
        }
        for (int emp = 0; emp < n; emp++) {
            if (manager[emp] != -1) {
                children.get(manager[emp]).add(emp);
            }
        }
        int ans = 0;
        // queue me (employee, head se yahan tak laga time)
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{headID, 0});
        while (!q.isEmpty()) {
            int[] item = q.poll();
            int emp = item[0], t = item[1];
            ans = Math.max(ans, t);
            for (int sub : children.get(emp)) {
                q.offer(new int[]{sub, t + informTime[emp]});
            }
        }
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.numOfMinutes(1, 0, new int[]{-1}, new int[]{0})); // Expected: 0
        System.out.println(s.numOfMinutes(6, 2, new int[]{2, 2, -1, 2, 2, 2}, new int[]{0, 0, 1, 0, 0, 0})); // Expected: 1
        System.out.println(s.numOfMinutes(7, 6, new int[]{1, 2, 3, 4, 5, 6, -1}, new int[]{0, 6, 5, 4, 3, 2, 1})); // Expected: 21
    }
}
