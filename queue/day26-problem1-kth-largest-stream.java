// Day 26 - Problem 1: Kth Largest Element in a Stream (LC 703) - Easy
// Topic: Queue (min-heap / priority queue - stream me top-k banaye rakhna)
// Approach: sirf k sabse bade numbers min-heap me rakho; heap ka root hi kth largest hai.
// Time: O(n log k) constructor, O(log k) per add | Space: O(k)
import java.util.PriorityQueue;
class KthLargest {
    private PriorityQueue<Integer> heap;
    private int k;
    public KthLargest(int k, int[] nums) {
        this.k = k;
        heap = new PriorityQueue<>();
        for (int num : nums) { heap.offer(num); }
        while (heap.size() > k) { heap.poll(); }
    }
    public int add(int val) {
        heap.offer(val);
        if (heap.size() > k) { heap.poll(); }
        return heap.peek();
    }
}
