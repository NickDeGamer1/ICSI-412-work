public class HelloWorld extends UserlandProcess {

    public void main()
    {
        while(true)
        {
            System.out.println("Hello World");
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
