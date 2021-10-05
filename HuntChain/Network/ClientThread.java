package Network;

import Blockchain.Block;
import Blockchain.blockHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable {

    private Socket socket;
    private int id;
    private byte[] buf;
    private OutputStream os;



    public ClientThread(Socket socket, byte[] bufferSize, int id) {
        this.socket = socket;
        this.id = id;
        this.buf = bufferSize;

    }

    @Override
    public void run() {

        try {

            InputStream is = socket.getInputStream(); // get the input stream from the connected socket
            os = socket.getOutputStream();
            ObjectInput oi = new ObjectInputStream(is); // create a DataInputStream so we can read data from it.

            System.out.println(Thread.currentThread().getName());
            System.out.printf("TCP echo request from %s", socket.getInetAddress().getHostAddress());
            System.out.printf(" using port %d\n", socket.getPort());
            System.out.println("----------------------------------------------------------------");

            Block block = null;
            while (true) {

                /* Create Input Stream for receiving message */

                /* Receiving message */
                //Object o =  oi.readObject();
                //List<Blockchain.Block> bl = (List<Blockchain.Block>) oi.readObject();
                block = (Block) oi.readObject();
                //int br = is.read(buf); //Reads some number of bytes from the input stream and stores them into the buffer array b.
                //System.out.println(new String(buf).trim());// deletes empty spaces


                receivedDataToAdd(block);


                /* Compare sent and received message */
                //String receivedString = new String(buf, 0, br);

                ArrayList<Byte> byteArray = new ArrayList<>();

                for (int i=0;i<buf.length;i++){
                    byteArray.add(buf[i]);
                }

                //Allows to check if there are still data into the stream
                while(is.available()>0) {

                    //br = is.read(buf);

                    for (int i=0;i<buf.length;i++){
                        byteArray.add(buf[i]);
                    }

                   //receivedString += new String(buf, 0, br);

                }

                byte[] result = new byte[byteArray.size()];
                for(int i = 0; i < byteArray.size(); i++) {
                    result[i] = byteArray.get(i);
                }

                //System.out.println(receivedString);

                /* Create datagram packet for sending message */

                //sendDataToPeer()

                /* Send message*/
                String recepy = "Blockchain.Block received correctly!";
                //os.write(buf,0,new String(buf).trim().length());
                os.write(recepy.getBytes());
                //os.flush();


            }


        } catch (Exception se){
            System.err.println("Connection has been lost or the client " +id+ " closed it...");
            closeClient();
        } finally {
            closeClient();

        }

        System.out.printf("TCP echo request from %s", socket.getInetAddress().getHostAddress());
        System.out.printf(" using port %d has been closed\n", socket.getPort());



    }

    private void closeClient(){
        try {
            socket.close();
            Thread.currentThread().interrupt();

        }catch (IOException ie){
            //ie.printStackTrace();
        }
    }


    private void receivedDataToAdd(Block minedBlock){
        //FROM HERE THE SERVER RECEIVED THE MINED BLOCK
        // NOW WE NEED TO ADD THE BLOCK INTO THE BLOCKCHAIN!
        System.out.println("REEEEECEIVED ITIITITITIITITIT ");
        System.out.println(minedBlock.toString());


        blockHandler blockHandler = new blockHandler();

        if (blockHandler.isBlockAlreadyInChain(minedBlock)){
            System.out.println("The block is already in the chain");
        }else {
            System.out.println("Adding the block");
            blockHandler.addBlockToChain(minedBlock);
        }
       // System.out.println(minedBlock.toString());
        //Blockchain.blockHandler Blockchain.blockHandler = new Blockchain.blockHandler(minedBlock);
    }
}