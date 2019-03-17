package ru.bukharov.blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;

public class Blockchain implements Serializable {
    private static final Block ZERO_BLOCK = new Block(0, 0, "0", 0);

    private final int limit;
    @Getter
    private List<Block> blocks = new ArrayList<>();
    private List<BlockchainListener> listeners = new ArrayList<>();
    private int zeros;

    public Blockchain(int zeros, int limit) {
        this.zeros = zeros;
        this.limit = limit;
    }

    public synchronized AddBlockResult addBlock(Block block) {
        if (!block.getPreviousHash().equals(getLastHash())) {
            return AddBlockResult.INVALID_HASH;
        } else if (!block.getHash().startsWith(getHashPrefix())) {
            return AddBlockResult.INVALID_HASH_PREFIX;
        }

        blocks.add(block);
        BlockchainEvent event = BlockchainEvent.builder()
                .state(getState())
                .active(blocks.size() < limit)
                .build();

        notifyListeners(event);
        return AddBlockResult.BLOCK_ADDED;
    }

    public synchronized BlockchainState getState() {
        return new BlockchainState(getLastBlock(), getHashPrefix());
    }

    public void registerListener(BlockchainListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners(BlockchainEvent event) {
        listeners.forEach(listener -> listener.notifyEvent(event));
    }

    private boolean validateChain() {
        if (blocks.size() < 2) {
            return true;
        }

        Iterator<Block> iter = blocks.iterator();
        Block curBlock = iter.next();
        while (iter.hasNext()) {
            Block nextBlock = iter.next();
            if (!nextBlock.getPreviousHash().equals(curBlock.getHash())) {
                return false;
            }
            curBlock = nextBlock;
        }
        return true;
    }

    private String getHashPrefix() {
        return new String(new char[zeros]).replace('\0', '0');
    }

    private String getLastHash() {
        return getLastBlock().getHash();
    }

    private Block getLastBlock() {
        if (blocks.isEmpty()) {
            return ZERO_BLOCK;
        }
        return blocks.get(blocks.size() - 1);
    }
}