import java.util.concurrent.atomic.AtomicBoolean;

public class DisplayTiming extends Thread {
    private final AtomicBoolean running = new AtomicBoolean(false);
    private long startTime;
    private long totalTime;
    private long elapsedTime;

    public void startTimer(long totalTime) {
        this.totalTime = totalTime;
        running.set(true);
        startTime = System.currentTimeMillis();
        start();
    }

    public void stopTimer() {
        running.set(false);
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getRemainingTime() {
        return totalTime - elapsedTime;
    }

    @Override
    public void run() {
        while (running.get()) {
            elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = totalTime - elapsedTime;
            long remainingSeconds = remainingTime / 1000; // Convert milliseconds to seconds
            System.out.print("\rRemaining time: " + remainingSeconds + " seconds    ");
            try {
                Thread.sleep(2000); // Update every 2 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // Move to the next line after stopping
    }
}