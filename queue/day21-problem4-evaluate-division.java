// Day 21 - Problem 4: Evaluate Division (LC 399) - Medium
// Topic: Queue (BFS on graph - division ke equations ka graph)
//
// PROBLEM (Hinglish):
// Equations diye hain jaise a / b = 2.0, b / c = 3.0.
// Queries poochhi jaati hain jaise a / c = ? (jawab 6.0, kyunki
// a/c = (a/b) * (b/c) = 2 * 3). Agar jawab nikaal na sake to -1.0.
// Example:
// equations = [["a","b"],["b","c"]], values = [2.0, 3.0]
// queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
// output = [6.0, 0.5, -1.0, 1.0, -1.0]
//
// APPROACH (Hinglish):
// Variables ko graph ke nodes banao: a -> b edge ka weight 2.0
// (a/b), aur ulta edge b -> a ka weight 1/2.0 (b/a).
// Har query ke liye BFS: start variable se target tak path dhundho,
// raaste me edge weights ko multiply karte jao. Path mil gaya to
// product hi jawab hai; variable hi nahi hai ya path nahi hai to -1.0.
// (x/x bhi -1.0 hai kyunki x graph me hai hi nahi.)
//
// TIME COMPLEXITY: O(E + Q * (V + E)) - graph banao + har query ek BFS
// SPACE COMPLEXITY: O(V + E) - graph + visited set
//
// KEY LEARNING:
// Division ke relations ek WEIGHTED graph hain - "a/b" ek edge hai
// aur chain multiply karna = graph me path dhundhna. BFS me node
// ke saath chalta hua product bhi queue me rakhna padta hai.

import java.util.*;

class Solution {
    // graph: var -> (neighbour var -> edge weight)
    private Map<String, Map<String, Double>> graph = new HashMap<>();

    private double bfs(String start, String target) {
        // start ya target graph me hi nahi to -1.0
        if (!graph.containsKey(start) || !graph.containsKey(target)) {
            return -1.0;
        }
        // queue me (variable, start se yahan tak ka product)
        Queue<Object[]> q = new LinkedList<>();
        q.offer(new Object[]{start, 1.0});
        Set<String> visited = new HashSet<>();
        visited.add(start);
        while (!q.isEmpty()) {
            Object[] item = q.poll();
            String var = (String) item[0];
            double prod = (double) item[1];
            if (var.equals(target)) {
                return prod;
            }
            for (Map.Entry<String, Double> e : graph.get(var).entrySet()) {
                if (!visited.contains(e.getKey())) {
                    visited.add(e.getKey());
                    q.offer(new Object[]{e.getKey(), prod * e.getValue()});
                }
            }
        }
        return -1.0;
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // graph banao: a -> b weight val, b -> a weight 1/val
        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            double val = values[i];
            graph.computeIfAbsent(a, k -> new HashMap<>()).put(b, val);
            graph.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / val);
        }
        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            ans[i] = bfs(queries.get(i).get(0), queries.get(i).get(1));
        }
        return ans;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        List<List<String>> eq = Arrays.asList(
            Arrays.asList("a", "b"), Arrays.asList("b", "c"));
        double[] vals = {2.0, 3.0};
        List<List<String>> qs = Arrays.asList(
            Arrays.asList("a", "c"), Arrays.asList("b", "a"),
            Arrays.asList("a", "e"), Arrays.asList("a", "a"),
            Arrays.asList("x", "x"));
        System.out.println(Arrays.toString(s.calcEquation(eq, vals, qs)));
        // Expected: [6.0, 0.5, -1.0, 1.0, -1.0]
        // simple chain - x/y = 4
        Solution s2 = new Solution();
        List<List<String>> eq2 = Arrays.asList(Arrays.asList("x", "y"));
        List<List<String>> qs2 = Arrays.asList(
            Arrays.asList("x", "x"), Arrays.asList("y", "x"));
        System.out.println(Arrays.toString(s2.calcEquation(eq2, new double[]{4.0}, qs2)));
        // Expected: [1.0, 0.25]
        // disconnected components
        Solution s3 = new Solution();
        List<List<String>> eq3 = Arrays.asList(
            Arrays.asList("a", "b"), Arrays.asList("c", "d"));
        List<List<String>> qs3 = Arrays.asList(
            Arrays.asList("a", "c"), Arrays.asList("c", "d"));
        System.out.println(Arrays.toString(s3.calcEquation(eq3, new double[]{2.0, 3.0}, qs3)));
        // Expected: [-1.0, 3.0]
    }
}
