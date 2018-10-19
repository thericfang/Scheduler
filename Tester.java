import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Collections;

public class Tester {
    public static int numOfProcesses; 
    static ArrayList<Process> arrivingProcesses = new ArrayList<Process>(); // arriving processes
    static ArrayList<Process> readyProcesses = new ArrayList<Process>(); // ready procesess
    static ArrayList<Process> blockedProcesses = new ArrayList<Process>(); // blocked processess
    static ArrayList<Process> terminatedProcesses = new ArrayList<Process>(); // terminated processes
    static Process RunningProcess; // current running processes
    public static ArrayList<Integer> randomNumList = new ArrayList<Integer>(); 
    public static int time;
    static int randomCounter = 0;
    static int removedFromBlockCounter = 0;
    // static Comparator<Process> comparator = new MyComparator();
    // static PriorityQueue<Process> readyProcesses = new PriorityQueue<Process>(comparator);
    
   
    
    public static void main(String args[]) {
        System.out.println("What is the file name?");
        Scanner kbScanner = new Scanner(System.in); // New Scanner to get file name
        String inputName = kbScanner.nextLine();
        fileReader(inputName);
        randomNumbersReader();
        // sortList(arrivingProcesses);
        Collections.sort(arrivingProcesses, 
            (o1, o2) -> o2.compareTo(o1));  
        Collections.sort(readyProcesses, 
            (o1, o2) -> o2.compareTo(o1));  
        for (int i = 0; i < arrivingProcesses.size(); i++) {
            arrivingProcesses.get(i).priority++;
            if (i == 0) {
                ;
            }
            else if (arrivingProcesses.get(i).a == arrivingProcesses.get(i-1).a) {
                arrivingProcesses.get(i).priority = arrivingProcesses.get(i).priority+1;
            }
        }
                
        callScheduler(1);
        Collections.sort(terminatedProcesses, 
                    (o1, o2) -> o2.compareTo(o1));
        
        for (int j = 0; j < arrivingProcesses.size(); j++) {
            Process temp = arrivingProcesses.get(j);
            System.out.println("Process " + j);
            System.out.println("        (A,B,C,M) = (" + temp.a + ", " + temp.b + ", " + temp.c + ", " + temp.m + ")" );
            System.out.println("        Finishing time: " + temp.finishingTime);
            System.out.println("        Turnaround time: " + (temp.finishingTime - temp.a));
            System.out.println("        I/O time: " + temp.totalIOTime);
            System.out.println("        Waiting time: " + temp.waitingTime);
            // System.out.println(temp + "   Finishing time: " + temp.finishingTime + " IO Time: " + temp.totalIOTime + " Waiting Time: " + temp.waitingTime);

        }
        System.out.println("Size of arrivingProcesses: " + arrivingProcesses.size());
        
    }

    public static void fileReader(String inputName) { // New Scanner for file input
        try {
            Scanner fileInput = new Scanner(new FileInputStream(inputName)).useDelimiter("\\D+");
            numOfProcesses = fileInput.nextInt();
            while (fileInput.hasNext()) {   
                if (fileInput.hasNextInt()) {
                    for (int i = 0; i < numOfProcesses; i++) {
                        ArrayList<Integer> temp = new ArrayList<Integer>();
                        int a,b,c,m;
                        for (int j = 0; j < 4; j++) {
                            temp.add(fileInput.nextInt());
                        }
                        a = temp.get(0);
                        b = temp.get(1);
                        c = temp.get(2);
                        m = temp.get(3);
                        Process curProcess = new Process(a,b,c,m);
                        arrivingProcesses.add(curProcess);          
                    }
                    
                }

                else {
                    fileInput.next();
                }
               
            }
            fileInput.close(); 
            
        }
        catch (Exception ex) {
            // System.out.println("Error reading file " + inputName);
            System.out.println(ex);
            System.exit(0);
        }
        
    }

