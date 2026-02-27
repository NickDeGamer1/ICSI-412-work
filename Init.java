public class Init extends UserlandProcess {

    @Override
    public void main() {
        OS.CreateProcess(new MemTest(), OS.PriorityType.interactive);
        OS.CreateProcess(new MemTest2(), OS.PriorityType.interactive);
        OS.CreateProcess(new MemTest3(), OS.PriorityType.interactive);//should segfault
        OS.Exit();
    }
}
