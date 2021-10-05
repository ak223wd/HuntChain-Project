import Blockchain.Block;
import Blockchain.ScanBlockchainData;
import Blockchain.Start;
import Blockchain.blockHandler;
import MalwareDetection.IntegrityChecker;
import MalwareDetection.TopAndTail;
import viewer.Viewer;
import Network.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * HuntChain is a blockchain-based malware detection.
 * This project has been done for the Degree Project course (2dv50e) at Linnaeus University.
 *
 * I am submitting this code on Github so it can be ready for the Degree Project report.
 * However, the code needs cleaning and more comments, which will be done afterward hopefully...
 *
 * This is a prototype, so there are still a lot of things that can added to make it better.
 * If you have any question about the project, please let me know, I'd be glad answering your questions.
 *
 *
 *
 * HuntChain contains 4 packages, each one of them has a purpose.
 * The package Blockchain is everything related to the blockchain architecture.
 * The package MalwareDetection is about the different techniques to detect malware
 * The package Network is about creating a peer-to-peer connection
 * The package viewer is mainly about printing menu/submenu etc.
 *
 * @author Anas Kwefati (ak223wd)
 * @version 0.1
 *
 */
public class mainChain {

    public static ArrayList<Block> blockChain = new ArrayList<>();
    public static HashMap<String,String> resultMap;
    public static int difficulty = 5;

    public static void main(String[] args){

        // ---------------- PEER Network.Server --------------

        /*
         *
         * This part the server is listening in the background for any incoming connections from clients.
         * */

        new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.runServer();
            }
        }).start();


        // ---------------- MENU ----------------
        /*int count = 0;
        int arr[] = new int[22];
        while (count<22){
            Viewer v = new Viewer();
            String result = v.getInput();
            //resultMap = v.malwareDataForMining(); //-> Malware Data
            blockHandler blockHandler = new blockHandler(resultMap);
            count++;
            arr[count] += blockHandler.estimatedTime;
            System.out.println(Arrays.toString(arr));

            v.menu2();
            int res = v.getInput2();
            ScanBlockchainData scanBlockchainData = null;
            if (res == 1){
                IntegrityChecker integrityChecker = new IntegrityChecker(v.getPath());
                System.out.println("Checking the file..");
               scanBlockchainData = new ScanBlockchainData(integrityChecker.getHash(), integrityChecker.getFileName(), 1);
            } else if (res == 2){

                TopAndTail tt = new TopAndTail(v.getPath(), 1000);
                System.out.println("Checking the file..");
                scanBlockchainData = new ScanBlockchainData(tt.fullHash, tt.getFileName(), 2);
            }

            count++;
            arr[count] += scanBlockchainData.estimatedTime;
            System.out.println(Arrays.toString(arr));

        }*/

        while (true){
            Viewer v = new Viewer();
            String result = v.getInput();

            if(result.equals("a")){
                resultMap = v.malwareDataForMining(); //-> Malware Data

                // ---------------- BLOCK CHAIN ----------------

                blockHandler blockHandler = new blockHandler(resultMap);
                //blockHandler bl = new blockHandler();

            } else if (result.equals("b")){

               //System.out.println("Checking the file..");
                v.menu2();
                int res = v.getInput2();
                if (res == 1){
                    IntegrityChecker integrityChecker = new IntegrityChecker(v.getPath());
                    System.out.println("Checking the file..");
                    ScanBlockchainData scanBlockchainData = new ScanBlockchainData(integrityChecker.getHash(), integrityChecker.getFileName(), 1);
                } else if (res == 2){

                    TopAndTail tt = new TopAndTail(v.getPath(), 1000);
                    System.out.println("Checking the file..");
                    ScanBlockchainData scanBlockchainData = new ScanBlockchainData(tt.fullHash, tt.getFileName(), 2);
                }


                //System.out.println("Checking the file..");
                //IntegrityChecker integrityChecker = new IntegrityChecker("/Users/AK47/Desktop/2dv50/code/simpleBlockchain/malware/notMal.jar");
                //ScanBlockchainData scanBlockchainData = new ScanBlockchainData(integrityChecker.getHash(), integrityChecker.getFileName());



            }
        }



    }
}
