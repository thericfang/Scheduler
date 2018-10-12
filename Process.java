public class Process {
    int a, b, c, m;
    int timeLeft;
    int currentCPUBurst, currentIOBurst;
    boolean ready, blocked, running;

    public Process(int a, int b, int c, int m) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.m = m;
        this.timeLeft = a;
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
    
    // public double finishingTime() { // TODO: finishing time of process

    // }

    // public double turnaroundTime() { // TODO: turnaround time of process = finishing time - A

    // }

    // public double iOTime() { // TODO: compute I/O time -> time in blocked state

    // }

    // public double waitingTime() { //TODO: time in waiting state

    // } 

    public int CPUBurstTime() {
        this.currentCPUBurst = randomOS(b);
        timeLeft -= currentCPUBurst;
        running = true;
        blocked = false;
        ready = false;
        
    }

    public int IOBurstTime() {
        this.currentIOBurst = currentCPUBurst * this.m;
        timeLeft -= currentIOBurst;
        blocked = true;
        blocked = false;
        running = false;
    }

    public int randomOS(int u) {
        int random = (int)(Math.random()*Tester.randomNumList.size());
        System.out.println("random: "+random);
        return 1 + (Tester.randomNumList.get(random)%u);
    }

    public String toString() {
        return "Process Values: A:" + a + "    B:" + b + "    C:" + c + "     M:" + m;
    }
}