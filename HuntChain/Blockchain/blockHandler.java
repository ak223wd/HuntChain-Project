package Blockchain;

import Blockchain.Block;
import Network.Client;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class blockHandler implements InterfaceBlockHanlder {

    public Block block = null;
    public  ArrayList<Block> blockChain = new ArrayList<>();
    public  HashMap<String,String> resultMap;
    public  int difficulty = 5;
    public String directory;
    public long estimatedTime;

    public blockHandler(HashMap<String,String> resultMap){
        this.resultMap = resultMap;
        setEverything();
        miningBlock();
    }

    public blockHandler(){
        //getLastBlock();
        String currentDirectory = getCurrentDirector();
        directory = currentDirectory + "/MalwareDetectionBlockchain/blocks";
        setEverything();

    }


    private void setEverything(){
        String currentDirectory = getCurrentDirector();
        directory = currentDirectory + "/MalwareDetectionBlockchain/blocks";

        if (!isDirExist()){
            setCreateBlockDir(directory);
        } else {
            System.out.println("The directory already exists");
        }

    }


    public void miningBlock(){

        if(isFolderEmpty()){
            //GENESIS BLOCK
            //blockChain.add(new Blockchain.Block(resultMap, "0"));
            System.out.println("\nMining Genesis Blockchain.Block ...");
            //blockChain.get(0).miningBlock(difficulty);

            //new Blockchain.Block(resultMap, "0").miningBlock(difficulty);

            Block genesisBlock = new Block(resultMap, "0");
            genesisBlock.miningBlock(difficulty);
            isBlockValid(genesisBlock);
            addBlockToChain(genesisBlock);

            String jsonArray = new GsonBuilder().setPrettyPrinting().create().toJson(genesisBlock);
            System.out.println("\n"+jsonArray);

            //addBlockToChain(blockChain.get(0));

            //blockChain.remove(0);

        } else {
            System.out.println("no");
            //NEED TO MAKE IT SKIM THROUGH FILES
            //DON4T FORGET TO VERIFY THE BLOOOOOOCK

            //blockChain.add(new Blockchain.Block(resultMap, getLastBlock().hash));
            System.out.println("\nMining block 1 ...");
            //blockChain.get(0).miningBlock(difficulty);

            Block newBlock = new Block(resultMap, getLastBlock().hash);
            newBlock.miningBlock(difficulty);

            if (!isBlockValid(newBlock)){
                System.out.println("Block cannot be added");
            } else {
                addBlockToChain(newBlock);
            }


            String jsonArray = new GsonBuilder().setPrettyPrinting().create().toJson(newBlock);
            System.out.println("\n"+jsonArray);
            //blockChain.remove(0);
        }


        /*blockChain.add(new Blockchain.Block(resultMap, "0"));
        System.out.println("Mining block 1 ...");
        blockChain.get(0).miningBlock(difficulty);*/

        //writeObjectToFile(block);

        //isBlockValid(blockChain);

        /*blockChain.add(new Blockchain.Block("AnotherMalawreDate", blockChain.get(blockChain.size()-1).hash));
        System.out.println("\nMining block 2 ...");
        blockChain.get(1).miningBlock(difficulty);

        blockChain.add(new Blockchain.Block("AnotherMalawreDcte", blockChain.get(blockChain.size()-1).hash));
        System.out.println("\nMining block 3 ...");
        blockChain.get(2).miningBlock(difficulty);*/
    }





    @Override
    public boolean isBlockAlreadyInChain(Block block) {
        //return true if contains, false if not

        String [] allFiles = getAllFilesInDir();
        Arrays.sort(allFiles);
        //System.out.println(Arrays.toString(allFiles));


        if (!isFolderEmpty()){
            //mayb create a private function to get rid of that shitty .DS_Store
            ArrayList<String> newFiles = new ArrayList<>();

            for (int i = 0; i<allFiles.length;i++){
                if (!allFiles[i].contains(".DS_Store")){
                    //System.out.println(allFiles[i]);
                    newFiles.add(allFiles[i]);
                }
            }

            for(int k = 0 ; k<newFiles.size();k++){
                if (readObjectFile(newFiles.get(k)).data.equals(block.data)){
                    return true;
                }
            }


        }

        return false;

    }

    @Override
    public void addBlockToChain(Block block) {
        //It is supposed to receive block from a peer.
        if (!isBlockAlreadyInChain(block)){
            System.out.println("Data not in the Array yet, so adding it... ");

            String blockName = createNewFileName();
            writeObjectToFile(block, blockName);

            //Check blockchain validity
            //isBlockValid()

            //Send block to peers
            Client cl = new Client(block);
            if (cl.hostAvailable){

                //long startTime = System.nanoTime();
                cl.sendAndReceive();
                //estimatedTime = System.nanoTime() - startTime;
                //System.out.println("It took : " + estimatedTime + " ns");

            } else {
                System.out.println("Cannot send Data to peer -> No peer connected");
            }


        } else {
            System.out.println("Duplicate Block in the chain already - Discard this block");
        }

        System.out.println("Blockchain.Block added to the chain!");


    }



    @Override
    public boolean isBlockValid(Block block) {
        Checker isValid = new Checker(block, difficulty);
        if (isValid.checkBlockValidity()){
            System.out.println("Now verifying the whole blockchain validity..");
            if (isValid.checkChainValidity()){
                return true;
            }
            return false;
        }

        return false;
    }


    /*public boolean isBlockValid1() {
        Checker isValid = new Checker(blockChain, difficulty);
        isValid.checkChainValidity1();
        return false;
    }*/

    private Block getLastBlock(){
        String [] allFiles = getAllFilesInDir();
        Arrays.sort(allFiles);
        ArrayList<String> newFiles = new ArrayList<>();

        for (int i = 0; i<allFiles.length;i++){
            if (!allFiles[i].contains(".DS_Store")){
                //System.out.println(allFiles[i]);
                newFiles.add(allFiles[i]);
            }
        }

        int[] allFilesOrdered;
        if (Arrays.asList(allFiles).contains(".DS_Store")){

            allFilesOrdered = new int[allFiles.length - 1];
        } else {
            allFilesOrdered = new int[allFiles.length];
        }

        for (int i = 0; i<newFiles.size();i++){
            String[] splitFileO = newFiles.get(i).split("[.]");
            allFilesOrdered[i] = Integer.parseInt(splitFileO[0]);
        }
        Arrays.sort(allFilesOrdered);

        Block readBlock = readObjectFile(allFilesOrdered[newFiles.size()-1] + ".block");


                //allFiles[allFiles.length - 1]);

        //System.out.println(allFiles[allFiles.length-1]);


        return readBlock;
    }

    private void writeObjectToFile(Block block, String fileName){
        try{
            FileOutputStream fout = new FileOutputStream(directory + "/" + fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(block);
            oos.close();
            System.out.println("The file has been written");

            File file = new File(directory+ "/"+fileName);
            file.setReadOnly();

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    private Block readObjectFile(String fileName){
        Block newBlock = null;

        try {
            FileInputStream fis = new FileInputStream(directory + "/" + fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            newBlock = (Block) ois.readObject();
            /*System.out.println("HERE IS THE DATA READ  ----------------");
            System.out.println(newBlock.toString());
            //blockChain.add(newBlock);
            System.out.println("HERE IS THE DATA READ ----------------");*/
            ois.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return newBlock;
    }


    //------------------------------------------------------------------
    //--------------- HANDLING DIRECTORIES AND FILE --------------------
    //------------------------------------------------------------------


    private boolean verifyFileExist(String fileName){

        File file = new File(directory);
        String[] allFileNames = file.list();
        for (String i : allFileNames ){
            if (i.contains(fileName)){
               //System.out.println(i);
                return true;
            }
        }

        return false;
    }

    private boolean isFolderEmpty(){

        String[] allFiles = getAllFilesInDir();
        //System.out.println(allFiles.length);
        //String [] newFiles = new String[allFiles.length - 1];

        ArrayList<String> newFiles = new ArrayList<>();

        for (int i = 0; i<allFiles.length;i++){
            if (!allFiles[i].contains(".DS_Store")){
                //System.out.println(allFiles[i]);
                newFiles.add(allFiles[i]);
            }
        }

        //System.out.println(newFiles);

        return newFiles.isEmpty();
    }


    private String[] getAllFilesInDir(){
       // directory = getCurrentDirector() + "/MalwareDetectionBlockchain/blocks";
        File file = new File(directory);
        String[] allFileNames = file.list();
        return allFileNames;
    }

    private String createNewFileName(){
        String fileName = "";
        String extension = ".block";

        String[] allFiles = getAllFilesInDir(); //check all available files
        Arrays.sort(allFiles);
        String fullFileName = "";

        ArrayList<String> newFiles = new ArrayList<>();

        for (int i = 0; i<allFiles.length;i++){
            if (!allFiles[i].contains(".DS_Store")){
                //System.out.println(allFiles[i]);
                newFiles.add(allFiles[i]);
            }
        }

        if(!isFolderEmpty()){
            String[] splitFile = newFiles.get(newFiles.size()-1).split("[.]");

            int[] allFilesOrdered;
            if (Arrays.asList(allFiles).contains(".DS_Store")){

                allFilesOrdered = new int[allFiles.length - 1];
            } else {
                allFilesOrdered = new int[allFiles.length];
            }

            for (int i = 0; i<newFiles.size();i++){
                String[] splitFileO = newFiles.get(i).split("[.]");
                allFilesOrdered[i] = Integer.parseInt(splitFileO[0]);
            }
            Arrays.sort(allFilesOrdered);
            //System.out.println("ORDERED : "+Arrays.toString(allFilesOrdered));

            //System.out.println(Arrays.toString(splitFile));
            /*for (int i =0 ; i<allFiles.length;i++){
                System.out.println(allFiles[i]);
            }*/
            //int count = Integer.parseInt(splitFile[0]) + 1;
            int count = allFilesOrdered[allFilesOrdered.length - 1] + 1;

            //System.out.println("file doesn't exist");
            fullFileName = fileName + count + extension;

            try {
                File file = new File(directory+"/" + fullFileName);
                file.createNewFile();
                //System.out.println("File created!");
            }catch (IOException io){
                io.printStackTrace();
            }

            //System.out.println(fullFileName);
        } else {
            fullFileName = fileName + 0 + extension;

            try {
                File file = new File(directory+"/" + fullFileName);
                file.createNewFile();
                //System.out.println("File created!");
            }catch (IOException io){
                io.printStackTrace();
            }

            //System.out.println(fullFileName);
        }

        return fullFileName;
    }

    private String getCurrentDirector(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //System.out.println("Current relative path is: " + s);

        return s;
    }

    private boolean isDirExist(){
        String currentDirectory = getCurrentDirector();

        File file = new File(currentDirectory);
        String [] dir = file.list();

        for(String i : dir){
            if(i.contains("Malware")){
                //if directory already exists than return false no need to create a dir
                return true;
            }
        }

        return false;
    }

    private void setCreateBlockDir(String directoryToCreate){
        try {
            Path path = Paths.get(directoryToCreate);
            Files.createDirectories(path); //create the directory with sub dir

        }catch (IOException io){
            System.err.println("Failed to create directory!" + io.getMessage());
        }

    }












}
