package ru.bukharov.blockchain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BlockchainUtil {

    public static void saveBlockchain(Blockchain blockchain, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(blockchain);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Blockchain readBlockchain(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            Blockchain blockchain = (Blockchain) ois.readObject();
            return blockchain;
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }


}
