public class KernelMessage {
    public int senderPID;
    public int targetPID;
    public int messageType;
    public byte[] message;


    public KernelMessage(int senderPID, int targetPID, int messageType,  byte[] message) {//constructer
        this.senderPID = senderPID;
        this.targetPID = targetPID;
        this.messageType = messageType;
        this.message = message.clone();
    }

    public static KernelMessage copyMessage(KernelMessage ikm) {//static builder
        return new KernelMessage(ikm.senderPID, ikm.targetPID, ikm.messageType, ikm.message);
    }

    @Override
    public String toString() {//for debug
        return "Sender: " + senderPID + ", Target: " + targetPID + ", MessageType: " + messageType + ", Message: " + message;
    }
}
