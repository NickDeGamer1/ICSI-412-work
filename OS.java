import java.util.ArrayList;
import java.util.List;

/*
...................................+........................................@....................%................................@..............
.::::.@..::::::::::::::.@::::::-:*+..::::::::::::.....#%@:......:--@@@.:@.@.........#@:::.+..::..@.@...::::::::::::::::::::::...@.....@::...::::.
.:::.-@.:::::::::::::::..*:.:::::..:::::::::::::::::::....@@-*@:::......@.@.:::.:::......#-:::::..@..:::::::::::::::::::::...-@...:::....%@.:....
....=@:.::::::::::::::::.:+.:::::::::-::.:.......::::::::.......:::::::.....:::::::::::..+.::::::.....:::::::::::::::::....@%...::::::...@..::+@.
.%=@...::::-::::::::::-:::+:::::::::....:#++==@%=:::.:::::::::::::::::::::....:::::::::.#=.::::::::::::::::::::::::::...=@....:::::...:@...:::.@.
.=...:::::::::::::::::::::.:.::::::-:.*@.........:+#..::.::::::::-:::....=*%%-...:::::.-:=.::::::::::::::::::::::::...#=...:::::::..%@...:::::...
.:::::::::::::::::::::::::.%-.::::::.@...:::::::::..%-::::::::.:::....@@:::...-@*:::::.=:=::::::::::::::::::::::::.:%@:.:::::::...@+..::::::::::.
.::::::-::-::::::::::::-:::.#.::::::...::::::::::::.:::::::::::::::#@:....:::....:::::::.+.:::::::::::::::::::::..#@...::::::..-@:..::::::::::::.
.::::::::::::::::::::::::::.@.:::::::::::.........:::::::::.::::-::.-..:-:::::::::::::::.#...::::::::::::::::...@#...::::::..=@...::::::::::::::.
.::::::::::::::::::::::::::.+=.:::::::....=@@@*-@=...:::::::::::::.:........::::::::::::.@+@-...:::::::::::..+@:...:::::...=@..:::::::::::::::::.
.:::::::::::::::::::::-:::::.@.::::::..@*.........=@:.::::::::::::...@@@@@@:..::::::::::.#..:@@=:..::::::..*@...:::::::..=@..:::::::::::::::::::.
.::::-::::::::::::::::::::::.%..::::::@..::::::::...%::.::-::::::::@.......:@..::::::::::#.:....#=..:....@+...::::::...+@..:::::::::::::::::::::.
.:::::::-::::::::::--:::::::.=@.:::::...::-:-:::::...=::::::::::::=..:::::::.#-..::::::::#.::::...@.::=@....::::::...@%...::::::::::::::::::::::.
....:::.::::::::::-:::::::::.:#::::::::-::::::::::::::.::...:::::.::::::::::..+:.::::::..=:::::::.*:.-...::::::...#@+...::::::::::::::::::::::::.
.@@:............:::::-::::-::.=-.:-::::..................@+.:-:::::::.......:.:%::::::::.#.:-::::.@@+@@*+=:::...@@....::::::::::::::::::::::::::.
.%.:@@@@@@@@@@@:.:::-::::::-::.=.:::::.@+@*-@....@@*@@@...@.::::.:::..#@..#:....::::::::.%.::::::.==.........@@....:::::::::::::.:::::::::::::::.
...............@.:::-::::::::::+.:::::.@#..*..@@%.@...%...*.:::@=:...@..@@.=.@@.::::::::.@.:::::..@-.::...-@@...::::::-:-:-:::::::::::::::::::::.
..::..@@.@@@...@..:::---::::::.+.:::::...::.-:......:.....@.:::..:*#@@@*:.@*...:::::::::.#:.:::..#@.....@@...:::::--:::.::::::::::::::::::::::::.
.::::.@.@@..@.-@..::::::-:::::.+.:-::::::::::.::::::::::.@-.::::::..........::::-::::::..#-.::.:-@...:@-...::::::::::::::..:::::::::::::::::::::.
..:::.@@:@#@..:@..::::::::::::.+.::::::::::::::::-:::::.@-..::::::::+.:::::::::::::::-::.*:::..=%...@...::....--:-::-*+*+==:::......:::....:::::.
..::-..*@=.....@.:-:::::::::::.+.::::::::::::-:::::::..#..::::::::::+:.::::::::::::-::::.*::..@:...:..::::=#=::::::::..:..:::::#**+*+::-@=..::::.
.:::::.........@..-.::::::::::.+:::::::::::::::::::..=@..:::::::-:::.#.::::::::::::::::::#...@....#+.:.:-=..::::::::::::::::::....:..::..:@..:::.
.-::....:..#@..%..-:-:-:::::::.=.::::::::::::::::..-@......::::...-:.%..:-::::::::::::::.#..@....*+...++.....:::::::::::::::::::::::::.....@:..:.
.:...@:..:@..-@.-@:..:::::::::.==::::::::::::::::.@-..@%@@:.::.:@=::::@-::::::::::::::::.*.@..:.@:..%+.==#%=.::::::::::::::::::::::::..=@%..:*:..
...@@@@@@...@=.=@.+@..:::-::::.=-.:::::::::::::::-...@....@:::+-:.:::..:=:::::::::-:::::.@:....%:.:--..-...@.:...:..:-......:::...:..=@...::..*..
..@..@..@.@...@..+..#@..::::::.--.:-::-:::::::::::::...::..:::::::::::::::::::::::::::::.#...=*...@.::.@..@=..#@..*.@.@*.@@:....@.@-.@..::::..@..
..@.@@.@#*..@..:@.:...@=::::::::#.:::::::::::::-:::::::::::::::::::-::::::::::::::::::::.#.:@....@..::.@@...@.#....@@+..@..@@..@-:...@@..:::.++..
...@@@....#@..@.....@@..::::-:::#.:-:::::::::-...::::::-::::::::::::::::::::::::::.:::::.#@:....@..:::.%#.%@..%#.@@..@......@..@..:@*...:::..%...
.=...@@@@@+:@@....:@...:::::::::@.::::::::::...@=:::::::::::::::::::::::::::::::::=*:::::-=.:..@..:.........:.............:.@..::-:...::::.:@..:.
.::...........@@.%.::@:...:::::.#::::::-::...@:..:::-::::::........:::::::::::::-::.%:.:..%.:.@:.:-:@@+:.::.......::.::.@*.:@.::.:::::-.:.-=..::.
.::::::-::.@...:#..:..#@@...:::.%.::-::::..@+..::::::::...-:@@@%@@:..::::::::::::::..@..:.@.:...:::....:::.+@@@%-=::.....................-#@.:::.
.:::-::-::.@.::..::--....@@...:.%.::::::..@..::-::::--:=@::........**:::::::::::::::..@:.:==::::-::::::::::......:::*@@@@@@@@@@@@@@@@@@@@....-::.
...::::::-.@.:::::::-:::...@@...+.:::::.:@..::::.:.............................::::::..#::--:::::-::::::::::-:-::::.......................:::.::.
.@:::::--:.@.:::::::-::-::...@@=-:::::..@.::::::=#+**##*++#@@@@@@@@@@@@@@@@%*@::::::::.+:::+.::-::::::::::::::::-:::::-:::::::-::::::::::.::::::.
...:::::::.@.::::::::::::::::..:-:::::.@+.:::-::.:::.::-==.....................:::::::.#::.#.:::::::::::::::::::::::::::::-:::::::::::::::::::::.
.=:::::=:..@.::::::::::::::::::-*::::::+.:::::::::::::....::::::::::::::::::::--:---::.%...*:::::::::::::::-::-:::::::::::::::::::::::::::::::::.
..:::::-:.%@.:::::-:-:::-::::::.@.::::.@.::::::-::::::::+-..........+@::::::::::::::::.@..-*:.::::::::::::::::::::::::::::::::::::::::-:::::-:::.
.::::::...@:.:::::::=::::::::::.+:::::.@.:::::::::::-::::.+@@%:%@@@*.:::::::::::--::-:.@..=-=::::::::::::::::::::::::-::::::::::::::::::::::::::.
.:::..:-%.@.::---:::-:::-:-::-::--.:::.@.:::::::::-:::::::...:::....::::::-::::-:-:-::.@..=@.-::::::::-::::::::::::::::::::::::::::::::::::-::::.
.::::#=:..@.:::::-:--::-:--::::::#.:-::.#.:::::::-:::::::::::-::::::::::::::::::::::..%::.+::.:::::::::::::::::::::::::::::-:::::::::::-:::::::-.
.:::-:::.:@.:::::::::::::::-::::.@.-:::.-@.::::::::::::::::::-::-::--::::::::::::::..@..:.#:-.:-::::::::::::::::::::::::::::::::::::::::::::.:::.
.-::...:.%:.::::::::-:::::-:::::.@.::::::::.......:::::----:::::-::::::::::::::::::%%.:::.+.:-::::::::::::::::::::::::::::-:::::::::::::::::::-:.
.:::.@.:.@.:::::::::-::-::-:-:::.@.:::::::.:@@@@@%.:::::::::::::::-::::::::::--::::.::::..@:.-::::::::::::::::::::::-:::::::::::::::::::::::::::.
.........:.......................................................................................................................................
 */
