package com.pablo.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Graph<U> {
    private List<Node<U>> nodes = new ArrayList<>();

    public Graph(){}

    public int insertNode(Node<U> u){
        if(!nodes.contains(u))
            nodes.add(u);

        return nodes.indexOf(u);
    }

    public void insertEdge(Node<U> u, Node<U> w){
        int uIndex = this.insertNode(u);
        int wIndex = this.insertNode(w);
        this.getNodes().get(uIndex).getAdjacentNodes().add(this.getNodes().get(wIndex));

    }

    public List<Node<U>> getNodes(){
        return this.nodes;
    }

    public int size(){
        return this.nodes.size();
    }

    public static class Node<T>{
        private List<Node<T>> adjacentNodes = new ArrayList<>();
        T value;
        boolean visited;

        public Node(T value){
            this.value = value;
        }

        public T getValue(){
            return value;
        }

        public List<Node<T>> getAdjacentNodes(){
            return this.adjacentNodes;
        }

        public boolean isVisited(){
            return this.visited;
        }

        public void visit(boolean v){
            this.visited = v;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj){
            if(obj==null) return false;
            if(!(obj instanceof Node)) return false;
            if(obj==this) return true;
            return ((Node<T>) obj).value == this.value;
        }

        @Override
        public int hashCode(){
            int hash = 7;
            hash = 31 * hash + value.hashCode();
            return hash;
        }

        @Override
        public String toString(){
            return " Value: " + this.value + " ";
        }
    }

    public static <T> void bfs(Graph<T> g, Node<T> node){
        LinkedList<Node<T>> nodeQueue = new LinkedList<>();
        nodeQueue.addFirst(node);
        g.getNodes().stream().filter(eNode -> !eNode.equals(node)).forEach(eNode -> eNode.visit(false));
        node.visit(true);
        while(!nodeQueue.isEmpty()){
            Node<T> u = nodeQueue.removeFirst();
            System.out.println(u);
            u.getAdjacentNodes().forEach(adjNode -> {
                if(!adjNode.isVisited()){
                    adjNode.visit(true);
                    nodeQueue.addFirst(adjNode);
                }
            });
        }
    }

    public static <T> void erdos(Graph<T> g, Node<T> node, HashMap<Node<T>, Integer> erdosMap){
        LinkedList<Node<T>> nodeQueue = new LinkedList<>();
        nodeQueue.addFirst(node);
        g.getNodes().stream().filter(eNode -> !eNode.equals(node)).forEach(
                eNode -> erdosMap.put(eNode, -1)
        );
        erdosMap.put(node, 0);
        while(!nodeQueue.isEmpty()){
            Node<T> temp = nodeQueue.removeLast();
            temp.getAdjacentNodes().forEach(
                    adjNode -> {
                        if(erdosMap.get(adjNode)==-1){
                            erdosMap.replace(adjNode, -1, erdosMap.get(temp)+1);
                            nodeQueue.addFirst(adjNode);
                        }
                    }
            );
        }

    }
}
