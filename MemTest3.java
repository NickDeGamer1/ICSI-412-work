public class MemTest3 extends UserlandProcess {
    public void main()
    {
        System.out.println("MemTest3");
        int location = OS.AllocateMemory(2048);
        byte test = 0;
        Hardware.Write(location + 3000,test);//should segfault
        OS.Sleep(200);
        System.out.println(Hardware.Read(location + 1));
        OS.Exit();
    }
}