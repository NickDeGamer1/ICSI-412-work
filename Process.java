import java.util.concurrent.Semaphore;

public abstract class Process implements Runnable{

    protected boolean expired = false;//is expired,
    final protected Semaphore semaphore = new Semaphore(0);//pauses the process
    public Thread thread;//makes it a thread

    public Process() {//constructer, starts thread
        thread = new Thread(this);
        thread.start();
    }

    public void requestStop(){
        expired = true;//makes program stop
    }

    public abstract void main();//to be overloaded

    public boolean isStopped() {
        return semaphore.availablePermits() != 0;//checks if is current
    }

    public boolean isDone() {
        return !thread.isAlive();
    }

    public void stop() {
        try {
            semaphore.acquire();//aquires from semaphore
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    public void sleep(int ms){
//        OS.Sleep(ms);
//        stop();
//    }

    public void start() {
        semaphore.release();//Starts thread
    }

    @Override
    public void run() { // This is called by the Thread - NEVER CALL THIS!!!
        stop();//pauses thread once its made
        main();
    }

    public void cooperate() {
        if (expired)
        {
            expired = false;//ends
            OS.switchProcess();
        }
    }

    public int SemaphoreNum() {
        return semaphore.availablePermits();
    }
}
