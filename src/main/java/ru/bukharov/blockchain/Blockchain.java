package ru.bukharov.blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Blockchain {
    private List<Block> blocks = new ArrayList<>();

    public void addBlock() {
        Block block = new Block(blocks.size() + 1, new Date(), getLastHash());
        blocks.add(block);
    }

    public boolean validateChain() {
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

    List<Block> getBlocks() {
        return blocks;
    }

    private String getLastHash() {
        if (blocks.isEmpty()) {
            return "0";
        }
        return blocks.get(blocks.size() - 1).getHash();
    }
}
