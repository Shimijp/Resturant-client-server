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
        ServerResponse response;
        // decide what to do based on request type
        if((request instanceof ClientRequest))
        {

            RequestType reqType = ((ClientRequest)request).getRequestType();
            switch (reqType)
            {
                case GET_MENU:
                    // fetch menu from menu class and send it back
                    response = new ServerResponse(ResponseType.MENU,menu.getAllMenuItems());
                    break;

                case PLACE_ORDER:
                    /*todo: process order here */
                    response = new ServerResponse(ResponseType.ORDER_CONFIRMATION, "Order has been placed successfully");
                    break;

                default:
                    //default case error
                    response = new ServerResponse(ResponseType.ERROR, "Unknown request type");
                   break;



            }
            System.out.println("Processed request of type: " + reqType);
            System.out.println("Sending response of type: " + response.getResponseType());
            //finally send the response, what ever it is, polymorphism in action!!!!
            objectOutputStream.writeObject(response);
            objectInputStream.close();
            inputStream.close();
            objectOutputStream.close();
            outputStream.close();
            socket.close();
        }


    }
}
