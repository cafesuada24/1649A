package com.hahsm.algorithm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hahsm.common.type.Graph;

public class UniformCostSearchTest {

    private Graph cityGraph;

    @BeforeEach
    public void setUp() {
        // Initialize the graph and add edges
        cityGraph = new Graph();
        cityGraph.addEdge("Hanoi", "Haiphong", 120.0);
        cityGraph.addEdge("Hanoi", "Bac Ninh", 35.0);
        cityGraph.addEdge("Hanoi", "Hai Duong", 57.0);
        cityGraph.addEdge("Hanoi", "Vinh Phuc", 75.0);
        cityGraph.addEdge("Haiphong", "Bac Ninh", 85.0);
        cityGraph.addEdge("Bac Ninh", "Hai Duong", 40.0);
    }

    @Test
    public void testDirectPath() {
        // Test a direct path between Hanoi and Haiphong
        double cost = UniformCostSearch.search("Hanoi", "Haiphong", cityGraph);
        assertEquals(120.0, cost, 0.001, "The cost from Hanoi to Haiphong should be 120.0 km");
    }

    @Test
    public void testIndirectPath() {
        // Test an indirect path between Hanoi and Hai Duong
        double cost = UniformCostSearch.search("Hanoi", "Hai Duong", cityGraph);
        assertEquals(57.0, cost, 0.001, "The cost from Hanoi to Hai Duong should be 57.0 km");
    }

    @Test
    public void testShortestPathWithMultipleOptions() {
        // Test path where there are multiple options, expecting the shortest one
        double cost = UniformCostSearch.search("Haiphong", "Hai Duong", cityGraph);
        assertEquals(125.0, cost, 0.001, "The shortest path from Haiphong to Hai Duong should be 125.0 km");
    }

    @Test
    public void testSameCity() {
        // Test when the start and target cities are the same
        double cost = UniformCostSearch.search("Hanoi", "Hanoi", cityGraph);
        assertEquals(0.0, cost, 0.001, "The cost from a city to itself should be 0.0 km");
    }

    @Test
    public void testNoPath() {
        // Test when there is no path between two cities
        double cost = UniformCostSearch.search("Hanoi", "Nonexistent City", cityGraph);
        assertEquals(-1.0, cost, "The cost should be -1 if there is no path between the cities");
    }
}
