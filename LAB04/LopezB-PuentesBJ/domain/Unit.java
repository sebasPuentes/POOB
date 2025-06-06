package domain;

public abstract class Unit{
    protected String code;
    protected String name;
    
    
    public Unit(String code, String name){
        this.code=code;
        this.name=name;
    }
    
    /**
     * Return the code
     * @return
     */
    public String code(){
        return code;
    }
    
    /**
     * Return the name
     * @return
     */
    public String name(){
        return name;
    }

 
    /**
     * Return the credits
     * @return
     * @throws Plan15Exception, if the credits is unknown or has an error
     */
    public abstract int credits() throws Plan15Exception;
    
    
    /**
     * Return in_person hours
     * @return
     * @throws Plan15Exception, if inPerson hours
     */
    public abstract int inPerson() throws Plan15Exception;
    
    
    /**
     * Return independent hours
     * @return
     * @throws Plan15Exception, if it is not available or has a miscalculation
     */
    public final int independent() throws Plan15Exception{
        return credits()* 3-inPerson();
    }
    
    /**
     * Return the representation as string
     * @return
     * @throws Plan15Exception, if the data is not complete or has an error (credits or in_person)
     */    
    public abstract String data() throws Plan15Exception;

}
