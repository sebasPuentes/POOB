package Test;
import domain.*;
import static org.junit.Assert.*;

import java.beans.Transient;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Plan15Test {

    @Test
    public void shouldAddCoreAndCourseSuccessfully(){
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("COURSE1", "Curso 1", "4", "3");
            plan.addCourse("COURSE2", "Curso 2", "3", "2");
            plan.addCore("NFE", "Nucleo De Formacion Electivo", "40", "COURSE1\nCOURSE2");
            Unit addedCore = plan.consult("NFE");
            assertEquals("Nucleo De Formacion Electivo", addedCore.name()); 
        }catch(Plan15Exception e){
            
        }
    }

    @Test
    public void shouldAddSecondCoreAndCourseSuccessfully(){
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("POOB", "Programacion Orientada A Objetos", "4", "3");
            plan.addCourse("ECDI", "Ecuaciones Diferenciales", "3", "2");
            plan.addCore("PLF", "PUENTES LOPEZ FORMACION", "40", "POOB\nECDI");
            Unit addedCore = plan.consult("PLF");
            assertEquals("PUENTES LOPEZ FORMACION", addedCore.name());
        }catch(Plan15Exception e){
            
        }
    }   

    @Test
    public void shouldDoToString() {
        try{
            Plan15 plan = new Plan15();
            plan.addCourse("IPRO", "Introduccion a la programacion", "4", "4");
    
            //System.out.println("Unidades actuales: " + plan.numberUnits());
            //System.out.println("Salida real:\n" + plan.toString());
    
            assertEquals("7 unidades\n" +
                        ">PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        ">DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        ">MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">NFCC: Nucleo formacion por comun por campo. [50%]\n" +
                        "\t>PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        "\t>DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        "\t>MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">NFPE: Nucleo de formacion especifica. [100%]\n" +
                        "\t>DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">IPRO: Introduccion a la programacion. Creditos:4[4+8]\n", 
                        plan.toString());
        }catch(Plan15Exception e){
            
        }
        
    }

    @Test
    public void shouldDoToStringAddingTwoCourses() {
       try{
           Plan15 plan = new Plan15();
            
           plan.addCourse("DOPO", "Desarrollo Orientado a Objetos", "4", "4");
           plan.addCourse("FDSI", "Fundamentos de Seguridad de la Información", "3", "4");
    
            //System.out.println("Unidades actuales: " + plan.numberUnits());
            //System.out.println("Salida real:\n" + plan.toString());
            
           assertEquals("8 unidades\n" +
                        ">PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        ">DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        ">MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">NFCC: Nucleo formacion por comun por campo. [50%]\n" +
                        "\t>PRI1: Proyecto Integrador. Creditos:9[3+24]\n" +
                        "\t>DDYA: Diseño de Datos y Algoritmos. Creditos:4[4+8]\n" +
                        "\t>MPIN: Matematicas para Informatica. Creditos:3[4+5]\n" +
                        ">NFPE: Nucleo de formacion especifica. [100%]\n" +
                        "\t>DOSW: Desarrollo y Operaciones Software. Creditos:4[4+8]\n" +
                        ">DOPO: Desarrollo Orientado a Objetos. Creditos:4[4+8]\n" +
                        ">FDSI: Fundamentos de Seguridad de la Información. Creditos:3[4+5]\n",  
                        plan.toString());
        }catch(Plan15Exception e){
            
        }
    }
    
    @Test
    public void shouldSearch() {
        try{
            Plan15 plan = new Plan15();
            System.out.println("Unidades actuales: " + plan.numberUnits());
            String searching = plan.search("PRI").stripTrailing(); 
            System.out.println("Salida obtenida:\n" + searching);
            assertEquals("6 unidades\n" + 
                        ">PRI1: Proyecto Integrador. Creditos:9[3+24]", 
                        searching);
        }catch(Plan15Exception e){
            
        }
    }

    @Test
    public void shouldFailWheniSEmptyName() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCourse("CCC1", null, "3", "2");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_NAME, e.getMessage());
            }
        } catch (Plan15Exception f) {
        }
    }

    @Test
    public void shouldFailWhenNonIntegerCredits() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCourse("C1", "COURSE", "abc", "2");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_CREDITS, e.getMessage());
            } 
        } catch (Plan15Exception f) {
            
        }
    }
    
    @Test
    public void shouldFailWhenPortageIsNotBetween0And100() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCore("CCC1", "COURSE", "101", "PRYE");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.PORTAGE_ERROR, e.getMessage());
            } 
        } catch (Plan15Exception f) {
            
        }
    }
    
    @Test
    public void shouldFailWhenIfNameAndInitialsAreTheSame() {
        try {
            Plan15 plan = new Plan15();
            try {
                plan.addCourse("CCC1", "CCC1", "3", "2");
            } catch (Plan15Exception e) {
                assertEquals(Plan15Exception.INVALID_NAMES, e.getMessage());
            } 
        } catch (Plan15Exception f) {
            
        }
    }
}
