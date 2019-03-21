package ru.bukharov.blockchain;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
class Block implements Serializable {
    final private long id;
    final private long timestamp;
    final private String previousHash;
    final private long magic;
    final private List<String> messages;

    private String minerName;
    private long generatedTime;
    private String comment;

    public String getHash() {
        return StringUtils.applySha256(id  + timestamp + previousHash + magic + String.join(";", messages));
    }

    public String toString() {
        return "Id: " + getId() + "\n" +
                "Created by: " + minerName + "\n" +
                "Timestamp: " + timestamp + "\n" +
                "Magic number: " + magic + "\n" +
                "Hash of the previous block:\n" + previousHash + "\n" +
                "Hash of the block:\n" + getHash() + "\n" +
                "Block data: " + convertDataToString() + "\n" +
                "Block was generating for " + generatedTime + " milliseconds\n" +
                comment + "\n";
    }

    private String convertDataToString() {
        if (messages.isEmpty()) {
            return "no messages";
        }
        return "\n" + String.join("\n", messages);
    }

}