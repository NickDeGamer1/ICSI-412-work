import java.util.Arrays;

public class VFS {

    Device[] devices = new Device[10];
    int[] ids = new int[10];

    public VFS() {
        Arrays.fill(devices, null);//fills with null and -1
        Arrays.fill(ids, -1);
    }

    public int Open(String inp){
        String[] inps = inp.split(" ", 2);//splits into multiple strings
        String devName = inps[0];//sets vars
        String args = inps[1];

        for (int i = 0; i < ids.length; i++) {//loops through ids
            if (ids[i] == -1) {//if not found
                switch (devName) {//case for devName
                    case "file" -> devices[i] = new FakeFileSystem();
                    case "random" -> devices[i] = new RandomDevice();
                }
                if (devices[i] == null) {
                    return -1;// input not found
                }
                try {
                    ids[i] = devices[i].Open(args);
                    //System.out.println(ids[i]);
                    return i;//returns index of big array
                }
                catch (Exception e) {
                    return -1;// cant open
                }
            }
        }
        return -1;
    }

    public void close(int id){
        devices[id].Close(ids[id]);//calls close in device
        devices[id] = null;//clears index of array
        ids[id] = -1;
    }

    public byte[] Read(int id, int size){
        return devices[id].Read(ids[id], size);//passthrough
    }

    public int Write(int id, byte[] data){
        return devices[id].write(ids[id], data);
    }

    public void seek(int id, int to){
        devices[id].seek(ids[id], to);//passthrough
    }
}
