import java.util.*;

class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }
        Map<Integer, List<Integer>> stopToRoutes = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                stopToRoutes.computeIfAbsent(stop, k -> new ArrayList<>()).add(i);
            }
        }
        if (!stopToRoutes.containsKey(source) || !stopToRoutes.containsKey(target)) {
            return -1;
        }
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{source, 0});
        Set<Integer> visitedStops = new HashSet<>();
        visitedStops.add(source);
        Set<Integer> visitedRoutes = new HashSet<>();
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int stop = cur[0], buses = cur[1];
            for (int r : stopToRoutes.get(stop)) {
                if (visitedRoutes.contains(r)) {
                    continue;
                }
                visitedRoutes.add(r);
                for (int nxt : routes[r]) {
                    if (nxt == target) {
                        return buses + 1;
                    }
                    if (!visitedStops.contains(nxt)) {
                        visitedStops.add(nxt);
                        q.offer(new int[]{nxt, buses + 1});
                    }
                }
            }
        }
        return -1;
    }
}
