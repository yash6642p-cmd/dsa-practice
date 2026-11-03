// Day 14 - Problem 5: Shortest Path with Alternating Colors (LC 1129) - Medium
// Topic: Queue (BFS with state)
//
// PROBLEM (Hinglish):
// n nodes wale directed graph me har edge red ya blue hai. Node 0
// se har node tak shortest alternating-color path ki lambai return
// karo, na pahunche to -1.
// Example 1: n = 3, red = [[0,1],[1,2]], blue = [] -> [0,1,-1]
// Example 2: n = 3, red = [[0,1]], blue = [[1,2]] -> [0,1,2]
//
// APPROACH (Hinglish):
// Sirf node visited mark karna kaafi nahi - wahi node red se aur
// blue se alag state me pahunch sakta hai, aur aage ka rasta is
// par depend karta hai ki pichhli edge kaunsi thi. Isliye state =
// (node, pichhli edge ka color). Node 0 ko dono colors ke saath
// queue me daalo, har state se sirf opposite color ki edges par
// aage badho. Har node ka jawab dono colors me se chhota distance.
//
// TIME COMPLEXITY: O(n + E) - har state (node, color) ek baar visit hota hai
// SPACE COMPLEXITY: O(n + E) - adjacency lists + dist arrays
//
// KEY LEARNING:
// Jab aage ka rasta "kaise pahunche" par depend kare, to visited me
// sirf node nahi, poora state rakho - yeh state-BFS pattern aage
// ke hard graph problems me baar-baar aayega.

import java.util.*;

class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        // graph[0] = red edges, graph[1] = blue edges
        List<Integer>[][] graph = new List[2][n];
        for (int c = 0; c < 2; c++) {
            for (int i = 0; i < n; i++) {
                graph[c][i] = new ArrayList<>();
            }
        }
        for (int[] e : redEdges) {
            graph[0][e[0]].add(e[1]);
        }
        for (int[] e : blueEdges) {
            graph[1][e[0]].add(e[1]);
        }
        final int INF = Integer.MAX_VALUE;
        // dist[color][node]: us color ki edge se node tak shortest distance
        int[][] dist = new int[2][n];
        for (int c = 0; c < 2; c++) {
            Arrays.fill(dist[c], INF);
        }
        dist[0][0] = 0;
        dist[1][0] = 0;
        // node 0 dono colors ke saath shuru karo (pehli edge koi bhi ho)
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{0, 0});
        q.offer(new int[]{0, 1});
        while (!q.isEmpty()) {
            int[] state = q.poll();
            int node = state[0];
            int lastColor = state[1];
            int nxtColor = 1 - lastColor; // agla color opposite hona chahiye
            for (int nei : graph[nxtColor][node]) {
                if (dist[nxtColor][nei] == INF) {
                    dist[nxtColor][nei] = dist[lastColor][node] + 1;
                    q.offer(new int[]{nei, nxtColor});
                }
            }
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int d = Math.min(dist[0][i], dist[1][i]);
            ans[i] = (d == INF) ? -1 : d;
        }
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(Arrays.toString(s.shortestAlternatingPaths(3,
            new int[][]{{0, 1}, {1, 2}}, new int[][]{}))); // Expected: [0, 1, -1]
        System.out.println(Arrays.toString(s.shortestAlternatingPaths(3,
            new int[][]{{0, 1}}, new int[][]{{1, 2}}))); // Expected: [0, 1, 2]
        System.out.println(Arrays.toString(s.shortestAlternatingPaths(3,
            new int[][]{{0, 1}}, new int[][]{{2, 1}}))); // Expected: [0, 1, -1]
    }
}
