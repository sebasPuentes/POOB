package domain;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.io.FileReader;

/*No olviden adicionar la documentacion*/
public class City implements Serializable {
    static private int SIZE = 25;
    private Item[][] locations;

    /**
     * Constructor of city instance.
     */
    public City() {
        locations = new Item[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                locations[r][c] = null;
            }
        }
        someItems();
    }

    /**
     * Size of the matrix.
     *
     * @return SIZE.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Get the item in a specific position.
     *
     * @return Item.
     */
    public Item getItem(int r, int c) {
        return locations[r][c];
    }

    /**
     * Sets an Item in a specific position.
     */
    public void setItem(int r, int c, Item e) {
        locations[r][c] = e;
    }

    /**
     * Calling constructors for creating instances of walkers, person, ... etc.
     */
    public void someItems() {
        Person adan = new Person(this,10,10);
        Person eva = new Person(this,15,15);
        Walker messner = new Walker(this,18,16);
        Walker kukuczka = new Walker(this,21,10);
        Light alarm = new Light(this,0,0);
        Light alert = new Light(this,0,24);
        Katalan lopez = new Katalan(this,18,13);
        Katalan puentes = new Katalan(this,20,10);
        Identifier sebastian = new Identifier(this,24,14);
        Identifier julian = new Identifier(this,0,10);
        Schelling p1 =new Schelling(this,14,23);
        Schelling p2 = new Schelling(this,15,23);
        Person zeus =new Person(this,15,22);
        Person ares =new Person(this,14,24);
    }


    /**
     * Checks the number of neighborEquals around an item.
     *
     * @param r row.
     * @return num number of neighborEquals.
     * @parm c column.
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inLocations(r, c) && locations[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inLocations(r + dr, c + dc) &&
                            (locations[r + dr][c + dc] != null) && (locations[r][c].getClass() == locations[r + dr][c + dc].getClass()))
                        num++;
                }
            }
        }
        return num;
    }

    /**
     * Checks the number of neighborEquals around an item.
     *
     * @param r row.
     * @return num number of neighborEquals.
     * @parm c column.
     */
    public int neighbors(int r, int c) {
        int num = 0;
        if (inLocations(r, c) && locations[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inLocations(r + dr, c + dc) &&
                            (locations[r + dr][c + dc] != null) && (locations[r][c].getClass().equals(locations[r + dr][c + dc].getClass())))
                        num++;
                }
            }
        }
        return num;
    }

    /**
     * Changes the position of an item.
     */
    public void changeItemPosition(int row, int column, int newRow, int newColumn, Item item) {
        if (inLocations(newRow, newColumn) && locations[row][column] == item && locations[newRow][newColumn] == null) {
            locations[row][column] = null;
            locations[newRow][newColumn] = item;
        }
    }

    /**
     * Verifys if there is an item in a specific position.
     *
     * @return boolean.
     */
    public boolean isEmpty(int r, int c) {
        return (inLocations(r, c) && locations[r][c] == null);
    }

    /**
     * Verifys if an specific position exists inside the board.
     *
     * @return boolean.
     */
    public boolean inLocations(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Logic for tic tac button where items change and decide.
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (locations[r][c] != null) {
                    locations[r][c].change();
                    locations[r][c].decide();
                }
            }
        }
    }

    //-----------------------NORMAL-----------------------
    public City open(File file) throws CityException {
        throw new CityException("Opción open en construcción. Archivo " + file.getName());
    }

    public void save(File file) throws CityException {
        throw new CityException("Opción save en construcción. Archivo " + file.getName());
    }

    public void importFile(File file) throws CityException {
        throw new CityException("Opción import en construcción. Archivo " + file.getName());
    }

    public void export(File file) throws CityException {
        throw new CityException("Opción export en construcción. Archivo " + file.getName());
    }

    //-----------------------00-----------------------
    //Open Con Parametro Archivo
    public City open00(File file) throws CityException {
        if (!file.exists()) {
            System.out.println("No se encontro el archivo");
            return null;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object deserialize = in.readObject();
            if (deserialize instanceof City) {
                City newCity = (City) deserialize;
                return newCity;
            } else {
                System.out.println("Error al deserializar el archivo");
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new CityException("Error al abrir el archivo " + file.getName());
        }
    }
    //Save Con Parametro Archivo
    public void save00(File file) throws CityException{
        try {
            ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream(file)));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo");
        }
    }
    //---------------------------------01-----------------------
    public City open01(File file) throws CityException {
        if (file == null) {
            throw new CityException("El archivo especificado es nulo");
        }
        if (!file.exists()) {
            throw new CityException("No se encontró el archivo: " + file.getAbsolutePath());
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Object deserialize = in.readObject();
            if (deserialize instanceof City) {
                return (City) deserialize;
            } else {
                throw new CityException("El archivo no contiene un objeto de tipo City válido");
            }
        } catch (FileNotFoundException e) {
            throw new CityException("No se pudo encontrar el archivo: " + file.getName());
        } catch (ClassNotFoundException e) {
            throw new CityException("No se encontró la definición de la clase al deserializar");
        } catch (IOException e) {
            throw new CityException("Error de entrada/salida al leer el archivo: " + file.getName());
        }
    }

    public void save01(File file) throws CityException{
        if (file == null) {
            throw new CityException("El archivo especificado es nulo");
        }
        if (!file.exists()) {
            throw new CityException("El archivo especificado no existe");
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream((new FileOutputStream(file)));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            throw new CityException("Error de entrada/salida al escribir el archivo: " + file.getName());
        }
    }
    //------------------------00-----------------------
    //Importar Archivo 00
    public void import00(File file) throws CityException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 3) continue;
                String type = parts[0];
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                Item item = null;
                if ("Person".equals(type)) {
                    item = new Person(this, row, col);
                } else if ("Walker".equals(type)) {
                    item = new Walker(this, row, col);
                } else if ("Light".equals(type)) {
                    item = new Light(this, row, col);
                } else if ("Katalan".equals(type)) {
                    item = new Katalan(this, row, col);
                } else if ("Identifier".equals(type)) {
                    item = new Identifier(this, row, col);
                } else if ("Schelling".equals(type)) {
                    item = new Schelling(this, row, col);
                } else {
                    System.out.println("Tipo desconocido: " + type);
                }
                if (item != null) {
                    locations[row][col] = item;
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException("Error al importar el archivo " + file.getName());
        }
    }
    //Exportar Archivo 00
    public void export00(File file) throws CityException {
        try (FileWriter writer = new FileWriter(file)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        writer.write(type + " " + r + " " + c + "\n");
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException("No se pudo exportar el archivo: " + file.getName());
        }
    }

    //-----------------------01-----------------------
    //Importar Archivo 01
    public void import01(File file) throws CityException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 3) continue;
                String type = parts[0];
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);
                Item item = null;
                if ("Person".equals(type)) {
                    item = new Person(this, row, col);
                } else if ("Walker".equals(type)) {
                    item = new Walker(this, row, col);
                } else if ("Light".equals(type)) {
                    item = new Light(this, row, col);
                } else if ("Katalan".equals(type)) {
                    item = new Katalan(this, row, col);
                } else if ("Identifier".equals(type)) {
                    item = new Identifier(this, row, col);
                } else if ("Schelling".equals(type)) {
                    item = new Schelling(this, row, col);
                } else {
                    throw new CityException("Tipo desconocido: " + type);
                }
                if (item != null) {
                    locations[row][col] = item;
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException("Error al importar el archivo " + file.getName());
        }
    }
    //Exportar Archivo 01
    public void export01(File file) throws CityException {
        try (FileWriter writer = new FileWriter(file)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        writer.write(type + " " + r + " " + c + "\n");
                    }else{
                        throw new CityException("Error al exportar el archivo " + file.getName());
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException("No se pudo exportar el archivo: " + file.getName());
        }
    }
    //-------------------------------02-----------------------
    public void import02(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.FILE_NOT_FOUND + ": File cannot be null");
        }
        StringBuilder errorLog = new StringBuilder();
        int errorLine = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorLine++;
                line = line.trim();
                if (line.trim().isEmpty()) continue;

                String[] parts = line.trim().split(" ");

                if (parts.length != 3){
                    String errorMessage = String.format("Error in line %d: invalid format '%s'%n", errorLine, line);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                String type = parts[0];

                if(!isValidType(type)) {
                    String errorMessage = String.format("Error in line %d: invalid type '%s'%n", errorLine, type);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }
                int row, col;

                try {
                    row = Integer.parseInt(parts[1]);
                    col = Integer.parseInt(parts[2]);
                }catch (NumberFormatException e) {
                    String errorMessage = String.format("Error in line %d: invalid number format '%s %s' '%n", errorLine, parts[1], parts[2]);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                if(!inLocations(row, col)) {
                    errorLog.append(String.format("Error in line %d: Coordinates out of bounds: row %d, column %d%n", errorLine, row, col));
                    throw new CityException("Coordinates out of bounds: row " + row + ", column " + col);
                }

                if (locations[row][col] != null) {
                    errorLog.append(String.format("Error in line %d: the position (%d, %d) is occupied%n", errorLine, row, col));
                    throw new CityException("Position already occupied: row " + row + ", column " + col);
                }

                try{
                    Item item = switch (type) {
                        case "Person" -> new Person(this, row, col);
                        case "Walker" -> new Walker(this, row, col);
                        case "Katalan" -> new Katalan(this, row, col);
                        case "Identifier" -> new Identifier(this, row, col);
                        case "Schelling" -> new Schelling(this, row, col);
                        case "Light" -> new Light(this, row, col);
                        default -> throw new CityException("Unknown type: " + type);
                    };
                    locations[row][col] = item;
                } catch (NumberFormatException e) {
                    errorLog.append(String.format("Error in line %d: %s%n", errorLine, e.getMessage()));
                    throw new CityException("Invalid number format in line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException(CityException.IMPORT_ERROR + ": " + fileName.getName());
        }
    }

    private boolean isValidType(String type) {
        return switch (type){
            case "Person", "Walker", "Katalan", "Identifier", "Schelling", "Light" -> true;
            default -> false;
        };
    }
    //Exportar Archivo 02
    public void export02(File file) throws CityException {
        try (FileWriter writer = new FileWriter(file)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        writer.write(type + " " + r + " " + c + "\n");
                    }else{
                        throw new CityException("Error al exportar el archivo " + file.getName());
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException("No se pudo exportar el archivo: " + file.getName());
        }
    }

    //-----------------------03 (BONO)-----------------------
    public void import03(File fileName) throws CityException {
        if(fileName == null) {
            throw new CityException(CityException.FILE_NOT_FOUND + ": File cannot be null");
        }
        StringBuilder errorLog = new StringBuilder();
        int errorLine = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorLine++;
                line = line.trim();
                if (line.trim().isEmpty()) continue;

                String[] parts = line.trim().split(" ");

                if (parts.length != 3){
                    String errorMessage = String.format("Error in line %d: invalid format '%s'%n", errorLine, line);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                String type = parts[0];
                int row, col;

                try {
                    row = Integer.parseInt(parts[1]);
                    col = Integer.parseInt(parts[2]);
                }catch (NumberFormatException e) {
                    String errorMessage = String.format("Error in line %d: invalid number format '%s %s' '%n", errorLine, parts[1], parts[2]);
                    errorLog.append(errorMessage);
                    throw new CityException(errorMessage);
                }

                if(!inLocations(row, col)) {
                    errorLog.append(String.format("Error in line %d: Coordinates out of bounds: row %d, column %d%n", errorLine, row, col));
                    throw new CityException("Coordinates out of bounds: row " + row + ", column " + col);
                }

                if (locations[row][col] != null) {
                    errorLog.append(String.format("Error in line %d: the position (%d, %d) is occupied%n", errorLine, row, col));
                    throw new CityException("Position already occupied: row " + row + ", column " + col);
                }

                try{
                    Class<?> itemClass = Class.forName("domain." + type);
                    Item item = (Item) itemClass.getDeclaredConstructor(City.class, int.class, int.class).newInstance(this, row, col);
                    locations[row][col] = item;

                } catch (ClassNotFoundException e) {
                    errorLog.append(String.format("Error in line %d: %s%n", errorLine, e.getMessage()));
                    throw new CityException("Invalid number format in line: " + line);
                } catch (ReflectiveOperationException e) {
                    String errorMessage = String.format("Error en línea %d: no se pudo crear objeto de tipo '%s'", errorLine, type);
                    errorLog.append(errorMessage).append("\n");
                    throw new CityException(errorMessage);
                }
            }
        } catch (IOException | NumberFormatException e) {
            throw new CityException(CityException.IMPORT_ERROR + ": " + fileName.getName());
        }
    }

    public void export03(File file) throws CityException {
        try (FileWriter writer = new FileWriter(file)) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    Item item = locations[r][c];
                    if (item != null) {
                        String type = item.getClass().getSimpleName();
                        writer.write(type + " " + r + " " + c + "\n");
                    }else{
                        throw new CityException("Error al exportar el archivo " + file.getName());
                    }
                }
            }
        } catch (IOException e) {
            throw new CityException("No se pudo exportar el archivo: " + file.getName());
        }
    }



}