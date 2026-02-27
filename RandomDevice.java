import java.util.Arrays;
import java.util.Random;

public class RandomDevice implements Device {

    Random[] devices = new Random[10];//randomArray

    public RandomDevice()
    {
        Arrays.fill(devices, null);
    }

    @Override
    public int Open(String s) {
        Random r;//to be filled
        if (s == null)
            r = new Random();
        else//filled if inp seed
            r = new Random(Integer.parseInt(s));

        for (int i = 0; i < devices.length; i++) {
            if (devices[i] == null)//loops till null is found
            {
                devices[i] = r;//fills
                return i;//returns index
            }
        }
        return -1;//array full
    }

    @Override
    public void Close(int id) {
        //System.out.println("Closing device " + id);
        devices[id] = null;//nullifies id
    }

    @Override
    public byte[] Read(int id, int size) {
        byte[] ret = new byte[size];//makes new array to be filled
        devices[id].nextBytes(ret);//fills
        return ret;
    }

    @Override
    public void seek(int id, int to) {
        byte[] dis = new byte[to];//skips
        devices[id].nextBytes(dis);
    }

    @Override
    public int write(int id, byte[] data) {
        return 0;//doesnt do anything
    }
}
