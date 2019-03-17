package ru.bukharov.blockchain;

import java.io.Serializable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor()
class Block implements Serializable {
    final private long id;
    final private long timestamp;
    final private String previousHash;
    final private long magic;

    private String minerName;
    private long generatedTime;

    public String getHash() {
        return StringUtils.applySha256(id  + timestamp + previousHash + magic);
    }

    public String toString() {
        return "Id: " + getId() + "\n" +
                "Timestamp: " +timestamp + "\n" +
                "Magic number: " + magic + "\n" +
                "Hash of the previous block: " + previousHash + "\n" +
                "Hash of the block: " + getHash() + "\n" +
                "Miner: " + minerName + "\n" +
                "Generated time: " + generatedTime + "\n";
    }

}