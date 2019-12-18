import java.io.*;
import java.util.*; 

public class Job implements Comparable<Job>, Cloneable{
    String name;
    Integer AT,ST,RT,completed;
    Float wait;

    public Job(String name, Integer AT, Integer ST){
        this.name = name;
        this.AT = AT;
        this.ST = ST;
        this.RT = ST;
        this.completed = 0;
        this.wait = 0.0f;
    }

    public String toString(){
        return name;
    }
    
    public Integer getAT(){return AT;}
    public Integer getST(){return ST;}
    public Integer getRT(){return RT;}
    public void setRT(Integer RT){this.RT = RT;}
    public void setWait(Float wait){this.wait = wait;}
    public Integer getCompleted(){return completed;}
    public Float getWait(){return wait;}
    public Boolean setCompleted(Integer completed){
        this.completed = completed; 
        return true;
    }
    @Override
    public int compareTo(Job other){
        if (this.getST() == null || other.getST() == null){
            return 0;
        }
        return this.getST().compareTo(other.getST());
    }

    @Override
    protected Job clone() 
    { 
        return new Job(name,AT,ST); 
    } 
}
