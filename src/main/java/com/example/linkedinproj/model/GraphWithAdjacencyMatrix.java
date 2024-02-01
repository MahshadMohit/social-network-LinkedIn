package com.example.linkedinproj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphWithAdjacencyMatrix {

    private final int DEFAULT_CAPACITY = 10;

    private Map<String, Integer> vertexIndices;
    private String[] vertices;
    private int[][] adjacencyMatrix;
    private int numVertices;
    private int numEdges;

    public GraphWithAdjacencyMatrix() {
        this.vertexIndices = new HashMap<>();
        this.vertices = new String[DEFAULT_CAPACITY];
        this.adjacencyMatrix = new int[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.numVertices = 0;
        this.numEdges = 0;
    }

    private int getIndex(String label) {
        return vertexIndices.get(label);
    }

    private void resizeMatrix() {
        int newSize = Math.max(numVertices + 1, adjacencyMatrix.length * 2);

        int[][] newMatrix = new int[newSize][newSize];
        String[] newVertices = new String[newSize];

        for (int i = 0; i < numVertices; i++) {
            newVertices[i] = vertices[i];
            for (int j = 0; j < numVertices; j++) {
                newMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }

        vertices = newVertices;
        adjacencyMatrix = newMatrix;
    }

    public int numVertices() {
        return numVertices;
    }

    public List<String> vertices() {
        List<String> vertexList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            vertexList.add(vertices[i]);
        }
        return vertexList;
    }

    public int numEdges() {
        return numEdges;
    }

    public List<String> edges() {
        List<String> edgeList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    edgeList.add(vertices[i] + " - " + vertices[j]);
                }
            }
        }
        return edgeList;
    }

    private int getWeight(int u, int v) {
        return adjacencyMatrix[u][v];
    }

    public String getEdge(String u, String v) {
        if (vertexIndices.containsKey(u) && vertexIndices.containsKey(v)) {
            int uIndex = getIndex(u);
            int vIndex = getIndex(v);

            if (adjacencyMatrix[uIndex][vIndex] != 0) {
                return u + " - " + v;
            }
        }
        return null;
    }

    public List<String> endVertices(String e) {
        String[] vertices = e.split(" - ");
        List<String> endVertices = new ArrayList<>();
        endVertices.add(vertices[0]);
        endVertices.add(vertices[2]);
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
        if (vertexIndices.containsKey(v)) {
            int vIndex = getIndex(v);
            int outDegree = 0;
            for (int i = 0; i < numVertices; i++) {
                if (adjacencyMatrix[vIndex][i] != 0) {
                    outDegree++;
                }
            }
            return outDegree;
        }
        return 0;
    }

    public int inDegree(String v) {
        if (vertexIndices.containsKey(v)) {
            int vIndex = getIndex(v);
            int inDegree = 0;
            for (int i = 0; i < numVertices; i++) {
                if (adjacencyMatrix[i][vIndex] != 0) {
                    inDegree++;
                }
            }
            return inDegree;
        }
        return 0;
    }

    public List<String> outgoingEdges(String v) {
        if (vertexIndices.containsKey(v)) {
            int vIndex = getIndex(v);
            List<String> outgoingEdges = new ArrayList<>();
            for (int i = 0; i < numVertices; i++) {
                if (adjacencyMatrix[vIndex][i] != 0) {
                    outgoingEdges.add(v + " - " + vertices[i]);
                }
            }
            return outgoingEdges;
        }
        return null;
    }

    public List<String> incomingEdges(String v) {
        if (vertexIndices.containsKey(v)) {
            int vIndex = getIndex(v);
            List<String> incomingEdges = new ArrayList<>();
            for (int i = 0; i < numVertices; i++) {
                if (adjacencyMatrix[i][vIndex] != 0) {
                    incomingEdges.add(vertices[i] + " - " + v);
                }
            }
            return incomingEdges;
        }
        return null;
    }

    public void insertVertex(String x) {
        if (!vertexIndices.containsKey(x)) {
            if (numVertices == vertices.length) {
                resizeMatrix();
            }
            vertexIndices.put(x, numVertices);
            vertices[numVertices] = x;
            numVertices++;
        }
    }

    public void insertEdge(String u, String v, int x) {
        if (vertexIndices.containsKey(u) && vertexIndices.containsKey(v)) {
            int uIndex = getIndex(u);
            int vIndex = getIndex(v);

            if (adjacencyMatrix[uIndex][vIndex] == 0) {
                adjacencyMatrix[uIndex][vIndex] = x;
                numEdges++;
            }
        }
    }

    /*public void removeVertex(String v) {
        if (vertexIndices.containsKey(v)) {
            int vIndex = getIndex(v);

            // Remove edges associated with the vertex
            for (int i = 0; i < numVertices; i++) {
                if (adjacencyMatrix[vIndex][i] != 0) {
                    removeEdge(vertices[vIndex] + " - " + vertices[i]);
                }
                if (adjacencyMatrix[i][vIndex] != 0) {
                    removeEdge(vertices[i] + " - " + vertices[vIndex]);
                }
            }

            // Remove the vertex itself
            vertexIndices.remove(v);
            numVertices--;
            for (int i = vIndex; i < numVertices; i++) {
                vertices[i] = vertices[i + 1];
                for (int j = 0; j < numVertices; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
                }
            }
            for (int i = vIndex; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    adjacencyMatrix[j][i] = adjacencyMatrix[j][i + 1];
                }
            }
        }
    }*/

    public void removeEdge(String u, String v) {
        if (vertexIndices.containsKey(u) && vertexIndices.containsKey(v)) {
            int uIndex = getIndex(u);
            int vIndex = getIndex(v);

            if (adjacencyMatrix[uIndex][vIndex] != 0) {
                adjacencyMatrix[uIndex][vIndex] = 0;
                numEdges--;
            }
        }
    }
}