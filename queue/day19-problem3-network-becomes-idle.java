// Day 19 - Problem 3: The Time When the Network Becomes Idle (LC 2039) - Medium
// Topic: Queue (BFS - shortest distance, phir timing ka hisaab)
//
// PROBLEM (Hinglish):
// `n` servers ka network hai, server 0 master hai, baaki data
// servers hain. Har data server second 0 par master ko ek
// message bhejta hai. Reply aane tak, har `patience[i]` seconds
// par wo message DOBARA bhejta rehta hai (agar reply abhi tak
// nahi aaya). Ek message ko server i se master tak jaane aur
// wapas aane me 2 * (shortest distance) seconds lagte hain.
// Network idle tab hota hai jab koi message kahin travel nahi
// kar raha. Wo earliest second return karo jab se network idle
// ho jaata hai.
// Example: edges = [[0,1],[1,2]], patience = [0,2,1] -> output 8
//
// APPROACH (Hinglish):
// Step 1: BFS se har server ki master (0) se shortest distance
//   `d` nikalo. Round trip time = 2 * d.
// Step 2: Server i ke liye - agar round trip patience se chhota
//   ya barabar hai, to wo dobara message bhejta hi nahi, aur
//   uska last reply 2*d second par aata hai.
//   Agar round trip bada hai, to wo har patience[i] seconds par
//   resend karta hai. Last resend ka time =
//   ((2*d - 1) / patience[i]) * patience[i]
//   (ye wo aakhri resend hai jo reply aane se pehle bhej diya
//   gaya). Uska reply aata hai last_resend + 2*d par, aur
//   uske 1 second baad network us server ke liye idle.
// Step 3: Saare servers me se MAXIMUM idle time hi answer hai.
//
// TIME COMPLEXITY: O(n + m) - BFS (n nodes, m edges) + ek pass
// SPACE COMPLEXITY: O(n + m) - adjacency list + distance array
//
// KEY LEARNING:
// BFS sirf distance nikalne ke liye hai - asli trick timing ka
// formula hai. `(2*d - 1) / patience` wala hissa dhyaan se
// samjho: ye batata hai ki reply aane se pehle kitni baar
// resend ho chuka hai. Aise "BFS + maths" combo problems me
// pehle distance nikalo, phir formula lagao.

import java.util.*;

class Solution {
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int n = patience.length;
        // adjacency list banao
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        // BFS se master (0) se har server ki shortest distance
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[0] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int nb : graph.get(node)) {
                if (dist[nb] == -1) {
                    dist[nb] = dist[node] + 1;
                    queue.offer(nb);
                }
            }
        }

        // har server ka idle time nikalo, maximum lo
        int answer = 0;
        for (int i = 1; i < n; i++) {
            int roundTrip = 2 * dist[i];
            // aakhri resend ka time (reply aane se pehle)
            int lastSend = ((roundTrip - 1) / patience[i]) * patience[i];
            // uska reply aane ke 1 second baad idle
            int idleTime = lastSend + roundTrip + 1;
            answer = Math.max(answer, idleTime);
        }

        return answer;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.networkBecomesIdle(new int[][]{{0, 1}, {1, 2}}, new int[]{0, 2, 1})); // Expected: 8
        // Example 2
        System.out.println(s.networkBecomesIdle(new int[][]{{0, 1}, {0, 2}, {1, 2}}, new int[]{0, 10, 10})); // Expected: 3
        // single data server, patience chhota - resend hoga
        System.out.println(s.networkBecomesIdle(new int[][]{{0, 1}}, new int[]{0, 1})); // Expected: 4
        // patience bada - koi resend nahi
        System.out.println(s.networkBecomesIdle(new int[][]{{0, 1}, {1, 2}, {2, 3}}, new int[]{0, 100, 100, 100})); // Expected: 7
    }
}
