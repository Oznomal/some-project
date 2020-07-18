import java.time.LocalTime;

public class Bank
{
    private static Bank instance;
    private static LocalTime currentTime;

    private Bank(){
        currentTime = LocalTime.of(+9, 0); // DEFAULTING THE TIME OF THE SINGLETON TO 9:00 AM
    }

    public static Bank getInstance(){
        if(instance == null)
            instance = new Bank();

        return instance;
    }

    public static LocalTime getCurrentTime() {
        return currentTime;
    }

    public static void setCurrentTime(LocalTime newTime) {
        currentTime = newTime;
    }
}
