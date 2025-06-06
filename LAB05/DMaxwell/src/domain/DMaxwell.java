package domain;
import java.util.*;
public class DMaxwell {

    private int h = 11;
    private int w = 41;
    private int r = 10;
    private int b = 10;
    private int o = 6;
    private int demon;

    private final int posDemonDefault = 225;
    private final int[] wallDefault = {20,61,102,143,184,225,266,307,348,389,430};
    private final int[] blueDefault = {43,52,139,254,291,343,67,201,228,310};
    private final int[] redDefault = {48,55,126,336,79,112,193,277,326,360};
    private final int[] defaultHoles = {116,129,175,288,356,364};

    private int[] blues;
    private int[] reds;
    private int[] holes;
    private int[] wall;
    private int particlesInHoles;

    /*
     * Constructor DMaxwell
     * Initializes the grid with default values for walls, blue, red particles, 
     * holes, and demon position.
     */
    public DMaxwell() {
        blues = blueDefault.clone();
        reds = redDefault.clone();
        holes = defaultHoles.clone();
        wall = wallDefault.clone();
        demon = posDemonDefault;
        particlesInHoles = 0;
    }

    /*
     * Constructor DMaxwell
     * @param height The height of the grid.
     * @param width The width of the grid.
     * @param numBlue The number of blue particles.
     * @param numRed The number of red particles.
     * @param numHoles The number of holes in the grid.
     * @throws DMaxwellExceptions 
     *
     */
    public DMaxwell(int height, int width, int numBlue, int numRed, int numHoles) throws DMaxwellExceptions {
        if (height < 0 || width < 0 || numBlue < 0 || numRed < 0 || numHoles < 0)
            throw new DMaxwellExceptions(DMaxwellExceptions.ONLY_POSITIVE_DIMENTIONS);
        
        h = height;
        w = width + 1;
        r = numRed;
        b = numBlue;
        o = numHoles;
        checkMidWall(h, w);
        createNewPositions();
    }

    /*
     * Method to move all particles based on the provided direction.
     * @param direction The direction to move particles. Valid values: 'u' (up), 'd' (down), 
     * 'r' (right), 'l' (left).
     */
    public void move(char direction) {
        int[] blues1 = blues.clone();
        int[] reds1 = reds.clone();
        
        ArrayList<Integer> ocupados = new ArrayList<>();
        for (int pos : blues1) ocupados.add(pos);
        for (int pos : reds1) ocupados.add(pos);
    

        for (int i = 0; i < blues1.length; i++) {
            ocupados.remove((Integer) blues1[i]); 
            int pos = positions(blues1[i], direction, ocupados);
            if (verifyHole(pos)) {
                blues1[i] = pos;
                ocupados.add(pos); 
            } else {
                blues1[i] = -1;
            }
        }
    

        for (int i = 0; i < reds1.length; i++) {
            ocupados.remove((Integer) reds1[i]); 
            int pos = positions(reds1[i], direction, ocupados);
            if (verifyHole(pos)) {
                reds1[i] = pos;
                ocupados.add(pos); 
            } else {
                reds1[i] = -1;
            }
        }
    
        blues = removePositions(blues1);
        reds = removePositions(reds1);
        
    }
    

    /*
     * Method to change particle positions based on movement direction.
     * @param num The current position of the particle.
     * @param direccion The direction to move the particle ('u', 'd', 'r', 'l').
     * @return The new position of the particle after movement.
     */
    private int positions(int num, char direccion, ArrayList<Integer> ocupados) {
        int col = num % w;
    
        if (num < 0 || num >= h * w) {
            return num;
        }
    
        int newNum = num;
    
        if (direccion == 'u') {
            if (num >= w) {
                int target = num - w;
                if ((!contains(wall, target) || target == demon) && !ocupados.contains(target)) {
                    newNum = target;
                }
            }
        } else if (direccion == 'd') {
            if (num < (h - 1) * w) {
                int target = num + w;
                if ((!contains(wall, target) || target == demon) && !ocupados.contains(target)) {
                    newNum = target;
                }
            }
        } else if (direccion == 'r') {
            if ((col + 1) < w) {
                int target = num + 1;
                if ((!contains(wall, target) || target == demon) && !ocupados.contains(target)) {
                    newNum = target;
                }
            }
        } else if (direccion == 'l') {
            if (col > 0) {
                int target = num - 1;
                if ((!contains(wall, target) || target == demon) && !ocupados.contains(target)) {
                    newNum = target;
                }
            }
        }
    
        return newNum;
    }
    
    

    /*
     * Method to remove invalid positions 
     * @param array The array of positions.
     * @return A new array with only valid positions
     */
    private int[] removePositions(int[] array) {
        int count = 0;
        for (int i : array) {
            if (i != -1) {
                count++;
            }
        }
        int[] newArray = new int[count];
        int index = 0;
        for (int i : array) {
            if (i != -1) {
                newArray[index++] = i;
            }
        }
        return newArray;
    }

    /*
     * Method to verify if a position is a valid spot (not a hole).
     * @param position The position to check.
     * @return true if the position is valid (not a hole), false otherwise.
     */
    public boolean verifyHole(int position) {
        for (int hole : holes) {
            if (hole == position) {
                return false;
            }
        }
        return true;
    }

    /*
     * Method to create new random positions for blue, red particles, and holes,
     */
    private void createNewPositions() {
        Random random = new Random();
        ArrayList<Integer> wall1 = arrayToArrayList(wall);
        ArrayList<Integer> disponibles = new ArrayList<>();

        for (int i = 0; i < h * w; i++) {
            if (!wall1.contains(i) && i != demon) {
                disponibles.add(i);
            }
        }

        Collections.shuffle(disponibles, random);

        blues = new int[b];
        reds = new int[r];
        holes = new int[o];

        int index = 0;
        for (int i = 0; i < b; i++) {
            blues[i] = disponibles.get(index++);
        }
        for (int i = 0; i < r; i++) {
            reds[i] = disponibles.get(index++);
        }
        for (int i = 0; i < o; i++) {
            holes[i] = disponibles.get(index++);
        }
    }

    /*
     * Method to check and create the mid wall of the grid.
     * @param h The height of the grid.
     * @param wi The width of the grid.
     */
    private void checkMidWall(int h, int wi) {
        int widthBoard = (wi - 1) / 2;
        int salto = (2 * widthBoard) + 1;
        wall = new int[h];
        int inicio = widthBoard;
        for (int fila = 0; fila < h; fila++) {
            wall[fila] = inicio + fila * salto;
        }
        demon = wall[h / 2];
    }

    /*
     * Method to convert an array to an ArrayList.
     * @param array The array to convert.
     * @return An ArrayList containing all elements of the array.
     */
    public static ArrayList<Integer> arrayToArrayList(int[] array) {
        ArrayList<Integer> lista = new ArrayList<>();
        for (int num : array) {
            lista.add(num);
        }
        return lista;
    }

    /*
     * Method to consult the information of the particles and their current state.
     * @return An array representing the current state of the particles.
     */
    public int[] consult() {
        int[] particles = new int[5];
        return particles;
    }

    /*
     * Method to get the information of the container.
     * @return A 2D array containing blue, red, hole, and wall positions.
     */
    public int[][] container() {
        return new int[][] { blues, reds, holes, wall };
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) return true;
        }
        return false;
    }
}
