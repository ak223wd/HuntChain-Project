package Network;/*
  TCPEchoClient.java
  This application sends a request to the TCPEchoServer,
  waits for the response, and, when the response is received, displays it to the standard output.
*/

import Blockchain.Block;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public  final int BUFSIZE= 1024;
    public  final int MYPORT= 0;
    public  final Block MSG;

    //PARAMETER FOR ARGS
    public  String IP = "127.0.0.1";
    public  int PORT = 5000;
    public  int buffSize = 10000;
    public boolean hostAvailable = true;


    private  Socket socket = new Socket();

    public Client(Block MSG){
        this.MSG = MSG;
        if (isHostAvailable(IP,PORT)){
            connect();
        } else {
            hostAvailable = false;
            System.out.println("No peer found");
        }

    }


    private  void connect(){
        try {
            /* Create socket */


            /*
             * InetSocketAddress implements an IP Socket Address (IP address + port number) extends SocketAddress class
             */

            InetAddress inet = InetAddress.getByName(IP);

            /* Create local endpoint using bind() */
            //InetSocketAddress localAddr = new InetSocketAddress(PORT);
            //Creates a socket address where the IP address is the wildcard address and the port number a specified value.
            //socket.bind(localAddr);

            /* Create remote endpoint */
            InetSocketAddress RemoteAddr = new InetSocketAddress(inet,PORT);


            /* Create a connection to the server for sending message */
            socket.connect(RemoteAddr,2500);
            System.out.println("Connecting to the TCP server : " + RemoteAddr.getAddress().getHostAddress() + ":"
                    + RemoteAddr.getPort() + "\n");

            System.out.println("Sending Blockchain.Block");

        } catch (IOException ie){
            //hostAvailable = false;
            disconnect();
            //System.out.println("Network.Server is shutdown!");
            //System.exit(1);
        }
    }

    public  void disconnect(){

        System.err.println("Server is shutdown...");
        try {
            socket.close();
        }catch (IOException iee){
            iee.printStackTrace();
        }

        System.exit(1);
    }

    public boolean isHostAvailable(String serverIP, int serverPort){
        try (Socket s = new Socket(serverIP,serverPort)){
            return true;
        }catch (IOException io){

        }
        return false;
    }

    public void sendAndReceive(){

        try{
            byte[] buf= new byte[buffSize];


            /* Send  message*/
            OutputStream os = socket.getOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(MSG);
            //oos.flush();


            //os.write(MSG,0,MSG.toString().length());



            /* Create InputStream for receiving echoed message */
            InputStream is = socket.getInputStream(); // read the what is received

            /* Receive message*/
            int br = is.read(buf);
            //System.out.println(new String(buf,0,br));

            //System.out.println(new String(buf));
            /* Compare sent and received message */
            String receivedString = new String(buf, 0, br);

            //Allows to check if there are still data into the stream
            while(is.available()>0){
                br = is.read(buf);
                receivedString += new String(buf, 0, br);
            }

            /*if (receivedString.compareTo(MSG) == 0){
                System.out.printf("%d bytes sent and received\n", receivedString.length());
            } else {
                System.out.printf("Sent and received msg not equal!\n");
            }*/

            System.out.println(receivedString);
        }catch (IOException ie){
            System.out.println("YO");
            disconnect();
        }






    }



}
