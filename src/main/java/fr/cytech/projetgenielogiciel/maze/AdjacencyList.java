package fr.cytech.projetgenielogiciel.maze;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Class representing an adjacency list.
 *
 * An adjacency list is a data structure used to represent a graph.
 * It represents this graph through only the edges between nodes.
 * Note that this is not a class to represent a full graph, this only
 * handles connections, not the contained data.
 */
/*
 * NOTE:
 * This class is final as to force composition and avoid the
 * horrible anti-pattern of inheritance from an individual component of another
 * class.
 * Please do not do this. It is a horrendous idea.
 * If you need to extend this class, please don't. You're already doing
 * something wrong.
 * Classes meant as components for another class should be composed, always.
 */

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class AdjacencyList {
    @EqualsAndHashCode.Include
    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    /**
     * Adds an edge to the adjacency list.
     *
     * @param sourceId the source node ID
     * @param targetId the target node ID
     */
    public void addEdge(Integer sourceId, Integer targetId) {
        adjacencyList.computeIfAbsent(sourceId, k -> new java.util.ArrayList<>()).add(targetId);
        adjacencyList.computeIfAbsent(targetId, k -> new java.util.ArrayList<>()).add(sourceId);
    }

    /**
     * Adds an edge to the adjacency list, unidirectionally.
     *
     * @param sourceId the source node ID
     * @param targetId the target node ID
     */
    public void addEdgeOneWay(Integer sourceId, Integer targetId) {
        adjacencyList.computeIfAbsent(sourceId, k -> new java.util.ArrayList<>()).add(targetId);
    }

    /**
     * Removes an edge from the adjacency list.
     *
     * @param sourceId the source node ID
     * @param targetId the target node ID
     */
    public void removeEdge(Integer sourceId, Integer targetId) {
        List<Integer> sourceNeighbors = adjacencyList.get(sourceId);
        List<Integer> targetNeighbors = adjacencyList.get(targetId);
        if (sourceNeighbors != null) {
            sourceNeighbors.remove(targetId);
        }
        if (targetNeighbors != null) {
            targetNeighbors.remove(sourceId);
        }
    }

    /**
     * Removes an edge from the adjacency list, unidirectionally.
     *
     * @param sourceId the source node ID
     * @param targetId the target node ID
     */
    public void removeEdgeOneWay(Integer sourceId, Integer targetId) {
        List<Integer> sourceNeighbors = adjacencyList.get(sourceId);
        if (sourceNeighbors != null) {
            sourceNeighbors.remove(targetId);
        }
    }

    /**
     * Gets the neighbors of a node.
     *
     * @param nodeId the node ID
     * @return the list of neighbors
     */
    public List<Integer> getNeighbors(Integer nodeId) {
        return adjacencyList.getOrDefault(nodeId, java.util.Collections.emptyList());
    }

    /**
     * Checks if an edge exists between two nodes.
     * Specifically, from the source to the target.
     * This does not check whether the connection is bidirectional.
     *
     * @param sourceId the source node ID
     * @param targetId the target node ID
     * @return true if the edge exists, false otherwise
     */
    public boolean hasEdge(Integer sourceId, Integer targetId) {
        List<Integer> neighbors = adjacencyList.get(sourceId);
        return neighbors != null && neighbors.contains(targetId);
    }

    /**
     * ToString method for the adjacency list.
     * Format: {nodeId: [neighbor1, neighbor2, ...], ...}
     * 
     * @return the string representation of the adjacency list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");
        return sb.toString();
    }
}
