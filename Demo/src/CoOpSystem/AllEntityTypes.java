/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoOpSystem;

import Entities.Enemies.*;
import Entities.Units.*;
import Main.UnitType;

/**
 *
 * @author anawi
 */
public class AllEntityTypes {
    private static final UnitType[] UNIT_TYPES = {
        new UnitType(Skeleton.class),
        new UnitType(Slime.class),
        new UnitType(Kaniwall.class),
        new UnitType(Explosive_turtle.class),
        new UnitType(Mimic.class),
        new UnitType(SemiAutoBot.class),
        new UnitType(BigBall.class),
        new UnitType(Vampire.class),
        new UnitType(FlatSlime.class),
        new UnitType(Moai.class),
        new UnitType(GiveawaySlime.class),
        new UnitType(MiPya.class),
        new UnitType(BlackSkeleton.class),
        new UnitType(Explosion.class),
        new UnitType(Snake.class),
        new UnitType(AlphaWolf.class),
        new UnitType(Werewolf.class),
        new UnitType(Python.class),
        new UnitType(Cannon.class),
        new UnitType(Tofu.class),
        new UnitType(Nike.class),
        new UnitType(Ghost.class),
        new UnitType(JavaSkeleton.class),
        new UnitType(CandlesExplosion.class),
        new UnitType(Barrier.class),
        new UnitType(Deathlocked.class),
        new UnitType(Kitsune.class)
    };

    public static UnitType[] getUNIT_TYPES() {
        return UNIT_TYPES;
    }
    
    public static UnitType getUnitTypeFromName(String name) {
        for (UnitType unitType: UNIT_TYPES) {
            if (unitType.getClassName().equals(name)) {
                return unitType;
            }
        }
        return null;
    }
    
    private static final Enemy[] ENEMIES = {
        new Bandit(0, 0),
        new BanditV2(0, 0),
        new BanditV3(0, 0),
        new Ninja(0, 0),
        new LittleRedHood(0, 0),
        new RobotMonoWheel(0, 0),
        new Sorcerer(0, 0),
        new RCBomber(0, 0),
        new IShowSpeed(0, 0),
        new KnightWalker(0, 0),
        new SongChinWu(0, 0),
        new Tank(0, 0),
        new TheBlueSword(0, 0),
        new TheRedSword(0, 0),
        new AntKing(0, 0),
        new Billionaire(0, 0)
    };

    public static Enemy[] getENEMIES() {
        return ENEMIES;
    }
    
    public static Enemy getEnemyFromName(String name) {
        for (Enemy enemy: ENEMIES) {
            if (enemy.getClass().getSimpleName().equals(name)) {
                return enemy;
            }
        }
        return null;
    }
}
