import java.io.*;
import java.util.*; 

public class Scheduler{
    public class RTComparator implements Comparator<Job>{
        public int compare(Job x, Job y) { 
            if (x.getRT() < y.getRT()) 
                return -1; 
            else if (x.getRT() > y.getRT()) 
                return 1; 
            return 0; 
        } 
    }
    public class RRComparator implements Comparator<Job>{
        public int compare(Job x, Job y) { 
            Float xRR, yRR;
            xRR = (x.getWait()+x.getST())/x.getWait();
            yRR = (y.getWait()+y.getST())/y.getWait();
            if (xRR < yRR) 
                return -1; 
            else if (xRR > yRR)
                return 1; 
            return 0; 
        } 
    }
    public void FCFS(ArrayList<Job> jobList){
        Iterator<Job> jobIter = jobList.iterator();
        ArrayDeque<Job> processQueue = new ArrayDeque<Job>();
        Integer counter = 0;
        Job waitingJob = jobIter.next();
        for(Job job: jobList){
            System.out.print(job.toString()+' ');
        }
        System.out.print("\n");

        //Clock Loop
        //Runs until there are no processes to be run
        while(!processQueue.isEmpty()||jobIter.hasNext()){
            //Will process newest job over older
            //get next job
            if (waitingJob!=null){
                if(waitingJob.getAT() == counter){
                    processQueue.add(waitingJob);
                    waitingJob = jobIter.next();
                    if(!jobIter.hasNext()){
                        processQueue.add(waitingJob);
                        waitingJob = null;
                    }
                }
            }

            //do processing of next process here
            //must poll in here
            if(!processQueue.isEmpty()){
                Job currentJob = processQueue.poll();
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");
                currentJob.setCompleted(currentJob.getCompleted()+1);
                if(currentJob.getCompleted()<currentJob.getST())
                    processQueue.addFirst(currentJob);
            }

            counter++;
        }
    }

    //quantum 1
    public void RR(ArrayList<Job> jobList){
        Iterator<Job> jobIter = jobList.iterator();
        ArrayDeque<Job> processQueue = new ArrayDeque<Job>();
        Integer counter = 0;
        Job waitingJob = jobIter.next();
        Job currentJob;
        for(Job job: jobList){
            System.out.print(job.toString()+' ');
        }
        System.out.print("\n");

        //Clock Loop
        //Runs until there are no processes to be run
        while(!processQueue.isEmpty()||jobIter.hasNext()){
            counter++;
            
            //Will process newest job over older
            //get next job
            if (waitingJob!=null){
                if(waitingJob.getAT() <= counter){
                    if(jobIter.hasNext()){
                        processQueue.add(waitingJob);
                        waitingJob = jobIter.next();
                    }
                }
            }

            //System.out.println(processQueue);
            //do processing of next process here
            //must poll in here
            if(!processQueue.isEmpty()){
                currentJob = processQueue.poll();
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");
                currentJob.setCompleted(currentJob.getCompleted()+1);
                //is not done processing
                if(currentJob.getCompleted() < currentJob.getST()){
                    processQueue.add(currentJob);
                }
            }
        }
    }

