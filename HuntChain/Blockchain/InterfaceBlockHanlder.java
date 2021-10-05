package Blockchain;

import Blockchain.Block;

import java.util.ArrayList;

public interface InterfaceBlockHanlder {

    boolean isBlockAlreadyInChain(Block block);
    void addBlockToChain(Block block);
    boolean isBlockValid(Block block);

}