    public static void randomNumbersReader() {
        try {
            Scanner randomNumReader = new Scanner(new FileInputStream("random-numbers"));
            while (randomNumReader.hasNextInt()) {
                randomNumList.add(randomNumReader.nextInt());
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void callScheduler(int i) { //while processes still remain
        switch (i) {
            case 1: {
                
                
                
                while (terminatedProcesses.size() != arrivingProcesses.size()) {
                // while (time < 1117) {
                    printCycles();
                    doBlockedProcesses(i);
                    doRunningProcesses(i);
                    doArrivingProcesses(i);
                    doReadyProcesses(i);
                    time++;
                }
            
            }
            case 3: {
               
                
                
                

        
                while (terminatedProcesses.size() != numOfProcesses) {
                    doBlockedProcesses(i);
                    doRunningProcesses(i);
                    doArrivingProcesses(i);
                    doReadyProcesses(i);
                    time++;
                }
            }
        }
        
       

    }

    public static void doReadyProcesses(int i) {
        switch (i) { // 1 for FCFS, 2 for RR with quantum 2, 3 for LCFS, 4 for HPRN.
            case 1: {     
                if (RunningProcess == null && readyProcesses.size() > 0) {
                    RunningProcess = readyProcesses.get(0);
                    
                    
                    int temp = randomOS(RunningProcess.b);
                    if (temp > RunningProcess.remainingTime) {
                        RunningProcess.CPUBurst = RunningProcess.remainingTime;
                    }
                    else {
                        RunningProcess.CPUBurst = temp;
                    }
                    
                    RunningProcess.state = "Running";
                    RunningProcess.remainingBurstTime = RunningProcess.CPUBurst;
                    RunningProcess.outOfRunning = time + RunningProcess.CPUBurst;
                    readyProcesses.remove(0);

                }
    
                    
                    for (Process p : readyProcesses) {
                        p.remainingBurstTime = 0;
                        p.waitingTime++;
                    }
                
                
            }

            case 3: {
                if (RunningProcess == null && readyProcesses.size() > 0) {
                    Collections.sort(readyProcesses, 
                    (o1, o2) -> o1.compareTo(o2));
                    
                    RunningProcess = readyProcesses.get(readyProcesses.size()-1);
                    
                    RunningProcess.CPUBurst = randomOS(RunningProcess.getB());
                    RunningProcess.remainingBurstTime = RunningProcess.CPUBurst;
                    RunningProcess.state = "Running";
                    RunningProcess.outOfRunning = time + RunningProcess.CPUBurst;
                    RunningProcess.waitingTime += (time  - RunningProcess.outOfBlocked);
                    readyProcesses.remove(readyProcesses.size()-1);
                }
                
            }
            default: 
        }
    }

    public static void doRunningProcesses(int i) {
        if (RunningProcess != null) {
            if (RunningProcess.outOfRunning == time) {
                RunningProcess.IOBurst = RunningProcess.CPUBurst * RunningProcess.m;
                RunningProcess.outOfBlocked = RunningProcess.IOBurst + time;
                RunningProcess.state = "Blocked";
                RunningProcess.remainingBurstTime = RunningProcess.IOBurst;
                RunningProcess.remainingTime -= RunningProcess.CPUBurst;
                // System.out.println("Running Process Remaining Time: " + RunningProcess.remainingTime);
                if (RunningProcess.remainingTime <= 0) {
                    RunningProcess.finishingTime = time;
                    RunningProcess.state = "Terminated";
                    RunningProcess.remainingBurstTime = 0;
                    terminatedProcesses.add(RunningProcess);
                    RunningProcess = null;
                }
                else {
                    blockedProcesses.add(RunningProcess);
                    RunningProcess = null;
                }
               
            }
            else RunningProcess.remainingBurstTime--;
            
            
            

        }
    }

    public static void doBlockedProcesses(int i) { //check if out_of_block == current_time where out_of_block = current_time + io_burst 
        switch (i) {
            case 1:
            case 3: {
                Iterator<Process> iter = blockedProcesses.iterator();
                removedFromBlockCounter = 0;
                while (iter.hasNext()) {
                    Process p = iter.next();
                    if (p.outOfBlocked == time) {
                        p.state = "Ready";
                        p.totalIOTime += p.IOBurst;
                        readyProcesses.add(p);
                        iter.remove();
                        removedFromBlockCounter++;
                    }
                    else {
                        p.remainingBurstTime--;
                    }
                }
                // if (removedFromBlockCounter > 1) {
                //     if (readyProcesses.get(readyProcesses.size()-1).priority > readyProcesses.get(readyProcesses.size()-2).priority) {
                //         Collections.swap(readyProcesses, readyProcesses.size()-1, readyProcesses.size()-2);
                //     }
                // }
                // for (Process p : blockedProcesses) {
                //     if (p.outOfBlocked == time) {
                //         p.state = "Ready";
                //         readyProcesses.add(p);
                //         blockedProcesses.remove(p);

                        
                //     }
                // }
            }
            default: ;
        }
        
    }

    public static void doArrivingProcesses(int i) {
        for (Process p : arrivingProcesses) {
            if (p.a == time) {
                p.state = "Ready";
                readyProcesses.add(p);
                
            }
            
        }
    }

    // public static void sortList(ArrayList<Process> arrivingProcesses) {
    //     Process temp;
    //     for (int i = 0; i < arrivingProcesses.size(); i++) {
    //         for (int j = 0; j < arrivingProcesses.size(); j++) {
    //             if (arrivingProcesses.get(j).a < arrivingProcesses.get(j+1).a) {
    //                 temp = arrivingProcesses.get(j);
    //                 arrivingProcesses.set(j, arrivingProcesses.get(j+1));
    //                 arrivingProcesses.set(j+1, temp);
    //             }
    //         }
            
    //     }
    // }
    public static int randomOS(int u) {
        
        int random = randomNumList.get(randomCounter);
        random = 1 + (random%u);
        randomCounter++;
        return random;
        
    }

    public static void printCycles() {
        // String pString = "Before cycle " + time + ":";
        System.out.print("Before cycle ");
        System.out.printf("%4d:", time);
        for (int i = 0; i < arrivingProcesses.size(); i++) {
            System.out.printf("%-11s", arrivingProcesses.get(i).state);
            System.out.printf("%-3d", arrivingProcesses.get(i).remainingBurstTime);
            // pString += "    " + arrivingProcesses.get(i).state + "     " + arrivingProcesses.get(i).remainingBurstTime;
        }
        System.out.println("");
        
    }
   // System.out.println("Running Process Remaining Time: " + RunningProcess.remainingTime);
    
}
