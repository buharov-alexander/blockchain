package ru.bukharov.blockchain;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class BlockchainTest {

    @Test
    public void oneMiner() throws InterruptedException {
        Blockchain blockchain = new Blockchain(0, 7);
        Miner miner = new Miner("Miner#1", blockchain);

        miner.run();
        miner.join();

        printBlockchain(blockchain);
        Assert.assertEquals(blockchain.getBlocks().size(), 7);
    }

    @Test
    public void severalMiners() {
        Blockchain blockchain = new Blockchain(0, 10);

        List<Miner> miners = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            miners.add(new Miner("Miner#" + i, blockchain));
        }

        startMiners(miners, new ArrayList<>());

        printBlockchain(blockchain);
        Assert.assertEquals(blockchain.getBlocks().size(), 10);
    }

    @Test
    public void severalMinersWithUsers() {
        Blockchain blockchain = new Blockchain(0, 10);

        List<Miner> miners = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            miners.add(new Miner("Miner#" + i, blockchain));
        }

        List<User> users = new ArrayList<>();
        users.add(new User("Sasha", blockchain));
        users.add(new User("Ghanna", blockchain));

        startMiners(miners, users);

        printBlockchain(blockchain);
        Assert.assertEquals(blockchain.getBlocks().size(), 10);
    }


    private void startMiners(List<Miner> miners, List<User> users) {
        miners.forEach(Miner::start);
        users.forEach(User::start);

        miners.forEach(miner -> {
            try {
                miner.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
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
