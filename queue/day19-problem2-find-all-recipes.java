// Day 19 - Problem 2: Find All Possible Recipes from Given Supplies (LC 2115) - Medium
// Topic: Queue (Topological Sort / Kahn's Algorithm - dependency graph)
//
// PROBLEM (Hinglish):
// Tumhe `n` recipes hain. `recipes[i]` banane ke liye uske saare
// ingredients `ingredients[i]` chahiye. Ek recipe khud bhi kisi
// aur recipe ka ingredient ho sakti hai. `supplies` me wo
// ingredients hain jo tumhare paas pehle se hain (unlimited).
// Return karo un sab recipes ki list jo tum bana sakte ho
// (kisi bhi order me). Note: do recipes ek dusre par depend
// bhi kar sakti hain (cycle) - aisi recipes kabhi nahi banengi.
// Example: recipes = ["bread","sandwich"],
// ingredients = [["yeast","flour"],["bread","meat"]],
// supplies = ["yeast","flour","meat"] -> ["bread","sandwich"]
//
// APPROACH (Hinglish):
// Ye Course Schedule jaisa dependency problem hai - Kahn's
// algorithm (BFS topological sort) use karo:
// 1. Har ingredient se un recipes tak edge banao jo use chahte
//    hain; har recipe ka in-degree = uske ingredients ki count.
// 2. Queue me pehle saari `supplies` daalo (ye starting points hain).
// 3. Queue se ingredient nikalo, jo recipes us par depend karti
//    hain unka in-degree 1 ghatao; jiska in-degree 0 ho gaya, wo
//    recipe ban gayi - use answer me daalo aur queue me daalo
//    (kyunki ab ye khud ek available ingredient hai).
// Cycle wali recipes ka in-degree kabhi 0 nahi hota, to wo
// kabhi answer me nahi aati - bilkul sahi!
//
// TIME COMPLEXITY: O(N + E) - N = recipes, E = total ingredients
// SPACE COMPLEXITY: O(N + E) - graph + in-degree map + queue
//
// KEY LEARNING:
// Dependency wale problems me Kahn's algorithm yaad rakho:
// in-degree 0 wale nodes se BFS start karo, aur jab kisi node
// ki saari dependencies poori ho jaayein to use process karo.
// Yehi pattern Course Schedule (LC 207/210) me bhi tha.

import java.util.*;

class Solution {
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        // ingredient -> un recipes ki list jo is ingredient ko chahte hain
        Map<String, List<String>> graph = new HashMap<>();
        // har recipe ko kitne ingredients abhi bhi chahiye
        Map<String, Integer> indegree = new HashMap<>();

        for (int i = 0; i < recipes.length; i++) {
            indegree.put(recipes[i], ingredients.get(i).size());
            for (String ing : ingredients.get(i)) {
                graph.computeIfAbsent(ing, k -> new ArrayList<>()).add(recipes[i]);
            }
        }

        // queue me pehle saari supplies daalo
        Queue<String> queue = new LinkedList<>();
        for (String s : supplies) {
            queue.offer(s);
        }
        List<String> answer = new ArrayList<>();

        while (!queue.isEmpty()) {
            String item = queue.poll();
            // is item ko chahne wali har recipe ka in-degree ghatao
            for (String recipe : graph.getOrDefault(item, new ArrayList<>())) {
                indegree.put(recipe, indegree.get(recipe) - 1);
                if (indegree.get(recipe) == 0) {
                    // recipe ban gayi! answer me daalo
                    answer.add(recipe);
                    // ab ye recipe khud ek available ingredient hai
                    queue.offer(recipe);
                }
            }
        }

        return answer;
    }

    // TEST
    public static void main(String[] args) {
        Solution s = new Solution();
        // Example 1
        System.out.println(s.findAllRecipes(new String[]{"bread"},
                List.of(List.of("yeast", "flour")),
                new String[]{"yeast", "flour", "corn"})); // Expected: [bread]
        // Example 2
        System.out.println(s.findAllRecipes(new String[]{"bread", "sandwich"},
                List.of(List.of("yeast", "flour"), List.of("bread", "meat")),
                new String[]{"yeast", "flour", "meat"})); // Expected: [bread, sandwich]
        // chain: burger ko sandwich chahiye, sandwich ko bread
        System.out.println(s.findAllRecipes(new String[]{"bread", "sandwich", "burger"},
                List.of(List.of("yeast", "flour"), List.of("bread", "meat"), List.of("sandwich", "meat", "bread")),
                new String[]{"yeast", "flour", "meat"})); // Expected: [bread, sandwich, burger]
        // cycle: a ko b chahiye, b ko a - kuch nahi banega
        System.out.println(s.findAllRecipes(new String[]{"a", "b"},
                List.of(List.of("b"), List.of("a")),
                new String[]{})); // Expected: []
        // ek ingredient missing - sandwich nahi banega
        System.out.println(s.findAllRecipes(new String[]{"bread", "sandwich"},
                List.of(List.of("yeast", "flour"), List.of("bread", "meat")),
                new String[]{"yeast", "flour"})); // Expected: [bread]
    }
}
