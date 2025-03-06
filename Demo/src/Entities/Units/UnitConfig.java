/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entities.Units;

/**
 *
 * @author anawi
 */
public interface UnitConfig {
    char ATTACKER = 'A';
    char COST_GEN = 'C';
    char DEFENDER = 'D';
    char EXPLOTION = 'E';
    char GUARD = 'G';
    
    // All sprite sheet ️🎥
    UnitSpriteSheets SKELETON_SRITE = new UnitSpriteSheets("Skeleton", "SkeletonThrow");
    UnitSpriteSheets SLIME_SPRITE = new UnitSpriteSheets("Slime_re", "SlimeGenerate");
    UnitSpriteSheets VINEWAL_SPRITE = new UnitSpriteSheets("kaniwall", "kaniwall_crack", "kaniwall_cracker");
    UnitSpriteSheets MIMIC_SPRITE = new UnitSpriteSheets("Mimic", "Mimic");
    UnitSpriteSheets CANDLES6_SPRITE = new UnitSpriteSheets("Candles6", "Candles6_activate2");
    
    
    
    // All Unit stats 💡
    UnitStats SKELETON_STATS = new UnitStats(SKELETON_SRITE, 100, 10, 1500, 100, 5, UnitConfig.ATTACKER);
    UnitStats SLIME_STATS = new UnitStats(SLIME_SPRITE, 50, 0, 0, 50, 5, UnitConfig.COST_GEN);
    UnitStats VINEWALL_STATS = new UnitStats(VINEWAL_SPRITE, 4000, 0, 0, 50, 30, UnitConfig.DEFENDER);
    UnitStats MIMIC_STATS = new UnitStats(MIMIC_SPRITE, 50, 999, 50, 500, 500, UnitConfig.GUARD);
    // Too OP
    UnitStats CANDLES6_STATS = new UnitStats(CANDLES6_SPRITE, 10000, 0, 0, 200, 0, UnitConfig.EXPLOTION);
}
