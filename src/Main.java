import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;

//Video showing the math proof, and the inspiration for this pointless program: https://www.youtube.com/watch?v=RZBhSi_PwHU
public class Main implements Runnable {
    private static long numOfIterations = 10000000000L;
    private static long randomMaxValue = Long.MAX_VALUE;
    private static LongAdder counter = new LongAdder();
    private static LongAdder totalIterations = new LongAdder();

    private static int numOfThreads = Runtime.getRuntime().availableProcessors();

    @Override
    public void run() {
        while(totalIterations.longValue() < numOfIterations) {
            if (euclidGCD(randomWithRange(1, randomMaxValue), randomWithRange(1, randomMaxValue)) == 1) {
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
        System.out.println("Iterations per second: " + df.format(numOfIterations / ((System.currentTimeMillis() - startTime) / 1000)));

        double x = (double) counter.longValue() / numOfIterations;

        double calculatedPi = Math.sqrt(6 / x);

        System.out.println("X: " + x);
        System.out.println("Calculated pi: " + calculatedPi);
        System.out.println("Percent error: " + (Math.abs(100 - (calculatedPi / Math.PI) * 100)));
    }

    static long randomWithRange(long min, long max) {
        return ThreadLocalRandom.current().nextLong(max - 1) + 1;
    }

    static long euclidGCD(long a, long b) {
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
