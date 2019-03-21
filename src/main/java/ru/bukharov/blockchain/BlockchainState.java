package ru.bukharov.blockchain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BlockchainState {
    @Getter
    private Block lastBlock;
    @Getter
    private List<String> data;
    @Getter
    private String hashPrefix;
}
