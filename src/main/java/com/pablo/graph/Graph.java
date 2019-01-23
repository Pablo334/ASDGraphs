package com.pablo.graph;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph<U> {
    private List<Node<U>> nodes = new ArrayList<>();

    public Graph(){}

    public void insertNode(Node<U> u){
        if(!nodes.contains(u))
            nodes.add(u);
    }

    public void insertEdge(Node<U> u, Node<U> w){
        this.insertNode(u);
        this.insertNode(w);
        this.getNodes().get(this.getNodes().indexOf(u)).getAdjacentNodes().add(w);

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

    public static <T,U> void bfs(Graph<T> g, Node<U> node){
        LinkedList<Node<U>> nodeQueue = new LinkedList<>();
        nodeQueue.addFirst(node);
        g.getNodes().stream().filter(eNode -> !eNode.equals(node)).forEach(eNode -> eNode.visit(false));
        node.visit(true);
        while(!nodeQueue.isEmpty()){
            Node<U> u = nodeQueue.removeFirst();
            System.out.println(u);
            u.getAdjacentNodes().forEach(adjNode -> {
                if(!adjNode.isVisited()){
                    adjNode.visit(true);
                    nodeQueue.addFirst(adjNode);
                }
            });
        }
    }
}
