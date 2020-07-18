import java.time.LocalTime;

public class Teller
{
    private final Bank bank;
    private final String name;
    private final int customerProximity;
    private LocalTime availableTime;

    public Teller(String name, LocalTime availableTime, int customerProximity, Bank bank) {
        this.name = name;
        this.availableTime = availableTime;
        this.customerProximity = customerProximity;
        this.bank = bank;
    }

    public boolean isAvailable(){
        return availableTime.equals(bank.getCurrentTime())
                || bank.getCurrentTime().isAfter(availableTime);
    }

    public String getName() {
        return name;
    }


    public LocalTime getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(LocalTime availableTime) {
        this.availableTime = availableTime;
    }

    public int getCustomerProximity() {
        return customerProximity;
    }
}
