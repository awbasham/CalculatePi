import java.util.ArrayList;

//Video showing the math proof, and the inspiration for this pointless program: https://www.youtube.com/watch?v=RZBhSi_PwHU
public class Main implements Runnable {
    private static int numOfIterations = 100000000;
    private static int randomMaxValue = Integer.MAX_VALUE;
    private static int counter = 0;

    private static int numOfThreads = 4;

    @Override
    public void run() {
        //Allow each thread to perform equal parts of the calculation task
        int totalIterations = numOfIterations / numOfThreads;
        for (int i = 0; i < totalIterations; i++) {
            if (euclidGCD(randomWithRange(1, randomMaxValue), randomWithRange(1, randomMaxValue)) == 1) {
                //Synchronized iterate counter
                iterateCounter();
            }
        }

    }

    private static synchronized void iterateCounter() {
        counter++;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < numOfThreads; i++) {
                threads.add(new Thread(new Main()));
                threads.get(i).start();
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
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
