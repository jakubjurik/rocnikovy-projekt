package input;

import graph.*;
import java.io.*;
import java.util.*;

public class GraphLoader {

    public static List<Graph> loadGraphsFromFile(String filePath) throws FileNotFoundException {
        List<Graph> graphs = new ArrayList<>();

        File file = new File(filePath);

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextInt()) {
            int graphId = scanner.nextInt();
            int vertexCount = scanner.nextInt();

            Graph graph = new Graph(graphId);
            Vertex[] vertices = new Vertex[vertexCount];

            for (int i = 0; i < vertexCount; i++) {
                vertices[i] = new Vertex(i);
                graph.addVertex(vertices[i]);
            }

            int edgeId = 0;
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < 3; j++) {
                    int neighbour = scanner.nextInt();
                    if (neighbour > i) {
                        Edge edge = new Edge(edgeId, vertices[i], vertices[neighbour]);
                        graph.addEdge(edge);
                        edgeId++;
                    }
                }
            }

            graphs.add(graph);
        }

        scanner.close();
        return graphs;

    }

}
