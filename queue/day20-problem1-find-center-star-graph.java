// Day 20 - Problem 1: Find Center of Star Graph (LC 1791) - Easy
// Topic: Queue (Graph - star graph ka center dhundho)
//
// PROBLEM (Hinglish):
// `n` nodes wala undirected STAR graph hai (nodes 1 se n).
// Star graph matlab: ek CENTER node hota hai jo baaki saare nodes se
// connected hota hai, aur baaki nodes sirf center se connected hote hain.
// `edges` me saare edges hain. Center node return karo.
// Example: edges = [[1,2],[2,3],[4,2]] -> output 2
// (node 2 sabse connected hai, wahi center hai)
//
// APPROACH (Hinglish):
// Center node har edge me aata hai - kyunki wo har node se connected
// hai. Toh pehle do edges me jo node COMMON hai, wahi center hai!
// Sirf edges[0] aur edges[1] compare karo - O(1) kaam.
// (Degree-counting wala tareeka bhi hai: jis node ki degree n-1 ho,
// wahi center - lekin first-two-edges trick sabse fast hai.)
//
// TIME COMPLEXITY: O(1) - sirf pehle do edges dekhte hain
// SPACE COMPLEXITY: O(1) - koi extra memory nahi
//
// KEY LEARNING:
// "Graph ki structure se shortcut nikalo" - star graph ki property
// (center har edge me hota hai) se poora graph scan karne ki zaroorat
// hi nahi. Pehle problem ki khaasiyat samjho, phir code likho.

class Solution {
    public int findCenter(int[][] edges) {
        // pehle do edges me common node hi center hai
        // edges[0] ka pehla node edges[1] me hai to wahi center
        if (edges[0][0] == edges[1][0] || edges[0][0] == edges[1][1]) {
            return edges[0][0];
        }
        // warna edges[0] ka doosra node center hai
        return edges[0][1];
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.findCenter(new int[][]{{1, 2}, {2, 3}, {4, 2}})); // Expected: 2
        // Example 2
        System.out.println(s.findCenter(new int[][]{{1, 2}, {5, 1}, {1, 3}, {1, 4}})); // Expected: 1
        // chhota star - center 3
        System.out.println(s.findCenter(new int[][]{{1, 3}, {2, 3}})); // Expected: 3
    }
}
