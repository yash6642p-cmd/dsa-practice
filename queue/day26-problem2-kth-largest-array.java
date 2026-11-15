// Day 26 - Problem 2: Kth Largest Element in an Array (LC 215) - Medium
// Topic: Queue (min-heap / priority queue - sirf k bade rakho)
// Approach: array scan karo, size-k min-heap banaye rakho; root = kth largest.
// Time: O(n log k) | Space: O(k)
import java.util.PriorityQueue;
class Solution {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int num : nums) {
            heap.offer(num);
            if (heap.size() > k) { heap.poll(); }
        }
        return heap.peek();
    }
}
