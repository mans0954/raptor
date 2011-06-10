package uk.ac.cardiff.model.wsmodel;

import java.io.Serializable;

public class Suggestion  implements Serializable{
    
    /** Generate Serial UID */
    private static final long serialVersionUID = 6492859645919853121L;

    /** The value of the base type of this suggestions e.g. class type*/
    private String base;
    
    /** The value of this suggestion */
    private String value;
    
    public Suggestion(String base, String value){
        this.base = base;
        this.value = value;
    }

    /**
     * Default constructor
     */
    public Suggestion(){
        
    }
    
    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass() + "@[");
        builder.append(base); builder.append(":"); builder.append(value);        
        builder.append("]");
        return builder.toString();
    }
    

}
