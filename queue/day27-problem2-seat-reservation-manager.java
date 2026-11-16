import java.util.PriorityQueue;

class SeatManager {
    private PriorityQueue<Integer> free;
    public SeatManager(int n) {
        free = new PriorityQueue<>();
        for (int i = 1; i <= n; i++) free.offer(i);
    }
    public int reserve() { return free.poll(); }
    public void unreserve(int seatNumber) { free.offer(seatNumber); }
}
