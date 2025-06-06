package domain;  
import java.util.ArrayList;

public class Core extends Unit{
   

    private int inPersonPercentage;
    private ArrayList<Course> courses;
    
    /**
     * Constructs a new core unit
     * @param code
     * @param name
     * @param credits 
     */
    public Core(String code, String name, int inPersonPercentage){
        super(code, name);
        this.inPersonPercentage=inPersonPercentage;
        courses= new ArrayList<Course>();
    }


     /**
     * Add a new course
     * @param s
     */   
    public void addCourse(Course c){
        courses.add(c);
    }
    
    /**
     * Calculates the total number of credits for all courses in this unit.
     * @return The total number of credits for this unit.
     * @throws Plan15Exception IMPOSSIBLE If the total number of credits is 0,      
     */
    @Override
    public int credits() throws Plan15Exception{
        int countCredits = 0;
        for(Course c : courses){
            countCredits += c.credits();   
        }
        if(countCredits == 0) {
            throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        }
        return countCredits;
    }
    

    @Override
    public int inPerson() throws Plan15Exception{
        return 0;
    }

    /**
    * Calculate the actual or estimated credits 
    * If necessary (unknown or error), assumes the number of credits is 3 
    * is equal to the in-person hours.
    * @return the estimated credits 
    * @throws Plan15Exception IMPOSSIBLE, If there are no credits 
    */
    public int creditsEstimated() throws Plan15Exception{
        int estimatedCredits = 0;
        for(Course c : courses){
            try{
                estimatedCredits += c.credits();    
            }catch(Plan15Exception e){
                estimatedCredits += 3;
            }
        }
        if(estimatedCredits == 0) throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
        return estimatedCredits;
    }
    
    /**
    * Calculate the estimated in-person hours, considering courses that do not have credit issues. 
    * If the hours of a course are not known, calculate the course in-person hours using the percentage suggested in the unit core.
    * If the hours of a course are incorrect, it is assumed that all the time is in person. 
    * @return the estimated hours per person
    * @throws Plan15Exception IMPOSSIBLE, If there are no courses or all courses has credit issues
    */
    public int inPersonEstimated() throws Plan15Exception {
        int estimatedHours = 0;
        for (Course c : courses) {
            try {
                estimatedHours += c.inPerson(); 
            } catch (Plan15Exception e) {
                try {
                    estimatedHours += c.independent();
                } catch (Plan15Exception ex) {
                estimatedHours += 3*3;
            }
        }
    }
    if (estimatedHours == 0) {
        throw new Plan15Exception(Plan15Exception.IMPOSSIBLE);
    }
    return estimatedHours;
    }

    @Override
    public String data() throws Plan15Exception {
        StringBuffer answer = new StringBuffer();
        answer.append(code + ": " + name + ". [" + inPersonPercentage + "%]");
        for (Course c : courses) {
            answer.append("\n\t>").append(c.data()); 
        }
        return answer.toString();
    }

    
 

}
