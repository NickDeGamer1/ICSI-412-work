public class MemTest2 extends UserlandProcess {
    public void main()
    {
        System.out.println("MemTest2");
        int location = OS.AllocateMemory(2048);//multi memory at the same time test
        byte test = 0;
        System.out.println(location);
        Hardware.Write(location + 1024,test);
        OS.Sleep(200);
        System.out.println(Hardware.Read(location + 1024));
        OS.Exit();
    }
}