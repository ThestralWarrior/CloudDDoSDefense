package ddos;

public class TrafficInfo {
    public long timestamp;
    public int count;
    public TrafficInfo() {
        timestamp = System.currentTimeMillis();
        count = 0;
    }
}
