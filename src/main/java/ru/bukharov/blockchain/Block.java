package ru.bukharov.blockchain;

import java.util.Date;

class Block {
    private long id;
    private Date date;
    private String previousHash;

    Block(long id, Date date, String previousHash) {
        this.id = id;
        this.date = date;
        this.previousHash = previousHash;
    }

    public String getHash() {
        return StringUtils.applySha256(id + " " + date.toString());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String toString() {
        return "Block:\n" +
                "Id: " + getId() + "\n" +
                "Timestamp: " + getDate().getTime() + "\n" +
                "Hash of the previous block:\n" + getPreviousHash() + "\n" +
                "Hash of the block: \n" + getHash();
    }
}
