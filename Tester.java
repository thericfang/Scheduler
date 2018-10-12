import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Tester {
    public static int numOfProcesses; 
    static ArrayList<Process> arrivingProcesses = new ArrayList<Process>(); // arriving processes
    static ArrayList<Process> readyProcesses = new ArrayList<Process>(); // ready procesess
    static ArrayList<Process> blockedProcesses = new ArrayList<Process>(); // blocked processess
    static ArrayList<Process> terminatedProcesses = new ArrayList<Process>(); // terminated processes
    Process RunningProcess; // current running process
    public static ArrayList<Integer> randomNumList = new ArrayList<Integer>(); 
    public static ArrayList<Integer> sortedProcesses = new ArrayList<Process>(); 
    public static int time;
    
    public static void main(String args[]) {
        System.out.println("What is the file name?");
        Scanner kbScanner = new Scanner(System.in); // New Scanner to get file name
        String inputName = kbScanner.nextLine();
        fileReader(inputName);
        randomNumbersReader();
        System.out.println(processes.get(0).randomOS(processes.get(0).getB()));
        for (int i = 0; i < processes.size(); i++) {
            System.out.println(processes.get(i));
        }
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
                        processes.add(curProcess);          
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

    public static void randomNumbersReader () {
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
        while (terminatedProcesses.size() == numOfProcesses) {
            doBlockedProcesses(i);
            doRunningProcesses(i);
            doArrivingProcesses(i);
            doReadyProcesses(i);
            time++;
        }
    }

    public static void doReadyProcesses(int i) {

    }

    public static void doRunningProcesses(int i) {

    }

    public static void doBlockedProcesses(int i) { //check if out_of_block == current_time where out_of_block = current_time + io_burst 
        
    }

    public static void doArrivingProcesses(int i) {
        for (Process p : arrivingProcesses) {
            if (p.a == time) {
                readyProcesses.add(p);
            }
        }
    }
}