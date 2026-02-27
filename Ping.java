import java.util.Arrays;

public class Ping extends UserlandProcess {
    public void main()
    {
        System.out.println("Ping id: " + OS.GetPID());
        int ToSend = OS.GetPidByName("Pong");
        int self = OS.GetPID();
        byte[] toSend = new byte[10];
        Arrays.fill(toSend, (byte) 0);
        KernelMessage km = new KernelMessage(self, ToSend, 0, toSend);
        OS.SendMessage(km);
        while(true)
        {
            cooperate();
            KernelMessage incoming = OS.WaitForMessage();
            System.out.println("Ping: from: " + incoming.senderPID + " to: " + incoming.targetPID + " what: " + incoming.messageType);
            incoming.targetPID = ToSend;
            incoming.senderPID = self;
            OS.SendMessage(incoming);
        }
    }
}
