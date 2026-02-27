import java.util.Arrays;

public class FileTest extends UserlandProcess{
    @Override
    public void main()
    {
        int i = OS.Open("file test1");
        byte[] b = new byte[10];
        Arrays.fill(b, (byte)'a');
        OS.Write(i, b);
        OS.Seek(i, 0);
        byte[] r = OS.Read(i, 10);
        for (int ii = 0; ii < r.length; ii++)
        {
            System.out.println(r[ii]);
        }
        OS.Exit();
    }
}
