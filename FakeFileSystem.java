import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class FakeFileSystem implements Device {
    RandomAccessFile[] raf = new RandomAccessFile[10];//file access

    @Override
    public int Open(String inp){
        if (inp == null)//failed
            return -1;

        for (int i = 0; i < raf.length; i++) {//loops through till file is not null
            if (raf[i] == null)
            {
                try {
                    raf[i] = new RandomAccessFile(inp, "rw");//creates
                    return i;
                }
                catch (FileNotFoundException e) {//there was an issue
                    raf[i] = null;
                    return -1;
                }
            }
        }
        return -1;
    }

    @Override
    public void Close(int id) {
        try {
            raf[id].close();//closes
            raf[id] = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        raf[id] = null;
    }

    @Override
    public byte[] Read(int id, int size) {
        byte[] ret = new byte[size];//creates bytes to return
        try{
            int br = raf[id].read(ret);//reads
            if  (br == -1)//out of bounds
            {
                return new byte[0];
            }
            if (br < size)//if out of bounds
            {
                byte[] trimmed = new byte[br];//trims if neccessary
                System.arraycopy(ret, 0, trimmed, 0, br);
                return trimmed;
            }
            return ret;
        }
        catch (Exception e){
            e.printStackTrace();//something went wrong
            return new byte[0];
        }
    }

    @Override
    public void seek(int id, int to) {
        try {
            raf[id].seek(to);//goes to
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int write(int id, byte[] data) {
        try {
            raf[id].write(data);//writes
            return data.length;//returns if done
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;//if issue
        }
    }
}