    public void SPN(ArrayList<Job> jobList){
        Iterator<Job> jobIter = jobList.iterator();
        ArrayDeque<Job> processQueue = new ArrayDeque<Job>();
        //PriorityQueue<Job> sortQueue = new PriorityQueue<Job>();
        Integer counter = 0;
        Job waitingJob = jobIter.next();
        for(Job job: jobList){
            System.out.print(job.toString()+' ');
        }
        System.out.print("\n");

        //Clock Loop
        //Runs until there are no processes to be run
        while(!processQueue.isEmpty()||jobIter.hasNext()){
            //Will process newest job over older
            //adds next process to
            if (waitingJob!=null){
                if(waitingJob.getAT() == counter){
                    processQueue.add(waitingJob);
                    waitingJob = jobIter.next();
                    if(!jobIter.hasNext()){
                        processQueue.add(waitingJob);
                        waitingJob = null;
                    }
                }
            }
            //do processing of next process here
            //must poll in here
            if(!processQueue.isEmpty()){
                Job currentJob = processQueue.poll();
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");
                currentJob.setCompleted(currentJob.getCompleted()+1);
                if(currentJob.getCompleted()<currentJob.getST())
                    processQueue.addFirst(currentJob);
                else{ // from deque -> (sort here) toArray -> back to deque
                    PriorityQueue<Job> pJorb = new PriorityQueue<>(processQueue);
                    processQueue.clear();
                    while(!pJorb.isEmpty())
                        processQueue.add(pJorb.poll());
                }
                // List<Integer> list = Arrays.asList(array);
                // ArrayDeque<Integer> ad = new ArrayDeque<>(list);
            }
            counter++;
        }
    }

    public void SRT(ArrayList<Job> jobList){
        Iterator<Job> jobIter = jobList.iterator();
        ArrayDeque<Job> processQueue = new ArrayDeque<Job>();
        //PriorityQueue<Job> sortQueue = new PriorityQueue<Job>();
        Integer counter = 0;
        Job waitingJob = jobIter.next();
        for(Job job: jobList){
            System.out.print(job.toString()+' ');
        }
        System.out.print("\n");

        //Clock Loop
        //Runs until there are no processes to be run
        while(!processQueue.isEmpty()||jobIter.hasNext()){
            PriorityQueue<Job> pJorb = new PriorityQueue<Job>(new RTComparator());
            //Will process newest job over older
            //adds next process to
            if (waitingJob!=null){
                if(waitingJob.getAT() == counter){
                    processQueue.add(waitingJob);
                    waitingJob = jobIter.next();
                    if(!jobIter.hasNext()){
                        processQueue.add(waitingJob);
                        waitingJob = null;
                    }
                }
            }
            while(!processQueue.isEmpty()){
                Job jerb = processQueue.poll();
                pJorb.add(jerb);
            }
            processQueue.clear();
            while(!pJorb.isEmpty())
                processQueue.add(pJorb.poll());
            pJorb.clear();
            //do processing of next process here
            //must poll in here
            if(!processQueue.isEmpty()){
                Job currentJob = processQueue.poll();
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");
                currentJob.setCompleted(currentJob.getCompleted()+1);
                currentJob.setRT(currentJob.getST()-currentJob.getCompleted());
                if(currentJob.getCompleted()<currentJob.getST())
                    processQueue.add(currentJob);
                // List<Integer> list = Arrays.asList(array);
                // ArrayDeque<Integer> ad = new ArrayDeque<>(list);
            }
            while(!processQueue.isEmpty()){
                Job jerb = processQueue.poll();
                pJorb.add(jerb);
            }
            processQueue.clear();
            while(!pJorb.isEmpty())
                processQueue.add(pJorb.poll());
            pJorb.clear();
            counter++;
        }
    }
    
    public void HRRN(ArrayList<Job> jobList){
        Iterator<Job> jobIter = jobList.iterator();
        ArrayDeque<Job> processQueue = new ArrayDeque<Job>();
        //PriorityQueue<Job> sortQueue = new PriorityQueue<Job>();
        Integer counter = 0;
        Job waitingJob = jobIter.next();
        for(Job job: jobList){
            System.out.print(job.toString()+' ');
        }
        System.out.print("\n");

        //Clock Loop
        //Runs until there are no processes to be run
        while(!processQueue.isEmpty()||jobIter.hasNext()){
            //Will process newest job over older
            //adds next process to
            if (waitingJob!=null){
                if(waitingJob.getAT() == counter){
                    processQueue.add(waitingJob);
                    waitingJob = jobIter.next();
                    if(!jobIter.hasNext()){
                        processQueue.add(waitingJob);
                        waitingJob = null;
                    }
                }
            }
            //do processing of next process here
            //must poll in here
            if(!processQueue.isEmpty()){
                Job currentJob = processQueue.poll();
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");
                currentJob.setWait((float)(currentJob.getAT()-counter));
                currentJob.setCompleted(currentJob.getCompleted()+1);
                if(currentJob.getCompleted()<currentJob.getST())
                    processQueue.addFirst(currentJob);
                else{ // from deque -> (sort here) toArray -> back to deque
                    PriorityQueue<Job> pJorb = new PriorityQueue<Job>(new RRComparator());
                    while(!processQueue.isEmpty()){
                        Job jerb = processQueue.poll();
                        pJorb.add(jerb);
                    }
                    processQueue.clear();
                    while(!pJorb.isEmpty())
                        processQueue.add(pJorb.poll());
                    pJorb.clear();
                }
                // List<Integer> list = Arrays.asList(array);
                // ArrayDeque<Integer> ad = new ArrayDeque<>(list);
            }
            counter++;
        }
    }

