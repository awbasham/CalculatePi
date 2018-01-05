//Video showing the math proof, and the inspiration for this pointless program: https://www.youtube.com/watch?v=RZBhSi_PwHU
public class Main implements Runnable {
    private static int numOfIterations = 100000000;
    private static final int halfway = numOfIterations / 2;
    private static int randomMaxValue = Integer.MAX_VALUE;
    private static int currentIteration = 0;
    private static int counter = 0;

    @Override
    public void run() {
        while (getCurrentIteration() < numOfIterations) {
            doCalculate();
        }
    }

    static synchronized void doCalculate() {
        if(getCurrentIteration() < numOfIterations) {
            if (euclidGCD(randomWithRange(1, randomMaxValue), randomWithRange(1, randomMaxValue)) == 1) {
                iterateCounter();
            }
            iterateIteration();
        }
    }

    static synchronized int getCurrentIteration() {
        return currentIteration;
    }

    static synchronized void iterateCounter() {
        counter++;
    }

    static synchronized void iterateIteration() {
        currentIteration++;
        if(currentIteration == halfway) {
            System.out.println("Half way done!");
        }
    }

    public static void main(String[] args) {
        try {
            Thread t1 = new Thread(new Main());
            Thread t2 = new Thread(new Main());
            Thread t3 = new Thread(new Main());
            Thread t4 = new Thread(new Main());
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        double x = (double) counter / numOfIterations;

        double calculatedPi = Math.sqrt(6 / x);

        System.out.println("X: " + x);
        System.out.println("Calculated pi: " + calculatedPi);
        System.out.println("Percent error: " + (Math.abs(100 - (calculatedPi / Math.PI) * 100)));
    }

    static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    static int euclidGCD(int a, int b) {
        while (a != b) {
            if (a > b) {
                a -= b;
            } else {
                b -= a;
            }
        }
        return a;
    }
}
