import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Transaction{
    // A customer can only be checked into one place at a time.
    private int id;
    private String checkinStation;
    private int checkinTime;
    private String checkoutStation;
    private int checkoutTime;
    Transaction(int id, String checkinStation, int checkinTime){
        this.id = id;
        this.checkinStation = checkinStation;
        this.checkinTime = checkinTime;
    }
    public void setCheckout(String checkoutStation,int checkoutTime){
        this.checkoutStation = checkoutStation;
        this.checkoutTime = checkoutTime;
    }
    public String getCheckinStation(){
        return this.checkinStation;
    }
    public String getCheckoutStation(){
        return this.checkoutStation;
    }
    public String getCheckinout(){
        StringBuilder sb = new StringBuilder();
        sb.append(checkinStation);
        sb.append("-->");
        sb.append(checkoutStation);
        return sb.toString();
    }
    public int getStayTime(){
        return checkoutTime - checkinTime;
    }
}
class AverageTimeTable{
    private String checkinStation;
    private String checkoutStation;
    private List<Integer> stayTimes = new ArrayList<>();
    AverageTimeTable(String checkinStation,String checkoutStation,int time){
        this.checkinStation = checkinStation;
        this.checkoutStation = checkoutStation;
        stayTimes.add(time);
    }
    public void addStayTime(int time){
        stayTimes.add(time);
    }
    public double getAverageTime(){
        return stayTimes.stream().mapToDouble(Integer::doubleValue).average().getAsDouble();
    }
    // public String getCheckinStation{
    //     return this.checkinStation;
    // }
    // public String getCheckoutStation{
    //     return this.checkoutStation;
    // }
    // @Override
    // public equals(Object anObject){
    //     if (this == anObject) {
    //         return true;
    //     }
    //     if (anObject instanceof AverageTimeTable) {
    //         AverageTimeTable another = (AverageTimeTable)anObject;
    //         return another.getCheckinStation.equals(checkinStation)
    //             &&another.checkoutStation.equals(checkoutStation);
    //     }
    //     return false;
    // }
    // @Overrride
    // public int hashCode() {
    //     return Objects.hash(checkinStation,checkoutStation);
    // }
}

public class UndergroundSystem {
    // remove transaction after checkout
    private Map<Integer, Transaction> transactions = new HashMap<>();
    private Map<String ,AverageTimeTable> averageTimes = new HashMap<>();
    public UndergroundSystem() {

    }

    public void checkIn(int id, String stationName, int t) {
        transactions.put(id,new Transaction(id, stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        Transaction transaction = transactions.get(id);
        // in case this user checkin after checkout
        transactions.remove(id, transaction);
        transaction.setCheckout(stationName,t);
        String checkinout = transaction.getCheckinout();
        AverageTimeTable averageTime = averageTimes.getOrDefault(checkinout,null);
        if(averageTime == null){
            averageTime = new AverageTimeTable
                    (transaction.getCheckinStation(),
                            transaction.getCheckoutStation(),transaction.getStayTime());
            averageTimes.put(checkinout,averageTime);
        }else{
            averageTime.addStayTime(transaction.getStayTime());
        }
    }

    public double getAverageTime(String startStation, String endStation) {
        StringBuilder sb = new StringBuilder();
        sb.append(startStation);
        sb.append("-->");
        sb.append(endStation);
        return averageTimes.get(sb.toString()).getAverageTime();
    }
}

/**
 * Your UndergroundSystem object will be instantiated and called as such:
 * UndergroundSystem obj = new UndergroundSystem();
 * obj.checkIn(id,stationName,t);
 * obj.checkOut(id,stationName,t);
 * double param_3 = obj.getAverageTime(startStation,endStation);
**/