import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Collections;

/** Graph
 * 
 * @Juan Sebastian Puentes Julio and Julian Camilo Lopez Barrero
 * 
 */public class Graph {
    
    private ArrayList<String> vertices;
    private ArrayList<ArrayList<String>> edges;
    
    public Graph(){
        
    }
    
    public Graph(ArrayList<String> vertices,ArrayList<ArrayList<String>> edges){
        this.vertices = vertices;
        this.edges = edges;
        upperCaseVertices();
        upperCaseEdges();
        deleteDuplicateVertices();
        deleteDuplicateEdges();
    }
    
    public Graph(String[] vertices, String[][] edges) {
        this.vertices = new ArrayList<>(Arrays.asList(vertices));
        this.edges = new ArrayList<>();
        for (String[] edge : edges) {
            if (edge.length == 2) {
                this.edges.add(new ArrayList<>(Arrays.asList(edge)));
            }
        }
        upperCaseVertices();
        upperCaseEdges();
        deleteDuplicateVertices();
        deleteDuplicateEdges();
    }
    
    /**
    * Verifies that a graph has a given vertex.
    */
    public boolean contains(String vertex) {
        return vertices.contains(vertex);
    }   
    
    public Graph path(String start, String end) {
        start = start.toUpperCase();
        end = end.toUpperCase();

        if (!vertices.contains(start) || !vertices.contains(end)) {
            return new Graph(); 
        }

        ArrayList<String> visited = new ArrayList<>(); 
        ArrayList<String> parent = new ArrayList<>(); 

        for (String vertex : vertices) {
            parent.add(null); 
            visited.add("NO"); 
        }

        boolean found = dfs(start, end, visited, parent);

        if (!found) {
            return new Graph(); 
        }

        ArrayList<String> pathVertices = new ArrayList<>();
        ArrayList<ArrayList<String>> pathEdges = new ArrayList<>();

        String current = end;
        while (current != null) {
            pathVertices.add(current);
            int currentIndex = vertices.indexOf(current);
            String parentVertex = parent.get(currentIndex);
            if (parentVertex != null) {
                pathEdges.add(new ArrayList<>(Arrays.asList(parentVertex, current)));
            }
            current = parentVertex;
        }

        Collections.reverse(pathVertices);
        Collections.reverse(pathEdges);

        return new Graph(pathVertices, pathEdges);
    }

    /**
     * Recursive DFS to help method path.
     */
    private boolean dfs(String current, String end, ArrayList<String> visited, ArrayList<String> parent) {
        int currentIndex = vertices.indexOf(current);
        visited.set(currentIndex, "YES"); 

        if (current.equals(end)) {
            return true; 
        }
        
        for (ArrayList<String> edge : edges) {
            if (edge.get(0).equals(current)) {
                String neighbor = edge.get(1);
                int neighborIndex = vertices.indexOf(neighbor);
                if (neighborIndex != -1 && visited.get(neighborIndex).equals("NO")) {
                    parent.set(neighborIndex, current); 
                    if (dfs(neighbor, end, visited, parent)) {
                        return true; 
                    }
                }
            } else if (edge.get(1).equals(current)) {
                String neighbor = edge.get(0);
                int neighborIndex = vertices.indexOf(neighbor);
                if (neighborIndex != -1 && visited.get(neighborIndex).equals("NO")) {
                    parent.set(neighborIndex, current); 
                    if (dfs(neighbor, end, visited, parent)) {
                        return true; 
                    }
                }
            }
        }
        return false; 
    }
    
    /**
     * Method to do an union between two Graphs
     */
    public Graph union(Graph g) {
        ArrayList<String> combinedVertices = new ArrayList<>();
        for (String vertex : vertices) {
            combinedVertices.add(vertex);
        }
        for (String vertex : g.vertices) {
            if (!combinedVertices.contains(vertex)) {
                combinedVertices.add(vertex);
            }
        }
        ArrayList<ArrayList<String>> combinedEdges = new ArrayList<>();
        for (ArrayList<String> edge : edges) {
            combinedEdges.add(edge);
        }
        for (ArrayList<String> edge : g.edges) {
            if (!combinedEdges.contains(edge)) {
                combinedEdges.add(edge);
            }
        }
        return new Graph(combinedVertices, combinedEdges);
    }
    
    public Graph intersection(Graph g) {
        ArrayList<String> sameVertices = new ArrayList<>();
        for (String vertex : vertices) {
            if(g.contains(vertex)){
                sameVertices.add(vertex);
            }
        }
        ArrayList<ArrayList<String>> sameEdges = new ArrayList<>();
        for (ArrayList<String> edge : edges) {
            if(g.edges.contains(edge)){
                if (sameVertices.contains(edge.get(0)) && sameVertices.contains(edge.get(1))) {
                sameEdges.add(edge);
                }  
            }
        }
        Graph intersectionGraph = new Graph(sameVertices,sameEdges);
        return intersectionGraph;
    }
    
    public Graph difference(Graph g) {
        ArrayList<String> diffVertices = new ArrayList<>();
        ArrayList<ArrayList<String>> diffEdges = new ArrayList<>();
        // vértices de A que no están en B.
        for (String vertex : vertices) {
            if (!g.contains(vertex)) {
                diffVertices.add(vertex);
            }
        }
        // aristas de A que no están en B y vértices estén en diffVertices.
        for (ArrayList<String> edge : edges) {
            String vertex1 = edge.get(0);
            String vertex2 = edge.get(1);
            if (!g.edges.contains(edge) && diffVertices.contains(vertex1) && diffVertices.contains(vertex2)) {
                diffEdges.add(edge);
            }
        }
        return new Graph(diffVertices, diffEdges);
    }
    
    public Graph join(Graph g){
        Graph unionGraph = this.union(g);
        ArrayList<ArrayList<String>> newEdges = new ArrayList<>();
        for (String vertex1 : vertices) {
            for (String vertex2 : g.vertices) {
                ArrayList<String> edge = new ArrayList<>(Arrays.asList(vertex1, vertex2));
                if (!unionGraph.edges.contains(edge)) {
                    newEdges.add(edge);
                }
            }
        }
        Graph joinGraph = new Graph(unionGraph.vertices,newEdges);
        return joinGraph; 
    }
    
    public boolean containsVertices(String vertex1,String vertex2){
        return vertices.contains(vertex1) && vertices.contains(vertex2);
    }
    
    public Graph addEdge(String vertex1,String vertex2){
        ArrayList<String> newEdges = new ArrayList<>(Arrays.asList(vertex1, vertex2));
        if (!edges.contains(newEdges)) {
            edges.add(newEdges);
        }
        return new Graph(vertices,edges);
    }
    
    public Graph removeEdge(String vertex1,String vertex2){
        ArrayList<String> newEdges = new ArrayList<>(Arrays.asList(vertex1, vertex2));
        if (edges.contains(newEdges)) {
            edges.remove(newEdges);
        }
        return new Graph(vertices,edges);
    }
    
    /**
     * Returns the amount of vertex in a Graph.
     */
    public int vertices(){
        return vertices.size();
    }
    
    /**
     * Returns the amount of edges in a Graph.
     */
    public int edges(){
        return edges.size();
    }    
    
    /**
     * Verify if a graph is equal to another 
     */
    public boolean equals(Graph g) {
        if (g == null) {
            return false;
        }
        
        if (!vertices.equals(g.vertices)) {
            return false;
        }
        
        if (!edges.equals(g.edges)) {
            return false;
        }
        return true;
    }
    
    /**
     * Delete Duplicate Vertexes
     */
    private void deleteDuplicateVertices(){
        ArrayList<String> uniqueVertices = new ArrayList<>();
        for(String vertex : vertices){
            if(!uniqueVertices.contains(vertex)){
                uniqueVertices.add(vertex);
            }
        }
        vertices = uniqueVertices;
    }
    
    /**
     * Delete Duplicate Edges
     */
    private void deleteDuplicateEdges(){
        ArrayList<ArrayList<String>> uniqueEdges = new ArrayList<>();
        for(ArrayList<String> edge : edges){
            if(!uniqueEdges.contains(edge)){
                uniqueEdges.add(edge);
            }
        }
        edges = uniqueEdges;
    }
    
    /**
     * Transform the vertexes to uppercase
     */
    private void upperCaseVertices() {
        for (int i = 0; i < vertices.size(); i++) {
        vertices.set(i, vertices.get(i).toUpperCase());
        }
    }
    
    /**
     * Transform the edges to uppercase
     */
    private void upperCaseEdges(){
        for (int i = 0; i < edges.size(); i++){
            ArrayList<String> edge = edges.get(i);
            for (int j = 0; j < edge.size(); j++){
                edge.set(j, edge.get(j).toUpperCase());
            }
        }
    }
    
    public boolean equals(Object g){
        return equals((Graph)g);
    }
    
    //Only arcs in space-separated tuples. The vertices are capitalized. The edges must always be in alphabetical order.
    //For example, (A, B) (C, D)
    /**
     * Transform to String Vertexes and Edges
     */
    @Override
    public String toString() {
        List<ArrayList<String>> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Comparator.comparing(edge -> edge.get(0) + edge.get(1)));
        String text = "";
        for (ArrayList<String> edge : sortedEdges) {
            Collections.sort(edge);
            text += "(" + edge.get(0) + ", " + edge.get(1) + ") ";
        }
        return text.trim();
    }
    
    public ArrayList<ArrayList<String>> getEdges() {
    return edges;
}
}
