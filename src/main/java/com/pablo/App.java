package com.pablo;

import com.pablo.graph.Graph;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        Graph<Integer> graph = new Graph<>();
        graph.insertNode(new Graph.Node<>(3));
        graph.insertNode(new Graph.Node<>(5));
        graph.insertEdge(new Graph.Node<>(3), new Graph.Node<>(5));

        Graph.bfs(graph, graph.getNodes().get(0));
    }
}
