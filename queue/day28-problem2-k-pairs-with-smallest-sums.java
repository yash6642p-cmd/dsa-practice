import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0) { return ans; }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < Math.min(k, nums1.length); i++) {
            heap.offer(new int[]{nums1[i] + nums2[0], i, 0});
        }
        while (!heap.isEmpty() && ans.size() < k) {
            int[] cur = heap.poll();
            int i = cur[1], j = cur[2];
            ans.add(List.of(nums1[i], nums2[j]));
            if (j + 1 < nums2.length) {
                heap.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }
        return ans;
    }
}
