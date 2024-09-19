package core;

import ddos.AttackDetector;

import java.util.List;

public class DetectionEngine {
    private final List<AttackDetector> attackDetectors;
    DetectionEngine(List<AttackDetector> attackDetectors) {
        this.attackDetectors = attackDetectors;
    }
    public boolean runAnalysis(String ipAddress, String protocol, int packetSize) {
        if(Mitigation.isBlocked(ipAddress)) return true;
        for(AttackDetector attackDetector: attackDetectors) {
            attackDetector.analyzePacket(ipAddress, protocol, packetSize);
            if(attackDetector.isAttackDetected()) {
                Mitigation.mitigate(ipAddress); // made changes
                return true;
            }
        }
        return false;
    }
    public void resetDetectors() {
        for(AttackDetector attackDetector: attackDetectors) {
            attackDetector.reset();
        }
    }
}
