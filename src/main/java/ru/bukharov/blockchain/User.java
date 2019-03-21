package ru.bukharov.blockchain;

import java.util.concurrent.TimeUnit;

public class User extends Thread implements BlockchainListener {
    private String name;
    private Blockchain blockchain;
    private int messageNum;
    private boolean isRunning;

    public User(String name, Blockchain blockchain) {
        super();
        this.name = name;
        this.blockchain = blockchain;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                blockchain.addData(String.format("%s: message#%s", name, messageNum));
                messageNum++;
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void notifyEvent(BlockchainEvent event) {
        isRunning = event.isActive();
    }
}
