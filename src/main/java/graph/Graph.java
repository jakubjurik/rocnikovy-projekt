package graph;

import java.util.*;

public class Graph {
    private final int id;
    private final Set<Vertex> vertices;
    private final Set<Edge> edges;

    public Graph(int id) {
        this.id = id;
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    public Graph(int id, Set<Vertex> vertices, Set<Edge> edges) {
        this.id = id;
        this.vertices = vertices;
        this.edges = edges;
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);
    }

    public void addEdge(Edge edge) {
        if (!vertices.contains(edge.getFrom()) || !vertices.contains(edge.getTo())) {
            throw new IllegalArgumentException("Incident vertex does not exist");
        }

        edge.getFrom().addEdge(edge);
        edge.getTo().addEdge(edge);

        edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        edge.getFrom().removeEdge(edge);
        edge.getTo().removeEdge(edge);

        edges.remove(edge);
    }

    public int getId() {
        return id;
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public List<Vertex> getSortedVertices() {
        List<Vertex> sortedVertices = new ArrayList<>(vertices);
        sortedVertices.sort(Comparator.comparing(Vertex::getId));
        return sortedVertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getSortedEdges() {
        List<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Comparator.comparing(Edge::getId));
        return sortedEdges;
    }

    public Edge getEdge(Vertex from, Vertex to) {
        for (Edge edge : edges) {
            if ((edge.getFrom() == from && edge.getTo() == to) || (edge.getFrom() == to && edge.getTo() == from)) {
                return edge;
            }
        }
        throw new IllegalArgumentException("Edge not found");
    }

    public void truncateVertex(Vertex v) {
        int nextVertexId = vertices.size();
        int nextEdgeId = edges.size();

        Vertex v1 = new Vertex(nextVertexId++);
        Vertex v2 = new Vertex(nextVertexId);

        addVertex(v1);
        addVertex(v2);

        List<Edge> edges = new ArrayList<>(v.getSortedEdges());

        edges.get(0).exchangeVertex(v, v1);
        edges.get(1).exchangeVertex(v, v2);

        addEdge(new Edge(nextEdgeId++, v, v1));
        addEdge(new Edge(nextEdgeId++, v, v2));
        addEdge(new Edge(nextEdgeId, v1, v2));
    }

    public void truncate() {
        for (Vertex v : new ArrayList<>(getVertices())) {
            truncateVertex(v);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append("\n");
        sb.append(vertices.size()).append("\n");

        for (Vertex v : getSortedVertices()) {
            List<Vertex> neighbours = new ArrayList<>(v.getNeighbours());
            neighbours.sort(Comparator.comparing(Vertex::getId));

            for (Vertex neighbour : neighbours) {
                sb.append(neighbour.getId()).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
