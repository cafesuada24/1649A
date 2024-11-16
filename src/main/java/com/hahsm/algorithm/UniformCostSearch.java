package com.hahsm.algorithm;

import com.hahsm.common.type.Graph;
import com.hahsm.common.type.Pair;
import com.hahsm.datastructure.HashMap;
import com.hahsm.datastructure.PriorityQueue;
import com.hahsm.datastructure.adt.Map;

public class UniformCostSearch {

    // Dijkstra's Algorithm
    public static double search(String startCity, String targetCity, final Graph graph) {
        if (startCity.equals(targetCity)) {
            return 0;
        }

        final PriorityQueue<Pair<String, Double>> frontier = new PriorityQueue<Pair<String, Double>>((a, b) -> {
            return a.getSecond() <= b.getSecond() ? -1 : 1;
        });

        frontier.add(new Pair<String, Double>(startCity, 0.0));
        Map<String, Boolean> visited = new HashMap<>();


        while (!frontier.isEmpty()) {
            final var current = frontier.remove();
            
            if (visited.containsKey(current.getFirst())) {
                continue;
            }
            if (current.getFirst().equals(targetCity)) {
                return current.getSecond();
            }
            visited.put(current.getFirst(), false);


            for (var neighbor : graph.getNeighbors(current.getFirst())) {
                if (visited.containsKey(neighbor.getDestination())) {
                    continue;
                }
                final double nextCost = current.getSecond() + neighbor.getDistance();
                frontier.add(new Pair<String, Double>(neighbor.getDestination(), nextCost));
            }
        }

        return -1;
    }
}
