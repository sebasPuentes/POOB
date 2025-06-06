package domain;    

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;
public class LogPOOBKEMON{
    /**
     * The name of the logger and log file.
     * This value is used both as the logger name and as the prefix for the log file.
     */
    public static String nombre="POOBKEMON";
    
    /**
     * Records an exception to a log file.
     * This method creates or appends to a log file named "POOBKEMON.log" in the current directory.
     * The exception is logged with SEVERE level, including the exception message and stack trace.
     * 
     * @param e The exception to be logged
     */
    public static void record(Exception e){
        try{
            Logger logger=Logger.getLogger(nombre);
            logger.setUseParentHandlers(false);
            FileHandler file=new FileHandler(nombre+".log",true);
            file.setFormatter(new SimpleFormatter());
            logger.addHandler(file);
            logger.log(Level.SEVERE,e.toString(),e);
            file.close();
        }catch (Exception oe){
            oe.printStackTrace();
            System.exit(0);
        }
    }
}