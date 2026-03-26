import colouring.*;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import input.GraphLoader;
import colouring.StrongEdgeColouring;
import org.junit.Test;

import java.io.*;
import java.util.*;

public class DemoTest {

    @Test
    public void test_4_3_3() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/4_3_3.asc";

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        List<Graph> graphs = new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath));

        for (Graph graph : graphs) {
            System.out.println(graph);
            graph.truncate();

            System.out.println("truncated");
            System.out.println(graph);

            StrongEdgeColouring c = new StrongEdgeColouring();

            c.colour(graph);

        }
    }

    @Test
    public void test_10_3_3() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/10_3_3.asc";

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        List<Graph> graphs = new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath));

        for (Graph graph : graphs) {
            System.out.println(graph);
            graph.truncate();

            System.out.println("truncated");
            System.out.println(graph);

            StrongEdgeColouring c = new StrongEdgeColouring();

            c.colour(graph);

            break;

        }
    }

    @Test
    public void truncVertexTest() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/domcek.asc";

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        List<Graph> graphs = new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath));

        for (Graph graph : graphs) {
            System.out.println(graph);
            Vertex vertex = graph.getSortedVertices().get(0);

            List<Edge> vEdges = vertex.getSortedEdges();

            graph.truncateVertex(vertex);
            System.out.println("truncated vertex " + vertex.getId());
            System.out.println(graph);

            graph.truncate();
            System.out.println("truncated graph");
            System.out.println(graph);

            StrongEdgeColouring c = new StrongEdgeColouring();

            Palette palette = c.getPalette(graph, vertex, vEdges.get(0), vEdges.get(1), vEdges.get(2));

            Set<Solution> solutions = palette.getElements();
            /*
            for (Solution solution : solutions) {
                System.out.println(solution);
            }

             */
            System.out.println("found " + solutions.size() + " pallets");

            break;
        }
    }

    @Test
    public void truncAndGetPaletteTest() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/domcek.asc";

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        List<Graph> graphs = new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath));

        for (Graph graph : graphs) {
            StrongEdgeColouring c = new StrongEdgeColouring();
            Palette palette = c.truncAndGetPalette(graph, 0, 0, 1, 2);

            Set<Solution> solutions = palette.getElements();
            /*
            for (Solution solution : solutions) {
                System.out.println(solution);
            }

             */
            System.out.println("found " + solutions.size() + " pallets");

            break;
        }

    }

    @Test
    public void getPalette6Test() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/4_3_3.asc";
        // 4_3_3.asc
        // domcek.asc

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        List<Graph> graphs = new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath));

        for (Graph graph : graphs) {
            System.out.println(graph);
            List<Vertex> graphVertices = graph.getSortedVertices();
            Vertex vertex = graphVertices.get(0);

            List<Edge> vEdges = vertex.getSortedEdges();
            for (Edge edge : vEdges) {
                System.out.println(edge);
            }
            System.out.println();

            Edge edgeA = vEdges.get(0);
            Edge edgeD = vEdges.get(1);
            Edge edgeG = vEdges.get(2);

            StrongEdgeColouring c = new StrongEdgeColouring();

            Palette6 palette = c.getPalette6(graph, vertex, edgeA, edgeD, edgeG);

            Set<Solution6> solutions = palette.getElements();

            System.out.println("found " + solutions.size() + " palettes");

            for (Solution6 solution : solutions) {
                System.out.println(solution);
            }

             //*/

            break;
        }

    }

    @Test
    public void getPaletteTest() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/4_3_3.asc";
        // 4_3_3.asc
        // domcek.asc

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        List<Graph> graphs = new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath));

        for (Graph graph : graphs) {
            System.out.println(graph);
            List<Vertex> graphVertices = graph.getSortedVertices();
            Vertex vertex = graphVertices.get(0);

            List<Edge> vEdges = vertex.getSortedEdges();
            for (Edge edge : vEdges) {
                System.out.println(edge);
            }
            System.out.println();

            Edge edgeA = vEdges.get(0);
            Edge edgeD = vEdges.get(1);
            Edge edgeG = vEdges.get(2);

            StrongEdgeColouring c = new StrongEdgeColouring();

            Palette palette = c.getPalette(graph, vertex, edgeA, edgeD, edgeG);

            Set<Solution> solutions = palette.getElements();

            System.out.println("found " + solutions.size() + " palettes");

            /*
            for (Solution solution : solutions) {
                System.out.println(solution);
            }

             //*/

            break;
        }

    }

}
