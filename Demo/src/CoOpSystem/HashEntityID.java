/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoOpSystem;

import Entities.Entity;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anawi
 * @param <T>
 */
public class HashEntityID<T> {
    private static final int hashSize = 100;
    private int counter = 0;
    private final HashMap<Integer, T> dataStore = new HashMap<>();
    
    public int add(T entity) {
        int hash = generateHash();
        dataStore.put(hash, entity);
        return hash;
    }
    
    public void insert(int id, T entity) {
        dataStore.put(id, entity);
    }
    
    public void remove(T entity) {
        Integer keyToRemove = null;
    
        for (Map.Entry<Integer, T> entry : dataStore.entrySet()) {
            if (entry.getValue().equals(entity)) {
                keyToRemove = entry.getKey();
                break;
            }
        }
        if (keyToRemove != null) {
            dataStore.remove(keyToRemove);
        }
    }

    private int generateHash() {
        counter = (counter + 1) % hashSize;
        return counter;
    }

    public T get(int hash) {
        return dataStore.get(hash);
    }
    
    public void clear() {
        dataStore.clear();
    }
    
    public Set<Integer> getAllID() {
        return dataStore.keySet();
    }
    
    public Collection<T> values() {
        return dataStore.values();
    }
    
    public boolean containID(int id) {
        return dataStore.containsKey(id);
    }
}
