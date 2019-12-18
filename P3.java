import java.io.*;
import java.util.*; 
import Scheduler;
import Job;

public class P3 {
    public static void main(String args[]) throws Exception{
        //read in text file
        File file = new File("./jobs.txt");
        Scanner sc = new Scanner(file);

        String line = "";
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<Job> jobList = new ArrayList<Job>();
        
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            values = new ArrayList<String>(Arrays.asList(line.split("\t")));
            Job currJob = new Job(values.get(0),Integer.valueOf(values.get(1)),Integer.valueOf(values.get(2)));
            jobList.add(currJob);
        }
        sc.close();

        Scheduler sim = new Scheduler(); 
        String choice = args[0];

        switch(choice){
            case "FCFS":
                sim.FCFS(jobList);
                break;
            case "RR":
                sim.RR(jobList);
                break;
            case "SPN":
                sim.SPN(jobList);
                break;
            case "SRT":
                sim.SRT(jobList);
                break;
            case "HRRN":
                sim.HRRN(jobList);
                break;
            case "FB":
                sim.FB(jobList);
                break;
            case "ALL":
                sim.ALL(jobList);
                break;
        }
    }
}