public class Process {
    int a, b, c, m;
    int remainingTime;
    int timeOfNext;
    int CPUBurst, IOBurst;
    int runningTimeToDate = 0;
    int remainingBurstTime;
    int totalIOTime;
    boolean ready, blocked, running;
    boolean terminated;
    int outOfBlocked;
    int outOfRunning;
    int currentState;
    String transitionState;
    String state;
    int finishingTime; 
    int turnaroundTime = finishingTime - a;
    int waitingTime;
    int priority;
    int arrivalTimeToReady;
    int arrivingTime;
    int penaltyRatio;

    public Process(int a, int b, int c, int m) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.m = m;
        this.remainingTime = c;
        this.totalIOTime = 0;
        this.state = "Unstarted";
    }
    public int getA() {
        return this.a;
    }
    public int getB() {
        return this.b;
    }
    public int getC() {
        return this.c;
    }
    public int getM() {
        return this.m;
    }
    

    // public int CPUBurstTime() {
    //     this.currentCPUBurst = randomOS(b);
    //     timeLeft -= currentCPUBurst;
        
    // }
    
    public void addCPUBurstTime() {
        CPUBurst = Tester.randomOS(this.b);
        outOfRunning = Tester.time + CPUBurst;
        outOfBlocked = outOfRunning + CPUBurst * m;
    }

    // public int IOBurstTime() {
    //     this.currentIOBurst = currentCPUBurst * this.m;
    //     timeLeft -= currentIOBurst;
    //     blocked = true;
    //     blocked = false;
    //     running = false;
    // }

    

    public String toString() {
        return "Process Values: A:" + a + "    B:" + b + "    C:" + c + "     M:" + m;
    }

    public int compareTo(Process p, int a) {
        if (a == 1) {
            return (p.arrivalTimeToReady - this.arrivalTimeToReady);
        }
        else if (a == 2) {
            return (p.penaltyRatio - this.penaltyRatio);
        }
        else 
        return(p.a-this.a);
        
    }
    public int compareTo(Process p) {
        return compareTo(p, 0);
    }
}