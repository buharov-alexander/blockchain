package ru.bukharov.blockchain;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class BlockchainTest {

    @Test
    public void add5Blocks() {
        int zeros = 2;

        Blockchain blockchain = new Blockchain(zeros);
        for (int i = 0; i < 5; i++) {
            blockchain.addBlock();
        }

        printBlockchain(blockchain);
        Assert.assertTrue(blockchain.validateChain());
    }

    @Test
    public void add5BlocksAndChangeOneBlock() {
        int zeros = 2;

        Blockchain blockchain = new Blockchain(zeros);
        for (int i = 0; i < 5; i++) {
            blockchain.addBlock();
        }

        blockchain.getBlocks().get(1).setId(-1);
        printBlockchain(blockchain);
        Assert.assertFalse(blockchain.validateChain());
    }


    private void printBlockchain(Blockchain blockchain) {
        StringBuilder sb = new StringBuilder();
        for (Block block : blockchain.getBlocks()) {
            sb.append("Block:\n");
            sb.append(block.toString());
            sb.append("\n\n");
        }
        System.out.println(sb.toString());
    }
}
