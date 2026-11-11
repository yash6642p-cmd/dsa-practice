// Day 22 - Problem 5: Jump Game IV (LC 1345) - Hard (stretch)
// Topic: Queue (BFS + hashmap - same value pe teleport)
//
// PROBLEM (Hinglish):
// Array arr diya hai, tum index 0 pe ho. Aakhri index (n-1) tak
// MINIMUM steps me pahunchna hai. Ek step me index i se ja sakte ho:
// - i + 1 (aage)
// - i - 1 (peeche)
// - koi bhi j jahan arr[i] == arr[j] (same value pe TELEPORT)
// Example: arr = [100,-23,-23,404,100,23,23,23,3,404] -> 3
// (0 -> 4 (same value 100) -> 3 (peeche) -> 9 (same value 404))
// Example: arr = [7,6,9,6,9,6,9,7] -> 1 (0 se seedha 7, same value)
// Example: arr = [7] -> 0 (pehle se hi aakhri index pe ho)
//
// APPROACH (Hinglish):
// Minimum steps = BFS, level by level. Lekin same value wale jumps
// ke liye har baar poora array scan mat karo - pehle ek hashmap banao:
// value -> us value wale saare indices. Jab kisi value ke indices
// ko ek baar use kar lo, to us list ko map se HATA DO (remove) -
// warna har baar wahi indices dobara queue me aayenge aur O(n^2)
// ho jayega. Ye ek line hi is problem ki jaan hai: list remove karne
// se har index sirf ek baar process hota hai, total O(n).
//
// TIME COMPLEXITY: O(n) - har index ek baar visit, value list ek baar remove
// SPACE COMPLEXITY: O(n) - hashmap + queue + visited
//
// KEY LEARNING:
// BFS me jab ek hi "group" se baar-baar jump ho sakta hai, to group
// ko ek baar use karke hata do. Bina remove kiye ye problem TLE dega -
// [5,5,5,...50000 baar] wale case me 2.5 billion operations ho jate hain!

import java.util.*;

class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1) {
            return 0;
        }
        // value -> us value wale saare indices
        Map<Integer, List<Integer>> valueToIndices = new HashMap<>();
        for (int i = 0; i < n; i++) {
            valueToIndices.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
        }
        boolean[] visited = new boolean[n];
        visited[0] = true;
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        int steps = 0;
        while (!q.isEmpty()) {
            // ek level = ek jump
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int i = q.poll();
                if (i == n - 1) {
                    return steps;
                }
                // aage aur peeche
                if (i + 1 < n && !visited[i + 1]) {
                    visited[i + 1] = true;
                    q.offer(i + 1);
                }
                if (i - 1 >= 0 && !visited[i - 1]) {
                    visited[i - 1] = true;
                    q.offer(i - 1);
                }
                // same value wale saare indices - sirf EK baar
                List<Integer> same = valueToIndices.get(arr[i]);
                if (same != null) {
                    for (int nxt : same) {
                        if (!visited[nxt]) {
                            visited[nxt] = true;
                            q.offer(nxt);
                        }
                    }
                }
                // KEY TRICK: is value ki list ab kabhi kaam nahi aayegi
                valueToIndices.remove(arr[i]);
            }
            steps++;
        }
        return -1;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1 (LeetCode)
        System.out.println(s.minJumps(new int[]{100, -23, -23, 404, 100, 23, 23, 23, 3, 404})); // Expected: 3
        // Example 2 (LeetCode) - single element
        System.out.println(s.minJumps(new int[]{7})); // Expected: 0
        // Example 3 (LeetCode) - seedha teleport
        System.out.println(s.minJumps(new int[]{7, 6, 9, 6, 9, 6, 9, 7})); // Expected: 1
        // simple walk: 0->1->2
        System.out.println(s.minJumps(new int[]{6, 1, 9})); // Expected: 2
        // same value chain: 0 -> 5 (teleport) -> 6
        System.out.println(s.minJumps(new int[]{5, 5, 5, 5, 5, 5, 9})); // Expected: 2
    }
}
