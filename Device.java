import java.io.FileNotFoundException;

public interface Device {
    int Open (String s) throws FileNotFoundException;
    void Close (int id);
    byte[] Read (int id, int size);
    void seek(int id, int to);
    int write(int id, byte[] data);
}
