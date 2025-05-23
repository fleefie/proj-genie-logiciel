package fr.cytech.projetgenielogiciel.maze;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing an adjacency list.
 * This class is used to represent a graph.
 */
public final class AdjacencyList implements Serializable {
    private final Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

    /**
     * Constructor for the AdjacencyList class.
     */
    public AdjacencyList() {
    }

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

    /**
     * HashCode method for the adjacency list.
     *
     * @return the hash code of the adjacency list
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((adjacencyList == null) ? 0 : adjacencyList.hashCode());
        return result;
    }

    /**
     * Equals method for the adjacency list.
     *
     * @param obj the object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AdjacencyList other = (AdjacencyList) obj;
        if (adjacencyList == null) {
            if (other.adjacencyList != null)
                return false;
        } else if (!adjacencyList.equals(other.adjacencyList))
            return false;
        return true;
    }

    /**
     * Gets the adjacency list.
     *
     * @return the adjacency list
     */
    public Map<Integer, List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }
}
