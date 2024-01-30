package com.example.linkedinproj.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {


    public static class GraphWithEdgeList {

        private class Node {
            private String label;
            private List<Edge> edges = new ArrayList<>();

            public Node(String label) {
                this.label = label;
            }

            @Override
            public String toString() {
                return label;
            }

            public void addEdge(Node to, int weight) {
                var newEdge = new Edge(this, to, weight);
                if (!edges.contains(newEdge)) {
                    edges.add(newEdge);
                }
            }

            public void removeEdge(Edge e) {
                edges.remove(e);
            }

            public List<Edge> getEdges() {
                return edges;
            }
        }


        private class Edge {
            private Node from;
            private Node to;
            private int weight;

            public Edge(Node from, Node to, int weight) {
                this.from = from;
                this.to = to;
                this.weight = weight;
            }

            @Override
            public String toString() {
                return from + "->" + to;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;

                Edge otherEdge = (Edge) obj;
                return this.from.equals(otherEdge.from) && this.to.equals(otherEdge.to) && this.weight == otherEdge.weight;
            }
        }


        private Map<String, Node> nodes;
        private List<Edge> edgeList;

        public GraphWithEdgeList() {
            this.nodes = new HashMap<>();
            this.edgeList = new ArrayList<>();
        }

        public void addNode(String label) {
            nodes.putIfAbsent(label, new Node(label));
        }

        public void addEdge(String from, String to, int weight) {
            var fromNode = nodes.get(from);
            if (from == null)
                throw new IllegalStateException();

            var toNode = nodes.get(to);
            if (to == null)
                throw new IllegalStateException();

            fromNode.addEdge(toNode, weight);
            toNode.addEdge(fromNode, weight);

            var e = new Edge(fromNode, toNode, weight);
            if (!edgeList.contains(e)) {
                edgeList.add(e);
            }
            var reverse = new Edge(toNode, fromNode, weight);
            if (!edgeList.contains(reverse)) {
                edgeList.add(reverse);
            }
        }

        public void print() {
            for (var node : nodes.values()) {
                var edge = node.getEdges();
                if (!edge.isEmpty())
                    System.out.println(node + " is connected to" + edge);
            }
        }

        public int numVertices() {
            return nodes.size();
        }

        public Iterable<Node> Vertices() {
            return nodes.values();
        }

        public int numEdges() {
            return edgeList.size();
        }

        public Iterable<Edge> Edges() {
            return edgeList;
        }

        public List<String> endVertices(Edge e) {
            List<String> vertices = new ArrayList<>();
            for (Node node : nodes.values()) {
                if (e.to == node || e.from == node)
                    vertices.add(node.label);
            }
            return vertices;
        }

        public Edge getEdge(String from, String to) {
            var fromNode = nodes.get(from);
            var toNode = nodes.get(to);
            for (Edge e : fromNode.getEdges()) {
                if (e.to == toNode)
                    return e;
            }
            return null;
        }


        public String opposite(String label, Edge e) {
            var node = nodes.get(label);
            if (label == null)
                return null;
            List<String> vertices = endVertices(e);
            if (vertices.size() == 2) {
                if (e.from == node) {
                    return e.to.label;
                } else if (e.to == node) {
                    return e.from.label;
                }
            }
            return null;
        }

        public int outDegree(String label) {
            var node = nodes.get(label);
            if (node == null)
                return 0;

            return node.getEdges().size();
        }

        public int inDegree(String label) {
            return outDegree(label);
        }


        public Iterable<Edge> outComingEdges(String label) {
            var node = nodes.get(label);
            if (node == null)
                return null;

            return node.getEdges();
        }

        public Iterable<Edge> inComingEdges(String label) {
            return outComingEdges(label);
        }


        public void removeNode(String label) {
            var node = nodes.get(label);
            if (node != null) {

                List<Edge> edgesToRemove = new ArrayList<>();
                for (var edge : edgeList) {
                    if (edge.from == node || edge.to == node) {
                        edgesToRemove.add(edge);
                    }
                }


                for (var edge : edgesToRemove) {
                    removeEdge(edge);
                }


                nodes.remove(label);
            }
        }


        public void removeEdge(Edge e) {
            if (e != null) {
                Node from = e.from;
                Node to = e.to;

                // Remove the edge from the 'from' node's edge list
                from.removeEdge(e);

                // Create the reverse edge
                Edge reverse = new Edge(to, from, e.weight);

                // Remove the reverse edge from the 'to' node's edge list
                to.removeEdge(reverse);

                // Remove both edges from the overall edge list
                edgeList.removeIf(edge -> edge.equals(e));
                edgeList.removeIf(edge -> edge.equals(reverse));
            }
        }

    }


    public static class GraphWithAdjacencyList {

        private class Edge {


            private Node from;
            private Node to;
            private int weight;

            public Edge(Node from, Node to, int weight) {
                this.from = from;
                this.to = to;
                this.weight = weight;
            }

            public Edge(int weight) {
                this.weight = weight;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;

                Edge otherEdge = (Edge) obj;
                return this.from.equals(otherEdge.from) && this.to.equals(otherEdge.to) && this.weight == otherEdge.weight;
            }

            @Override
            public String toString() {
                return from + "->" + to;
            }

            public int getWeight() {
                return weight;
            }
        }

        private class Node {
            private String label;

            public Node(String label) {
                this.label = label;
            }

            @Override
            public String toString() {
                return label;
            }
        }

        private Map<String, Node> nodes;
        private Map<Node, List<Node>> adjacencyList;
        private Map<Node, List<Edge>> adjacencyList1;

        public GraphWithAdjacencyList() {
            this.nodes = new HashMap<>();
            this.adjacencyList = new HashMap<>();
            this.adjacencyList1 = new HashMap<>();
        }

        public int numVertices() {
            return nodes.size();
        }

        public Iterable<Node> Vertices() {
            return nodes.values();
        }

        public int numEdge() {
            int total = 0;
            for (var list : adjacencyList1.values()) {
                total += list.size();
            }
            return total;
        }

        public Iterable<Edge> Edges() {
            List<Edge> edgeList = new ArrayList<>();
            for (var list : adjacencyList1.values()) {
                edgeList.addAll(list);
            }
            return edgeList;
        }


        public Edge getEdge(String from, String to) {
            var fromNode = nodes.get(from);
            var toNode = nodes.get(to);
            if (fromNode == null || toNode == null)
                return null;
            for (var e : adjacencyList1.get(fromNode)) {
                if (e.to == toNode)
                    return e;
            }
            return null;
        }

        public List<String> endVertices(Edge e) {
            List<String> endVertices = new ArrayList<>();
            for (var map : adjacencyList1.entrySet()) {
                for (var edge : map.getValue()) {
                    if (e.equals(edge)) {
                        endVertices.add(map.getKey().label);
                    }
                }
            }
            return endVertices;
        }


        public String opposite(String label, Edge e) {
            Node from = nodes.get(label);

            if (from == null)
                return null;

            var vertices = endVertices(e);
            if (vertices.size() == 2) {
                if (e.from == from) {
                    return e.to.label;
                } else if (e.to == from) {
                    return e.from.label;
                }
            }
            return null;
        }


        public int outDegree(String label) {
            var node = nodes.get(label);
            if (node == null)
                return 0;

            return adjacencyList1.get(node).size();
        }

        public int inDegree(String label) {
            return outDegree(label);
        }


        public Iterable<Edge> outComingEdges(String label) {
            var node = nodes.get(label);
            if (node == null)
                return null;

            return adjacencyList1.get(node);
        }

        public Iterable<Edge> inComingEdges(String label) {
            return outComingEdges(label);
        }

        public void
        addNode(String label) {
            var node = new Node(label);
            nodes.putIfAbsent(label, node);
            adjacencyList.putIfAbsent(node, new ArrayList<>());
            adjacencyList1.putIfAbsent(node, new ArrayList<>());
        }

        public void addEdge(String from, String to, int weight) {
            var fromNode = nodes.get(from);
            if (from == null)
                throw new IllegalStateException();

            var toNode = nodes.get(to);
            if (to == null)
                throw new IllegalStateException();
            var edge = new Edge(fromNode, toNode, weight);
            var reverse = new Edge(toNode, fromNode, weight);

            adjacencyList.get(fromNode).add(toNode);
            adjacencyList.get(toNode).add(fromNode);

            if (!adjacencyList1.get(fromNode).contains(edge)) {
                adjacencyList1.get(fromNode).add(edge);
            }
            if (!adjacencyList1.get(toNode).contains(reverse)) {
                adjacencyList1.get(toNode).add(reverse);
            }
        }

        public void print() {
            for (var source : adjacencyList.keySet()) {
                var list = adjacencyList.get(source);
                if (!list.isEmpty())
                    System.out.println(source + "is connected to" + list);
            }
        }

        public void removeNode(String label) {
            var node = nodes.get(label);
            if (node == null)
                return;
            for (var n : adjacencyList.keySet()) {
                adjacencyList.get(n).remove(node);
            }

            List<Edge> edgeToRemove = new ArrayList<>();
            for (var n : adjacencyList1.values()) {
                for (var m : n) {
                    if (m.to == node) {
                        edgeToRemove.add(new Edge(m.from, node, m.weight));
                    } else if (m.from == node) {
                        edgeToRemove.add(new Edge(node, m.to, m.weight));
                    }
                }

            }
            for (var e:edgeToRemove) {
                removeEdge(e.from.label,e.to.label,e);
            }

            adjacencyList1.remove(node);
            adjacencyList.remove(node);
            nodes.remove(label);

        }

        public void removeEdge(String from, String to, Edge e) {
            var fromNode = nodes.get(from);
            var toNode = nodes.get(to);
            if (fromNode == null || toNode == null)
                return;
            adjacencyList.get(fromNode).remove(toNode);
            adjacencyList.get(toNode).remove(fromNode);


            adjacencyList1.get(fromNode).remove(e);

        }


    }

}