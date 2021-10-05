package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private  final int BUFSIZE= 10000;
    private int PORT = 4091;
    private  int id = 0;

    private Socket socket;


    public Server() {
    }


    /**
     *
     * This method listens for connections and whenever it receives a client it will create a thread
     * and will call the run method in Network.ClientThread.
     * @see ClientThread
     *
     */
    public void runServer(){

        try {

            byte[] buf = new byte[BUFSIZE];
            /* Create socket */
            //Creates a socket address where the IP address is the wildcard address and the port number a specified value.
            ServerSocket serverSocket = new ServerSocket(PORT);

            System.out.println("Network.Server has been started...\n");

            while(true){
                socket = serverSocket.accept(); // Listen for connection
                //socket.setSoTimeout(1000);

                try {

                    ClientThread cl = new ClientThread(socket,buf,id); //runnable
                    System.out.println("----------------------------------------------------------------");
                    System.out.println("Network.Client connected "+ id+ " has been connected");
                    Thread newClient = new Thread(cl, "Network.Client "+id + " :");
                    newClient.start(); //run the thread



                    id+=1;
                } catch (Exception e){
                    e.printStackTrace();

                    socket.close();
                    Thread.currentThread().interrupt();
                    //System.out.println("here2");

                    break;
                }


            }

        } catch (IOException ie){
            try {
                socket.close();
            } catch (IOException io){
                //System.out.println("here");
            }

            //ie.printStackTrace();
            //Thread.currentThread().interrupt();
        }

    }
}
