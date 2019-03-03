package ru.bukharov.blockchain;

import org.testng.annotations.Test;

@Test
public class BlockchainTest {

    private Blockchain blockchain = new Blockchain();

    @Test
    public void add10Blocks() {
        for (int i = 0; i < 10; i++) {
            blockchain.addBlock();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(blockchain.getBlocks().get(i).toString());
            sb.append("\n\n");
        }

        System.out.println(sb.toString());
    }
}
