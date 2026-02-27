import java.util.Arrays;
import java.util.LinkedList;

public class PCB { // Process Control Block

    public Process process;
    private static int nextPid = 1;
    public int pid;
    private OS.PriorityType priority;
    private int failNum = 0;
    public int[] deviceIDs = new int[10];
    public long sleepnum = 0;
    public VirtualToPhysicalMapping[] pagetable = new VirtualToPhysicalMapping[100];


    public boolean WaitForMessage = false;

    public LinkedList<KernelMessage> KMqueue = new LinkedList<>();

    PCB(UserlandProcess up, OS.PriorityType priority) {
        process = up;
        this.priority = priority;
        Arrays.fill(deviceIDs, -1);
    }

    public String getName() {
        //System.out.println(process.getClass().getName());
        return process.getClass().getName();
    }

    public int[] getDeviceIDs() {
        return deviceIDs;
    }

    OS.PriorityType getPriority() {
        return priority;
    }

    public void requestStop() {
        process.requestStop();
    }

    public void stop() { /* calls userlandprocess’ stop. Loops with Thread.sleep() until ulp.isStopped() is true.  */
        //System.out.println("Stopping process " + process + " with num " + process.SemaphoreNum());
        do{
            process.stop();
            try{
                Thread.sleep(sleepnum);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        } while (process.isStopped());
    }

    public void fail(){
        if (priority != OS.PriorityType.background)
        {
            failNum++;
            if (failNum >= 5) {
                failNum = 0;
                switch (priority) {
                    case realtime -> priority = OS.PriorityType.interactive;
                    case interactive -> priority = OS.PriorityType.background;
                }
            }
        }
    }

    public boolean isDone() { /* calls userlandprocess’ isDone() */
        return process.isDone();
    }

    void start() { /* calls userlandprocess’ start() */
        process.start();
    }

    public void setPriority(OS.PriorityType newPriority) {
        priority = newPriority;
    }

    public void addtoDeviceList(int inp)
    {
        for (int i = 0; i < deviceIDs.length; i++)
        {
            if  (deviceIDs[i] == -1)
            {
                deviceIDs[i] = inp;
                break;
            }
        }
    }

    public void removefromDeviceList(int inp)
    {
        deviceIDs[inp] = -1;
    }

    public void AddToKMQueue(KernelMessage km)
    {
        KMqueue.add(km);
    }

    public boolean CheckKMQueue(){
        return !KMqueue.isEmpty();
    }

    public int AddToTable(VirtualToPhysicalMapping inp)
    {
        for (int i = 0; i < pagetable.length; i++) {
            if (pagetable[i] == null) {
                pagetable[i] = inp;
                return i * 1024;
            }
        }
        return -1;
    }

    public void removeFromTable(int inp)
    {
        for  (int i = 0; i < pagetable.length; i++) {
            if (pagetable[i].PPN != inp) {
                pagetable[inp] = new VirtualToPhysicalMapping();
                break;
            }
        }
    }
}
