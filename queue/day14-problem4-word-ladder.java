// Day 14 - Problem 4: Word Ladder (LC 127) - Hard
// Topic: Queue (BFS on words / implicit graph)
//
// PROBLEM (Hinglish):
// beginWord se endWord tak sequence banao: har step me sirf ek
// letter badal sakte ho, har beech wala word wordList me hona
// chahiye. Sabse chhoti sequence ki lambai return karo, nahi to 0.
// Example: "hit" -> "cog", list ["hot","dot","dog","lot","log","cog"] -> 5
//
// APPROACH (Hinglish):
// Words ko graph samjho, edge jab ek letter ka farq ho. Unweighted
// graph me shortest path = BFS, level-by-level. Har word ke har
// position par 26 letters try karo; jo naya word list me mile use
// queue me daalo aur set se hata do (visited). Jis level par
// endWord mile, wahi jawab hai. (LeetCode par rating ab Hard hai.)
//
// TIME COMPLEXITY: O(N * L * 26) - N words, L lambai, har position par 26 letters
// SPACE COMPLEXITY: O(N * L) - word set + queue
//
// KEY LEARNING:
// Graph pehle se banane ki zaroorat nahi - padosi on the fly
// generate karo, aur visited ko enqueue karte waqt hi hatao.

import java.util.*;

class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> words = new HashSet<>(wordList);
        // endWord list me hi nahi to sequence ban hi nahi sakti
        if (!words.contains(endWord)) {
            return 0;
        }
        Queue<String> q = new LinkedList<>();
        q.offer(beginWord);
        int length = 1; // beginWord sequence ka pehla word hai
        while (!q.isEmpty()) {
            // poora ek level ek saath process karo
            int size = q.size();
            for (int k = 0; k < size; k++) {
                String word = q.poll();
                if (word.equals(endWord)) {
                    return length;
                }
                // har position par har letter try karo
                char[] chars = word.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    char original = chars[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == original) {
                            continue;
                        }
                        chars[i] = c;
                        String nxt = new String(chars);
                        if (words.contains(nxt)) {
                            // mil gaya to queue me daalo aur set se hatao (visited)
                            words.remove(nxt);
                            q.offer(nxt);
                        }
                    }
                    chars[i] = original;
                }
            }
            length++;
        }
        return 0;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.ladderLength("hit", "cog",
            Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"))); // Expected: 5
        System.out.println(s.ladderLength("hit", "cog",
            Arrays.asList("hot", "dot", "dog", "lot", "log"))); // Expected: 0
        System.out.println(s.ladderLength("a", "c",
            Arrays.asList("a", "b", "c"))); // Expected: 2
    }
}
