import graph.Graph;
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
}
