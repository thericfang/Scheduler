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
    static int quantumCounter = 0;
    static boolean verbose = false;
    static int iOTime = 0;
    static String inputName;
   
    
    public static void main(String args[]) {
        try {
            if (args != null && args[0].equals("--verbose")) {
                verbose = true;
                inputName = args[1];
                
            }
            else {
                inputName = args[0];
            }
        }
        catch (Exception ex) {
            verbose = false;
        }
        runStuff(inputName, 1);
        runStuff(inputName, 2);
        runStuff(inputName, 3);
        runStuff(inputName, 4);
       

        
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
                        // curProcess.arrivingTime = i;

                        arrivingProcesses.add(curProcess);          
                    }
                    
                }

                else {
                    fileInput.next();
                }
               
            }

            Collections.sort(arrivingProcesses, 
                (o1, o2) -> o2.compareTo(o1));  
            for (int k = 0; k < arrivingProcesses.size(); k++) {
                arrivingProcesses.get(k).arrivingTime = k;
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
                
                
                
                while (terminatedProcesses.size() != numOfProcesses) {
                // while (time < 1117) {
                    if (verbose) {
                        printCycles();
                    }
                    doBlockedProcesses(i);
                    doRunningProcesses(i);
                    doArrivingProcesses(i);
                    doReadyProcesses(i);
                    time++;
                }
                time = 0;
                break;
            
            }
            case 2: {
                
                    while (terminatedProcesses.size() != numOfProcesses) {
                        if (verbose) {
                            printCycles();
                        }                        
                        doBlockedProcesses(i);
                        doRunningProcesses(i);
                        doArrivingProcesses(i);
                        doReadyProcesses(i);
                        time++;
                    }
                    time = 0;
                break;
                    

            }
            case 3: {
        
                while (terminatedProcesses.size() != numOfProcesses) {
                    if (verbose) {
                        printCycles();
                    }                    
                    doBlockedProcesses(i);
                    doRunningProcesses(i);
                    doArrivingProcesses(i);
                    doReadyProcesses(i);
                    time++;
                }
                time = 0;
                
                break;
            }
            case 4: {
                while (terminatedProcesses.size() != numOfProcesses) {
                    if (verbose) {
                        printCycles();
                    }                    
                    doBlockedProcesses(i);
                    doRunningProcesses(i);
                    doArrivingProcesses(i);
                    doReadyProcesses(i);
                    time++;
                }
                time = 0;
                break;

            }
            default: ;
        }
        switch (i) {
            case 1: System.out.println("The scheduling algorithm used was First Come First Served");
            break;
            case 2: System.out.println("The scheduling algorithm used was Round Robin");
            break;
            case 3: System.out.println("The scheduling algorithm used was Last Come First Served");
            break;
            case 4: System.out.println("The scheduling algorithm used was Highest Penalty Ratio First");
            break;
            default: ;
        }
        
       

    }

    public static void doReadyProcesses(int i) {
        switch (i) { // 1 for FCFS, 2 for RR with quantum 2, 3 for LCFS, 4 for HPRN.
            case 1: {     
                if (RunningProcess == null && readyProcesses.size() > 0) {
                    Process tempProcess = readyProcesses.get(0);
                    for (int j = 1; j < readyProcesses.size(); j++) {
                        if (tempProcess.arrivalTimeToReady > readyProcesses.get(j).arrivalTimeToReady) {
                            tempProcess = readyProcesses.get(j);
                        }
                        else if (tempProcess.arrivalTimeToReady == readyProcesses.get(j).arrivalTimeToReady) {
                            if (tempProcess.arrivingTime > readyProcesses.get(j).arrivingTime) {
                                tempProcess = readyProcesses.get(j);
                            }
                        }
                    }
                    RunningProcess = tempProcess;
                    readyProcesses.remove(tempProcess);   
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
                }    
                for (Process p : readyProcesses) {
                    p.remainingBurstTime = 0;
                    p.waitingTime++;
                }
                break;  
            }
            case 2: {
                if (RunningProcess == null && readyProcesses.size() > 0) {
                    Process tempProcess = readyProcesses.get(0);
                    for (int j = 1; j < readyProcesses.size(); j++) {
                        if (tempProcess.arrivalTimeToReady > readyProcesses.get(j).arrivalTimeToReady) {
                            tempProcess = readyProcesses.get(j);
                        }
                        else if (tempProcess.arrivalTimeToReady == readyProcesses.get(j).arrivalTimeToReady) {
                            if (tempProcess.arrivingTime > readyProcesses.get(j).arrivingTime) {
                                tempProcess = readyProcesses.get(j);
                            }
                        }
                    }
                    RunningProcess = tempProcess;
                    readyProcesses.remove(tempProcess);  

                    if (RunningProcess.CPUBurst == 0) {
                        int temp = randomOS(RunningProcess.b);
                        if (temp > RunningProcess.remainingTime) {
                            RunningProcess.CPUBurst = RunningProcess.remainingTime;
                        }
                        else {
                            RunningProcess.CPUBurst = temp;
                        }
                        if (RunningProcess.CPUBurst < 2) {
                            RunningProcess.remainingBurstTime = 1;
                        }
                        else {
                            RunningProcess.remainingBurstTime = 2;
                        }
                        RunningProcess.state = "Running";
                        RunningProcess.outOfRunning = time + RunningProcess.CPUBurst;
                        RunningProcess.IOBurst = RunningProcess.CPUBurst * RunningProcess.m;
                    }
                    else {

                        RunningProcess.state = "Running";
                        if (RunningProcess.CPUBurst < 2) {
                            RunningProcess.remainingBurstTime = 1;
                        }
                        else {
                            RunningProcess.remainingBurstTime = 2;
                        }
                        RunningProcess.outOfRunning = time + RunningProcess.CPUBurst;
                    }
                }
                for (Process p : readyProcesses) {
                    p.remainingBurstTime = 0;
                    p.waitingTime++;
                }
                break;
            }
           
            case 3: {
                if (RunningProcess == null && readyProcesses.size() > 0) {
                    Collections.sort(readyProcesses,
                    (o1, o2) -> o1.compareTo(o2, 1));
                    Process tempProcess = readyProcesses.get(0);
                    for (int j = 1; j < readyProcesses.size(); j++) {
                        if (tempProcess.arrivalTimeToReady < readyProcesses.get(j).arrivalTimeToReady) {
                            tempProcess = readyProcesses.get(j);
                        }
                        else if (tempProcess.arrivalTimeToReady == readyProcesses.get(j).arrivalTimeToReady) {
                            if (tempProcess.arrivingTime > readyProcesses.get(j).arrivingTime) {
                                tempProcess = readyProcesses.get(j);
                            }
                        }
                    }
                    RunningProcess = tempProcess;
                    readyProcesses.remove(tempProcess);
                    
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
                    

                }
    
                    for (Process p : readyProcesses) {
                        p.remainingBurstTime = 0;
                        p.waitingTime++;
                    }
                break;
            }
            case 4: {
                if (RunningProcess == null && readyProcesses.size() > 0) {
                    for (Process k : readyProcesses) {
                        k.penaltyRatio = time/Math.max(k.runningTimeToDate,1);
                    }
                    Collections.sort(readyProcesses,
                    (o1, o2) -> o1.compareTo(o2, 2));
                    
                    Process tempProcess = readyProcesses.get(0);
                    RunningProcess = tempProcess;
                    readyProcesses.remove(tempProcess);
                    
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
                    

                }
    
                    for (Process p : readyProcesses) {
                        p.remainingBurstTime = 0;
                        p.waitingTime++;
                    }
                break;

            }
            default: ;
        }
    }

    public static void doRunningProcesses(int i) {
        switch (i) {
            case 1:
            case 3: 
            case 4: {
                if (RunningProcess != null) {
                    if (RunningProcess.outOfRunning == time) {
                        RunningProcess.IOBurst = RunningProcess.CPUBurst * RunningProcess.m;
                        RunningProcess.outOfBlocked = RunningProcess.IOBurst + time;
                        RunningProcess.state = "Blocked";
                        RunningProcess.remainingBurstTime = RunningProcess.IOBurst;
                        RunningProcess.remainingTime -= RunningProcess.CPUBurst;
                        RunningProcess.runningTimeToDate++;
                        RunningProcess.CPUBurst = 0;
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
                    else {
                        RunningProcess.runningTimeToDate++;
                        RunningProcess.remainingBurstTime--;
                    }
                }
                break;
            }
            case 2: {
                if (RunningProcess != null) {
                    if (RunningProcess.outOfRunning == time) {
                        RunningProcess.outOfBlocked = RunningProcess.IOBurst + time;
                        RunningProcess.state = "Blocked";
                        RunningProcess.remainingBurstTime = RunningProcess.IOBurst;
                        RunningProcess.remainingTime -= RunningProcess.IOBurst / RunningProcess.m;
                        RunningProcess.runningTimeToDate++;
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
                    
                    else {
                        RunningProcess.runningTimeToDate++;
                        RunningProcess.remainingBurstTime--;
                        if (RunningProcess.remainingBurstTime == 0) {
                            Process temp = RunningProcess;
                            temp.state = "Ready";
                            temp.arrivalTimeToReady = time;
                            temp.CPUBurst = temp.CPUBurst - 2;
                            readyProcesses.add(temp);
                            RunningProcess = null;
                        }
                        
                           
                        
                        
                        
                        
                    }
                }
                break;
                         
            }

        }
        
    }

    public static void doBlockedProcesses(int i) { //check if out_of_block == current_time where out_of_block = current_time + io_burst 
        switch (i) {
            case 1:
            case 3:
            case 4: {
                Iterator<Process> iter = blockedProcesses.iterator();
                removedFromBlockCounter = 0;
                while (iter.hasNext()) {
                    Process p = iter.next();
                    if (p.outOfBlocked == time) {
                        p.state = "Ready";
                        p.totalIOTime += p.IOBurst;
                        p.arrivalTimeToReady = time;
                        readyProcesses.add(p);
                        iter.remove();
                        removedFromBlockCounter++;
                    }
                    else {
                        p.remainingBurstTime--;
                    }
                }
                
       
                break;
            }
           
            case 2: {
                Iterator<Process> iter = blockedProcesses.iterator();
                removedFromBlockCounter = 0;
                if (iter.hasNext()) {
                    iOTime++;
                }
                while (iter.hasNext()) {
                    Process p = iter.next();
                    if (p.outOfBlocked == time) {
                        p.state = "Ready";
                        p.totalIOTime += p.IOBurst;
                        p.arrivalTimeToReady = time;
                        p.remainingBurstTime = 0;
                        p.CPUBurst = 0;
                        readyProcesses.add(p);
                        iter.remove();
                        removedFromBlockCounter++;
                    }
                    else {
                        p.remainingBurstTime--;
                    }
                }
                
                break;
            }
            default: ;
        }
        
    }

    public static void doArrivingProcesses(int i) {
        for (Process p : arrivingProcesses) {
            if (p.a == time) {
                p.state = "Ready";
                p.arrivalTimeToReady = time;
                readyProcesses.add(p);      
                
            }
            
        }
    }

    public static int randomOS(int u) {
        
        int random = randomNumList.get(randomCounter);
        random = 1 + (random%u);
        randomCounter++;
        return random;
        
    }

    public static void printCycles() {
        System.out.print("Before cycle ");
        System.out.printf("%4d:", time);
        for (int j = 0; j < arrivingProcesses.size(); j++) {
            System.out.printf("%-11s", arrivingProcesses.get(j).state);
            System.out.printf("%-3d", arrivingProcesses.get(j).remainingBurstTime);
            // pString += "    " + arrivingProcesses.get(i).state + "     " + arrivingProcesses.get(i).remainingBurstTime;
        }
        System.out.println("");
        
    }
    // System.out.println("Running Process Remaining Time: " + RunningProcess.remainingTime);
    public static void printStuff() {
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
        System.out.println("Summary Data:");
        System.out.println("        Finishing Time: " + terminatedProcesses.get(terminatedProcesses.size()-1).finishingTime);
        int totalCPUTime = 0;
        int totalIOTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        for (int k = 0; k < terminatedProcesses.size(); k++) {
            totalCPUTime += terminatedProcesses.get(k).c;
            totalTurnaroundTime += (terminatedProcesses.get(k).finishingTime - terminatedProcesses.get(k).a);
            totalWaitingTime += terminatedProcesses.get(k).waitingTime;

        }
    
    
        System.out.println("        CPU Utilization: " + (double)totalCPUTime/terminatedProcesses.get(terminatedProcesses.size()-1).finishingTime);
        System.out.println("        I/O Utilization: " + (double)iOTime/terminatedProcesses.get(terminatedProcesses.size()-1).finishingTime);
        System.out.println("        Throughput: " + (double)numOfProcesses/(terminatedProcesses.get(terminatedProcesses.size()-1).finishingTime)*100);
        System.out.println("        Average Turnaround Time: " + (double) totalTurnaroundTime / terminatedProcesses.size());
        System.out.println("        Average Waiting Time: " + (double)totalWaitingTime/terminatedProcesses.size());
       
   }

    public static void runStuff(String inputName, int j) {
        fileReader(inputName);
        randomNumbersReader();
        // sortList(arrivingProcesses);
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
        callScheduler(j);
        printStuff();
        terminatedProcesses.clear();
        blockedProcesses.clear();
        readyProcesses.clear();
        arrivingProcesses.clear();
        System.out.println("---------------------------------------------");
    }
    
}
