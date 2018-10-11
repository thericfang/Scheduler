import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Tester {
    static int numOfProcesses;
    static ArrayList<Process> processes = new ArrayList<Process>();
    
    public static void main(String args[]) {
        System.out.println("What is the file name?");
        Scanner kbScanner = new Scanner(System.in); // New Scanner to get file name
        String inputName = kbScanner.nextLine();
        fileReader(inputName);
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

}