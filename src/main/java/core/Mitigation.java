package core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Mitigation {
    private static final Map<String, Boolean> blockedIPs = new ConcurrentHashMap();
    public static boolean isBlocked(String ipAddress) {
        synchronized (blockedIPs) {
            return blockedIPs.getOrDefault(ipAddress, false);
        }
    }
    public static void mitigate(String ipAddress) {
        // AlertSystem.alert();
        synchronized (blockedIPs) {
            System.out.println("Taking mitigating measures - blacklisting IP Address " + ipAddress);
            blockedIPs.put(ipAddress, true);
        }
    }
}
