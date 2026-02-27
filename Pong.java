import java.util.Arrays;

public class Pong extends UserlandProcess {
    public void main()
    {
        System.out.println("Pong id: " + OS.GetPID());

        int ToSend = OS.GetPidByName("Ping");
        int self = OS.GetPID();
        byte[] toSend = new byte[10];
        Arrays.fill(toSend, (byte) 0);

        while (true) {
            KernelMessage incoming = OS.WaitForMessage();
            System.out.println("Pong: from: " + incoming.senderPID + " to: " + incoming.targetPID + " what: " + incoming.messageType);
            incoming.targetPID = ToSend;
            incoming.senderPID = self;
            incoming.messageType+=1;
            OS.SendMessage(incoming);
            cooperate();
        }
    }
}
