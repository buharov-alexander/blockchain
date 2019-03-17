package ru.bukharov.blockchain;

public interface BlockchainListener {
    void notifyEvent(BlockchainEvent event);
}
