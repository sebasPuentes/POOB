import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphTest{

    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
    }

    
    @Test
    public void shouldNotFindPathWhenNoPathExists() {
        String[] vertices = {"A", "B", "C", "D"};
        String[][] edges = {{"A", "B"}, {"C", "D"}};
        Graph graph = new Graph(vertices, edges);
        Graph path = graph.path("A", "D");
        assertEquals(0, path.vertices()); 
        assertEquals(0, path.edges()); 
        assertEquals("", path.toString().trim()); 
    }
    
     @Test
    public void shouldCreateEmptyGraph(){
        String [] vertices ={};
        String [][] edges = {};
        assertEquals(0, new Graph(vertices,edges).vertices());
        assertEquals(0, new Graph(vertices,edges).edges());
    }    
   
    @Test
    public void shouldCreateGraphs(){
        String [] vertices ={"DDYA","MYSD","DOPO"};
        String [][] edges = {{"DDYA","MYSD"},{"DDYA","DOPO"}};    
        assertEquals(3, new Graph(vertices,edges).vertices());
        assertEquals(2, new Graph(vertices,edges).edges());
    }    
    
    @Test
    public void shouldNotHaveDuplicateVerticesEdges(){
        String [] vertices ={"DDYA","MYSD","DOPO","DOPO"};
        String [][] edges = {{"DDYA","MYSD"},{"DDYA","DOPO"},{"DDYA","DOPO"}};    
        assertEquals(3, new Graph(vertices,edges).vertices());
        assertEquals(2, new Graph(vertices,edges).edges());
    }    

    @Test
    public void shouldNotBeCaseSensitive(){     
        String [] vertices ={"Ddya","MYSD","DOPO","dopo"};
        String [][] edges = {{"DDYA","Mysd"},{"ddya","dopo"},{"DDya","doPo"}};    
        assertEquals(3, new Graph(vertices,edges).vertices());
        assertEquals(2, new Graph(vertices,edges).edges());
    }
    
    @Test
    public void shouldConvertToString(){
        String [] vertices ={"DDYA","MYSD","DOPO"};
        String [][] edges = {{"DDYA","MYSD"},{"DDYA","DOPO"}};  
        String data= "(DDYA, DOPO) (DDYA, MYSD)";
        assertEquals(data, new Graph(vertices,edges).toString());
    }

    @Test
    public void shouldValityEquality(){
        String [] vertices ={};
        String [][] edges = {};
        assertEquals(new Graph(vertices,edges),new Graph(vertices,edges));
        String [] verticesA ={"DDYA","MYSD","DOPO"};
        String [][] edgesA = {{"DDYA","MYSD"},{"DDYA","DOPO"}};      
        String [] verticesB ={"Ddya","MYSD","DOPO","dopo"};
        String [][] edgesB = {{"DDYA","Mysd"},{"ddya","dopo"},{"DDya","doPo"}}; 
        assertEquals(new Graph(verticesA,edgesA),new Graph(verticesB,edgesB));
    }    
    
    @Test
    public void shouldMakeUnionBetweenGraphs() {
        ArrayList<String> vertices1 = new ArrayList<>(Arrays.asList("DDYA", "MYSD", "DOPO"));
        ArrayList<ArrayList<String>> edges1 = new ArrayList<>();
        edges1.add(new ArrayList<>(Arrays.asList("DDYA", "DOPO")));
        edges1.add(new ArrayList<>(Arrays.asList("DDYA", "MYSD")));
    
        ArrayList<String> vertices2 = new ArrayList<>(Arrays.asList("MBDA", "POOB", "ECDI", "DOPO", "DDYA"));
        ArrayList<ArrayList<String>> edges2 = new ArrayList<>();
        edges2.add(new ArrayList<>(Arrays.asList("MBDA", "POOB")));
        edges2.add(new ArrayList<>(Arrays.asList("MBDA", "ECDI")));
        edges2.add(new ArrayList<>(Arrays.asList("DDYA", "DOPO")));
        edges2.add(new ArrayList<>(Arrays.asList("DOPO", "ECDI")));
    
        Graph A = new Graph(vertices1, edges1);
        Graph B = new Graph(vertices2, edges2);
        Graph unionGraph = A.union(B);
    
        ArrayList<String> expectedVertices = new ArrayList<>(Arrays.asList("DDYA", "MYSD", "DOPO", "MBDA", "POOB", "ECDI"));
        ArrayList<ArrayList<String>> expectedEdges = new ArrayList<>();
        expectedEdges.add(new ArrayList<>(Arrays.asList("DDYA", "DOPO")));
        expectedEdges.add(new ArrayList<>(Arrays.asList("DDYA", "MYSD")));
        expectedEdges.add(new ArrayList<>(Arrays.asList("MBDA", "POOB")));
        expectedEdges.add(new ArrayList<>(Arrays.asList("MBDA", "ECDI")));
        expectedEdges.add(new ArrayList<>(Arrays.asList("DOPO", "ECDI")));
    
        Graph expectedGraph = new Graph(expectedVertices, expectedEdges);
        assertEquals(expectedGraph, unionGraph);
    }
    
    @Test
    public void shouldMakeIntersectBetweeenGraphs(){
        ArrayList<String> vertices1 = new ArrayList<>(Arrays.asList("DDYA","MYSD","DOPO"));
        ArrayList<ArrayList<String>> edges1 = new ArrayList<>();
        edges1.add(new ArrayList<>(Arrays.asList("DDYA","DOPO")));
        edges1.add(new ArrayList<>(Arrays.asList("DDYA","MYSD")));
        
        ArrayList<String> vertices2 = new ArrayList<>(Arrays.asList("DDYA","DOPO","DORA"));
        ArrayList<ArrayList<String>> edges2 = new ArrayList<>();
        edges2.add(new ArrayList<>(Arrays.asList("DDYA","DOPO")));
        edges2.add(new ArrayList<>(Arrays.asList("DDYA","DORA")));
    
        Graph A = new Graph(vertices1,edges1);
        Graph B = new Graph(vertices2,edges2);
        Graph intersection = A.intersection(B);
        
        ArrayList<String> expectedVertices = new ArrayList<>(Arrays.asList("DDYA","DOPO"));
        ArrayList<ArrayList<String>> expectedEdges = new ArrayList<>();
        expectedEdges.add(new ArrayList<>(Arrays.asList("DDYA","DOPO")));
    
        Graph expectedGraph = new Graph(expectedVertices,expectedEdges);
        assertEquals(expectedGraph,intersection);
    }
    
    @Test
    public void shouldMakeDifferenceBetweenGraphs() {
    
        ArrayList<String> vertices1 = new ArrayList<>(Arrays.asList("DDYA", "MYSD", "DOPO", "MBDA"));
        ArrayList<ArrayList<String>> edges1 = new ArrayList<>();
        edges1.add(new ArrayList<>(Arrays.asList("DDYA", "MYSD")));
        edges1.add(new ArrayList<>(Arrays.asList("MYSD", "DOPO")));
        edges1.add(new ArrayList<>(Arrays.asList("DOPO", "MBDA")));

        ArrayList<String> vertices2 = new ArrayList<>(Arrays.asList("MYSD", "DOPO", "MBDA"));
        ArrayList<ArrayList<String>> edges2 = new ArrayList<>();
        edges2.add(new ArrayList<>(Arrays.asList("MYSD", "DOPO")));

        Graph A = new Graph(vertices1, edges1);
        Graph B = new Graph(vertices2, edges2);
    
        Graph differenceGraph = A.difference(B);

        ArrayList<String> expectedVertices = new ArrayList<>(Arrays.asList("DDYA"));
        ArrayList<ArrayList<String>> expectedEdges = new ArrayList<>();

        Graph expectedGraph = new Graph(expectedVertices, expectedEdges);
        assertEquals(expectedGraph, differenceGraph);
    }

    @Test
    public void shouldMakeJoinBetweenGraphs() {
    
        ArrayList<String> vertices1 = new ArrayList<>(Arrays.asList("A", "B"));
        ArrayList<ArrayList<String>> edges1 = new ArrayList<>();
        edges1.add(new ArrayList<>(Arrays.asList("A", "B")));

        ArrayList<String> vertices2 = new ArrayList<>(Arrays.asList("C", "D"));
        ArrayList<ArrayList<String>> edges2 = new ArrayList<>();
        edges2.add(new ArrayList<>(Arrays.asList("C", "D")));

        Graph A = new Graph(vertices1, edges1);
        Graph B = new Graph(vertices2, edges2);
    
        Graph joinGraph = A.join(B);

        ArrayList<String> expectedVertices = new ArrayList<>(Arrays.asList("A","B","C","D"));
        ArrayList<ArrayList<String>> expectedEdges = new ArrayList<>();
        expectedEdges.add(new ArrayList<>(Arrays.asList("A", "C")));
        expectedEdges.add(new ArrayList<>(Arrays.asList("A", "D")));
        expectedEdges.add(new ArrayList<>(Arrays.asList("B", "C")));
        expectedEdges.add(new ArrayList<>(Arrays.asList("B", "D")));
        
        Graph expectedGraph = new Graph(expectedVertices, expectedEdges);
        assertEquals(expectedGraph, joinGraph);
    }
    
    @Test
    public void shouldPass(){
        assertTrue(null,10 < 20);    
    }

    @Test
    public void shouldFail(){
        assertTrue(null,9 > 10);  
    }
    
    @Test 
    public void shouldErr(){
        String texto = null;
        int longitud = texto.length();  
    }
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
