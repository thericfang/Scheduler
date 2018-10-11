public class Process {
    int a, b, c, m;

    public Process(int a, int b, int c, int m) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.m = m;
    }
    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }
    public int getC() {
        return c;
    }
    public int getM() {
        return m;
    }
    
    // public double finishingTime() { // TODO: finishing time of process

    // }

    // public double turnaroundTime() { // TODO: turnaround time of process = finishing time - A

    // }

    // public double iOTime() { // TODO: compute I/O time -> time in blocked state

    // }

    // public double waitingTime() { //TODO: time in waiting state

    // } 

    public String toString() {
        return "Process Values: A:" + a + "    B:" + b + "    C:" + c + "     M:" + m;
    }
}