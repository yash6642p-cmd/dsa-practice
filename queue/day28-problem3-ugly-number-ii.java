import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
class Solution {
    public int nthUglyNumber(int n) {
        PriorityQueue<Long> heap = new PriorityQueue<>();
        Set<Long> seen = new HashSet<>();
        heap.offer(1L);
        seen.add(1L);
        long ugly = 1;
        for (int i = 0; i < n; i++) {
            ugly = heap.poll();
            for (long factor : new long[]{2, 3, 5}) {
                long nxt = ugly * factor;
                if (!seen.contains(nxt)) { seen.add(nxt); heap.offer(nxt); }
            }
        }
        return (int) ugly;
    }
}
