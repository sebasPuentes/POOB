package Maxwell;
import shapes.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * MaxwellContainer.
 * 
 * @author (Juan Sebastian Puentes Julio y Julian Camilo Lopez Barrero) 
 * @version (4/03/2025)
 */
public class MaxwellContainer{
    private Rectangle backgroundBoard = new Rectangle();
    private Rectangle leftBoard;
    private Rectangle rightBoard;
    private Particles particle;
    private Hole hole;
    private ArrayList<Demon> demons = new ArrayList<>();
    private ArrayList<Particles> particlesAvailables = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();
    private boolean lastOperation = false;
    private boolean isVisible = false;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private int size = 15;
    /**
     * Constructor de MaxwellContainer.
     * @param height
     * @param weight
     */
    public MaxwellContainer(int h, int w){
        backgroundBoard = new Rectangle(h+40,2*w+40);
        rightBoard = new Rectangle(h,w);
        leftBoard = new Rectangle(h,w);
        
        backgroundBoard.moveTo(380-w,380-h);
        backgroundBoard.changeColor("black");
        leftBoard.moveTo(390-w,400-h);
        rightBoard.moveTo(410,400-h);
        
        width = w;
        height = h;
    }
    
    public MaxwellContainer(){
        int h =200;
        int w=300;
        
        backgroundBoard = new Rectangle(h+40,2*w+40);
        rightBoard = new Rectangle(h,w);
        leftBoard = new Rectangle(h,w);
        
        backgroundBoard.moveTo(380-w,380-h);
        backgroundBoard.changeColor("black");
        leftBoard.moveTo(390-w,400-h);
        rightBoard.moveTo(410,400-h);
        
        width = w;
        height = h;
    }
    
