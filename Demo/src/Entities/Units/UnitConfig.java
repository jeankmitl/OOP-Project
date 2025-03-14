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
    UnitSpriteSheets EXPLOSION_SPRITE = new UnitSpriteSheets("Explosion", "Explosion_atk"); //BETA
    UnitSpriteSheets GOLEM_SUPPORT_SPRITE = new UnitSpriteSheets("Golem_idle", null);
    UnitSpriteSheets CANNON_SPRITE = new UnitSpriteSheets("Explosion", "Explosion_atk");
    UnitSpriteSheets BIGBALL_SPRITE = new UnitSpriteSheets("bigball_idle", "bigball_shoot");
    UnitSpriteSheets EXPLOSIVE_TURTLE_SPRITE = new UnitSpriteSheets("explosive_tutle_idle", "explosive_tutle_atk");
    UnitSpriteSheets NIKE_SPRITE = new UnitSpriteSheets("nike", "nike");
    UnitSpriteSheets SEMI_AUTO_BOT_SPRITE = new UnitSpriteSheets("mini_lazer_idle", "mini_lazer_atk");
    UnitSpriteSheets GIVEAWAY_SLIME_SPRITE = new UnitSpriteSheets("Slime_re", "SlimeGenerate");
    
    // All Unit stats 💡
    UnitStats SKELETON_STATS = new UnitStats(SKELETON_SRITE, 300, 20, 1.5, 100, 7, ATTACKER);
    UnitStats SLIME_STATS = new UnitStats(SLIME_SPRITE, 300, 0, 25, 50, 7, COST_GEN);
    UnitStats KANIWALL_STATS = new UnitStats(KANIWALL_SPRITE, 4000, 0, 0, 50, 30, DEFENDER);
    ///BETA///
    UnitStats CANNON_STATS = new UnitStats(CANNON_SPRITE,300,500,60,250,20,ATTACKER);
    UnitStats MIMIC_STATS = new UnitStats(MIMIC_SPRITE, 300, 999, 42, 150, 7, GUARD);
    UnitStats EXPLOSION_STATS = new UnitStats(EXPLOSION_SPRITE, 300, 1800, 50, 50, 30, UnitConfig.EXPLOTION);
    UnitStats GIVEAWAY_SLIME_STATS = new UnitStats(GIVEAWAY_SLIME_SPRITE, 1, 20, 0.5, 50, 60, COST_GEN);
    //TEST ONLY//
    UnitStats GOLEM_SUPPORT_STATS = new UnitStats(GOLEM_SUPPORT_SPRITE, 300, 0, 5, 200, 50, COST_GEN);
    UnitStats BIGBALL_STATS = new UnitStats(BIGBALL_SPRITE, 300, 100, 10, 400, 7, ATTACKER);
    UnitStats EXPLOSIVE_TURTLE_STATS = new UnitStats(EXPLOSIVE_TURTLE_SPRITE, 1, 500, 0, 50, 5, EXPLOTION);
    UnitStats SEMI_AUTO_BOT_STATS = new UnitStats(SEMI_AUTO_BOT_SPRITE, 300, 20, 3, 200, 20, ATTACKER);
    // Too OP
    UnitStats CANDLES6_STATS = new UnitStats(CANDLES6_SPRITE, 9999, 1800, 0, 125, 50, EXPLOTION);
    UnitStats NIKE_STATS = new UnitStats(NIKE_SPRITE, 1, 9999, 1, 0, 9999, EXPLOTION);
}
