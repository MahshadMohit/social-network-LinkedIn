package com.example.linkedinproj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphWithAdjacencyMap {

    private class Vertex {
        private String label;
        private Map<Vertex, Integer> neighbors;

        public Vertex(String label) {
            this.label = label;
            this.neighbors = new HashMap<>();
        }

        public String getLabel() {
            return label;
        }

        public Map<Vertex, Integer> getNeighbors() {
            return neighbors;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private Map<String, Vertex> vertices;
    private int numEdges;

    public GraphWithAdjacencyMap() {
        this.vertices = new HashMap<>();
        this.numEdges = 0;
    }

    public int numVertices() {
        return vertices.size();
    }

    public List<String> vertices() {
        List<String> vertexList = new ArrayList<>();
        for (Vertex vertex : vertices.values()) {
            vertexList.add(vertex.getLabel());
        }
        return vertexList;
    }

    public int numEdges() {
        return numEdges;
    }

    public List<String> edges() {
        List<String> edgeList = new ArrayList<>();
        for (Vertex vertex : vertices.values()) {
            for (Map.Entry<Vertex, Integer> entry : vertex.getNeighbors().entrySet()) {
                edgeList.add(vertex.getLabel() + " - " + entry.getKey().getLabel());
            }
        }
        return edgeList;
    }

    private int getWeight(Vertex u, Vertex v) {
        return u.getNeighbors().get(v);
    }

    public String getEdge(String u, String v) {
        Vertex vertexU = vertices.get(u);
        Vertex vertexV = vertices.get(v);

        if (vertexU != null && vertexV != null && vertexU.getNeighbors().containsKey(vertexV)) {
            return u + " - " + v;
        }
        return null;
    }

    public List<String> endVertices(String e) {
        String[] vertices = e.split(" - ");
        List<String> endVertices = new ArrayList<>();
        endVertices.add(vertices[0]);
        endVertices.add(vertices[1]);
        return endVertices;
    }

    public String opposite(String v, String e) {
        List<String> endVertices = endVertices(e);
        if (endVertices.size() == 2) {
            if (endVertices.get(0).equals(v)) {
                return endVertices.get(1);
            } else if (endVertices.get(1).equals(v)) {
                return endVertices.get(0);
            }
        }
        return null;
    }

    public int outDegree(String v) {
        Vertex vertex = vertices.get(v);
        return vertex != null ? vertex.getNeighbors().size() : 0;
    }

    public int inDegree(String v) {
        int inDegree = 0;
        for (Vertex vertex : vertices.values()) {
            if (vertex.getNeighbors().containsKey(vertices.get(v))) {
                inDegree++;
            }
        }
        return inDegree;
    }

    public List<String> outgoingEdges(String v) {
        Vertex vertex = vertices.get(v);
        if (vertex != null) {
            List<String> outgoingEdges = new ArrayList<>();
            for (Vertex neighbor : vertex.getNeighbors().keySet()) {
                outgoingEdges.add(vertex.getLabel() + " - " + neighbor.getLabel());
            }
            return outgoingEdges;
        }
        return null;
    }

    public List<String> incomingEdges(String v) {
        List<String> incomingEdges = new ArrayList<>();
        for (Vertex vertex : vertices.values()) {
            if (vertex.getNeighbors().containsKey(vertices.get(v))) {
                incomingEdges.add(vertex.getLabel() + " - " + v);
            }
        }
        return incomingEdges;
    }

    public void insertVertex(String x) {
        if (!vertices.containsKey(x)) {
            vertices.put(x, new Vertex(x));
        }
    }

    public void insertEdge(String u, String v, int x) {
        Vertex vertexU = vertices.get(u);
        Vertex vertexV = vertices.get(v);

        if (vertexU != null && vertexV != null && !vertexU.getNeighbors().containsKey(vertexV)) {
            vertexU.getNeighbors().put(vertexV, x);
            numEdges++;
        }
    }

    public void removeVertex(String v) {
        Vertex vertexToRemove = vertices.get(v);

        if (vertexToRemove != null) {
            for (Vertex vertex : vertices.values()) {
                if (vertex.getNeighbors().containsKey(vertexToRemove)) {
                    removeEdge(vertex.getLabel(), v);
                }
            }

            vertices.remove(v);
        }
    }

    public void removeEdge(String u, String v) {
        Vertex vertexU = vertices.get(u);
        Vertex vertexV = vertices.get(v);

        if (vertexU != null && vertexV != null && vertexU.getNeighbors().containsKey(vertexV)) {
            vertexU.getNeighbors().remove(vertexV);
            numEdges--;
        }
    }
}