import java.util.PriorityQueue;

class Solution {
    private double gain(int p, int t) {
        return (double) (p + 1) / (t + 1) - (double) p / t;
    }
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) ->
            Double.compare(gain(b[0], b[1]), gain(a[0], a[1])));
        for (int[] c : classes) {
            heap.offer(c);
        }
        for (int i = 0; i < extraStudents; i++) {
            int[] c = heap.poll();
            c[0]++;
            c[1]++;
            heap.offer(c);
        }
        double total = 0;
        for (int[] c : heap) {
            total += (double) c[0] / c[1];
        }
        return total / classes.length;
    }
}
