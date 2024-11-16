package com.hahsm.common.type;


import com.hahsm.datastructure.ArrayList;
import com.hahsm.datastructure.HashMap;
import com.hahsm.datastructure.adt.List;
import com.hahsm.datastructure.adt.Map;

public class Graph {
    private final Map<String, List<Edge>> graph = new HashMap<>();

    // Edge class to store the neighbor city and distance
    public static class Edge {
        private String destination;
        private double distance;

        Edge(String destination, double distance) {
            this.destination = destination;
            this.distance = distance;
        }

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}
    }

    // Method to add a bidirectional edge
    public void addEdge(String city1, String city2, double distance) {
        graph.computeIfAbsent(city1, _ -> new ArrayList<>()).add(new Edge(city2, distance));
        graph.computeIfAbsent(city2, _ -> new ArrayList<>()).add(new Edge(city1, distance));
    }

    // Get neighbors of a city
    public List<Edge> getNeighbors(String city) {
        return graph.containsKey(city) ? graph.get(city) : new ArrayList<Edge>();
    }
}
