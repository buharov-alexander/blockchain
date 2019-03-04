package ru.bukharov.blockchain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
class Block implements Serializable {
    private long id;
    private Date date;
    private String previousHash;
    private long generationTime;
    private long magic = 0;

    Block(long id, Date date, String previousHash, int zeros) {
        this.id = id;
        this.date = date;
        this.previousHash = previousHash;
        generateHash(zeros);
    }

    private void generateHash(int zeros) {
        long time = System.currentTimeMillis();
        String zerosStr = new String(new char[zeros]).replace('\0', '0');

        int testMagic = 0;
        String input = id  + date.toString() + previousHash;
        String hash = StringUtils.applySha256(input + testMagic);

        while (!hash.startsWith(zerosStr)) {
            testMagic++;
            hash = StringUtils.applySha256(input + testMagic);
        }

        this.magic = testMagic;
        this.generationTime = System.currentTimeMillis() - time;
    }

    public String getHash() {
        return StringUtils.applySha256(id  + date.toString() + previousHash + magic);
    }

    public String toString() {
        return "Id: " + getId() + "\n" +
                "Timestamp: " + getDate().getTime() + "\n" +
                "Magic number: " + magic + "\n" +
                "Hash of the previous block:\n" + getPreviousHash() + "\n" +
                "Hash of the block: \n" + getHash() + "\n" +
                "Block was generating for " + generationTime + " seconds";
    }

}