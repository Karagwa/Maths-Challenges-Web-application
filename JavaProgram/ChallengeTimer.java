import java.util.concurrent.atomic.AtomicBoolean;

class DisplayTiming extends Thread {
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
            System.out.print("\rRemaining time: " + remainingTime + " ms    ");
            try {
                Thread.sleep(100); // Update every 100 ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(); // Move to the next line after stopping
    }
}

