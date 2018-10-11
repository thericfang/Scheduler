import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Scheduler {
    private int schedulingAlg; // Type of scheduling algorithm. 1 for FCFS, 2 for RR with quantum 2, 3 for LCFS, 4 for HPRN.
    private ArrayList<Process> processes;
    public Scheduler(int i, ArrayList<Process> processes) {
        schedulingAlg = i;
        this.processes = processes;
    }

    public int getSchedulingAlg () {
        return schedulingAlg;
    }

    public String getSchedulingAlgInWords () {
        switch (schedulingAlg) {
            case 1: return "FCFS"; 
            case 2: return "RR with quantum 2"; 
            case 3: return "LCFS"; 
            default: return "HPRN"; 
        }
    }
    
    


   
}