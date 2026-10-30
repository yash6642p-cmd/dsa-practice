// Day 6 - Problem 3: Maximum Points You Can Obtain from Cards (LC 1423) - Medium
// Topic: Queue (Sliding Window)
//
// PROBLEM (Hinglish): Sirf ends se k cards uthane hain, sum maximum karo.
//
// APPROACH (Hinglish): Ulta socho - beech ke (n-k) cards chhodne hain jo ek
// continuous window honge. Minimum-sum window dhoondho, answer = total - min.
// Window ko aage sarkaao: left nikalo, right daalo (queue jaisa).
//
// TIME: O(n)  |  SPACE: O(1)
// KEY LEARNING: "Ends se uthana" = "beech ka minimum chhodna" (complement).

class Solution {
    public int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int total = 0;
        for (int v : cardPoints) total += v;
        if (k == n) return total;

        int window = n - k;
        int windowSum = 0;
        for (int i = 0; i < window; i++) windowSum += cardPoints[i];
        int minWindow = windowSum;
        for (int i = window; i < n; i++) {
            windowSum += cardPoints[i] - cardPoints[i - window];
            minWindow = Math.min(minWindow, windowSum);
        }
        return total - minWindow;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.maxScore(new int[]{1, 2, 3, 4, 5, 6, 1}, 3)); // 12
        System.out.println(s.maxScore(new int[]{2, 2, 2}, 2));             // 4
        System.out.println(s.maxScore(new int[]{9, 7, 7, 9, 7, 7, 9}, 7)); // 55
    }
}
