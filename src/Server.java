import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private Menu menu;
    private final int  port = 3333;
    private final String menuFile = "menuItems.txt";

    public Server() {
        menu = new Menu();

        try {
            menu.loadMenuFromFile(menuFile);
            System.out.println("Menu loaded successfully:");
            ServerSocket sc = null;
            Socket s;
            sc = new ServerSocket(port);
            while (true) {
                s = sc.accept();
                System.out.println("Client connected" + s.getInetAddress());
                new ServerThread(s, menu).start();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());

        }
    }
    public static void main(String[] args) {
        new Server();

    }
}
