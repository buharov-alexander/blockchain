package ru.bukharov.blockchain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BlockchainState {
    @Getter
    private Block lastBlock;
    @Getter
    private String hashPrefix;
}
