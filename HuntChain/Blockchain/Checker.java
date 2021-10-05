package Blockchain;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Checker {

    private Block currentHashBlock;
    private Block previousHashBlock;
    private Block blockchain;
    private int difficulty;
    private String hashTarget;
    private String directory;

    public Checker(Block block, int difficulty){
        this.blockchain = block;
        this.difficulty = difficulty;
        hashTarget =  new String(new char[this.difficulty]).replace('\0', '0');
    }

    /*protected boolean checkChainValidity(){

        for (int i=1;i<blockchain.size();i++){
            currentHashBlock = blockchain.get(i);
            previousHashBlock = blockchain.get(i-1);

            if(!currentHashBlock.hash.equals(currentHashBlock.createHash())){
                System.out.println("current block not equal!");
                return false;
            } else if(!previousHashBlock.hash.equals(currentHashBlock.previoushash)){
                System.out.println("Previous hash not equal!");
                return false;
            } else if(!currentHashBlock.hash.substring(0,difficulty).equals(hashTarget)){
                System.out.println("Can't mine this block...");
                return false;
            }
        }
        System.out.println("\nCurrent Blockchain.Block Mined and Correct!");
        return true;
    }*/


    public boolean checkBlockValidity(){
        String[] allFiles = getAllFilesInDir();
        Arrays.sort(allFiles);

        if (!isFolderEmpty()){
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

            //Block currentHashBlock = null;

            currentHashBlock = blockchain;
            previousHashBlock = readObjectFile(allFilesOrdered[newFiles.size()-1] + ".block");

                    //newFiles.get(newFiles.size() - 1));

            if(!currentHashBlock.hash.equals(currentHashBlock.createHash())){
                System.out.println("current block not equal!");
                return false;
            } else if(!previousHashBlock.hash.equals(currentHashBlock.previoushash)){
                System.out.println("Previous hash not equal!");
                return false;
            } else if(!currentHashBlock.hash.substring(0,difficulty).equals(hashTarget)){
                System.out.println("Can't mine this block...");
                return false;
            }
        }

        System.out.println("\nCurrent Block Mined and Correct!");
        return true;
    }




    public boolean checkChainValidity(){
        Block malwareBlockData = null;
        String[] allFiles = getAllFilesInDir();
        Arrays.sort(allFiles);

        if (!isFolderEmpty()){
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

            //Block currentHashBlock = null;
            //System.out.println(Arrays.toString(allFilesOrdered));

            for (int k = 1; k<newFiles.size();k++){
                currentHashBlock = readObjectFile(allFilesOrdered[k] + ".block");
                previousHashBlock = readObjectFile(allFilesOrdered[k-1] + ".block");

                /*System.out.println(previousHashBlock.toString());
                System.out.println(currentHashBlock.toString());
                System.out.println(" ");*/


                if(!currentHashBlock.hash.equals(currentHashBlock.createHash())){
                    System.out.println("current block not equal!");
                    return false;
                } else if(!previousHashBlock.hash.equals(currentHashBlock.previoushash)){
                    System.out.println("Previous hash not equal!");
                    return false;
                } else if(!currentHashBlock.hash.substring(0,difficulty).equals(hashTarget)){
                    System.out.println("Can't mine this block...");
                    return false;
                }

            }
        }

        System.out.println("\nCurrent Blockchain is Correct!");
        return true;
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
        System.out.println(" File to READ : " + fileName);
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
