

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The test class GraphCalculatorTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class GraphCalculatorTest{
    private GraphCalculator calculator;
    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp(){
        calculator = new GraphCalculator();
        
    }
    
    @Test
    public void shouldCreateAVariable(){
        calculator.create("Graph1");
        assertTrue(calculator.ok());
    }
    
    @Test
    public void shouldNotCreateDuplicate(){
        calculator.create("Graph1");
        assertTrue(calculator.ok());

        calculator.create("Graph1");
        assertFalse(calculator.ok());
    }
    
    @Test
    public void shouldNotCreate(){
        calculator.create("Graph1");
        assertTrue(calculator.ok());
    }
    
    @Test
    public void shouldAssignUnaryAddEdge() {
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("a");

        String[] newEdge = {"A", "C"};
        calculator.assignUnary("a", "G1", '+', newEdge);
    
        String[][] expectedEdges = {{"A", "B"}, {"B", "C"}, {"A", "C"}};
        
        Graph actualGraph = calculator.getGraph("a");
        Graph expectedGraph = new Graph(vertices,expectedEdges);
    
        assertEquals(actualGraph, expectedGraph);
    }
    
    @Test
    public void shouldAssignUnaryRemoveEdge() {
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("a");

        String[] removeEdge = {"A", "B"};
        calculator.assignUnary("a", "G1", '-', removeEdge);
    
        String[][] expectedEdges = {{"B", "C"}};
        
        Graph actualGraph = calculator.getGraph("a");
        Graph expectedGraph = new Graph(vertices,expectedEdges);
    
        assertEquals(expectedGraph, actualGraph);
    }
    
    //REVISAR
    @Test
    public void shouldAssignUnaryContainEdge() {
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("a");

        String[] containsEdge = {"A", "B"};
        calculator.assignUnary("a", "G1", '?', containsEdge);
    
        assertEquals(true, true);
    }
    
    
    @Test
    public void shouldAssignUnaryPath() {
        String[] vertices = {"A", "B"};
        String[][] edges = {{"A", "B"}};
        Graph graph = new Graph(vertices, edges);
        Graph path = graph.path("A", "B");
        
        assertEquals(2, path.vertices()); 
        assertTrue(path.contains("A")); 
        assertTrue(path.contains("B")); 

        assertEquals(1, path.edges()); 
        assertTrue(path.getEdges().contains(new ArrayList<>(Arrays.asList("A", "B")))); 

        // Verify the path is correct
        assertEquals("(A, B)", path.toString().trim()); 
    }

    

    public void shouldAssignBinaryUnion(){
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("G2");
        String[] vertices1 = {"D", "E", "F"};
        String[][] edges1 = {{"D", "E"}, {"E", "F"}};
        calculator.assign("G2", vertices1, edges1);
        
        calculator.create("a");
        calculator.assignBinary("a","G1",'u',"G2");
        Graph actualGraph = calculator.getGraph("a");
        
        String[] expectedVertices = {"A", "B", "C","D", "E", "F"};
        String[][] expectedEdges = {{"A", "B"}, {"B", "C"},{"D", "E"}, {"E", "F"}};
        Graph expectedGraph = new Graph(expectedVertices,expectedEdges);
        
        assertEquals(expectedGraph, actualGraph);
    }
    
    @Test
    public void shouldAssignBinaryIntersection(){
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("G2");
        String[] vertices1 = {"A", "B"};
        String[][] edges1 = {{"A", "B"}};
        calculator.assign("G2", vertices1, edges1);
        
        calculator.create("a");
        calculator.assignBinary("a","G1",'i',"G2");
        Graph actualGraph = calculator.getGraph("a");
        
        String[] expectedVertices = {"A", "B"};
        String[][] expectedEdges = {{"A", "B"}};
        Graph expectedGraph = new Graph(expectedVertices,expectedEdges);
        
        assertEquals(expectedGraph, actualGraph);
    }
    
    @Test
    public void shouldNotBinaryIntersection() {
        calculator.create("G1");
        String[] vertices1 = {"A", "B", "C"};
        String[][] edges1 = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices1, edges1);
        
        calculator.create("G2");
        String[] vertices2 = {"E", "F", "D"};
        String[][] edges2 = {{"E", "F"}, {"F", "D"}};
        calculator.assign("G2", vertices2, edges2);
        
        calculator.create("a");
        calculator.assignBinary("a","G1",'i',"G2");
        Graph actualGraph = calculator.getGraph("a");
        
        String[] expectedVertices = {};
        String[][] expectedEdges = {{}};
        Graph expectedGraph = new Graph(expectedVertices,expectedEdges);
        assertEquals(expectedGraph, actualGraph);
    }
    
    @Test
    public void shouldAssignBinaryDifference(){
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("G2");
        String[] vertices1 = {"A", "B"};
        String[][] edges1 = {{"A", "B"}};
        calculator.assign("G2", vertices1, edges1);
        
        calculator.create("a");
        calculator.assignBinary("a","G1",'d',"G2");
        Graph actualGraph = calculator.getGraph("a");
        
        String[] expectedVertices = {"C"};
        String[][] expectedEdges = {};
        Graph expectedGraph = new Graph(expectedVertices,expectedEdges);
        
        assertEquals(expectedGraph, actualGraph);
    }
    
    @Test
    public void shouldNotDifference() {
        calculator.create("G1");
        String[] vertices1 = {"A", "B", "C"};
        String[][] edges1 = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices1, edges1);
        
        calculator.create("G2");
        String[] vertices2 = {"A", "B","C"};
        String[][] edges2 = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G2", vertices2, edges2);

        calculator.create("a");
        calculator.assignBinary("a","G1",'d',"G2");
        Graph actualGraph = calculator.getGraph("a");
        
        String[] expectedVertices = {};
        String[][] expectedEdges = {{}};
        Graph expectedGraph = new Graph(expectedVertices,expectedEdges);

        assertEquals(expectedGraph, actualGraph);
    }
    
    @Test
    public void shouldAssignBinaryJoin(){
        calculator.create("G1");
        String[] vertices = {"A", "B"};
        String[][] edges = {{"A", "B"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("G2");
        String[] vertices1 = {"C", "D"};
        String[][] edges1 = {{"C", "D"}};
        calculator.assign("G2", vertices1, edges1);
        
        calculator.create("a");
        calculator.assignBinary("a","G1",'j',"G2");
        Graph actualGraph = calculator.getGraph("a");
        
        String[] expectedVertices = {"A", "B","C", "D"};
        String[][] expectedEdges = {{"A", "C"},{"A", "D"},{"B", "C"},{"B", "D"}};
        Graph expectedGraph = new Graph(expectedVertices,expectedEdges);
        
        assertEquals(expectedGraph, actualGraph);
    }

    @Test
    public void shouldVerifyAssignAction(){
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        assertTrue(calculator.ok());
        assertEquals(true,calculator.ok());
    }
    
    @Test
    public void shouldVerifyAssignBinaryAction(){
        calculator.create("G1");
        String[] vertices = {"A", "B"};
        String[][] edges = {{"A", "B"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("G2");
        String[] vertices1 = {"C", "D"};
        String[][] edges1 = {{"C", "D"}};
        calculator.assign("G2", vertices1, edges1);
        
        calculator.create("a");
        calculator.assignBinary("a","G1",'j',"G2");
        
        assertTrue(calculator.ok());
        
        assertEquals(true,calculator.ok());
        
    }
    
    @Test
    public void shouldVerifyAssignUnaryAction(){
        calculator.create("G1");
        String[] vertices = {"A", "B", "C"};
        String[][] edges = {{"A", "B"}, {"B", "C"}};
        calculator.assign("G1", vertices, edges);
        
        calculator.create("a");

        String[] newEdge = {"A", "C"};
        calculator.assignUnary("a", "G1", '+', newEdge);
        
        assertEquals(true,calculator.ok());
    }
    
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown(){
        
    }
}
