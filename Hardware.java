import java.util.Arrays;

public class Hardware {

    private static byte[] PhMem = new byte[1048576];
    //private static byte[] PhMem = new byte[2048]; //for testing purposes
    private static int[][] TLB = new int[2][2];

    public static byte Read(int address)
    {
        int page = address / 1024;
        int offset = address % 1024;//page and offset

        int pp = CheckTLB(page);

        if (pp == -1)
        {
            OS.GetMapping(page);//does it again
            pp = CheckTLB(page);
        }

        if  (pp != -1)//catch fail
        {
            int Padd = pp * 1024 + offset;
            return PhMem[Padd];
        }

        return 0;//killed
    }



    public static void Write(int address, byte value)
    {
        int page = address / 1024;
        int offset = address % 1024;//page and offset

        int pp = CheckTLB(page);

        if (pp == -1)
        {
            OS.GetMapping(page);//does it agian
            pp = CheckTLB(page);
        }

        if  (pp != -1)//catch fail
        {
            int Padd = pp * 1024 + offset;
            PhMem[Padd] = value;
        }
    }

    private static int CheckTLB(int page)
    {
        for (int i = 0; i < 2; i++)//loops through
        {
            if (TLB[i][0] == page){
                return TLB[i][1];//returns TLB
            }
        }
        return -1;//if not in TLB
    }


    public static void setTLB(int slot, int vpn, int pp)
    {
        TLB[slot][0] = vpn;//clears
        TLB[slot][1] = pp;
    }

    public static void ClearTLB()
    {
        TLB[0][0] = -1;
        TLB[0][1] = -1;
        TLB[1][0] = -1;
        TLB[1][1] = -1;
    }

    public static byte[] KernalReadBlock(int address)
    {
        byte[] toRet = new byte[1024];
        for (int i = 0; i < 1024; i++)
        {
            toRet[i] = PhMem[address + i];
        }
        return toRet;
    }

    //should be used by kernel only

    public static void KernalWriteBlock(int address, byte[] value)
    {
        for (int i = 0; i < 1024; i++)
        {
        PhMem[address + i] = value[i];
        }
    }

    public static void KernalClearBlock(int address)
    {
        for (int i = 0; i < 1024; i++)
        {
            PhMem[address + i] = (byte) 0;
        }
    }

    public static int KernalGetMemSize()
    {
        return PhMem.length / 1024;//so I dont have to change the mem size, im like 90% sure the hardware does this
    }
}
