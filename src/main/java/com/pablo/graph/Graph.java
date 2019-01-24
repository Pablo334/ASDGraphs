package com.pablo.graph;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
        boolean visited = false;

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
            return " " +this.value+" ";
        }
    }

    public static <T> void bfs(Graph<T> g, Node<T> node){
        LinkedList<Node<T>> nodeQueue = new LinkedList<>();
        nodeQueue.addFirst(node);
        g.getNodes().stream().filter(eNode -> !eNode.equals(node)).forEach(eNode -> eNode.visit(false));
        node.visit(true);
        while(!nodeQueue.isEmpty()){
            Node<T> u = nodeQueue.removeLast();
            System.out.println(u);
            u.getAdjacentNodes().forEach(adjNode -> {
                if(!adjNode.isVisited()){
                    adjNode.visit(true);
                    nodeQueue.addFirst(adjNode);
                }
            });
        }
    }

    public static <T> void erdos(Graph<T> g, Node<T> node, HashMap<Node<T>, Integer> erdosMap, Node<T>[] parent){
        LinkedList<Node<T>> nodeQueue = new LinkedList<>();
        nodeQueue.addFirst(node);
        g.getNodes().stream().filter(eNode -> !eNode.equals(node)).forEach(
                eNode -> erdosMap.put(eNode, -1)
        );
        erdosMap.put(node, 0);
        parent[g.getNodes().indexOf(node)] = null;
        while(!nodeQueue.isEmpty()){
            Node<T> temp = nodeQueue.removeLast();
            temp.getAdjacentNodes().forEach(
                    adjNode -> {
                        if(erdosMap.get(adjNode)==-1){
                            erdosMap.replace(adjNode, -1, erdosMap.get(temp)+1);
                            parent[g.getNodes().indexOf(adjNode)] = temp;
                            nodeQueue.addFirst(adjNode);
                        }
                    });
        }
    }

    public static <T> void printShortestPath(Graph<T> g, Node<T> src, Node<T> dst, Node<T>[] parent){
        if(src.equals(dst))
            System.out.println(dst);
        else if(parent[g.getNodes().indexOf(dst)] == null)
            System.out.println("Error..... There is no path from Node:" + src + " to Node:" + dst);
        else {
            printShortestPath(g, src, parent[g.getNodes().indexOf(dst)], parent);
            System.out.println(dst);
        }
    }

    public static <T> void dfsRecursive(Graph<T> g, Node<T> u){
        u.visit(true);
        System.out.println(u);
        u.getAdjacentNodes().forEach(node -> {
            if(!node.isVisited()){
                dfsRecursive(g, node);
            }
        });

    }

    public static <T> void dfsIterative(Graph<T> g, Node<T> u){
        Stack<Node<T>> stack = new Stack<>();
        stack.push(u);
        g.getNodes().forEach(node -> node.visit(false));
        while(!stack.empty()){
            Node<T> temp = stack.pop();
            if(!temp.isVisited()){
                System.out.println(temp);
                temp.visit(true);
                temp.getAdjacentNodes().forEach(stack::push);
            }
        }
    }

    public static <T> int[] connComponents(Graph<T> g){
        int[] id = new int[g.size()];
        g.getNodes().forEach(node -> id[g.getNodes().indexOf(node)] = 0);
        final AtomicReference<Integer> counter = new AtomicReference<>();
        g.getNodes().forEach(node -> {
            if(id[g.getNodes().indexOf(node)] == 0){
                Integer count = counter.get();
                counter.set(count+1);
                ccdfs(g, counter, node, id);

            }
        });
        return id;
    }

    private static <T> void ccdfs(Graph<T>g, AtomicReference<Integer> counter, Node<T> node, int[] id){
        id[g.getNodes().indexOf(node)] = counter.get();
        node.getAdjacentNodes().forEach(adjNode -> {
            if(id[g.getNodes().indexOf(adjNode)] == 0)
                ccdfs(g, counter, adjNode, id);
        });
    }
}
