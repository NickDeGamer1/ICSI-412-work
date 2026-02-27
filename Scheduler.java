import java.util.*;

public class Scheduler {

    //private LinkedList<PCB> processes = new LinkedList<>();//list of processes

    private LinkedList<PCB> realTime = new LinkedList<>();
    private LinkedList<PCB> interactive = new LinkedList<>();
    private LinkedList<PCB> background = new LinkedList<>();

    private LinkedList<PCB> sleeping = new LinkedList<>();
    public HashMap<Integer, PCB> WaitList = new HashMap<>();
    public KernelMessage toReturn = null;

    public HashMap<Integer, PCB> PList = new HashMap<>();

    private int processNum = 0;

    private Timer timer = new Timer();
    public PCB CurrentPCB = null;

    public Scheduler() {
        timer.schedule(new TimerTask() {//this repeatably calls the requestStop function every 250 ms
            @Override
            public void run() {
                if (CurrentPCB != null) {
                    CurrentPCB.fail();
                    CurrentPCB.requestStop();
                }
            }
        }, 250, 250);
    }

    public int CreateProcess(UserlandProcess up, OS.PriorityType p) {
        if (CurrentPCB != null) {
            CurrentPCB.requestStop();
        }
        PCB pc = new PCB(up, p);

        pc.pid = processNum;
        processNum++;

        switch(p) {
            case OS.PriorityType.realtime -> realTime.add(pc);
            case OS.PriorityType.interactive -> interactive.add(pc);
            case OS.PriorityType.background -> background.add(pc);
        }

        PList.put(pc.pid, pc);//adds to hashmap

        if (CurrentPCB != null) {
            CurrentPCB.start();
        }
        if (CurrentPCB == null) {
            SwitchProcess();//starts process if empty
        }

        return pc.pid;
    }

    public void SwitchProcess() {
        CheckSleep();
        if (CurrentPCB != null) {
            if (!CurrentPCB.isDone()){
                //processes.addLast(CurrentPCB);
                switch(CurrentPCB.getPriority()) {
                    case OS.PriorityType.realtime -> realTime.add(CurrentPCB);
                    case OS.PriorityType.interactive -> interactive.add(CurrentPCB);
                    case OS.PriorityType.background -> background.add(CurrentPCB);
                }
            }
        }
        Random rand = new Random();

        Hardware.ClearTLB();

        int pType = rand.nextInt(0, 10) + 1;
        //System.out.println(pType);
        switch(pType) {
            case 1:
            case 2:
            case 4:
            case 5:
            case 7:
            case 8:
                CurrentPCB = CheckLists(OS.PriorityType.realtime, 0);//realTime.removeFirst();
                break;
            case 3:
            case 6:
            case 9:
                CurrentPCB = CheckLists(OS.PriorityType.interactive, 0);
                break;
            case 10:
                CurrentPCB = CheckLists(OS.PriorityType.background, 0);
                break;
        }
    }

    private PCB CheckLists(OS.PriorityType p, int num) {
        if(num > 3)
        {
            throw new RuntimeException("No processes found");
        }
        switch(p) {
            case OS.PriorityType.realtime:
                if (realTime.isEmpty())
                    return CheckLists(OS.PriorityType.interactive, num+1);
                else
                    return realTime.removeFirst();
            case OS.PriorityType.interactive:
                if (interactive.isEmpty())
                    return CheckLists(OS.PriorityType.background, num+1);
                else
                    return interactive.removeFirst();
            case OS.PriorityType.background:
                if (background.isEmpty())
                    return CheckLists(OS.PriorityType.realtime, num+1);
                else
                    return background.removeFirst();
            default:
                throw new RuntimeException("Unknown priority type");
        }
    }

    public PCB GetCurrentPCB() {
        return CurrentPCB;
    }

    public void StartPCB() {
        //System.out.println("Starting PCB");
        if (CurrentPCB != null) {
            //System.out.println("Current PCB is " + CurrentPCB.process.SemaphoreNum());
            //System.out.println(CurrentPCB.process.getClass().getName());
            CurrentPCB.start();//starts thread
        }
    }

    public int getPID(){
        return CurrentPCB.pid;
    }

    private void CheckSleep(){
        if (!sleeping.isEmpty()) {
            for (int i = 0; i < sleeping.size(); i++) {
                //System.out.print("Process is waking up: ");
                long num = System.currentTimeMillis();
                //System.out.println(sleeping.get(i).sleepnum < num);
                if (sleeping.get(i).sleepnum < num) {
                    sleeping.get(i).sleepnum = 0;
                    switch (sleeping.get(i).getPriority()) {
                        case OS.PriorityType.realtime:
                            realTime.add(sleeping.remove(i));
                            i--;
                            break;
                        case OS.PriorityType.interactive:
                            interactive.add(sleeping.remove(i));
                            i--;
                            break;
                        case OS.PriorityType.background:
                            background.add(sleeping.remove(i));
                            i--;
                            break;
                    }
                }
            }
            //System.out.println(background);
        }
    }

    public void sleep(int mills) {
        CurrentPCB.sleepnum = System.currentTimeMillis() + (long)mills;
        sleeping.add(CurrentPCB);
        CurrentPCB = null;
        SwitchProcess();
    }

    public void CheckKMs(KernelMessage km) {
        //System.out.println(WaitList.size());
        if (!WaitList.isEmpty()) {
            if (WaitList.get(km.targetPID).CheckKMQueue()) {//puts back in run list
                switch (WaitList.get(km.targetPID).getPriority()) {
                    case OS.PriorityType.realtime -> realTime.add(WaitList.remove(km.targetPID));
                    case OS.PriorityType.interactive -> interactive.add(WaitList.remove(km.targetPID));
                    case OS.PriorityType.background -> background.add(WaitList.remove(km.targetPID));
                }
            }
            toReturn = km;
        }
        SwitchProcess();
    }

    public void PutInQueue() {
        WaitList.put(CurrentPCB.pid, CurrentPCB);//puts in waitlist
        CurrentPCB = null;
        //System.out.println("Current PCB has been put in queue");
        SwitchProcess();
    }


    public int[] getAllDevIDs(){
        return CurrentPCB.getDeviceIDs();
    }

    public void addToDeviceList(int inp) {
        CurrentPCB.addtoDeviceList(inp);
    }

    public void removeFromDeviceList(int inp) {
        CurrentPCB.removefromDeviceList(inp);
    }

    public void EndProcess() {
        PList.remove(CurrentPCB.pid);//removes from hashmap
        CurrentPCB = null;
        SwitchProcess();
    }

    public PCB getRandomPCB(){
        LinkedList<PCB> randPCB = new LinkedList<>();

        randPCB.addAll(realTime);
        randPCB.addAll(interactive);
        randPCB.addAll(background);
        randPCB.addAll(sleeping);
        while (true) {
            int rand = (int) (Math.random() * randPCB.size());

            PCB ToReturn = randPCB.get(rand);
            for (int q = 0; q < ToReturn.pagetable.length; q++) {
                if (ToReturn.pagetable[q] != null && ToReturn.pagetable[q].PPN != -1)
                    return ToReturn;
            }
        }
    }
}
