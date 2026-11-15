// Day 26 - Problem 3: Top K Frequent Elements (LC 347) - Medium
// Topic: Queue (min-heap / priority queue - frequency ke hisab se top-k)
// Approach: HashMap se frequency gino, phir size-k min-heap se top-k chuno.
// Time: O(n log k) | Space: O(n)
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) { freq.put(num, freq.getOrDefault(num, 0) + 1); }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            heap.offer(new int[]{e.getValue(), e.getKey()});
            if (heap.size() > k) { heap.poll(); }
        }
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) { ans[i] = heap.poll()[1]; }
        return ans;
    }
}