    public void FB(ArrayList<Job> jobList){
        Iterator<Job> jobIter = jobList.iterator();
        ArrayDeque<Job> processQueue = new ArrayDeque<Job>();
        Integer counter = 0;
        Job waitingJob = jobIter.next();
        Boolean done = false;
        Boolean wait = true;

        ArrayDeque<Job> queue1 = new ArrayDeque<Job>();
        ArrayDeque<Job> queue2 = new ArrayDeque<Job>();
        ArrayDeque<Job> queue3 = new ArrayDeque<Job>();

        for(Job job: jobList){
            System.out.print(job.toString()+' ');
        }
        System.out.print("\n");

        //Clock Loop
        //Runs until there are no processes to be run
        while(!done||jobIter.hasNext()){
            //Will process newest job over older
            if(waitingJob.getAT() <= counter){
                if(jobIter.hasNext()){
                    processQueue.add(waitingJob);
                    waitingJob = jobIter.next();
                }
            }
            //do processing of next process here
            //must poll in here
            if(!processQueue.isEmpty()){
                Job currJob = processQueue.poll();
                //put into first queue (first queue is not empty until no more processes)
                queue1.add(currJob);
            }
            else{
                wait = false;
            }

            //run 1 tick of processing
            if(!queue1.isEmpty()){
                Job currentJob = queue1.poll();
                
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");

                currentJob.setCompleted(currentJob.getCompleted()+1);

                if (wait){
                    queue1.add(currentJob);
                }
                else if(currentJob.getCompleted()<currentJob.getST())
                    queue2.add(currentJob);
            }
            else if(!queue2.isEmpty()){
                Job currentJob = queue2.poll();
                
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");

                currentJob.setCompleted(currentJob.getCompleted()+1);

                if(currentJob.getCompleted()<currentJob.getST())
                    queue3.add(currentJob);
            }
            else if(!queue3.isEmpty()){
                Job currentJob = queue3.poll();
                
                Integer activeIndex = jobList.indexOf(currentJob);
                for(int i=0; i<jobList.size(); i++){
                    if(i == activeIndex)
                        System.out.print("X ");
                    else
                        System.out.print("  ");
                }
                System.out.print("\n");
                
                currentJob.setCompleted(currentJob.getCompleted()+1);

                if(currentJob.getCompleted()<currentJob.getST())
                    queue3.add(currentJob);
            }
            else{
                done = true;
            }
            
            counter++;
        }
    }

    public void ALL(ArrayList<Job> jobList){
        FCFS(cleanClone(jobList));
        RR(cleanClone(jobList));
        SPN(cleanClone(jobList));
        SRT(cleanClone(jobList));
        HRRN(cleanClone(jobList));
        FB(cleanClone(jobList));
    }

    public ArrayList<Job> cleanClone(ArrayList<Job> jobList){
        ArrayList<Job> clean = new ArrayList<Job>();
        for(Job jorb:jobList){
            clean.add(jorb.clone());
        }
        return clean;
    }
}