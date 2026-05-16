import colouring.*;
import graph.Edge;
import graph.Graph;
import graph.Graph.Code;
import graph.Vertex;
import input.GraphLoader;
import colouring.StrongEdgeColouring;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

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

    @Test
    public void getCodeTest() throws FileNotFoundException {

        String filePath = "src/main/resources/genreg-modified/testgraphs.asc";

        List<Graph> graphs = GraphLoader.loadGraphsFromFile(filePath);

        for (Graph graph : graphs) {

            //if (graph.getId() != 2) continue;

            System.out.println(graph);

            List<Code> codes = graph.getCodes();

            StringBuilder sb = new StringBuilder();

            for (Code c : codes) {
                for (Vertex v : c.sequence) {
                    sb.append(v.getId());
                }

                sb.append("\n");
                sb.append(c.code);
                sb.append("\n\n");
            }

            System.out.println(sb);
        }
    }

    @Test
    public void codeTest() throws FileNotFoundException {

        String filePath = "src/main/resources/genreg-modified/testgraphs.asc";

        List<Graph> graphs = GraphLoader.loadGraphsFromFile(filePath);

        for (Graph graph : graphs) {

            if (graph.getId() != 2) continue;

            System.out.println(graph);

            Map<Integer, String> vertexCodes = graph.getVertexCodes();

            assertEquals(vertexCodes.get(0), vertexCodes.get(1));
            assertEquals(vertexCodes.get(0), vertexCodes.get(2));
            assertEquals(vertexCodes.get(0), vertexCodes.get(3));
            assertEquals(vertexCodes.get(0), vertexCodes.get(4));
            assertEquals(vertexCodes.get(0), vertexCodes.get(5));

            String e34 = graph.getUnorientedEdgeCode(3, 4);
            String e35 = graph.getUnorientedEdgeCode(3, 5);
            String e45 = graph.getUnorientedEdgeCode(4, 5);
            String e02 = graph.getUnorientedEdgeCode(0, 2);
            String e01 = graph.getUnorientedEdgeCode(0, 1);
            String e12 = graph.getUnorientedEdgeCode(1, 2);

            assertEquals(e34, e35);
            assertEquals(e34, e45);
            assertEquals(e34, e02);
            assertEquals(e34, e01);
            assertEquals(e34, e12);

            String e04 = graph.getUnorientedEdgeCode(0, 4);
            String e23 = graph.getUnorientedEdgeCode(2, 3);
            String e15 = graph.getUnorientedEdgeCode(1, 5);

            assertEquals(e04, e23);
            assertEquals(e04, e15);

            assertNotEquals(e23, e45);
            assertNotEquals(e12, e23);
            assertNotEquals(e02, e04);
            assertNotEquals(e15, e34);
            assertNotEquals(e01, e15);

            String e10 = graph.getUnorientedEdgeCode(1, 0);
            assertEquals(e01, e10);

            String oriented01 = graph.getOrientedEdgeCode(0, 1);
            String oriented15 = graph.getOrientedEdgeCode(1, 5);
            assertNotEquals(oriented01, oriented15);
        }
    }

    @Test
    public void comparePalettesOptimisedTest() throws FileNotFoundException {
        List<Graph> graphs = new ArrayList<>();
        List<Palette6> palettes = new ArrayList<>();
        int paletteCount = 0;

        for (int i = 4; i <= 8; i += 2) {
            String fileName = "0" + i + "_3_3.asc";
            String filePath = "src/main/resources/genreg-modified/" + fileName;

            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            graphs.addAll(GraphLoader.loadGraphsFromFile(filePath));
        }

        for (Graph originalGraph : graphs) {

            List<Vertex> vertices = originalGraph.getSortedVertices();
            Map<Integer, String> vertexCodes = originalGraph.getVertexCodes();

            List<Vertex> orbitVertices = new ArrayList<>();
            Set<String> uniqueVertexCodes = new HashSet<>();

            for (Vertex v : vertices) {
                String code = vertexCodes.get(v.getId());
                if (uniqueVertexCodes.add(code)) {
                    orbitVertices.add(v);
                }
            }

            Map<Integer, String> edgeCodes = new HashMap<>();
            for (Edge e : originalGraph.getSortedEdges()) {
                edgeCodes.put(
                        e.getId(),
                        originalGraph.getUnorientedEdgeCode(e.getFrom().getId(), e.getTo().getId())
                );
            }

            for (Vertex orbitVertex : orbitVertices) {
                List<Edge> vEdges = orbitVertex.getSortedEdges();

                List<Edge> orbitEdges = new ArrayList<>();
                Set<String> uniqueEdgeCodes = new HashSet<>();

                for (Edge e : vEdges) {
                    String code = edgeCodes.get(e.getId());
                    if (uniqueEdgeCodes.add(code)) {
                        orbitEdges.add(e);
                    }
                }

                List<Edge[]> combinations = new ArrayList<>();

                for (Edge edgeA : orbitEdges) {
                    List<Edge> remaining = new ArrayList<>(vEdges);
                    remaining.remove(edgeA);

                    Edge edgeD = remaining.get(0);
                    Edge edgeG = remaining.get(1);

                    if (edgeCodes.get(edgeD.getId()).equals(edgeCodes.get(edgeG.getId()))) {
                        combinations.add(new Edge[]{edgeA, edgeD, edgeG});
                    } else {
                        combinations.add(new Edge[]{edgeA, edgeD, edgeG});
                        combinations.add(new Edge[]{edgeA, edgeG, edgeD});
                    }
                }

                for (Edge[] comb : combinations) {

                    Graph graph = originalGraph.getCopy();

                    Map<Integer, Vertex> vertexMap = new HashMap<>();
                    for (Vertex v : graph.getVertices()) {
                        vertexMap.put(v.getId(), v);
                    }

                    Map<Integer, Edge> edgeMap = new HashMap<>();
                    for (Edge e : graph.getEdges()) {
                        edgeMap.put(e.getId(), e);
                    }

                    Vertex v = vertexMap.get(orbitVertex.getId());

                    Edge edgeA = edgeMap.get(comb[0].getId());
                    Edge edgeD = edgeMap.get(comb[1].getId());
                    Edge edgeG = edgeMap.get(comb[2].getId());

                    StrongEdgeColouring c = new StrongEdgeColouring();
                    Palette6 palette = c.getPalette6(graph, v, edgeA, edgeD, edgeG);
                    palettes.add(palette);
                    paletteCount++;

                    System.out.println("P" + paletteCount + ":\n" + palette);
                }
            }
        }

        System.out.println();

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= paletteCount; i++) {
            for (int j = 1; j <= paletteCount; j++) {
                if (i == j) continue;
                Palette6 pi = palettes.get(i - 1);
                Palette6 pj = palettes.get(j - 1);

                if (pi.equals(pj)) {
                    sb.append("P").append(i).append(" is equal to P").append(j).append("\n\n");
                } else if (pi.isSubsetOf(pj)) {
                    sb.append("P").append(i).append(" is subset of P").append(j).append("\n\n");
                }
            }
        }

        System.out.print(sb);
    }
}
