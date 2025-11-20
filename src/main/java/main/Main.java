package main;

import graph.*;
import input.*;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int k = scanner.nextInt();
        int l = scanner.nextInt();

        String filename = n + "_" + k + "_" + l + ".asc";
        String filePath = "src/main/resources/genreg-modified/" + filename;

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
        }

        scanner.close();

    }
}
