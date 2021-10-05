package Blockchain;

import Blockchain.Block;
import Blockchain.ScanBlockchainData;
import Blockchain.blockHandler;
import MalwareDetection.IntegrityChecker;
import viewer.Viewer;
import Network.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Start {

    public static ArrayList<Block> blockChain = new ArrayList<>();
    public static HashMap<String,String> resultMap;
    public static int difficulty = 5;

    public Start(){
        /*Blockchain.Block genesisBlock = new Blockchain.Block("ThisIsAMalwareHAsh", "0");
        Blockchain.Block secondBlock = new Blockchain.Block("AnotherMalawreDate", genesisBlock.hash);
        Blockchain.Block thirdBlock = new Blockchain.Block("AnotherMalawreDate1", secondBlock.hash);*/

        // ---------------- PEER Network.Server --------------

        /*
         *
         * This part the server is listening in the background for any incoming connections from clients.
         * */

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                server.runServer();
            }
        }).start();*/


        // ---------------- MENU ----------------

        Viewer v = new Viewer();

        String result = v.getInput();

        if(result.equals("a")){
            resultMap = v.malwareDataForMining(); //-> Malware Data

            // ---------------- BLOCK CHAIN ----------------

            blockHandler blockHandler = new blockHandler(resultMap);

        } else if (result.equals("b")){
            System.out.println("Checking the file..");

            //TopAndTail tt = new TopAndTail("/Users/AK47/Desktop/2dv50/code/simpleBlockchain/malware/malware0.txt", 10);

            IntegrityChecker integrityChecker = new IntegrityChecker("/Users/AK47/Desktop/2dv50/code/simpleBlockchain/malware/malware0.txt");
            ScanBlockchainData scanBlockchainData = new ScanBlockchainData(integrityChecker.getHash(), integrityChecker.getFileName(), 1);

            //Blockchain.ScanBlockchainData scanBlockchainData = new Blockchain.ScanBlockchainData(tt.fullHash);

        }
        //Blockchain.blockHandler blockHandler1 = new Blockchain.blockHandler();











        // -------------- BLOCKCHAIN -----------------------


        /*
         * Decentralized Malware Data for Detection steps.
         * 1. Ask User if they want to add malware Data (a) or Check file (b)
         * 2. if (a), ask user for malware information + hash
         * 3. Data is sent to all connected peers. (step might be deleted)
         * 4. Mine the block with New data
         * 5. Send new Blockchain.Block to other peers for verification
         * 6. Add block to blockchain
         * 7. if (b), get file's hash using Signature technique malware detection
         * 8. Try to find a match in the blockchain.
         * 9. If found -> Malware otherwise its not
         * 10. Message shown to user
         * */


        //blockChain.add(new Blockchain.Block(resultMap, "0"));
        //System.out.println("Mining block 1 ...");
        //blockChain.get(0).miningBlock(difficulty);

        //Network.Client, so the idea is it sends the data to other peers. (Here it sends back to itself.)
        //Need to configure pre configured Node, so it sends to those preconf IPs. The mined Blockchain.Block.
        //When block is sent, it should add it in their block chain too.
        //Each Node should Valid the blockchain and reply.



        /*blockChain.add(new Blockchain.Block("AnotherMalawreDcte", blockChain.get(blockChain.size()-1).hash));
        System.out.println("\nMining block 3 ...");
        blockChain.get(2).miningBlock(difficulty);*/



        //Network.Client cl = new Network.Client(blockChain.get(0));
        //cl.sendAndReceive();


        //Blockchain.Checker isValid = new Blockchain.Checker(blockChain, difficulty);
        //System.out.println("Blockchain Valid : "+ isValid.checkChainValidity());

        //Blockchain.blockHandler Blockchain.blockHandler = new Blockchain.blockHandler(blockChain.get(0));
        //Blockchain.blockHandler.addBlockToChain(blockChain.get(0));


        //System.out.println(blockChain.get(0).data.get("SHA256"));








        //-----ADDING 2ND-------
        /*if(result.equals("a")){
            resultMap = v.malwareDataForMining(); //-> Malware Data

        } else if (result.equals("b")){
            System.out.println("Check file");
        }

        blockChain.add(new Blockchain.Block(resultMap, blockChain.get(blockChain.size()-1).hash));
        System.out.println("\nMining block 2 ...");
        blockChain.get(1).miningBlock(difficulty);

        System.out.println("Blockchain Valid : "+ isValid.checkChainValidity());

        Blockchain.blockHandler.addBlockToChain(blockChain.get(1));*/




        /*String jsonArray = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);
        System.out.println("\n"+jsonArray);*/




        /*for (int i = 0 ; i<blockChain.size();i++){
            System.out.println(blockChain.get(i).hash);
        }*/

        /*System.out.println("Genesis Hash : " + genesisBlock.createHash());
        System.out.println("Blockchain.Block 2 Hash : " + secondBlock.createHash());
        System.out.println("Blockchain.Block 3 Hash : " + thirdBlock.createHash());



        System.out.println("\n\n-----------GENESIS BLOCK--------------");
        System.out.println("previous hash: "+ genesisBlock.previoushash);
        System.out.println("timeStamp: "+ new Date(genesisBlock.timeStamp).toString());
        System.out.println("Data : " + genesisBlock.data);
        System.out.println("Hash : " + genesisBlock.createHash());

        System.out.println("\n\n-----------Second BLOCK--------------");
        System.out.println("previous hash: "+ secondBlock.previoushash);
        System.out.println("timeStamp: "+ new Date(secondBlock.timeStamp).toString());
        System.out.println("Data : " + secondBlock.data);
        System.out.println("Hash : " + secondBlock.createHash());*/
    }
}
