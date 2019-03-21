package ru.bukharov.blockchain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

public class Blockchain implements Serializable {
    private static final Block ZERO_BLOCK = new Block(0, 0, "0", 0, new ArrayList<>());
    private static final int SECOND = 1000;
    private static final int LOW_TIME_LIMIT = 1 * SECOND;
    private static final int HIGH_TIME_LIMIT = 10 * SECOND;

    private final int limit;
    @Getter
    private List<Block> blocks = new ArrayList<>();
    private List<BlockchainListener> listeners = new ArrayList<>();
    private int zeros;
    private List<String> messages;

    public Blockchain(int zeros, int limit) {
        this.zeros = zeros;
        this.messages = new ArrayList<>();
        this.limit = limit;
    }

    public synchronized AddBlockResult addBlock(Block block) {
        if (!block.getPreviousHash().equals(getLastHash())) {
            return AddBlockResult.INVALID_HASH;
        } else if (!block.getHash().startsWith(getHashPrefix())) {
            return AddBlockResult.INVALID_HASH_PREFIX;
        }

        blocks.add(block);

        updateZeros(block);
        BlockchainEvent event = BlockchainEvent.builder()
                .state(getState())
                .active(blocks.size() < limit)
                .build();

        notifyListeners(event);
        this.messages = new ArrayList<>();
        BlockchainUtil.saveBlockchain(this, "temp.txt");
        return AddBlockResult.BLOCK_ADDED;
    }

    public synchronized BlockchainState getState() {
        List<String> messagesForBlock = new ArrayList<>(messages);
        return new BlockchainState(getLastBlock(), messagesForBlock, getHashPrefix());
    }

    public synchronized void addData(String message) {
        messages.add(message);
    }

    public void registerListener(BlockchainListener listener) {
        listeners.add(listener);
    }

    private void updateZeros(Block block) {
        long generatedTime = block.getGeneratedTime();
        String comment;

        if (generatedTime < LOW_TIME_LIMIT) {
            zeros++;
            comment = "The zeros number was increased to " + zeros;
        } else if (generatedTime > HIGH_TIME_LIMIT) {
            zeros--;
            comment = "The zeros number was decreased to " + zeros;
        } else {
            comment = "The zeros number stays the same";
        }
        block.setComment(comment);
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