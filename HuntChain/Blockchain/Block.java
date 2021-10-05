package Blockchain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Block implements Serializable {

    public String hash; // current digital fingerprint of the block
    public String previoushash; //previous block hash
    public HashMap<String,String>  data; //data in the hash
    protected long timeStamp; //current block date creation
    private int nonce;



    public Block(HashMap<String,String> data, String previoushash) {

        this.data = data;
        this.previoushash = previoushash;
        this.timeStamp = new Date().getTime();
        this.hash = createHash();
    }

    public String createHash(){
        StringUtil util = new StringUtil();

        String dataToHash = previoushash + data + Long.toString(timeStamp) + Integer.toString(nonce);
        String createdHash = util.sha256(dataToHash);

        return createdHash;
    }

    public void miningBlock(int prefixDifficulty){

        String prefixHashTarget = new String (new char[prefixDifficulty]).replace('\0', '0');
        while(!hash.substring(0,prefixDifficulty).equals(prefixHashTarget)){
            nonce++;
            hash = createHash();
        }
        System.out.println("Blockchain.Block mined : "+ hash);
    }


    @Override
    public String toString() {
        return "Blockchain.Block{" +
                "hash='" + hash + '\'' +
                ", previoushash='" + previoushash + '\'' +
                ", data=" + data +
                ", timeStamp=" + timeStamp +
                ", nonce=" + nonce +
                '}';
    }
}
