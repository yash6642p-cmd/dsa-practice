import java.util.Collections;
import java.util.PriorityQueue;
class MedianFinder {
    private PriorityQueue<Integer> low;
    private PriorityQueue<Integer> high;
    public MedianFinder() {
        low = new PriorityQueue<>(Collections.reverseOrder());
        high = new PriorityQueue<>();
    }
    public void addNum(int num) {
        low.offer(num);
        high.offer(low.poll());
        if (high.size() > low.size()) { low.offer(high.poll()); }
    }
    public double findMedian() {
        if (low.size() > high.size()) { return low.peek(); }
        return (low.peek() + high.peek()) / 2.0;
    }
}
