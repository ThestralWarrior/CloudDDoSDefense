package core;

import ddos.AttackDetector;

import java.util.List;

public class DetectionEngine {
    private final List<AttackDetector> attackDetectors;
    DetectionEngine(List<AttackDetector> attackDetectors) {
        this.attackDetectors = attackDetectors;
    }
    public void runAnalysis(String ipAddress, String protocol, int packetSize) {
        for(AttackDetector attackDetector: attackDetectors) {
            attackDetector.analyzePacket(ipAddress, protocol, packetSize);
            if(attackDetector.isAttackDetected()) {
                takeMitigationAction();
                break;
            }
        }
    }
    public void takeMitigationAction() {
        System.out.println("Mitigating attack.");
    }
    public void resetDetectors() {
        for(AttackDetector attackDetector: attackDetectors) {
            attackDetector.reset();
        }
    }
}
