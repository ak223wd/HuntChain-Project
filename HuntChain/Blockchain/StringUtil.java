package Blockchain;

import java.security.MessageDigest;

public class StringUtil {

    public StringUtil(){

    }


    public String sha256(String inputData){

        try {
            //This MessageDigest class provides applications the functionality of a message digest algorithm
            MessageDigest digestMessage = MessageDigest.getInstance("SHA-256"); //to get to use SHA256

            byte[] input = digestMessage.digest(inputData.getBytes("UTF-8"));
            String hexString = "";
            for (int i=0; i<input.length;i++){
                String hex = Integer.toHexString(0xff & input[i]);
                if(hex.length()==1){
                    hexString+='0';
                }
                hexString+=hex;
            }


            return hexString;
        } catch (Exception e){

            throw new RuntimeException();
        }


    }
}
