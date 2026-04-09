package colouring;

import graph.*;
import jdk.jshell.spi.ExecutionControl;

import java.util.*;

public class StrongEdgeColouring {

    private final Map<Edge, Integer> colouring = new HashMap<>();
    private final int maxColours = 6;
    private int colouringCount;
    private final Set<Solution> solutions = new HashSet<>();
    //private final Set<Solution6> solutions6 = new HashSet<>();

    private boolean canUseColour(Edge edge, int colour, Map<Edge, Integer> colouring) {
        for (Edge e : edge.getNeighboursWithinDistanceOfTwo()) {
            Integer c = colouring.get(e);
            if (c != null && c.equals(colour)) {
                return false;
            }
        }
        return true;
    }

    private int countAvailableColours(Edge e, Map<Edge, Integer> colouring) {
        int count = 0;
        for (int c = 0; c < maxColours; c++){
            if (canUseColour(e,c, colouring)) count++;
        }
        return count;
    }

    private Edge selectNextEdge(List<Edge> edges, Map<Edge, Integer> colouring) {
        Edge best = null;
        int minOptions = Integer.MAX_VALUE;

        for (Edge e : edges) {
            if (!colouring.containsKey(e)) {
                int options = countAvailableColours(e, colouring);
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
            for (int j = i + 1; j < edges.size(); j++) {
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

    private boolean edgeIsInConflict(Edge edge, Map<Edge, Integer> colouring) {
        List<Edge> edges = new ArrayList<>(edge.getNeighboursWithinDistanceOfTwo());
        for (Edge e : edges) {
            if (colouring.get(e).equals(colouring.get(edge))) {
                return true;
            }
        }
        return false;
    }

    private List<Edge> getConflictEdges(Edge edge, Map<Edge, Integer> colouring) {
        List<Edge> edges = new ArrayList<>(edge.getNeighboursWithinDistanceOfTwo());
        List<Edge> conflictEdges = new ArrayList<>();
        for (Edge e : edges) {
            if (colouring.get(e).equals(colouring.get(edge))) {
                conflictEdges.add(e);
            }
        }
        return conflictEdges;
    }

    private int getMinConflictColour(Edge edge, Map<Edge, Integer> colouring) {
        int optimal = colouring.get(edge);
        List<Edge> edges = new ArrayList<>(edge.getNeighboursWithinDistanceOfTwo());
        int minConflicts = Integer.MAX_VALUE;
        for (int i = 0 ; i < maxColours ; i++) {
            int currentConflicts = 0;
            for (Edge e : edges) {
                if (colouring.get(e) == i) {
                    currentConflicts++;
                }
            }
            if (currentConflicts < minConflicts) {
                minConflicts = currentConflicts;
                optimal = i;
            }
        }
        return optimal;
    }

    private boolean resolveConflict(Edge edge) {
        Set<Edge> visited = new HashSet<>();

        while (edgeIsInConflict(edge, colouring)) {
            if (visited.contains(edge)) {
                return false;
            }
            visited.add(edge);

            int newColour = getMinConflictColour(edge, colouring);
            colouring.put(edge, newColour);
            if (!edgeIsInConflict(edge, colouring)) {
                return true;
            }

            List<Edge> conflicts = getConflictEdges(edge, colouring);
            edge = conflicts.getFirst();
        }

        return true;
    }

    private Map<Edge, Integer> changeColour(Edge e1, Edge e2, Map<Edge, Integer> colouring) {
        Map<Edge, Integer> copy = new HashMap<>(colouring);
        int c1 = copy.get(e1);
        int c2 = copy.get(e2);
        copy.put(e1, c2);
        copy.put(e2, c1);
        return copy;
    }

    private List<Map<Edge, Integer>> recolourLocally(Edge e1, Edge e2, int conflictType, Map<Edge, Integer> colouring) {
        List<Map<Edge, Integer>> results = new ArrayList<>();

        for (Edge e3 : e2.getNeighbours()) {
            if (colouring.get(e3).equals(colouring.get(e1))) continue;

            if (conflictType == 1) {
                if (e3.equals(e1)) continue;
                if (e1.getNeighbours().contains(e3)) continue;
            }

            if (conflictType == 2) {
                if (e1.getNeighboursWithinDistanceOfTwo().contains(e3)) continue;
            }

            results.add(changeColour(e2, e3, colouring));
        }
        return results;
    }
    /**
     * */
    private boolean spreadConflict(Edge e1, Map<Edge, Integer> colouring, Set<String> visitedStates) {
        if (!edgeIsInConflict(e1, colouring)) {
            this.colouring.clear();
            this.colouring.putAll(colouring);
            return true;
        }

        String colouringString = getColouringString(colouring);
        if (visitedStates.contains(colouringString)) {
            return false;
        }

        visitedStates.add(colouringString);

        List<Edge> conflicts = getConflictEdges(e1, colouring);

        for (Edge e2 : conflicts) {
            int conflictType = getConflictType(e1, e2);
            List<Map<Edge, Integer>> nextStates = recolourLocally(e1, e2, conflictType, colouring);

            for (Map<Edge, Integer> nextState : nextStates) {
                Map<Edge, Integer> newColouring = new HashMap<>(nextState);

                newColouring.forEach((edge, integer) -> {
                    if (newColouring.get(edge).equals(colouring.get(edge))) {
                        newColouring.remove(edge, integer);
                    }
                });
                newColouring.remove(e2, colouring.get(e2));
                // ? newColouring.size() == 1
                Edge e3 = newColouring.keySet().iterator().next();

                if (spreadConflict(e3, nextState, visitedStates)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getConflictType(Edge e1, Edge e2) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private String getColouringString(Map<Edge, Integer> colouring) {
        List<Edge> sorted = new ArrayList<>(colouring.keySet());
        sorted.sort(Comparator.comparingInt(Edge::getId));

        StringBuilder sb = new StringBuilder();
        for (Edge e : sorted) {
            sb.append(colouring.get(e));
        }
        return sb.toString();
    }

    private Map<Edge, Integer> getColouringMap(String colouringString, List<Edge> edges) {
        List<Edge> sorted = new ArrayList<>(edges);
        sorted.sort(Comparator.comparingInt(Edge::getId));

        Map<Edge,Integer> colouring = new HashMap<>();
        for (int i = 0; i < colouringString.length(); i++) {
            colouring.put(sorted.get(i), colouringString.charAt(i) - '0');
        }
        return colouring;
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

        Edge edge = selectNextEdge(edges, colouring);
        if (edge == null) return;

        for (int colour = 0; colour < maxColours; colour++) {
            if (canUseColour(edge, colour, colouring)) {
                colouring.put(edge, colour);
                backtrack(graph, edges);
                colouring.remove(edge);
            }
        }
    }

    private boolean canFinish(List<Edge> edges, Map<Edge, Integer> colouring) {
        if (colouring.size() == edges.size()) {
            return true;
        }

        Edge edge = selectNextEdge(edges, colouring);
        if (edge == null) return false;

        for (int colour = 0; colour < maxColours; colour++) {
            if (canUseColour(edge, colour, colouring)) {
                colouring.put(edge, colour);
                if (canFinish(edges, colouring)) {
                    return true;
                }
                colouring.remove(edge);
            }
        }

        return false;
    }

    private void colourEdge(Edge edge, int colour, List<Edge> originalEdges, Map<Edge, Integer> colouring, Set<Edge> visited) {
        if (visited.contains(edge)) return;
        visited.add(edge);

        if (originalEdges.contains(edge)) return;

        colouring.put(edge, colour);

        for (Edge neighbour : edge.getNeighbours()) {
            colourEdge(neighbour, colour, originalEdges, colouring, visited);
        }
    }

    private Vertex getOpposite(Edge edge, Vertex vertex) {
        if (edge.getFrom() == vertex) {
            return edge.getTo();
        }
        if (edge.getTo() == vertex) {
            return edge.getFrom();
        }
        throw new IllegalArgumentException("Non-incident vertex to edge");
    }

    public Palette truncAndGetPalette(Graph graph, int vertexId, int edge1, int edge2, int edge3) {
        Vertex vertex = graph.getSortedVertices().get(vertexId);
        List<Edge> originalEdges = graph.getEdgesAndTruncate(graph, vertex);
        Edge e1 = originalEdges.get(edge1);
        Edge e2 = originalEdges.get(edge2);
        Edge e3 = originalEdges.get(edge3);
        return search(graph, vertex, e1, e2, e3);
    }

    public Palette getPalette(Graph graph, int vertexId, List<Edge> originalEdges, int edge1, int edge2, int edge3) {
        Vertex vertex = graph.getSortedVertices().get(vertexId);
        Edge e1 = originalEdges.get(edge1);
        Edge e2 = originalEdges.get(edge2);
        Edge e3 = originalEdges.get(edge3);
        return search(graph, vertex, e1, e2, e3);
    }

    public Palette getPalette(Graph graph, Vertex vertex, Edge e1, Edge e2, Edge e3) {
        graph.truncateVertex(vertex);
        graph.truncate();
        //System.out.println(graph);
        return search(graph, vertex, e1, e2, e3);
    }

    public Palette6 getPalette6(Graph graph, Vertex vertex, Edge edgeA, Edge edgeD, Edge edgeG) {
        Graph originalGraph = graph.getCopy();
        graph.truncateVertex(vertex);
        graph.truncate();
        return search6(graph, vertex, edgeA, edgeD, edgeG, originalGraph);
    }

    public Palette search(Graph graph, Vertex vertex, Edge edgeA, Edge edgeD, Edge edgeG) {
        List<Edge> edges = new ArrayList<>(graph.getSortedEdges());
        Map<Edge, Integer> baseColouring = new HashMap<>();
        List<Edge> originalEdges = new ArrayList<>(List.of(edgeA, edgeD, edgeG));

        Set<Edge> visited = new HashSet<>();
        Edge startEdge = null;
        for (Edge e : vertex.getSortedEdges()) {
            if (!originalEdges.contains(e)) {
                startEdge = e;
                break;
            }
        }
        colourEdge(startEdge, maxColours, originalEdges, baseColouring, visited);

        List<Edge> colouredEdges = new ArrayList<>(baseColouring.keySet());
        Set<Vertex> colouredVertices = new HashSet<>();
        for (Edge e : colouredEdges) {
            colouredVertices.add(e.getFrom());
            colouredVertices.add(e.getTo());
        }

        Vertex vertexA;
        if (colouredVertices.contains(edgeA.getFrom())) {
            vertexA = edgeA.getTo();
        } else if (colouredVertices.contains(edgeA.getTo())) {
            vertexA = edgeA.getFrom();
        } else {
            throw new IllegalArgumentException("Wrong edgeA");
        }
        List<Edge> edgesA = new ArrayList<>(vertexA.getSortedEdges());
        edgesA.remove(edgeA);
        Edge edgeB = edgesA.get(0);
        Edge edgeC = edgesA.get(1);

        Vertex vertexD;
        if (colouredVertices.contains(edgeD.getFrom())) {
            vertexD = edgeD.getTo();
        } else if (colouredVertices.contains(edgeD.getTo())) {
            vertexD = edgeD.getFrom();
        } else {
            throw new IllegalArgumentException("Wrong edgeD");
        }
        List<Edge> edgesD = new ArrayList<>(vertexD.getSortedEdges());
        edgesD.remove(edgeD);
        Edge edgeE = edgesD.get(0);
        Edge edgeF = edgesD.get(1);

        Vertex vertexG;
        if (colouredVertices.contains(edgeG.getFrom())) {
            vertexG = edgeG.getTo();
        } else if (colouredVertices.contains(edgeG.getTo())) {
            vertexG = edgeG.getFrom();
        } else {
            throw new IllegalArgumentException("Wrong edgeG");
        }
        List<Edge> edgesG = new ArrayList<>(vertexG.getSortedEdges());
        edgesG.remove(edgeG);
        Edge edgeH = edgesG.get(0);
        Edge edgeI = edgesG.get(1);

        //printColouring(baseColouring, 0);
        int count = 0;

        for (int a = 0 ; a < maxColours; a++) {
            for (int b = 0 ; b < maxColours; b++) {
                if (b == a) continue;
                for (int c = 0 ; c < maxColours; c++) {
                    if (c == a || c == b) continue;
                    for (int d = 0 ; d < maxColours; d++) {
                        for (int e = 0 ; e < maxColours; e++) {
                            if (e == d) continue;
                            for (int f = 0 ; f < maxColours; f++) {
                                if (f == d || f == e) continue;
                                for (int g = 0 ; g < maxColours; g++) {
                                    for (int h = 0 ; h < maxColours; h++) {
                                        if (h == g) continue;
                                        for (int i = 0; i < maxColours; i++) {
                                            if (i == g || i == h) continue;
                                            Map<Edge,Integer> testColouring = new HashMap<>(baseColouring);

                                            testColouring.put(edgeA, a);
                                            testColouring.put(edgeB, b);
                                            testColouring.put(edgeC, c);
                                            testColouring.put(edgeD, d);
                                            testColouring.put(edgeE, e);
                                            testColouring.put(edgeF, f);
                                            testColouring.put(edgeG, g);
                                            testColouring.put(edgeH, h);
                                            testColouring.put(edgeI, i);

                                            if (canFinish(edges, testColouring)) {
                                                //count++;
                                                //printColouring(testColouring, count);
                                                solutions.add(new Solution(a, b, c, d, e, f, g, h, i));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return new Palette(solutions);
    }

    public Palette6 search6(Graph graph, Vertex vertex, Edge edgeA, Edge edgeD, Edge edgeG, Graph originalGraph) {
        List<Edge> edges = new ArrayList<>(graph.getSortedEdges());
        Map<Edge, Integer> baseColouring = new HashMap<>();
        List<Edge> originalEdges = new ArrayList<>(List.of(edgeA, edgeD, edgeG));
        Set<Solution6> solutions = new HashSet<>();

        Set<Edge> visited = new HashSet<>();
        Edge startEdge = null;
        for (Edge e : vertex.getSortedEdges()) {
            if (!originalEdges.contains(e)) {
                startEdge = e;
                break;
            }
        }
        colourEdge(startEdge, maxColours, originalEdges, baseColouring, visited);

        List<Edge> colouredEdges = new ArrayList<>(baseColouring.keySet());
        Set<Vertex> colouredVertices = new HashSet<>();
        for (Edge e : colouredEdges) {
            colouredVertices.add(e.getFrom());
            colouredVertices.add(e.getTo());
        }

        Vertex vertexA;
        if (colouredVertices.contains(edgeA.getFrom())) {
            vertexA = edgeA.getTo();
        } else if (colouredVertices.contains(edgeA.getTo())) {
            vertexA = edgeA.getFrom();
        } else {
            throw new IllegalArgumentException("Wrong edgeA");
        }

        List<Edge> edgesA = new ArrayList<>(vertexA.getSortedEdges());
        edgesA.remove(edgeA);
        Edge edgeB = edgesA.get(0);
        Edge edgeC = edgesA.get(1);

        baseColouring.put(edgeA, 0);
        baseColouring.put(edgeB, 1);
        baseColouring.put(edgeC, 2);

        Vertex vertexD;
        if (colouredVertices.contains(edgeD.getFrom())) {
            vertexD = edgeD.getTo();
        } else if (colouredVertices.contains(edgeD.getTo())) {
            vertexD = edgeD.getFrom();
        } else {
            throw new IllegalArgumentException("Wrong edgeD");
        }
        List<Edge> edgesD = new ArrayList<>(vertexD.getSortedEdges());
        edgesD.remove(edgeD);
        Edge edgeE = edgesD.get(0);
        Edge edgeF = edgesD.get(1);

        Vertex vertexG;
        if (colouredVertices.contains(edgeG.getFrom())) {
            vertexG = edgeG.getTo();
        } else if (colouredVertices.contains(edgeG.getTo())) {
            vertexG = edgeG.getFrom();
        } else {
            throw new IllegalArgumentException("Wrong edgeG");
        }
        List<Edge> edgesG = new ArrayList<>(vertexG.getSortedEdges());
        edgesG.remove(edgeG);
        Edge edgeH = edgesG.get(0);
        Edge edgeI = edgesG.get(1);

        //printColouring(baseColouring, 0);
        int count = 0;

        for (int d = 0 ; d < maxColours; d++) {
            for (int e = 0 ; e < maxColours; e++) {
                if (e == d) continue;
                for (int f = 0 ; f < maxColours; f++) {
                    if (f == d || f == e) continue;
                    for (int g = 0 ; g < maxColours; g++) {
                        for (int h = 0 ; h < maxColours; h++) {
                            if (h == g) continue;
                            for (int i = 0; i < maxColours; i++) {
                                if (i == g || i == h) continue;
                                Map<Edge,Integer> testColouring = new HashMap<>(baseColouring);

                                testColouring.put(edgeD, d);
                                testColouring.put(edgeE, e);
                                testColouring.put(edgeF, f);
                                testColouring.put(edgeG, g);
                                testColouring.put(edgeH, h);
                                testColouring.put(edgeI, i);

                                if (canFinish(edges, testColouring)) {
                                    //count++;
                                    //printColouring(testColouring, count);
                                    solutions.add(new Solution6(d, e, f, g, h, i));
                                }
                            }
                        }
                    }
                }
            }
        }

        return new Palette6(solutions, originalGraph, vertex, edgeA, edgeD, edgeG);
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
        colouring.put(e0, 0);
        sb.append(e0).append(" : colour 0\n");

        Edge e1 = graph.getEdge(v0, v2);
        colouring.put(e1, 1);
        sb.append(e1).append(" : colour 1\n");

        Edge e2 = graph.getEdge(v0, v3);
        colouring.put(e2, 2);
        sb.append(e2).append(" : colour 2\n");

        Edge e3 = graph.getEdge(v2, v3);
        colouring.put(e3, 3);
        sb.append(e3).append(" : colour 3\n");

        List<Vertex> v2Neighbours = new ArrayList<>(v2.getNeighbours());
        v2Neighbours.remove(v0);
        v2Neighbours.remove(v3);

        Vertex v4 = v2Neighbours.getFirst();
        Edge e4 = graph.getEdge(v2, v4);
        colouring.put(e4, 4);
        sb.append(e4).append(" : colour 4\n");

        List<Vertex> v3Neighbours = new ArrayList<>(v3.getNeighbours());
        v3Neighbours.remove(v0);
        v3Neighbours.remove(v2);

        Vertex v5 = v3Neighbours.getFirst();
        Edge e5 = graph.getEdge(v3, v5);
        colouring.put(e5, 5);
        sb.append(e5).append(" : colour 5\n");

        System.out.println(sb);

        colouringCount = 0;
        backtrack(graph, edges);
        System.out.println("Number of colourings: " + colouringCount);
    }

}
