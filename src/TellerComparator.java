import java.util.Comparator;

public class TellerComparator implements Comparator<Teller>
{
    @Override
    public int compare(Teller teller1, Teller teller2)
    {
        // 1. IF BOTH TELLER S ARE AVAILABLE/UNAVAILABLE TAKE TELLER CLOSEST TO DOOR
        if((teller1.isAvailable() && teller2.isAvailable())
                || (!teller1.isAvailable() && !teller2.isAvailable())){
            if(teller1.getCustomerProximity() < teller2.getCustomerProximity())
                return -1;
            return 1;
        }

        // 2. ONE TELLER IS AVAILABLE AND ONE IS NOT, TAKE THE AVAILABLE ONE
        if(teller1.isAvailable() && !teller2.isAvailable())
            return -1;
        return 1;
    }
}
