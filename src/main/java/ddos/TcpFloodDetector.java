package ddos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class TcpFloodDetector implements AttackDetector {
    private static final int THRESHOLD = 10;
    private static final Map<String, Integer> trafficCount = new ConcurrentHashMap<>();
    private final AtomicBoolean attackDetected = new AtomicBoolean(false);
    @Override
    public void analyzePacket(String ipAddress, String protocol, int packetSize) {
        if("TCP".equalsIgnoreCase(protocol)) {
            int count = trafficCount.getOrDefault(ipAddress, 0) + 1;
            trafficCount.put(ipAddress, count);
            if(count > THRESHOLD) {
                attackDetected.set(true);
            }
        }
    }

    @Override
    public boolean isAttackDetected() {
        return attackDetected.get();
    }

    @Override
    public void reset() {
        attackDetected.set(false);
        trafficCount.clear();
    }
}
