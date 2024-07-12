import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class ChallengeTimer {

    private static int remainingTime; // in seconds
    private static Timer timer;

    public static void main(String[] args) {
        int challengeDuration = 120; // challenge duration in seconds
        startTimer(challengeDuration);
    }

    public static void startTimer(int durationInSeconds) {
        remainingTime = durationInSeconds;
        timer = new Timer();
        
        TimerTask task = new TimerTask() {
            public void run() {
                if (remainingTime > 0) {
                    System.out.println("Time remaining: " + formatTime(remainingTime));
                    remainingTime--;
                } else {
                    System.out.println("Time's up!");
                    timer.cancel();
                }
            }
        };

        // Schedule the task to run every second
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}
