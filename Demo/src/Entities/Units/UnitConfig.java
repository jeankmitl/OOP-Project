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
    int GUARD = 0;
    int DEFENDER = 1;
    int EXPLOTION = 2;
    int COST_GEN = 3;
    int ATTACKER = 4;
    
    // All sprite sheet ️🎥
    UnitSpriteSheets SKELETON_SRITE = new UnitSpriteSheets("Skeleton", "SkeletonThrow");
    UnitSpriteSheets SLIME_SPRITE = new UnitSpriteSheets("Slime_re", "SlimeGenerate");
    UnitSpriteSheets KANIWALL_SPRITE = new UnitSpriteSheets("kaniwall", "kaniwall_crack", "kaniwall_cracker");
    UnitSpriteSheets MIMIC_SPRITE = new UnitSpriteSheets("Mimic", "Mimic");
    UnitSpriteSheets CANDLES6_SPRITE = new UnitSpriteSheets("Candles6", "Candles6_activate2");
    UnitSpriteSheets EXPLOSION_SPRITE = new UnitSpriteSheets("Explosion", "Explosion_atk");
    UnitSpriteSheets GOLEM_SUPPORT_SPRITE = new UnitSpriteSheets("Golem_idle", "Golem_idle");
    
    // All Unit stats 💡
    UnitStats SKELETON_STATS = new UnitStats(SKELETON_SRITE, 100, 20, 1.5, 100, 5, ATTACKER);
    UnitStats SLIME_STATS = new UnitStats(SLIME_SPRITE, 50, 0, 10, 50, 5, COST_GEN);
    UnitStats KANIWALL_STATS = new UnitStats(KANIWALL_SPRITE, 4000, 0, 0, 50, 30, DEFENDER);
    UnitStats GOLEM_SUPPORT_STATS = new UnitStats(GOLEM_SUPPORT_SPRITE, 50, 0, 0, 200, 60, COST_GEN);
    ///BETA///
    UnitStats MIMIC_STATS = new UnitStats(MIMIC_SPRITE, 100, 999, 60, 250, 20, GUARD);
    UnitStats EXPLOSION_STATS = new UnitStats(EXPLOSION_SPRITE, 75, 999, 50, 50, 30, UnitConfig.EXPLOTION);
    // Too OP
    UnitStats CANDLES6_STATS = new UnitStats(CANDLES6_SPRITE, 10000, 0, 0, 200, 1000, EXPLOTION);
}
