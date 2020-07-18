import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main
{
    private static final String PROVIDE_FILE = "Please provide a valid file as a program arg.";
    private static final String ERROR_MSG = "Something went wrong, please restart the application and try again.";

    public static void main(String[] args)
    {
        PriorityQueue<Teller> tellerQueue = tellerInit();
        List<Event> customerEvents = parseCustomerEventInfo(args[0]);

        // LOOPS THROUGH ALL OF THE CUSTOMER EVENTS AND SENDS THEM TO APPROPRIATE TELLER
        for(Event event : customerEvents){
            Bank.setCurrentTime(event.getArrivalTime());

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            // PRIORITY QUEUE IS OPTIMIZED IN SUCH A WAY THAT IT WILL NOT COMPARE THE SAME 2 TELLERS BACK TO BACK
            // AND SINCE WE RE-ADD THE TELLER TO THE QUEUE AFTER PERFORMING THE TRANSACTION THE COMPARE TO METHOD
            // IN THE COMPARATOR IS ALREADY CALLED AND THUS WILL NOT BE CALLED AGAIN FOR THAT TELLER IN THE
            // PRIORITY QUEUE. TO GET AROUND THIS ISSUE WE REMOVE ALL OF THE OBJECTS FROM THE PRIORITY QUEUE
            // AND ADD THEM BACK AGAIN WHICH FORCES THE PRIORITY QUEUE TO RE-INVOKE THE COMPARE TO METHODS
            // FOR THE TELLERS AGAIN AFTER THE BANK CURRENT TIME HAS BEEN UPDATED. LINES 31-40
            Object [] tellerObjects = tellerQueue.toArray();
            tellerQueue.clear();

            List<Teller> tellers = new ArrayList<>();
            for(Object teller : tellerObjects){
                tellers.add((Teller)teller);
            }

            tellerQueue.clear();
            tellerQueue.addAll(tellers);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////

            Teller nextTeller = tellerQueue.poll();

            LocalTime serviceTime = nextTeller.getAvailableTime().isBefore(event.getArrivalTime())
                    ? event.getArrivalTime()
                    : nextTeller.getAvailableTime();

            LocalTime timeCompletion = serviceTime.plusMinutes(event.getServiceDuration());

            System.out.println(event.getCustomerName()
                    + " "
                    + event.getArrivalTime()
                    + " "
                    + serviceTime
                    + " "
                    + nextTeller.getName()
                    + " "
                    + timeCompletion);

            nextTeller.setAvailableTime(timeCompletion);

            tellerQueue.add(nextTeller);
        }
    }

    // INITIALIZES THE TELLERS INSIDE OF THE PRIORITY QUEUE
    private static PriorityQueue<Teller> tellerInit(){
        Teller t1 = new Teller("Kate", LocalTime.of(9, 0), 0, Bank.getInstance());
        Teller t2 = new Teller("Bob", LocalTime.of(9, 0), 1, Bank.getInstance());
        Teller t3 = new Teller("Alice", LocalTime.of(9, 0), 2, Bank.getInstance());

        PriorityQueue<Teller> tellerQueue = new PriorityQueue<>(3, new TellerComparator());
        tellerQueue.add(t2);
        tellerQueue.add(t1);
        tellerQueue.add(t3);

        return tellerQueue;
    }

    // PARSES INFO FROM THE INPUT FILE
    private static List<Event> parseCustomerEventInfo(final String filename){
        List<Event> customerEvents = new ArrayList<>();
        if (filename != null && filename.trim().length() > 0) {
            File inputFile = new File(filename);

            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if(line .startsWith("C") || line.startsWith("c"))
                    {
                        // 1. SPLIT THE LINE DATA BASED ON WHITE SPACE
                        String [] custData = line.split(" ");

                        // 2. CONVERT THE TIME STRING TO LOCAL TIME
                        String [] timeInfo = custData[1].split(":");
                        LocalTime arrivalTime = LocalTime.of(Integer.parseInt(timeInfo[0]), Integer.parseInt(timeInfo[1]));

                        // 3. CREATE A CUSTOMER EVENT BASED ON THE LINE PROVIDED
                        customerEvents.add(new Event(custData[0], arrivalTime, Integer.parseInt(custData[2])));
                    }
                }
            }
            catch(IOException | NumberFormatException e) {
                exitOnError(ERROR_MSG);
            }
        } else {
            exitOnError(PROVIDE_FILE);
        }

        return customerEvents;
    }

    // HANDLES ERRORS IN THE APPLICATION
    private static void exitOnError(final String errorMsg)
    {
        System.out.println(errorMsg);
        System.exit(0);
    }
}
