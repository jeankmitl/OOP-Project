/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoOpSystem;

import Entities.Entity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author anawi
 * @param <T>
 */
public class HashEntityID<T> {
    private int counter = 0;
    private final HashMap<Integer, T> dataStore = new HashMap<>();

    
    public int add(T entity) {
        int hash = generateHash();
        dataStore.put(hash, entity);
        return hash;
    }
    
    public void remove(T entity) {
        dataStore.values().remove(entity);
    }

    private int generateHash() {
        return counter++;
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
}
