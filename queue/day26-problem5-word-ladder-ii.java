// Day 26 - Problem 5: Word Ladder II (LC 126) - Hard (stretch)
// Topic: Queue (BFS + DFS - pehle shortest distance, phir saare shortest paths)
// Approach: BFS se parents map banao (level khatm hone par hi visited karo), phir DFS se saare shortest paths nikalo.
// Time: O(N * L * 26) BFS + paths | Space: O(N * L)
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        List<List<String>> result = new ArrayList<>();
        if (!wordSet.contains(endWord)) { return result; }
        Map<String, List<String>> parents = new HashMap<>();
        parents.put(beginWord, new ArrayList<>());
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);
        boolean found = false;
        while (!queue.isEmpty() && !found) {
            int levelSize = queue.size();
            Set<String> levelVisited = new HashSet<>();
            for (int s = 0; s < levelSize; s++) {
                String word = queue.poll();
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char orig = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == orig) continue;
                        chars[i] = c;
                        String nxt = new String(chars);
                        if (!wordSet.contains(nxt) || visited.contains(nxt)) { continue; }
                        if (!parents.containsKey(nxt)) {
                            parents.put(nxt, new ArrayList<>());
                            queue.offer(nxt);
                            levelVisited.add(nxt);
                        }
                        parents.get(nxt).add(word);
                        if (nxt.equals(endWord)) { found = true; }
                    }
                    chars[i] = orig;
                }
            }
            visited.addAll(levelVisited);
        }
        if (!parents.containsKey(endWord)) { return result; }
        List<String> path = new ArrayList<>();
        path.add(endWord);
        dfs(endWord, beginWord, parents, path, result);
        return result;
    }
    private void dfs(String word, String beginWord, Map<String, List<String>> parents, List<String> path, List<List<String>> result) {
        if (word.equals(beginWord)) {
            List<String> one = new ArrayList<>(path);
            java.util.Collections.reverse(one);
            result.add(one);
            return;
        }
        for (String parent : parents.get(word)) {
            path.add(parent);
            dfs(parent, beginWord, parents, path, result);
            path.remove(path.size() - 1);
        }
    }
}