public class OS {
    private static Kernel ki = new Kernel(); // The one and only one instance of the kernel.

    public static List<Object> parameters = new ArrayList<>();
    public static Object retVal;

    public enum CallType {SwitchProcess,SendMessage, Open, Close, Read, Seek, Write, GetMapping, CreateProcess, Sleep, GetPID, AllocateMemory, FreeMemory, GetPIDByName, WaitForMessage, Exit}
    public static CallType currentCall;

    private static void startTheKernel() {
        ki.start();
        if (ki.getCurrentPCB() != null) {
            ki.getCurrentPCB().stop();
        }
    }

    public static void switchProcess() {
        parameters.clear();
        currentCall = CallType.SwitchProcess;
        startTheKernel();
    }

    public static void Startup(UserlandProcess init) {
        Hardware.ClearTLB();
        CreateProcess(new IdleProcess(), PriorityType.background);
        CreateProcess(init, PriorityType.interactive);
    }

    public enum PriorityType {realtime, interactive, background}
    public static int CreateProcess(UserlandProcess up) {
        return CreateProcess(up,PriorityType.interactive);
    }

    // For assignment 1, you can ignore the priority. We will use that in assignment 2
    public static int CreateProcess(UserlandProcess up, PriorityType priority) {
        parameters.clear();
        parameters.add(up);
        parameters.add(priority);
        currentCall = CallType.CreateProcess;
        startTheKernel();

        while (retVal == null) {
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return (int) retVal;
    }

    public static int GetPID() {
        parameters.clear();
        currentCall = CallType.GetPID;
        startTheKernel();
        return (int) retVal;
    }

    public static void Exit() {
        try{
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        parameters.clear();
        currentCall = CallType.Exit;
        startTheKernel();
    }

    public static void Sleep(int mills) {
        parameters.clear();
        parameters.add(mills);
        currentCall = CallType.Sleep;
        startTheKernel();
    }

    // Devices
    public static int Open(String s) {
        parameters.clear();
        parameters.add(s);
        currentCall = CallType.Open;
        startTheKernel();
        do{
            try {
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        } while (retVal == null);
        int ret = (int) retVal;
        retVal = null;
        return ret;
    }

    public static void Close(int id) {
        parameters.clear();
        parameters.add(id);
        currentCall = CallType.Close;
        startTheKernel();
    }

    public static byte[] Read(int id, int size) {
        parameters.clear();
        parameters.add(id);
        parameters.add(size);
        currentCall = CallType.Read;
        startTheKernel();
        do{
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (retVal == null);

        byte[] ret = (byte[]) retVal;
        retVal = null;

        return ret;
    }

    public static void Seek(int id, int to) {
        parameters.clear();
        parameters.add(id);
        parameters.add(to);
        currentCall = CallType.Seek;
        startTheKernel();
    }

    public static int Write(int id, byte[] data) {
        parameters.clear();
        parameters.add(id);
        parameters.add(data);
        currentCall = CallType.Write;
        startTheKernel();
        do{
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (retVal == null);
        int ret = (int) retVal;
        retVal = null;
        return ret;
    }

    // Messages
    public static void SendMessage(KernelMessage km) {
        KernelMessage nkm = KernelMessage.copyMessage(km);
        parameters.clear();
        parameters.add(nkm);
        currentCall = CallType.SendMessage;
        startTheKernel();
    }

    public static KernelMessage WaitForMessage() {
        parameters.clear();
        currentCall = CallType.WaitForMessage;
        startTheKernel();
        retVal = ki.ReadMessage();//I could not figure out any other way to do this
        return (KernelMessage) retVal;
    }

    public static int GetPidByName(String name) {
        parameters.clear();
        parameters.add(name);
        currentCall = CallType.GetPIDByName;
        startTheKernel();
        return (int) retVal;
    }

    // Memory
    public static void GetMapping(int virtualPage) {
        parameters.clear();
        parameters.add(virtualPage);
        currentCall = CallType.GetMapping;
        startTheKernel();
    }

    public static int AllocateMemory(int size) {
        parameters.clear();
        parameters.add(size);
        currentCall = CallType.AllocateMemory;
        startTheKernel();
        return (int) retVal; // Change this
    }

    public static boolean FreeMemory(int pointer, int size) {
        parameters.clear();
        parameters.add(pointer);
        parameters.add(size);
        currentCall = CallType.FreeMemory;
        startTheKernel();
        return (boolean) retVal; // Change this
    }
}
