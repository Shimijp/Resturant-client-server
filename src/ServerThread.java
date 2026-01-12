import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;
    private Menu menu;
    public ServerThread(Socket s, Menu menu) {
        socket = s;
        this.menu = menu;
    }

    @Override
    public void run()
    {
        try
        {
            handleClientRequest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void handleClientRequest() throws Exception
    {

        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Object request = objectInputStream.readObject();
        if((request instanceof ClientRequest))
        {

            RequestType reqType = ((ClientRequest)request).getRequestType();
            switch (reqType)
            {
                case GET_MENU:
                    // Send menu items back to client
                    objectOutputStream.writeObject(menu.getAllMenuItems());
                    objectInputStream.close();
                    inputStream.close();
                    objectOutputStream.close();
                    outputStream.close();
                    socket.close();
                    break;

                case PLACE_ORDER:
                    break;
            }
        }


    }
}
