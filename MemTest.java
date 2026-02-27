public class MemTest extends UserlandProcess {
    public void main()
    {
        System.out.println("MemTest1");
        int location = OS.AllocateMemory(2048);//does this work
        Hardware.Write(location + 1,(byte)1);
        Hardware.Write(location + 1024,(byte)1);
        OS.Sleep(200);
        System.out.println(Hardware.Read(location + 1));
        System.out.println(Hardware.Read(location + 1024));
        OS.Exit();
    }
}
