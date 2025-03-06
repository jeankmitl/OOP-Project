/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import Entities.Units.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anawi
 */
public class EntityManager {

    private List<Class<? extends Unit>>unitOperators = new ArrayList<>();
    
    public EntityManager() {
        unitOperators.add(Skeleton.class);
        unitOperators.add(Slime.class);
        unitOperators.add(Vinewall.class);
    }

    public List<Class<? extends Unit>> getUnitOperators() {
        return unitOperators;
    }
    
}
