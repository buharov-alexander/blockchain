package ru.bukharov.blockchain;

import java.util.Date;

public class Miner extends Thread implements BlockchainListener {
    private String name;
    private Blockchain blockchain;

    private BlockchainState blockchainState;
    private boolean isRunning;
    private long magic;
    private long startGeneration;

    public Miner(String name, Blockchain blockchain) {
        this.name = name;
        this.blockchain = blockchain;

        blockchain.registerListener(this);
        blockchainState = blockchain.getState();
        isRunning = true;
        magic = 0;
        startGeneration = System.currentTimeMillis();
    }

    @Override
    public void notifyEvent(BlockchainEvent event) {
        blockchainState = event.getState();
        isRunning = event.isActive();
        magic = 0;
        startGeneration = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (isRunning) {
            Block block = createBlock();

            if (block.getHash().startsWith(blockchainState.getHashPrefix())) {
                blockchain.addBlock(block);
            } else {
                magic++;
            }
        }
    }

    private Block createBlock() {
        Block lastBlock = blockchainState.getLastBlock();
        Block block = new Block(lastBlock.getId() + 1, new Date().getTime(), lastBlock.getHash(), magic);

        block.setMinerName(name);
        block.setGeneratedTime(System.currentTimeMillis() - startGeneration);
        return block;
    }
}
