package ddos;

public interface AttackDetector {
    void analyzePacket(String ipAddress, String protocol, int packetSize);
    boolean isAttackDetected();
    void reset();
}
