package colouring;

import graph.*;
import java.util.*;

public class StrongEdgeColouring {

    private final Map<Edge, Integer> colouring = new HashMap<>();
    private final int maxColours = 6;
    private int colouringCount;

    private boolean canUseColour(Edge edge, int colour) {
        for (Edge e : edge.getNeighboursWithinDistanceOfTwo()) {
            Integer c = colouring.get(e);
            if (c != null && c.equals(colour)) {
                return false;
            }
        }
        return true;
    }

    private int countAvailableColours(Edge e){
        int count = 0;
        for (int c = 0; c < maxColours; c++){
            if (canUseColour(e,c)) count++;
        }
        return count;
    }

    private Edge selectNextEdge(List<Edge> edges) {
        Edge best = null;
        int minOptions = Integer.MAX_VALUE;

        for (Edge e : edges) {
            if (!colouring.containsKey(e)) {
                int options = countAvailableColours(e);
                if (options < minOptions){
                    minOptions = options;
                    best = e;
                }
            }
        }
        return best;
    }

    private boolean check(Graph graph, Map<Edge, Integer> colouring) {
        List<Edge> edges = new ArrayList<>(graph.getSortedEdges());

        for (int i = 0; i < edges.size(); i++) {
            for (int j = i = 0; j < edges.size(); j++) {
                if (i != j) {
                    Edge e1 = edges.get(i);
                    Edge e2 = edges.get(j);
                    if (e1.getNeighboursWithinDistanceOfTwo().contains(e2)) {
                        int c1 = colouring.get(e1);
                        int c2 = colouring.get(e2);
                        if (c1 == c2) {
                            return false;
                        }
                    }
                }

            }
        }

        return true;
    }

    public List<Conflict> findConflicts(Graph graph, Map<Edge, Integer> colouring) {
        List<Edge> edges = new ArrayList<>(graph.getSortedEdges());
        List<Conflict> conflicts = new ArrayList<>();

        for (Edge e : edges) {
            Vertex to  = e.getTo();
            List<Edge> toEdges = new ArrayList<>(to.getSortedEdges());
            toEdges.remove(e);

            Edge f1 = toEdges.getFirst();
            Vertex v1;
            if (f1.getFrom() == to) {
                v1 = f1.getTo();
            } else {
                v1 = f1.getFrom();
            }

            Edge f2 = toEdges.getLast();
            Vertex v2;
            if (f2.getFrom() == to) {
                v2 = f2.getTo();
            } else {
                v2 = f2.getFrom();
            }

            if (graph.getEdge(v1, v2) != null) {
                List<Edge> fromEdges = new ArrayList<>(e.getFrom().getSortedEdges());
                fromEdges.remove(e);
                for (Edge fromEdge : fromEdges) {
                    for (Edge toEdge : toEdges) {
                        int c1 = colouring.get(fromEdge);
                        int c2 = colouring.get(toEdge);
                        if (c1 == c2) {
                            StringBuilder sb = new StringBuilder();
                            for (Edge edge : edges) {
                                int colour = colouring.get(edge);
                                sb.append(colour);
                            }
                            conflicts.add(new Conflict(e, 1, c1, sb.toString()));
                        }
                    }
                }

                int c1 = colouring.get(e);
                List<Edge> eNeighboursNeighbours = new ArrayList<>(e.getNeighboursNeighbours());
                for (Edge neighbour : eNeighboursNeighbours) {
                    int c2 = colouring.get(neighbour);
                    if (c2 == c1) {
                        StringBuilder sb = new StringBuilder();
                        for (Edge edge : edges) {
                            int colour = colouring.get(edge);
                            sb.append(colour);
                        }
                        conflicts.add(new Conflict(e, 2, c1, sb.toString()));
                    }
                }
            }
        }

        return conflicts;
    }

    private void printColouring(Map<Edge, Integer> colouring, int number) {
        List<Edge> sorted = new ArrayList<>(colouring.keySet());
        sorted.sort(Comparator.comparingInt(Edge::getId));

        StringBuilder sb = new StringBuilder();
        sb.append("Colouring ").append(number).append(":\n");

        for (Edge e : sorted) {
            Integer c = colouring.get(e);
            sb.append("Edge ").append(e).append(" : colour ").append(c).append("\n");
        }

        System.out.println(sb);
    }

    private void backtrack(Graph graph, List<Edge> edges) {
        if (colouring.size() == edges.size()) {
            colouringCount++;
            printColouring(colouring, colouringCount);
            if (check(graph, colouring)) {
                System.out.println("Graph coloured successfully\n");
            } else {
                System.out.println("Graph coloured incorrectly\n");
            }
            return;
        }

        Edge edge = selectNextEdge(edges);
        if (edge == null) return;

        for (int colour = 0; colour < maxColours; colour++) {
            if (canUseColour(edge, colour)) {
                colouring.put(edge, colour);
                backtrack(graph, edges);
                colouring.remove(edge);
            }
        }
    }

    public void colour(Graph graph) {
        List<Edge> edges = new ArrayList<>(graph.getSortedEdges());
        List<Vertex> vertices = new ArrayList<>(graph.getSortedVertices());

        StringBuilder sb = new StringBuilder();
        sb.append("Started with triangle of edges:\n");

        Vertex v0 = vertices.getFirst();
        List<Vertex> v0Neighbours = new ArrayList<>(v0.getNeighbours());
        v0Neighbours.sort(Comparator.comparingInt(Vertex::getId));

        Vertex a = v0Neighbours.get(0);
        Vertex b = v0Neighbours.get(1);
        Vertex c = v0Neighbours.get(2);

        Vertex v1, v2, v3;

        if (a.isNeighbour(b)) {
            v2 = a;
            v3 = b;
            v1 = c;
        } else if (a.isNeighbour(c)) {
            v2 = a;
            v3 = c;
            v1 = b;
        } else {
            v2 = b;
            v3 = c;
            v1 = a;
        }

        Edge e0 = graph.getEdge(v0, v1);
        if (e0 == null) return;
        colouring.put(e0, 0);
        sb.append(e0).append(" : colour 0\n");

        Edge e1 = graph.getEdge(v0, v2);
        if (e1 == null) return;
        colouring.put(e1, 1);
        sb.append(e1).append(" : colour 1\n");

        Edge e2 = graph.getEdge(v0, v3);
        if (e2 == null) return;
        colouring.put(e2, 2);
        sb.append(e2).append(" : colour 2\n");

        Edge e3 = graph.getEdge(v2, v3);
        if (e3 == null) return;
        colouring.put(e3, 3);
        sb.append(e3).append(" : colour 3\n");

        List<Vertex> v2Neighbours = new ArrayList<>(v2.getNeighbours());
        v2Neighbours.remove(v0);
        v2Neighbours.remove(v3);

        Vertex v4 = v2Neighbours.getFirst();
        Edge e4 = graph.getEdge(v2, v4);
        if (e4 == null) return;
        colouring.put(e4, 4);
        sb.append(e4).append(" : colour 4\n");

        List<Vertex> v3Neighbours = new ArrayList<>(v3.getNeighbours());
        v3Neighbours.remove(v0);
        v3Neighbours.remove(v2);

        Vertex v5 = v3Neighbours.getFirst();
        Edge e5 = graph.getEdge(v3, v5);
        if (e5 == null) return;
        colouring.put(e5, 5);
        sb.append(e5).append(" : colour 5\n");

        System.out.println(sb);

        colouringCount = 0;
        backtrack(graph, edges);
        System.out.println("Number of colourings: " + colouringCount);
    }

}
