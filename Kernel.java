import java.util.Arrays;
import java.util.Random;

/*
Tests were done by changing the size of the Hardware PhMem, everything should work
 */

public class Kernel extends Process  {

    private Scheduler scheduler = new Scheduler();
    private VFS vfs = new VFS();
    private boolean[] PageUse;
    private int swFile = 0;
    private int NextSwapPage = 0;


    public Kernel() {
        super();//FOR THE LOVE OF GOD DON'T FORGET TO CALL THE SUPER!!!
        PageUse = new boolean[Hardware.KernalGetMemSize()];//sizeOfMem
        Arrays.fill(PageUse, false);//mem
        swFile = vfs.Open("file swapFile");
    }

    @Override
    public void main() {
            while (true) { // Warning on infinite loop is OK...
                //System.out.println(OS.currentCall);
                OS.retVal = null;
                switch (OS.currentCall) { // get a job from OS, do it
                    case CreateProcess ->  // Note how we get parameters from OS and set the return value
                            OS.retVal = CreateProcess((UserlandProcess) OS.parameters.get(0), (OS.PriorityType) OS.parameters.get(1));
                    case SwitchProcess -> SwitchProcess();
                    // Priority Schduler
                    case Sleep -> Sleep((int) OS.parameters.get(0));
                    case GetPID ->
                            OS.retVal = GetPid();
                    case Exit -> Exit();
                    // Devices
                    case Open ->
                            OS.retVal = Open((String) OS.parameters.get(0));
                    case Close -> Close((int) OS.parameters.get(0));
                    case Read ->
                            OS.retVal = Read((int) OS.parameters.get(0), (int) OS.parameters.get(1));
                    case Seek -> Seek((int) OS.parameters.get(0), (int) OS.parameters.get(1));
                    case Write ->
                            OS.retVal = Write((int) OS.parameters.get(0), (byte[])  OS.parameters.get(1));
                    // Messages
                    case GetPIDByName ->
                            OS.retVal = GetPidByName((String) OS.parameters.get(0));
                    case SendMessage ->
                                SendMessage((KernelMessage) OS.parameters.get(0));
                    case WaitForMessage ->
                                OS.retVal = WaitForMessage();
                    // Memory
                    case GetMapping -> GetMapping((int) OS.parameters.get(0));
                    case AllocateMemory ->
                            OS.retVal = AllocateMemory((int) OS.parameters.get(0));
                    case FreeMemory ->
                            OS.retVal = FreeMemory((int) OS.parameters.get(0), (int) OS.parameters.get(1));
                }
                // TODO: Now that we have done the work asked of us, start some process then go to sleep.
                scheduler.StartPCB();
                stop();
            }
    }

    public PCB getCurrentPCB(){
        return scheduler.GetCurrentPCB();
    }

    private void SwitchProcess() {
        scheduler.SwitchProcess();
    }

    // For assignment 1, you can ignore the priority. We will use that in assignment 2
    private int CreateProcess(UserlandProcess up, OS.PriorityType priority) {
        return scheduler.CreateProcess(up, priority);
    }

    private void Sleep(int mills) {
        scheduler.sleep(mills);
    }

