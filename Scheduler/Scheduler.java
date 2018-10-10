import java.util.Scanner;
import java.io.*;
import java.util.*;

public class Scheduler {
    static ArrayList<ArrayList<Integer>> processes = new ArrayList<ArrayList<Integer>>();
    static int numOfProcesses;
    public static void main(String args[]) {
        System.out.println("What is the file name?");
        Scanner kbScanner = new Scanner(System.in); // New Scanner to get file name
        String inputName = kbScanner.nextLine();
        fileReader(inputName);
        System.out.println(processes);
    }

    public static void fileReader(String inputName) { // New Scanner for file input
        try {
            Scanner fileInput = new Scanner(new FileInputStream(inputName)).useDelimiter("\\D+");
            numOfProcesses = fileInput.nextInt();
            while (fileInput.hasNext()) {   
                if (fileInput.hasNextInt()) {
                    for (int i = 0; i < numOfProcesses; i++) {
                        ArrayList<Integer> temp = new ArrayList<Integer>();
                        for (int j = 0; j < 4; j++) {
                            temp.add(fileInput.nextInt());
                        }
                        processes.add(temp);          
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