    /**
     * Constructor of MaxwellContainer with multiple parameters
     * @param h height of the container
     * @param w width of the container.
     * @param d number of demons
     * @param b number of blue balls
     * @param r number of red balls
     * @param particles array of particles
     * Our board min X and Y is 70 15.
     */
    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particles) {
        if(h<0) lastOperation = false;
        backgroundBoard = new Rectangle(h+40,2*w+40);
        rightBoard = new Rectangle(h,w);
        leftBoard = new Rectangle(h,w);
        
        backgroundBoard.moveTo(380-w,380-h);
        backgroundBoard.changeColor("black");
        leftBoard.moveTo(390-w,400-h);
        rightBoard.moveTo(410,400-h);
        
        width = w;
        height = h;
        lastOperation = true;
        addDemon(d);
        int total = b+r;
        for (int i = 0; i < particles.length; i++) {
            if (total <= r) {
                addParticle("blue", false, particles[i][0], particles[i][1], particles[i][2], particles[i][3]);
                continue;
            }
            addParticle("red", true, particles[i][0], particles[i][1], particles[i][2], particles[i][3]);
            total--;
        }
    }
    
    /**
     * Add particles.
     */
    public void addParticles(){
        addParticle("Coloreable","red",true,-210,90,3,10); 
        addParticle("Ephemeral","blue",false,100,50,3,15);
        addParticle("Flying","red",true,-100,100,4,10); 
        addParticle("Rotator","green",false,110,50,2,5); 
        addParticle("Flying","red",false,-90,90,10,10);
        addParticle("Flying","pink",false,200,15,10,10); 
    }
    
    /**
     * Method to add an specific demon in an specific Vertical Position.
     * @param d Vertical Distance
     */
    public void addDemon(String type, int d){
        if (0 <= d && d <= height - 20) {
            for (Demon existingDemon : demons) {
                int y = convertionBoardToCanvas(0,d).get(1) - 20 ;
                if (Math.abs(existingDemon.getY() - y) < 20) { 
                    JOptionPane.showMessageDialog(null, "There is already a Demon in that position");
                    lastOperation = false;
                    return;
                }
            }
            int y = convertionBoardToCanvas(0,d).get(1) - 20 ;
            int x = convertionBoardToCanvas(0,d).get(0);
            if(type == "Blue"){
                    Blue blueDemon = new Blue(x,y);
                    blueDemon.moveDemonTo(x,y);
                    demons.add(blueDemon);
                    lastOperation = true;
            }else if( type == "Weak"){
                    Weak weakDemon = new Weak(x,y);
                    weakDemon.moveDemonTo(x,y);
                    demons.add(weakDemon);
                    lastOperation = true;
            }else if(type == "Normal"){
                addDemon(d);
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "Invalid Range");
            lastOperation = false;
        }
    }
    
    /**
     * Method to add a demon in an specific Vertical Position.
     * @param d Vertical Distance
     */
    public void addDemon(int d) {
        if (0 <= d && d <= height - 20) {
            for (Demon existingDemon : demons) {
                int y = convertionBoardToCanvas(0,d).get(1) - 20 ;
                if (Math.abs(existingDemon.getY() - y) < 20) { 
                    JOptionPane.showMessageDialog(null, "There is already a Demon in that position");
                    lastOperation = false;
                    return;
                }
            }
            int y = convertionBoardToCanvas(0,d).get(1) - 20 ;
            int x = convertionBoardToCanvas(0,d).get(0);
            Demon demon = new Demon(x,y);
            demon.moveDemonTo(x,y);
            demons.add(demon);
            lastOperation = true;
        }  
        else {
            JOptionPane.showMessageDialog(null, "Invalid Range");
            lastOperation = false;
        }
    } 
    
    /**
     * Method to del a demon in an specific Vertical Position
     * @param d Vertical Distance
     */
    public void delDemon(int d) {
        int y = convertionBoardToCanvas(0,d).get(1) - 20 ;
        for (int i = 0; i < demons.size(); i++) {
            if (demons.get(i).getY() == y) {
                demons.get(i).makeInvisibleDemon();
                demons.remove(i);
                lastOperation = true;
                i--; //Indices No repetidos
            } else{
                lastOperation = false;
            }
        }
        
    }
    
    /**
     * Add particle.
     * @param String color.
     * @param boolean isRed.
     * @param int px.
     * @param int py
     * @param int vx.
     * @param int vy.
     */
    public void addParticle(String color,boolean isRed, int px, int py, int vx, int vy){
        if (isInLeft(px,py)){
            createInLeft("Normal",color,isRed, px,py,vx,vy);
            lastOperation = true;
        }
        else if (isInRight(px,py)){
            createInRight("Normal",color, isRed, px,py,vx,vy);
            lastOperation = true;
        }
    }
    
    /**
     * Add particle.
     * @param String type.
     * @param String color.
     * @param boolean isRed.
     * @param int px.
     * @param int py
     * @param int vx.
     * @param int vy.
     */
    public void addParticle(String type,String color,boolean isRed, int px, int py, int vx, int vy){
        if (isInLeft(px,py)){
            createInLeft(type,color,isRed, px,py,vx,vy);
            lastOperation = true;
        }
        else if (isInRight(px,py)){
            createInRight(type,color, isRed, px,py,vx,vy);
            lastOperation = true;
        }
    }
    
    /**
     * Method to delete a particle of an specific color
     * @param color Color of the particle.
     */
    public void delParticle(String color) {
        for (int i = 0; i < particlesAvailables.size(); i++) {
            Particles p = particlesAvailables.get(i);
            if (p.getColor().equals(color)) {
                p.makeInvisibleParticle();  
                particlesAvailables.remove(i);
                lastOperation = true;
                return;  
            }else{
                JOptionPane.showMessageDialog(null, "Not Particle Found.");
                lastOperation = false;
            }
        }
    }
    
    /**
     * Moves particles and verifys holes and demons rules.
     */
    public void moveParticles() {
        int maxXLeftBoard = leftBoard.getX() + leftBoard.getWidth() - size;
        int minX = leftBoard.getX();
        int minXRightBoard = rightBoard.getX();
        int maxXRightBoard = rightBoard.getX() + rightBoard.getWidth() - size; 
        int minY = leftBoard.getY();
        int maxY = leftBoard.getY() + leftBoard.getHeight() - size;
        ArrayList<Particles> toRemove = new ArrayList<>();
        for (Particles p : particlesAvailables) {
            boolean leftSide = isInLeftSide(p,leftBoard); 
            p.move();
            p.checkCollision(leftSide,minX, maxXLeftBoard, minXRightBoard, maxXRightBoard, minY, maxY);
            for (Demon d : demons) {
                if(isInLeftSide(p,leftBoard)){
                    if(d.isInLeft(p)){
                        p.setXPositionParticle(minXRightBoard); 
                    }
                }else{
                    if(d.isInRight(p)) {
                        p.setXPositionParticle(maxXLeftBoard);
                    }
                }
            }
            
            for (Hole h : holes) {
                boolean isInLeft = isInLeft(h,leftBoard);
                h.move();
                h.checkHoleCollision(isInLeft,minX,maxXLeftBoard,minXRightBoard,maxXRightBoard,minY,maxY);
                if (p.isInTheSamePosition(h) && h.canEat()) {
                    h.eatParticle();
                    toRemove.add(p);
                }else if(!h.canEat()){
                    h.makeInvisibleHole();
                }
            }
        }
        particlesAvailables.removeAll(toRemove);
        for (Particles p : toRemove) {
            p.makeInvisibleParticle();
        }
    }  
    
    /**
     * Checks if the particle is in the left Side.
     */
    private boolean isInLeftSide(Particles p,Rectangle leftBoard){
        return p.getX() < leftBoard.getX() + leftBoard.getWidth();
    }
    
    /**
     * Checks if the hole is in the left Side.
     */
    private boolean isInLeft(Hole h,Rectangle leftBoard){
        return h.getX() < leftBoard.getX() + leftBoard.getWidth();
    }

    /**
     * Method to add a hole with his position and nunber of particles to being eaten.
     * @param px position horizontal of the hole
     * @param py position vertical of the hole
     * @param particle number of particles to be eating
     */
    public void addHole(int px, int py, int particles){
        int x = convertionBoardToCanvas(px,py).get(0);
        int y = convertionBoardToCanvas(px,py).get(1);
        if (isInBoard(x,y)){ 
            Hole hole = new Hole(x,y,20,20,particles);
            holes.add(hole);
            lastOperation = true;
        }else{
            lastOperation = false;            
        }
    }
    
    /**
     * Method to add an specific hole with his position and number of particles to being eaten.
     * @param px position horizontal of the hole
     * @param py position vertical of the hole
     * @param particle number of particles to be eating
     */
    public void addHole(String type,int px, int py, int particles){
        int x = convertionBoardToCanvas(px,py).get(0);
        int y = convertionBoardToCanvas(px,py).get(1);
        if(type == "Movil"){
            if (isInBoard(x,y)){ 
                Movil movilHole = new Movil(x,y,20,20,particles);
                holes.add(movilHole);
                lastOperation = true;
            }else{
                lastOperation = false;            
            }
        }else if(type == "Normal"){
            addHole(px,py,particles);
        }
    }

    /**
     * Verifys if hole is inside boards limit.
     * @param px X position of the hole.
     * @param px Y position of the hole.
     */
    private boolean isInBoard(int px,int py){
        return (leftBoard.getY() <= py && py <= leftBoard.getY() + leftBoard.getHeight() - 20 &&  
                leftBoard.getX() <= px && px <= leftBoard.getX()+ leftBoard.getWidth() - 20 || 
                rightBoard.getX() <= px && px <= rightBoard.getX() + rightBoard.getWidth());
    }
    
    /**
     * Method to start the game
     * @param ticks number of ticks
     * @return void
     */
    public void start(int ticks) {
        for (int i = 0; i < ticks; i++) {
            if(isGoal()){
                JOptionPane.showMessageDialog(null, "The Game Has Ended.");
                finish();
                return;
            }
            moveParticles();
        }
    }
    
    /**
     * Method to verify if all of red an blue particles crossed demon.
     * @return boolean.
     */
    public boolean isGoal(){
        int minXLeftBoard = leftBoard.getX();
        for(Particles particle: particlesAvailables){
            if(particle.getColor().equals("red") && particle.getX() > rightBoard.getX() && 
                particle.getX() < rightBoard.getX() + rightBoard.getWidth()){
                continue;
            }else if(particle.getColor().equals("blue") && particle.getX() < leftBoard.getX() + leftBoard.getWidth() 
                    && particle.getX() > minXLeftBoard){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }
    
    /**
     * Method to see the position x and y of the particles
     * @return int[][] particles.
     */
    public int[][] particles() {
        int[][] positions = new int[particlesAvailables.size()][2];
        for (int i = 0; i < particlesAvailables.size(); i++) {
            Particles p = particlesAvailables.get(i);
            positions[i][0] = p.getX();
            positions[i][1] = p.getY();
        }
        sortMatrixParticles(positions);
        return positions;
    }

    /**
     * Method to see the position x and y of the particles of an specific type.
     * @return int[][] particles.
     */
    public int[][] particles(String type){
        ArrayList<Particles> specificParticle = new ArrayList<>();
        for(Particles p: particlesAvailables){
            if(p.getType().equals(type)){
                specificParticle.add(p);
            }
        }
        int[][] positions = new int[specificParticle.size()][2];
        for (int i = 0; i < specificParticle.size(); i++) {
            Particles p = particlesAvailables.get(i);
            positions[i][0] = p.getX();
            positions[i][1] = p.getY();
        }
        sortMatrixParticles(positions);
        return positions;
    }
    
    /**
     * Auxiliar method for organising particles positions and velocitys, also for holes.
     * @param positions.
     */
    private void sortMatrixParticles(int[][] positions){
        for (int i = 0; i < positions.length - 1; i++) {
            for (int j = 0; j < positions.length - 1 - i; j++) {
                if (positions[j][0] > positions[j + 1][0]) {
                    int[] aux = positions[j];
                    positions[j] = positions[j + 1];
                    positions[j + 1] = aux;
                }
            }
        }
    }
    
    /**
     * Method to see the position x and y of the holes
     * @return positions.
     */
    public int[][] holes() {
        int[][] positions = new int[holes.size()][2];
        for (int i = 0; i < holes.size(); i++) {
            Hole h = holes.get(i);
            positions[i][0] = h.getX();
            positions[i][1] = h.getY();
        }
        sortMatrixParticles(positions);
        return positions;
    }
    
    /**
     * Method to see the position x and y of the holes of an specific type.
     * @return positions.
     */
    public int[][] holes(String type){
        ArrayList<Hole> specificHoles = new ArrayList<>();
        for(Hole h: holes){
            if(h.getType().equals(type)){
                specificHoles.add(h);
            }
        }
        int[][] positions = new int[specificHoles.size()][2];
        for (int i = 0; i < specificHoles.size(); i++) {
            Hole h = holes.get(i);
            positions[i][0] = h.getX();
            positions[i][1] = h.getY();
        }
        sortMatrixParticles(positions);
        return positions;
    }
    
    /**
     * Method to see the position of the Demons
     * @return array of the demons
     */
    public int[] demons(){
        int[] dataDemons = new int[demons.size()];
        for(int i = 0; i < demons.size(); i++){
            dataDemons[i] = demons.get(i).getX();
        }
        return dataDemons;
    }
    
    /**
     * Method to see the position of the Demons of an specific position of an specific demon.
     * @param String type.
     * @return array of the demons
     */
    public int[] demons(String type){
        ArrayList<Demon> specificDemons = new ArrayList<>();
        for(Demon d : demons){
            if(d.getType().equals(type)){
                specificDemons.add(d);
            }
        }
        int[] dataDemons = new int[specificDemons.size()];
        for(int i = 0; i < specificDemons.size(); i++){
            dataDemons[i] = demons.get(i).getY();
        }
        return dataDemons;
    }
    
    /***
     * Method to Consult The Status Of The Holes
     */
    public void consultHoles(){
        for(Hole h : holes){
            System.out.println("Holes Status:\nPos X: " + h.getX() + "\nPos Y: " + h.getY() 
            +"\nEaten Particles: " + h.getEatenParticles());
        } 
    }
    
    /***
     * Method to Consult The Status Of The Particles
     */
    public void consultParticles(){
        for(Particles p : particlesAvailables){
            System.out.println("Particle Status:\nPos X: " + p.getX()+ " " +"Velocity X: " + p.getVx() + 
                                "\nPos Y: " + p.getY() + " " +"Velocity Y: "+ p.getVy());
        }
    }
    
    /**
     * Method to make visible all the game 
     * 
     */
    public void makeVisible(){
        makeVisibleBoards();
        for(Demon demon: demons){
            demon.makeVisibleDemon();   
        }
        
        for(Hole hole: holes){
            hole.makeVisibleHole();
        }
        
        for(Particles particle : particlesAvailables){
            particle.makeVisibleParticle();
        }
        isVisible = true;
    }
    
    /**
     * Method to make Invisible all the game .
     * 
     */
    public void makeinVisible(){
        makeInVisibleBoards();
        for(Demon demon: demons){
            demon.makeInvisibleDemon();   
        }
        
        for(Hole hole: holes){
            hole.makeInvisibleHole();
        }
        
        for(Particles particle : particlesAvailables){
            particle.makeInvisibleParticle();
        }
        isVisible = false;
    }
    
    /**
     * Method to do visible leftBoard, rightBoard and backgroundBoard.
     */
    private void makeVisibleBoards(){
        backgroundBoard.makeVisible();
        leftBoard.makeVisible();
        rightBoard.makeVisible();
    }
    
    /**
     * Method to do Invisible leftBoard, rightBoard and backgroundBoard.
     */
    private void makeInVisibleBoards(){
        backgroundBoard.makeInvisible();
        leftBoard.makeInvisible();
        rightBoard.makeInvisible();
    }
    
    /**
     * Method to end the game.
     */
    public void finish(){
        makeinVisible();
        particlesAvailables.clear();
        holes.clear();
        demons.clear();
        Canvas.getCanvas().close();
    }
    
    /**
     * Shows the Center of the canvas.
     */
     public void showCenter(){
      rightBoard.showCenter();
      leftBoard.showCenter();
    }
    
    /**
     * Converts canvas coordinates to board coordinates.
     * @param int x.
     * @param int y.
     * @return ArrayList<Integer> convertions.
     */
    public ArrayList<Integer> convertionCanvasToBoard(int x, int y){
        ArrayList<Integer> convertions = new ArrayList<>();
        int chamberXPos = leftBoard.getX() + width;
        int chamberYPos = leftBoard.getY() + height;
        int positionXEsperada = x - chamberXPos;
        int positionYEsperada = chamberYPos - y;
        convertions.add(positionXEsperada);
        convertions.add(positionYEsperada);
        return convertions;
    }
    
    /**
     * Converts board coordinates to canvas coordinates.
     * @param int x.
     * @param int y.
     * @return ArrayList<Integer> convertions.
     */
    public ArrayList<Integer> convertionBoardToCanvas(int x, int y){
        ArrayList<Integer> convertions = new ArrayList<>();
        int chamberXPos = leftBoard.getX() + width;
        int chamberYPos = leftBoard.getY() + height;
        int positionXEsperada = x + chamberXPos;
        int positionYEsperada = chamberYPos - y;
        convertions.add(positionXEsperada);
        convertions.add(positionYEsperada);
        return convertions;
    }
    
    /**
     * Creates the particles if they are in the left side of the chamber.
     * @param String type.
     * @param String color.
     * @param Boolean isRed.
     * @param int px.
     * @param int py.
     * @param int vx.
     * @param int vy.
     */
    private void createInLeft(String type,String color,boolean isRed,int px, int py, int vx, int vy){
        int chamberXPos = leftBoard.getX() + width + 10;
        int chamberYPos = leftBoard.getY() + height;
        if(type.equals(Particles.EPHEMERAL)){
            Ephemeral e = new Ephemeral(color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                e.makeVisibleParticle();
            }
            particlesAvailables.add(e);  
        }
        if(type.equals(Particles.FLYING)){
            Flying f = new Flying (color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                f.makeVisibleParticle();
            }
            particlesAvailables.add(f);
        }
        if(type.equals(Particles.ROTATOR)){
            Rotator r = new Rotator (color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                r.makeVisibleParticle();
            }
            particlesAvailables.add(r);
        }
        if(type.equals(Particles.NORMAL)){
            Normal n = new Normal(color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                n.makeVisibleParticle();
            }
            particlesAvailables.add(n);
        }
        if(type.equals(Particles.COLOREABLE)){
            Coloreable c = new Coloreable(color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                c.makeVisibleParticle();
            }
            particlesAvailables.add(c);
        }
    }
    
    /**
     * Creates the particles if they are in the right side of the chamber.
     * @param String type.
     * @param String color.
     * @param Boolean isRed.
     * @param int px.
     * @param int py.
     * @param int vx.
     * @param int vy.
     */
    private void createInRight(String type, String color,boolean isRed,int px, int py, int vx, int vy){
        int chamberXPos = rightBoard.getX();
        int chamberYPos = rightBoard.getY() + height;
        if(type.equals(Particles.EPHEMERAL)){
            Ephemeral e = new Ephemeral(color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                e.makeVisibleParticle();
            }
            particlesAvailables.add(e);  
        }
        if(type.equals(Particles.FLYING)){
            Flying f = new Flying (color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                f.makeVisibleParticle();
            }
            particlesAvailables.add(f);
        }
        if(type.equals(Particles.ROTATOR)){
            Rotator r = new Rotator (color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                r.makeVisibleParticle();
            }
            particlesAvailables.add(r);
        }
        if(type.equals(Particles.NORMAL)){
            Normal n = new Normal(color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                n.makeVisibleParticle();
            }
            particlesAvailables.add(n);
        }
        if(type.equals(Particles.COLOREABLE)){
            Coloreable c = new Coloreable(color, px + chamberXPos, chamberYPos - py, vx, vy,isRed);
            if(isVisible){
                c.makeVisibleParticle();
            }
            particlesAvailables.add(c);
        }
    }
    
    /**
     * Checks if the particle is in the left side.
     * @param int px.
     * @param int py.
     * @return boolean.
     */
    private boolean isInLeft(int px, int py){
        int auxXMin= -width;
        int auxXMax=0;         
        int auxYMin=0;
        int auxYMax= height; 
        return (px> auxXMin  && auxXMax>px  && py>= auxYMin  && py <= auxYMax  );
    }
    
    /**
     * Checks if the particle is in the right side.
     * @param int px.
     * @param int py.
     * @return boolean.
     */
    private boolean isInRight(int px, int py){
        int auxXMin=0;
        int auxXMax=width; 
        int auxYMin=0;
        int auxYMax= height; 
        return (px> auxXMin  && auxXMax>px  && py>auxYMin  && py< auxYMax);
    }
    
    /**
     * Verify if the last operation is valid or not.
     * @return Boolean
     */
    public boolean ok(){
        return lastOperation;
    }
    
}