    private void Exit() {
        int[] ids = scheduler.getAllDevIDs();
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] != -1) {
                vfs.close(ids[i]);
            }
        }
        FreeAllMemory(scheduler.CurrentPCB);
        scheduler.EndProcess();
    }

    private int GetPid() {
        return scheduler.getPID();
    }

    private int Open(String s) {
        int i = vfs.Open(s);
        scheduler.addToDeviceList(i);
        return i;
    }

    private void Close(int id) {
        vfs.close(id);
        scheduler.removeFromDeviceList(id);
    }

    private byte[] Read(int id, int size) {
        return vfs.Read(id, size);
    }

    private void Seek(int id, int to) {
        vfs.seek(id, to);
    }

    private int Write(int id, byte[] data) {
        return vfs.Write(id, data);
    }

    private void SendMessage(KernelMessage km) {
        km.senderPID = scheduler.getPID();//sets to right sender
        scheduler.PList.get(km.targetPID).AddToKMQueue(km);
        scheduler.CheckKMs(km);//checks
    }

    private KernelMessage WaitForMessage(){
        PCB pcb = scheduler.GetCurrentPCB();//sets as local

        if (pcb.CheckKMQueue()){//has something
            return pcb.KMqueue.removeFirst();//returns
        }
        else {
            scheduler.PutInQueue();//puts in waiting queue
            return null;//does this for now
        }
    }

    private int GetPidByName(String name) {
        for (int i = 0; i < scheduler.PList.size(); i++){
            if (scheduler.PList.get(i+1) != null)
                if (scheduler.PList.get(i+1).getName().equals(name))//looks in hashmap
                    return scheduler.PList.get(i+1).pid;
        }
        return -1;//if not found
    }

    public KernelMessage ReadMessage() {
        return scheduler.toReturn;//returns saved message
    }

    private void GetMapping(int virtualPage) {

        PCB pcb = scheduler.GetCurrentPCB();

        VirtualToPhysicalMapping page = pcb.pagetable[virtualPage];

        if (page == null) {
            System.out.println("There was a segfault");
            Exit();
            return;
        }

        if (page.PPN != -1)//in physical memory
        {
            int GoTo = (int)(Math.random()*2);//sets it as random in pcb
            Hardware.setTLB(GoTo, virtualPage, page.PPN);
            return;
        }

        int ppn = FindPhysicalPage();

        if (ppn == -1)//no free pages
        {
            PCB pcb2 = scheduler.getRandomPCB();
            VirtualToPhysicalMapping victimPage = null;

            int vp = 0;
            while (victimPage == null) {
                if (pcb2.pagetable[vp] != null && pcb2.pagetable[vp].PPN != -1)
                {
                    victimPage = pcb2.pagetable[vp];
                }
                vp++;
            }

            if (victimPage.DPN == -1) {//assign page
                victimPage.DPN = NextSwapPage++;
            }


            byte[] towrite = Hardware.KernalReadBlock(victimPage.PPN);

            vfs.seek(swFile, victimPage.DPN * 1024);
            vfs.Write(swFile, towrite);

            ppn = victimPage.PPN;
            victimPage.PPN = -1;
            PageUse[ppn] = false;
        }

        page.PPN = ppn;
        PageUse[page.PPN] = true;

        if (page.DPN != -1)//load from file
        {
            vfs.seek(swFile, page.DPN * 1024);
            byte[] toRam = vfs.Read(swFile, 1024);
            Hardware.KernalWriteBlock(page.PPN * 1024,  toRam);
//            for (int i = 0; i < toRam.length; i++)
//                Hardware.Write((page.PPN * 1024) + i, toRam[i]);
        }
        else//fill with 0s
        {
            Hardware.KernalClearBlock(page.PPN * 1024);
        }

        int GoTo = (int)(Math.random()*2);//sets it as random in pcb
        Hardware.setTLB(GoTo, virtualPage, page.PPN);
    }

    private int FindPhysicalPage()
    {
        for (int i = 0; i < PageUse.length; i++)
            if (!PageUse[i])
                return i;
        return -1;
    }

    private int AllocateMemory(int size) {//Now Lazy, like me turning this in on day of
        if (size % 1024 != 0)
            return -1;

        int pagesNeeded = size / 1024;

        VirtualToPhysicalMapping[] p = new VirtualToPhysicalMapping[pagesNeeded];

        for (int i = 0; i < pagesNeeded; i++) {
            p[i] = new VirtualToPhysicalMapping();
        }

        int toRet = scheduler.CurrentPCB.AddToTable(p[0]);//add to PCB
        for (int i = 1; i < pagesNeeded; i++) {
            scheduler.CurrentPCB.AddToTable(p[i]);
        }
        return toRet;
    }

    private boolean FreeMemory(int pointer, int size) {
        if (size % 1024 != 0 || pointer % 1024 != 0)
            return false;//if not working

        int page =  pointer / 1024;
        size = size / 1024;

        PCB pcb = scheduler.GetCurrentPCB();

        for (int i = page; i < page + size; i++) {
            VirtualToPhysicalMapping m = pcb.pagetable[i];
            if (m != null)
            {
                if (m.PPN != -1)
                {
                    PageUse[m.PPN] = false;//clears pageUse
                }
                pcb.pagetable[i] = null;
            }
        }
        return true;
    }

    private void FreeAllMemory(PCB currentlyRunning) {
        for (int i = 0; i < currentlyRunning.pagetable.length; i++){
            VirtualToPhysicalMapping m = currentlyRunning.pagetable[i];
            if (m != null) {//loops throgh and frees
                if (m.PPN != -1) {
                    PageUse[m.PPN] = false;
                }

                currentlyRunning.pagetable[i] = null;//Remove mapping
            }
        }
    }
}