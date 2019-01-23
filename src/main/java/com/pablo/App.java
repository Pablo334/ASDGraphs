package com.pablo;

import com.pablo.graph.Graph;

import java.util.HashMap;


public class App 
{
    public static void main( String[] args ) {

        Graph<Integer> graph = new Graph<>();
        graph.insertNode(new Graph.Node<>(3));
        graph.insertNode(new Graph.Node<>(5));
        graph.insertNode(new Graph.Node<>(20));
        graph.insertNode(new Graph.Node<>(25));
        graph.insertEdge(new Graph.Node<>(3), new Graph.Node<>(20));
        graph.insertEdge(new Graph.Node<>(20), new Graph.Node<>(25));
        graph.insertEdge(new Graph.Node<>(25), new Graph.Node<>(15));
        graph.insertEdge(new Graph.Node<>(3), new Graph.Node<>(5));
        graph.insertEdge(graph.getNodes().get(1), new Graph.Node<>(15));
        graph.insertNode(new Graph.Node<>(345));

        System.out.println("-----------------------BFS--------------------------");
        Graph.bfs(graph, graph.getNodes().get(0));

        System.out.println("-----------------------Erdos--------------------------");
        HashMap<Graph.Node<Integer>, Integer> erdosMap = new HashMap<>();

        @SuppressWarnings("unchecked")
        Graph.Node<Integer>[] parentList = new Graph.Node[graph.size()];

        Graph.erdos(graph, graph.getNodes().get(0), erdosMap, parentList);
        erdosMap.entrySet().stream().forEach(System.out::println);

        System.out.println("-----------------------Shortest Path--------------------------");
        Graph.printShortestPath(graph, graph.getNodes().get(0), graph.getNodes().get(5), parentList);

        System.out.println("-----------------------DFS Recursive--------------------------");
        graph.getNodes().forEach(node -> node.visit(false));
        Graph.dfsRecursive(graph, graph.getNodes().get(0));

        System.out.println("-----------------------DFS Iterative--------------------------");
        Graph.dfsIterative(graph, graph.getNodes().get(0));


    }
}
