public class IdleProcess extends UserlandProcess {
    @Override
    public void main() {
        while (true) {
            //System.out.println("Idle Process");
            try {
                Thread.sleep(50);
                cooperate();
            } catch (Exception e) { }
        }
    }
}
