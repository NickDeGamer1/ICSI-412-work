public class GoodbyeWorld extends UserlandProcess {

    public void main()
    {
        while(true)
        {
            System.out.println("Goodbye World");
            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            cooperate();
        }
    }
}