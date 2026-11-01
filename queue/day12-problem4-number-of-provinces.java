// Day 12 - Problem 4: Number of Provinces (LC 547) - Medium
// Topic: Queue (BFS - connected components)
//
// PROBLEM (Hinglish):
// n cities hain. isConnected matrix me isConnected[i][j] = 1 ka matlab
// city i aur city j seedha connected hain. Ek province = directly ya
// indirectly connected cities ka group. Total provinces gin kar return karo.
// Example: isConnected = [[1,1,0],[1,1,0],[0,0,1]] -> output 2
//
// APPROACH (Hinglish):
// Har city ko ek node samjho. Jo city abhi visited nahi hai, wahan se
// BFS shuru karo - queue se us province ki saari cities visit kar lo aur
// visited mark kar do, phir provinces me 1 jodo. Har nayi BFS = ek naya
// province. Matrix ki row scan karne se har city ke neighbors milte hain.
//
// TIME COMPLEXITY: O(n^2) - matrix ki har row har city ke liye scan hoti hai
// SPACE COMPLEXITY: O(n) - visited array + queue
//
// KEY LEARNING:
// "Kitne groups/components hain" poochha jaye to visited + BFS/DFS se
// har component ko ek baar ginna hi standard tareeqa hai - chahe input
// matrix ho ya adjacency list. (Union-Find bhi isi ka doosra tareeqa hai.)

import java.util.*;

class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int provinces = 0;
        for (int city = 0; city < n; city++) {
            if (visited[city]) continue;
            // naya province mila - iski saari cities BFS se visit karo
            provinces++;
            Queue<Integer> q = new LinkedList<>();
            q.offer(city);
            visited[city] = true;
            while (!q.isEmpty()) {
                int cur = q.poll();
                for (int nb = 0; nb < n; nb++) {
                    if (isConnected[cur][nb] == 1 && !visited[nb]) {
                        visited[nb] = true;
                        q.offer(nb);
                    }
                }
            }
        }
        return provinces;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.findCircleNum(
            new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}})); // Expected: 2
        System.out.println(s.findCircleNum(
            new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}})); // Expected: 3
        System.out.println(s.findCircleNum(
            new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}})); // Expected: 1
    }
}
