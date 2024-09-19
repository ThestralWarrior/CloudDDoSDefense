package ddos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class UdpFloodDetector implements AttackDetector {
    private static final int THRESHOLD = 10;
    private static final int RATE_LIMIT_MS = 1000;
    private static final Map<String, TrafficInfo> trafficMap = new ConcurrentHashMap<>();
    private final AtomicBoolean attackDetected = new AtomicBoolean(false);
    @Override
    public void analyzePacket(String ipAddress, String protocol, int packetSize) {
        if("UDP".equalsIgnoreCase(protocol)) {
            TrafficInfo info = trafficMap.computeIfAbsent(ipAddress, k -> new TrafficInfo());
            long currentTime = System.currentTimeMillis();
            synchronized (info) {
                if(currentTime - info.timestamp > RATE_LIMIT_MS) {
                    info.timestamp = currentTime;
                    info.count = 1;
                } else {
                    info.count++;
                }
                if(info.count > THRESHOLD) {
                    attackDetected.set(true);
                }
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
        trafficMap.clear();
    }
}
