public class RandomTest extends UserlandProcess {

    @Override
    public void main() {
        int i = OS.Open("random 100");
        byte[] b = new byte[10];
        b = OS.Read(i, b.length);

        for (int j = 0; j < b.length; j++) {
            System.out.println(b[j]);
        }

        OS.Exit();
    }
}
