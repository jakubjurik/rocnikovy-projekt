package graph;

import java.util.*;

public class Graph {
    private final int id;
    private final Set<Vertex> vertices;
    private final Set<Edge> edges;

    private int originalVertexCount;
    private int originalEdgeCount;

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

    public Set<Edge> getEdges() {
        return edges;
    }

    public void truncateVertex(Vertex v) {
        int nextVertexId = vertices.size();
        int nextEdgeId = edges.size();

        Vertex v1 = new Vertex(nextVertexId++);
        Vertex v2 = new Vertex(nextVertexId);

        addVertex(v1);
        addVertex(v2);

        List<Edge> edges = new ArrayList<>(v.getEdges());

        edges.get(0).exchangeVertex(v, v1);
        edges.get(1).exchangeVertex(v, v2);

        addEdge(new Edge(nextEdgeId++, v, v1));
        addEdge(new Edge(nextEdgeId++, v, v2));
        addEdge(new Edge(nextEdgeId, v1, v2));
    }

    public void truncate() {
        for (Vertex v : new ArrayList<>(vertices)) {
            truncateVertex(v);
        }
    }


    /*
    public void truncate() {
        originalVertexCount = vertices.size();
        originalEdgeCount = edges.size();
        int nextVertexId = originalVertexCount;
        int nextEdgeId = originalEdgeCount;

        Map<Vertex, Vertex[]> triangles = new HashMap<>();

        for (Vertex v : new HashSet<>(vertices)) {
            Vertex v1 = new Vertex(nextVertexId++);
            Vertex v2 = new Vertex(nextVertexId++);

            addVertex(v1);
            addVertex(v2);

            addEdge(new Edge(nextEdgeId++, v, v1));
            addEdge(new Edge(nextEdgeId++, v, v2));
            addEdge(new Edge(nextEdgeId++, v1, v2));

            triangles.put(v, new Vertex[]{v1, v2});
        }

        for (Vertex v : triangles.keySet()) {
            List<Edge> originalEdges = new ArrayList<>();
            for (Edge e : v.getEdges()) {
                if (e.getId() < originalEdgeCount) {
                    originalEdges.add(e);
                }
            }

            Edge e1 = originalEdges.getFirst();
            e1.setFrom(triangles.get(v)[1]);
            e1.setTo(triangles.get(e1.getTo())[0]);

            Edge e2 = originalEdges.get(2);
            e2.setFrom(triangles.get(v)[0]);
            e2.setTo(triangles.get(e2.getTo())[1]);
        }

    }

    public void untruncate() {
        List<Edge> toRemoveEdges = new ArrayList<>();

        for (Edge e : edges) {
            if (e.getId() >= originalEdgeCount) {
                toRemoveEdges.add(e);
            }
        }

        for (Edge e : toRemoveEdges) {
            edges.remove(e);
        }

        List<Vertex> toRemoveVertices = new ArrayList<>();

        for (Vertex v : vertices) {
            if (v.getId() >= originalVertexCount) {
                toRemoveVertices.add(v);
            }
        }

        for (Vertex v : toRemoveVertices) {
            vertices.remove(v);
        }
    }

     */

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append("\n");
        sb.append(vertices.size()).append("\n");

        int n = vertices.size();
        for (int i = 0; i < n; i++) {
            Vertex v = null;

            for (Vertex candidate : vertices) {
                if (candidate.getId() == i) {
                    v = candidate;
                    break;
                }
            }

            if (v == null) {
                continue;
            }

            Set<Vertex> neighbours = v.getNeighbours();
            List<Vertex> sorted = new ArrayList<>(neighbours);

            for (int a = 0; a < sorted.size(); a++) {
                for (int b = a + 1; b < sorted.size(); b++) {
                    if (sorted.get(a).getId() > sorted.get(b).getId()) {
                        Vertex temp = sorted.get(a);
                        sorted.set(a, sorted.get(b));
                        sorted.set(b, temp);
                    }
                }
            }

            for (Vertex vertex : sorted) {
                sb.append(vertex.getId()).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
