import colouring.*;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import input.GraphLoader;
import colouring.StrongEdgeColouring;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DemoTest {

    @Test
    public void test_04_3_3() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/04_3_3.asc";

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
    public void getPaletteTest() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/04_3_3.asc";
        // 04_3_3.asc
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

            System.out.println("found " + solutions.size() + " solutions");

            /*
            for (Solution solution : solutions) {
                System.out.println(solution);
            }

             //*/

            break;
        }

    }

    @Test
    public void getPalette6Test() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/04_3_3.asc";
        // 04_3_3.asc ~2s
        // domcek.asc / 06_3_3 ~40s - 1 graf
        // 08_3_3.asc ~10min - graf

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
            /*
            for (Edge edge : vEdges) {
                System.out.println(edge);
            }
            System.out.println();

             */

            Edge edgeA = vEdges.get(0);
            Edge edgeD = vEdges.get(1);
            Edge edgeG = vEdges.get(2);

            StrongEdgeColouring c = new StrongEdgeColouring();

            Palette6 palette = c.getPalette6(graph, vertex, edgeA, edgeD, edgeG);

            Set<Solution6> solutions = palette.getElements();

            System.out.println("found " + solutions.size() + " solutions\n");

            /*
            for (Solution6 solution : solutions) {
                System.out.println(solution);
            }

             //*/

        }

    }

    @Test
    public void comparePalettesTest() throws FileNotFoundException {
        List<Graph> graphs = new ArrayList<>();
        List<Palette6> palettes = new ArrayList<>();

        for (int i = 4 ; i <= 8 ; i += 2) {
            // 4 ~50s
            String fileName = "0" + i + "_3_3.asc";
            String filePath = "src/main/resources/genreg-modified/" + fileName;

            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            graphs.addAll(new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath)));
        }

        for (Graph graph : graphs) {
            System.out.println(graph);
            Graph originalGraph = graph.getCopy();
            List<Vertex> vertices = originalGraph.getSortedVertices();

            for (Vertex vertex : vertices) {

                for (int i = 0 ; i <= 2 ; i++) {
                    for (int j = 0 ; j <= 2 ; j++) {
                        if (i == j) continue;
                        for (int k = 0 ; k <= 2 ; k++) {
                            if (i == k || j == k) continue;
                            Vertex v = graph.getSortedVertices().get(vertex.getId());
                            List<Edge> vEdges = v.getSortedEdges();
                            System.out.println(v);
                            System.out.println(i + " " + j + " " + k);

                            Edge edgeA = vEdges.get(i);
                            Edge edgeD = vEdges.get(j);
                            Edge edgeG = vEdges.get(k);

                            System.out.println("EdgeA: " + edgeA);
                            System.out.println("EdgeD: " + edgeD);
                            System.out.println("EdgeG: " + edgeG);

                            StrongEdgeColouring c = new StrongEdgeColouring();
                            Palette6 palette = c.getPalette6(graph, v, edgeA, edgeD, edgeG);
                            palettes.add(palette);
                            Set<Solution6> solutions = palette.getElements();
                            System.out.println("found " + solutions.size() + " solutions\n");

                            graph = originalGraph.getCopy();
                        }
                    }
                }

            }
        }

        Set<Palette6> uniquePalettes = new HashSet<>(palettes);
        System.out.println("Number of unique palettes " + uniquePalettes.size());
        System.out.println();

        for (int i = 0; i < palettes.size(); i++) {
            for (int j = i + 1 ; j < palettes.size() ; j++) {
                if (palettes.get(i).equals(palettes.get(j))) {
                    System.out.println(palettes.get(i).toString() + "is equal to \n" + palettes.get(j).toString());
                } else if (palettes.get(i).isSubsetOf(palettes.get(j))) {
                    System.out.println(palettes.get(i).toString() + "is subset of \n" + palettes.get(j).toString());
                } else if (palettes.get(j).isSubsetOf(palettes.get(i))) {
                    System.out.println(palettes.get(j).toString() + "is subset of \n" + palettes.get(i).toString());
                }
            }
        }


    }

    @Test
    public void comparePalettesShortTest() throws FileNotFoundException {
        List<Graph> graphs = new ArrayList<>();
        List<Palette6> palettes = new ArrayList<>();

        for (int i = 4 ; i <= 4 ; i += 2) {
            // 4 ~2s
            // 6 ~2min
            // 8 ~26min
            String fileName = "0" + i + "_3_3.asc";
            String filePath = "src/main/resources/genreg-modified/" + fileName;

            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            graphs.addAll(new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath)));
        }

        for (Graph graph : graphs) {
            System.out.println(graph);

            Vertex vertex = graph.getSortedVertices().getFirst();
            List<Edge> vEdges = vertex.getSortedEdges();
            System.out.println(vertex);

            Edge edgeA = vEdges.get(0);
            Edge edgeD = vEdges.get(1);
            Edge edgeG = vEdges.get(2);

            System.out.println("EdgeA: " + edgeA);
            System.out.println("EdgeD: " + edgeD);
            System.out.println("EdgeG: " + edgeG);

            StrongEdgeColouring c = new StrongEdgeColouring();
            Palette6 palette = c.getPalette6(graph, vertex, edgeA, edgeD, edgeG);
            palettes.add(palette);
            /*
            palettes.add(palette);
            List<Solution6> solutionsModified = new ArrayList<>(palette.getElements());
            solutionsModified.remove(solutionsModified.getFirst());
            Palette6 paletteModified = new Palette6(new HashSet<>(solutionsModified), palette.getOriginalGraph());
            palettes.add(paletteModified);

             //*/
            Set<Solution6> solutions = palette.getElements();
            System.out.println("found " + solutions.size() + " solutions\n");
            //break;
        }

        Set<Palette6> uniquePalettes = new HashSet<>(palettes);
        System.out.println("Number of unique palettes " + uniquePalettes.size());
        System.out.println();

        for (int i = 0; i < palettes.size(); i++) {
            for (int j = i + 1 ; j < palettes.size() ; j++) {
                if (palettes.get(i).equals(palettes.get(j))) {
                    System.out.println(palettes.get(i).toString() + "is equal to \n" + palettes.get(j).toString());
                } else if (palettes.get(i).isSubsetOf(palettes.get(j))) {
                    System.out.println(palettes.get(i).toString() + "is subset of \n" + palettes.get(j).toString());
                } else if (palettes.get(j).isSubsetOf(palettes.get(i))) {
                    System.out.println(palettes.get(j).toString() + "is subset of \n" + palettes.get(i).toString());
                }
            }
        }

    }

    @Test
    public void truncateGraphCopyTest() throws FileNotFoundException {
        String filePath = "src/main/resources/genreg-modified/04_3_3.asc";

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        List<Graph> graphs = new ArrayList<>(GraphLoader.loadGraphsFromFile(filePath));

        for (Graph graph : graphs) {
            Graph copy = graph.getCopy();
            System.out.println("original graph");
            System.out.println(graph);
            graph.truncate();
            System.out.println("truncated original graph");
            System.out.println(graph);

            System.out.println("graph copy");
            System.out.println(copy);
            copy.truncate();
            System.out.println("truncated graph copy");
            System.out.println(copy);

            for (Vertex vOriginal : graph.getVertices()) {
                for (Vertex vCopy : copy.getVertices()) {
                    if (vOriginal == vCopy) {
                        throw new AssertionError("copy and original share the same vertex");
                    }
                }
            }

            for (Edge eOriginal : graph.getEdges()) {
                for (Edge eCopy : copy.getEdges()) {
                    if (eOriginal == eCopy) {
                        throw new AssertionError("copy and original share the same edge");
                    }
                }
            }

            System.out.println("graph copy is independent of original after truncate\n");

        }
    }
}
