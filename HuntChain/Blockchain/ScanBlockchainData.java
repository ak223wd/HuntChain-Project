package Blockchain;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ScanBlockchainData {


    private String hashToCheck;
    private String directory;
    private int sign;
    public long estimatedTime;

    public ScanBlockchainData(String hashToCheck, String fileName, int sign) {
        this.hashToCheck = hashToCheck;
        this.sign = sign;
        readBlockChainData(hashToCheck, fileName);
    }



    public void readBlockChainData(String hash, String fileName){

        int count = 0;
        Block malwareBlockData = null;

        String[] allFiles = getAllFilesInDir();
        Arrays.sort(allFiles);

        //System.out.println(Arrays.toString(allFiles));


        if(!isFolderEmpty()){
            //maybe create a private function to get rid of that shitty .DS_Store
            ArrayList<String> newFiles = new ArrayList<>();

            for (int i = 0; i<allFiles.length;i++){
                if (!allFiles[i].contains(".DS_Store")){
                    System.out.println(allFiles[i]);
                    newFiles.add(allFiles[i]);
                }
            }

            int[] allFilesOrdered;
            if (Arrays.asList(allFiles).contains(".DS_Store")){

                allFilesOrdered = new int[allFiles.length - 1];
            } else {
                allFilesOrdered = new int[allFiles.length];
            }

            System.out.println(allFilesOrdered.length);

            for (int i = 0; i<newFiles.size();i++){
                String[] splitFileO = newFiles.get(i).split("[.]");
                System.out.println(Arrays.toString(splitFileO));
                allFilesOrdered[i] = Integer.parseInt(splitFileO[0]);
            }
            Arrays.sort(allFilesOrdered);

            Block readBlock = null;
            System.out.println("Hash of the file to check : " + hash);

            long startTime = System.nanoTime();
            for (int k = 0; k<newFiles.size();k++){
                readBlock = readObjectFile(allFilesOrdered[k] + ".block");
                //readBlock = readObjectFile(newFiles.get(k));
                System.out.println("----------------- " + "Checking Blockchain.Block "+ k +" -----------------");
                //System.out.println(readBlock.data.get("SHA256"));
                //System.out.println(hash);
                //System.out.println("---------------------------------------------------------------");
                //System.out.println("Checking Blockchain.Block " + k +"..");



                if (sign == 1){

                    if (readBlock.data.get("SHA256").equals(hash)){
                        malwareBlockData = readBlock;
                        count++;
                        break;
                    }



                } else if(sign == 2){
                    if (readBlock.data.get("topTail").equals(hash)){
                        malwareBlockData = readBlock;
                        count++;
                        break;
                    }
                }
            }
            estimatedTime = System.nanoTime() - startTime;
            System.out.println("It took : " + estimatedTime + " ns");


            if (count>0){
                System.out.println("\n------------------------------------");
                System.out.println("!!!!!!! MALICIOUS FILE !!!!!!!");
                System.out.println("File : " + fileName + " is Malicious");
                System.out.println("Malware Name : " + malwareBlockData.data.get("Malware Name"));
                System.out.println("Malware Type : " + malwareBlockData.data.get("Malware Type"));
                System.out.println("SHA-256 : " + malwareBlockData.data.get("SHA256"));
                System.out.println("TopTail SHA-256 : " + malwareBlockData.data.get("topTail"));
                System.out.println("------------------------------------ \n");
                //System.out.println(malwareBlockData.data);


            } else{
                System.out.println("\n-------------- RESULT --------------");
                System.out.println("!!!!!!! BENIGN FILE !!!!!!!");
                System.out.println("File : " + fileName + " is not malicious");
                System.out.println("SHA-256: " + hash + "\n");
                System.out.println("The file is not malicious or the hash is not in the blockchain yet");
                System.out.println("If you believe so, please contact the support to add it.");
                System.out.println("------------------------------------ \n");
                //System.out.println("NOT MALICIOUS - BENIGN :)");
            }

        }




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

    private Block readObjectFile(String fileName){
        Block newBlock = null;

        try {
            FileInputStream fis = new FileInputStream(directory + "/" + fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            newBlock = (Block) ois.readObject();
            //System.out.println("HERE IS THE DATA READ  ----------------");
            //System.out.println(newBlock.toString());
            //blockChain.add(newBlock);
            //System.out.println("HERE IS THE DATA READ ----------------");
            ois.close();


        }catch (Exception e){
            e.printStackTrace();
        }

        return newBlock;
    }


    private String[] getAllFilesInDir(){
        directory = getCurrentDirector() + "/MalwareDetectionBlockchain/blocks";
        File file = new File(directory);
        String[] allFileNames = file.list();
        return allFileNames;
    }



    private String getCurrentDirector(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //System.out.println("Current relative path is: " + s);

        return s;
    }



}
