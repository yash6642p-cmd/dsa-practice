import java.util.PriorityQueue;

class Solution {
    public String reorganizeString(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                heap.offer(new int[]{freq[i], i});
            }
        }
        StringBuilder ans = new StringBuilder();
        int[] prev = null;
        while (!heap.isEmpty() || prev != null) {
            if (heap.isEmpty()) {
                return "";
            }
            int[] cur = heap.poll();
            ans.append((char) ('a' + cur[1]));
            if (prev != null) {
                heap.offer(prev);
                prev = null;
            }
            cur[0]--;
            if (cur[0] > 0) {
                prev = cur;
            }
        }
        return ans.toString();
    }
}
