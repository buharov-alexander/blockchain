package ru.bukharov.blockchain;

import lombok.Builder;
import lombok.Getter;

@Builder
public class BlockchainEvent {
    @Getter
    private BlockchainState state;
    @Getter
    private boolean active;
}
