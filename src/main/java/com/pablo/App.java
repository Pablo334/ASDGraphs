package com.pablo;

import com.pablo.graph.Graph;

import java.util.HashMap;


public class App 
{
    public static void main( String[] args ) {

        Graph<Integer> graph = new Graph<>();
        graph.insertNode(new Graph.Node<>(3));
        graph.insertNode(new Graph.Node<>(5));
        graph.insertEdge(new Graph.Node<>(3), new Graph.Node<>(5));
        graph.insertEdge(graph.getNodes().get(1), new Graph.Node<>(15));
        graph.insertNode(new Graph.Node<>(345));

        Graph.bfs(graph, graph.getNodes().get(0));

        HashMap<Graph.Node<Integer>, Integer> erdosMap = new HashMap<>();
        Graph.erdos(graph, graph.getNodes().get(0), erdosMap);

        erdosMap.entrySet().stream().forEach(System.out::println);
    }
}
