package colouring;

import graph.Edge;

public class Conflict {
    Edge edge;
    int type;
    int colour;
    String colouringString;

    public Conflict(Edge edge, int type, int colour, String colouringString) {
        this.edge = edge;
        this.type = type;
        this.colour = colour;
        this.colouringString = colouringString;
    }
}
