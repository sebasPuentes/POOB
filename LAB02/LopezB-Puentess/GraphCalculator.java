import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Arrays;

/** Graph Calculator
 * 
 * @Juan Sebastian Puentes Julio and Julian Camilo Lopez Barrero
 */
    
public class GraphCalculator{
    
    private TreeMap<String,Graph> variables;
    private boolean lastOperation = false;
    
    public GraphCalculator(){
        variables = new TreeMap<>();
    }
    
    //Create a new variable
    public void create(String nombre){
        if (variables.containsKey(nombre)){
            lastOperation = false;
        }
        else{
            variables.put(nombre,new Graph());
            lastOperation = true;
        }
    }
    
    //Assign a graph to an existing variable
    //a := graph
    public void assign(String graph, String[] vertices, String [][] edges ){
        if (variables.containsKey(graph)){
            variables.put(graph,new Graph(vertices,edges));
            lastOperation = true;
        }
    } 
    
    public Graph getGraph(String graph){
        return variables.get(graph);
    }
    
    //Assigns the value of a binary operation to a variable
    // a = b op v*
    //The operator characters are: '+' adding a edge between two vertices, '-' removing a edge between two vertex
    // '?' checking if a graph contains the given vertices
    // 'p' return the graph with a path that passes through all the vertices in the indicated order
    public void assignUnary(String a, String b, char op, String [] vertices){
        ArrayList<String> verticesList = new ArrayList<>(Arrays.asList(vertices));
        Graph graphB = variables.get(b);
        Graph graphA = null;
        if(op == '+'){
            graphA = graphB.addEdge(verticesList.get(0), verticesList.get(1));
            variables.put(a, graphA);
            lastOperation = true;
        }
        if(op == '-'){
            graphA = graphB.removeEdge(verticesList.get(0), verticesList.get(1));
            variables.put(a, graphA);
            lastOperation = true;
        }
        if(op == '?'){
            boolean containsVertex = graphB.containsVertices(verticesList.get(0),verticesList.get(1));
            lastOperation = true;
        }
        if(op == 'p'){
            return; 
        } 
    }
    //Assigns the value of a binary operation to a variable
    // a = b op c
    //The operator characters are:  'u' union, 'i' intersection, 'd' difference, 'j' join
    public void assignBinary(String a, String b, char op, String c){
        
        Graph graphB = variables.get(b);
        Graph graphC = variables.get(c);
        Graph graphA = null;
        if(op == 'u'){
            graphA = graphB.union(graphC);
            lastOperation = true;
        }
        if(op == 'i'){
            graphA = graphB.intersection(graphC);
            lastOperation = true;
        }
        if(op == 'd'){
            graphA = graphB.difference(graphC);
            lastOperation = true;
        }
        if(op == 'j'){
            graphA = graphB.join(graphC);
            lastOperation = true;
        }
        variables.put(a,graphA);
    }

    //Returns the graph with the edges in uppercase in alphabetical order.
    public String toString(String graph){
        return variables.get(graph).toString();
    }

    //If the last operation was successfully completed
    public boolean ok(){
        return lastOperation;
    }
}
    



