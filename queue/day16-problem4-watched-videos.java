// Day 16 - Problem 4: Get Watched Videos by Your Friends (LC 1311) - Medium
// Topic: Queue (BFS exact level)
//
// PROBLEM (Hinglish):
// friends[i] = i ke dost, watchedVideos[i] = i ke dekhe videos.
// id aur level diye hain. Un logon ke videos chahiye jo id se
// exactly 'level' doori par hain. Videos ko pehle frequency
// (kam se zyada), phir naam (alphabetical) se sort karke do.
// Example: watchedVideos = [["A","B"],["C"],["B","C"],["D"]],
// friends = [[1,2],[0,3],[0,3],[1,2]], id = 0, level = 1
// -> ["B","C"]
//
// APPROACH (Hinglish):
// BFS id se shuru, level-by-level chalao. Har level ke baad
// queue me sirf agle level ke log bachte hain. Exactly 'level'
// poora hone par queue me jo log hain wahi answer ke log hain.
// Unke videos gin lo, phir (count, naam) se sort karke naam do.
// visited zaroor rakho warna cycle me BFS ghoomta rahega.
//
// TIME COMPLEXITY: O(n + V log V) - BFS + videos ki sorting
// SPACE COMPLEXITY: O(n + V) - visited, queue aur frequency map
//
// KEY LEARNING:
// "Exactly k doori par" me BFS ko level ke hisab se chalao -
// poora level ek saath process karo, aur k level ke baad ruk jao.

import java.util.*;

class Solution {
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        Set<Integer> visited = new HashSet<>();
        visited.add(id);
        Queue<Integer> q = new LinkedList<>();
        q.offer(id);
        // level baar aage badho, har baar poora level ek saath
        for (int l = 0; l < level; l++) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int u = q.poll();
                for (int v : friends[u]) {
                    if (!visited.contains(v)) {
                        visited.add(v);
                        q.offer(v);
                    }
                }
            }
        }
        // ab queue me exactly 'level' doori wale log hain
        Map<String, Integer> freq = new HashMap<>();
        for (int person : q) {
            for (String video : watchedVideos.get(person)) {
                freq.put(video, freq.getOrDefault(video, 0) + 1);
            }
        }
        List<String> ans = new ArrayList<>(freq.keySet());
        // pehle frequency, phir naam se sort
        ans.sort((a, b) -> {
            int cmp = freq.get(a) - freq.get(b);
            return cmp != 0 ? cmp : a.compareTo(b);
        });
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        List<List<String>> wv = Arrays.asList(
            Arrays.asList("A", "B"), Arrays.asList("C"),
            Arrays.asList("B", "C"), Arrays.asList("D"));
        int[][] fr = {{1, 2}, {0, 3}, {0, 3}, {1, 2}};
        System.out.println(s.watchedVideosByFriends(wv, fr, 0, 1)); // Expected: [B, C]
        System.out.println(s.watchedVideosByFriends(wv, fr, 0, 2)); // Expected: [D]
        System.out.println(s.watchedVideosByFriends(wv, fr, 0, 0)); // Expected: [A, B]
    }
}
