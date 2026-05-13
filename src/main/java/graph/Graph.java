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

    public Graph getCopy() {
        Graph copy = new Graph(this.id);
        Map<Vertex, Vertex> vertexMap = new HashMap<>();

        for (Vertex v : this.vertices) {
            Vertex newV = new Vertex(v.getId());
            copy.addVertex(newV);
            vertexMap.put(v, newV);
        }

        for (Edge e : this.edges) {
            Vertex newFrom = vertexMap.get(e.getFrom());
            Vertex newTo = vertexMap.get(e.getTo());
            Edge newEdge = new Edge(e.getId(), newFrom, newTo);
            copy.addEdge(newEdge);
        }

        return copy;
    }

    public static class Code {
        public List<Vertex> sequence;
        public String code;

        public Code(List<Vertex> sequence, String code) {
            this.sequence = sequence;
            this.code = code;
        }
    }

    public Map<Integer, String> getVertexCodes() {
        List<Code> allPossibleCodes = getCodes();
        Map<Integer, String> codes = new HashMap<>();

        for (Code c : allPossibleCodes) {
            int v = c.sequence.getFirst().getId();
            String string = c.code;

            if (!codes.containsKey(v)) {
                codes.put(v, string);
            } else {
                if (string.compareTo(codes.get(v)) < 0) {
                    codes.replace(v, string);
                }
            }
        }
        return codes;
    }

    public String getOrientedEdgeCode(int uId, int vId) {
        List<Code> allCodes = getCodes();
        String minCode = null;

        for (Code c : allCodes) {
            if (c.sequence.get(0).getId() == uId && c.sequence.get(1).getId() == vId) {
                String current = c.code;
                if (minCode == null || current.compareTo(minCode) < 0) {
                    minCode = current;
                }
            }
        }
        return minCode;
    }

    public String getUnorientedEdgeCode(int uId, int vId) {
        String codeUV = getOrientedEdgeCode(uId, vId);
        String codeVU = getOrientedEdgeCode(vId, uId);

        return (codeUV.compareTo(codeVU) < 0) ? codeUV : codeVU;
    }

    public List<Code> getCodes() {
        List<Code> result = new ArrayList<>();
        List<List<Vertex>> sequences = new ArrayList<>();

        for (Vertex v : getSortedVertices()) {
            List<Vertex> sequence = new ArrayList<>();
            Set<Vertex> visited = new HashSet<>();

            sequence.add(v);
            visited.add(v);

            bfs(sequence, visited, 0, sequences);
        }

        for (List<Vertex> seq : sequences) {
            String codeStr = generateCodeString(seq);
            result.add(new Code(seq, codeStr));
        }

        return result;
    }

    private String generateCodeString(List<Vertex> sequence) {
        Map<Vertex, Character> labels = new HashMap<>();
        char nextLabel = 'a';
        StringBuilder sb = new StringBuilder();

        Vertex root = sequence.getFirst();
        labels.put(root, nextLabel++);

        Map<Vertex, Integer> sequenceIndexMap = new HashMap<>();
        for (int i = 0; i < sequence.size(); i++) {
            sequenceIndexMap.put(sequence.get(i), i);
        }

        for (int i = 0; i < sequence.size(); i++) {
            Vertex current = sequence.get(i);

            List<Vertex> neighbours = new ArrayList<>(current.getNeighbours());

            neighbours.sort(Comparator.comparingInt(sequenceIndexMap::get));

            if (i == 0) {
                for (Vertex neighbour : neighbours) {
                    if (!labels.containsKey(neighbour)) {
                        labels.put(neighbour, nextLabel++);
                    }
                }
            } else {
                for (Vertex neighbour : neighbours) {
                    if (labels.containsKey(neighbour)) {
                        sb.append(labels.get(neighbour));
                    } else {
                        sb.append("0");
                        labels.put(neighbour, nextLabel++);
                    }
                }
            }
        }

        return sb.toString();
    }

    private void bfs(List<Vertex> sequence, Set<Vertex> visited, int index, List<List<Vertex>> result) {
        if (index >= sequence.size()) {
            result.add(new ArrayList<>(sequence));
            return;
        }

        Vertex current = sequence.get(index);
        List<Vertex> neighbours = new ArrayList<>();

        for (Vertex v : current.getNeighbours()) {
            if (!visited.contains(v)) {
                neighbours.add(v);
            }
        }
        neighbours.sort(Comparator.comparing(Vertex::getId));

        backtrackNeighbours(neighbours, 0, sequence, index, visited, result);
    }

    private void backtrackNeighbours(
            List<Vertex> neighbours,
            int n,
            List<Vertex> sequence,
            int index,
            Set<Vertex> visited,
            List<List<Vertex>> result) {

        if (n == neighbours.size()) {
            int rollback = sequence.size();

            for (Vertex v : neighbours) {
                sequence.add(v);
                visited.add(v);
            }

            bfs(sequence, visited, index + 1, result);

            for (int i = sequence.size() - 1; i >= rollback; i--) {
                visited.remove(sequence.get(i));
                sequence.remove(i);
            }

            return;
        }

        for (int i = n; i < neighbours.size(); i++) {
            Collections.swap(neighbours, n, i);
            backtrackNeighbours(neighbours, n + 1, sequence, index, visited, result);
            Collections.swap(neighbours, n, i);
        }
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
        for (Vertex v : new ArrayList<>(getSortedVertices())) {
            truncateVertex(v);
        }
    }

    public List<Edge> getEdgesAndTruncate(Graph graph, Vertex vertex) {
        List<Edge> vertexEdges = vertex.getSortedEdges();
        graph.truncateVertex(vertex);
        graph.truncate();
        return vertexEdges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Graph graph)) return false;

        return vertices.equals(graph.vertices) && edges.equals(graph.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, edges);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(id).append("\n");
        sb.append(vertices.size()).append("\n");

        for (Vertex v : getSortedVertices()) {
            sb.append(v.getId()).append(": ");
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
