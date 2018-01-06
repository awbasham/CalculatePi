import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

//Video showing the math proof, and the inspiration for this pointless program: https://www.youtube.com/watch?v=RZBhSi_PwHU
public class Main implements Runnable {
    private static int numOfIterations = 1000000000;
    private static int randomMaxValue = Integer.MAX_VALUE;
    private static LongAdder counter = new LongAdder();
    private static LongAdder totalIterations = new LongAdder();

    private static int numOfThreads = 9;

    @Override
    public void run() {
        while(totalIterations.intValue() < numOfIterations) {
            if (euclidGCD(randomWithRange(1, randomMaxValue), randomWithRange(1, randomMaxValue)) == 1) {
                //Synchronized iterate counter
                counter.increment();
            }
            totalIterations.increment();
        }
    }

    public static void main(String[] args) {
        double startTime = System.currentTimeMillis();
        try {
            ArrayList<Thread> threads = new ArrayList<>();
            for (int i = 0; i < numOfThreads; i++) {
                threads.add(new Thread(new Main()));
                threads.get(i).start();
            }

            for(Thread thread : threads) {
                thread.join();
            }

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println("Minutes elapsed: " + df.format((System.currentTimeMillis() - startTime) / 1000 / 60));

        double x = (double) counter.intValue() / numOfIterations;

        double calculatedPi = Math.sqrt(6 / x);

        System.out.println("X: " + x);
        System.out.println("Calculated pi: " + calculatedPi);
        System.out.println("Percent error: " + (Math.abs(100 - (calculatedPi / Math.PI) * 100)));
    }

    static int randomWithRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max - 1) + 1;
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
