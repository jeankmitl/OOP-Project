/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoOpSystem;

import Entities.Entity;
import java.util.HashMap;

/**
 *
 * @author anawi
 */
public class HashEntityID {
    private int counter = 0;
    private final HashMap<Integer, Entity> dataStore = new HashMap<>();

    public int insert(Entity entity) {
        int hash = generateHash();
        dataStore.put(hash, entity);
        return hash;
    }

    private int generateHash() {
        return counter++;
    }

    public Entity get(int hash) {
        return dataStore.get(hash);
    }
}
