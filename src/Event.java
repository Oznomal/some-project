import java.time.LocalTime;

public class Event {
    private String customerName;
    private LocalTime arrivalTime;
    private int serviceDuration;

    public Event(String customerName, LocalTime arrivalTime, int serviceDuration) {
        this.customerName = customerName;
        this.arrivalTime = arrivalTime;
        this.serviceDuration = serviceDuration;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceDuration() {
        return serviceDuration;
    }
}
