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
    int SUPPORT = 5;
    
    
    String SKELETON_DESC = "A few damage but good for starting";
    String BLACK_SKELETON_DESC = "triple shots";
    String SNIPER_SKELETON_DESC = "The most dangerous weapon is Sniper!";
    String SLIME_DESC = "Generate 50 mana every atkSpeed, this is enough to balance mana before empty";
    String KANIWALL_DESC = "Defend";
    String CANNON_DESC = "IDK. When finish";
    String MIMIC_DESC = "CRUNCHHHHHHHHHYYYYYYYYYYYYYYYYYYYYYYYYYY!!Y!";
    String EXPLOSION_DESC = "It's a trap!";
    String MIPYA_DESC = "(mini pyanak) help us, without use more field";
    String SNAKE_DESC = "The snake is fall from tree & jumpscare";
    String PYTHON_DESC = "print(\"Hello Python!\")";
    String GOLEM_DESC = "Long wait? Let me check";
    String BIGBALL = "BIG BALL O, BALLER";
    String EXPLOSIVE_TURTLE_DESC = "I want to use it";
    String SEMI_AUTO_BOT_DESC = "3 strike";
    String ALPHA_WOLF_DESC = "Baby, I'm preyin' on you tonight ~ Just like animals-mals";
    String WEREWOLF_DESC = "Big furry once a full moon";
    String VAMPIRE_DESC = "Drink blood means ++health in your body";
    String CANDLES6_DESC = "6 candles. Summon beam to kill all enemy in that row";
    String BLOCK_FIELD_DESC = "Betray unit, why are you use this?";
    String NIKE_DESC = "Make screen clean w/o enemy";
    String GHOST_DESC = "don't TOUCH mep, I shy. UwU";
    String JAVA_SKELETON_DESC = "git add *\n git commit\n git push git PUSH";
    
    // All sprite sheet ️🎥
    UnitSpriteSheets SKELETON_SRITE = new UnitSpriteSheets("Skeleton", "SkeletonThrow");
    UnitSpriteSheets BLACK_SKELETON_SRITE = new UnitSpriteSheets("black_skeleton_idle", "black_skeleton_atk");
    UnitSpriteSheets SNIPER_SKELETON_SRITE = new UnitSpriteSheets("Skeleton", "SkeletonThrow");
    UnitSpriteSheets SLIME_SPRITE = new UnitSpriteSheets("Slime_re", "SlimeGenerate");
    UnitSpriteSheets KANIWALL_SPRITE = new UnitSpriteSheets("kaniwall", "kaniwall_crack", "kaniwall_cracker");
    UnitSpriteSheets MIMIC_SPRITE = new UnitSpriteSheets("Mimic_1", "Mimic-att");
    UnitSpriteSheets CANDLES6_SPRITE = new UnitSpriteSheets("Candles6", "Candles6_activate2");
    UnitSpriteSheets EXPLOSION_SPRITE = new UnitSpriteSheets("Explosion", "Explosion_atk"); //BETA
    UnitSpriteSheets GOLEM_SUPPORT_SPRITE = new UnitSpriteSheets("Golem_idle", "Golem_idle");
    UnitSpriteSheets CANNON_SPRITE = new UnitSpriteSheets("Cannon-Idle", "Cannon-Charge");
    UnitSpriteSheets BIGBALL_SPRITE = new UnitSpriteSheets("bigball_idle", "bigball_shoot");
    UnitSpriteSheets EXPLOSIVE_TURTLE_SPRITE = new UnitSpriteSheets("explosive_tutle_idle", "explosive_tutle_atk");
    UnitSpriteSheets NIKE_SPRITE = new UnitSpriteSheets("nike", "nike");
    UnitSpriteSheets SEMI_AUTO_BOT_SPRITE = new UnitSpriteSheets("mini_lazer_idle", "mini_lazer_atk");
    UnitSpriteSheets GIVEAWAY_SLIME_SPRITE = new UnitSpriteSheets("rainbow_slime", "rainbow_slime");
    UnitSpriteSheets BLOCK_FIELD_SPRITE = new UnitSpriteSheets();
    UnitSpriteSheets MIPYA_SPRITE = new UnitSpriteSheets("mipya_idle", "mipya_atkpng");
    UnitSpriteSheets SNAKE_SPRITE = new UnitSpriteSheets("snake_idle", "snake_atk");
    UnitSpriteSheets PYTHON_SPRITE = new UnitSpriteSheets("python_idle", "python_atk");
    UnitSpriteSheets ALPHA_WOLF_SPRITE = new UnitSpriteSheets("alpha_wolf_idle", "alpha_wolf_atk");
    UnitSpriteSheets WEREWOLF_SPRITE = new UnitSpriteSheets("werewolf_idle", "wolf-att");
    UnitSpriteSheets VAMPIRE_SPRITE = new UnitSpriteSheets("vampire_idle", "vampire_atk");
    UnitSpriteSheets GHOST_SPRITE = new UnitSpriteSheets("Ghost", "Ghost");
    UnitSpriteSheets JAVA_SKELETON_SPRITE = new UnitSpriteSheets("java_skeleton_idle", "java_skeleton_atk");
    
    // All Unit stats 💡
    UnitStats SKELETON_STATS = new UnitStats(SKELETON_SRITE, 300, 20, 1.5, 100, 7, ATTACKER, SKELETON_DESC);
    UnitStats BLACK_SKELETON_STATS = new UnitStats(BLACK_SKELETON_SRITE, 300, 25, 1.5, 300, 14, ATTACKER, BLACK_SKELETON_DESC);
    UnitStats SNIPER_SKELETON_STATS = new UnitStats(SNIPER_SKELETON_SRITE, 300, 20, 1.5, 0, 50, SUPPORT, SNIPER_SKELETON_DESC);
    UnitStats SLIME_STATS = new UnitStats(SLIME_SPRITE, 300, 30, 25, 50, 7, COST_GEN, SLIME_DESC);
    UnitStats KANIWALL_STATS = new UnitStats(KANIWALL_SPRITE, 4000, 0, 0, 50, 30, DEFENDER, KANIWALL_DESC);
    ///BETA///
    UnitStats CANNON_STATS = new UnitStats(CANNON_SPRITE,300,500,60,250,30,ATTACKER, CANNON_DESC);
    UnitStats MIMIC_STATS = new UnitStats(MIMIC_SPRITE, 300, 1800, 42, 150, 12, GUARD, MIMIC_DESC);
    UnitStats EXPLOSION_STATS = new UnitStats(EXPLOSION_SPRITE, 300, 1800, 50, 150, 50, EXPLOTION, EXPLOSION_DESC);
    UnitStats GIVEAWAY_SLIME_STATS = new UnitStats(GIVEAWAY_SLIME_SPRITE, 1, 20, 0.5, 0, 60, COST_GEN);
    UnitStats MIPYA_STATS = new UnitStats(MIPYA_SPRITE, 50, 10, 1.5, 100, 30, ATTACKER, MIPYA_DESC);
    UnitStats SNAKE_STATS = new UnitStats(SNAKE_SPRITE, 400, 35, 0.75, 200, 30, GUARD, SNAKE_DESC);
    UnitStats PYTHON_STATS = new UnitStats(PYTHON_SPRITE, 500, 20, 1.5, 300, 30, ATTACKER, PYTHON_DESC);
    UnitStats JAVA_SKELETON_STATS = new UnitStats(JAVA_SKELETON_SPRITE, 300, 20, 1.5, 200, 30, ATTACKER, JAVA_SKELETON_DESC);
    //TEST ONLY//
    UnitStats GOLEM_SUPPORT_STATS = new UnitStats(GOLEM_SUPPORT_SPRITE, 300, 0, 10, 300, 50, SUPPORT, GOLEM_DESC);
    UnitStats BIGBALL_STATS = new UnitStats(BIGBALL_SPRITE, 300, 400, 10, 400, 30, ATTACKER, BIGBALL);
    UnitStats EXPLOSIVE_TURTLE_STATS = new UnitStats(EXPLOSIVE_TURTLE_SPRITE, 300, 1800, 0, 25, 30, EXPLOTION, EXPLOSIVE_TURTLE_DESC);
    UnitStats SEMI_AUTO_BOT_STATS = new UnitStats(SEMI_AUTO_BOT_SPRITE, 300, 20, 3, 200, 7, ATTACKER, SEMI_AUTO_BOT_DESC);
    UnitStats ALPHA_WOLF_STATS = new UnitStats(ALPHA_WOLF_SPRITE, 300, 90, 5, 200, 50, GUARD, ALPHA_WOLF_DESC);
    UnitStats WEREWOLF_STATS = new UnitStats(WEREWOLF_SPRITE, 500, 30, 1, 300, 50, GUARD, WEREWOLF_DESC);
    UnitStats VAMPIRE_STATS = new UnitStats(VAMPIRE_SPRITE, 300, -50, 4, 100, 7, SUPPORT, VAMPIRE_DESC);
    // Not for Operator
    UnitStats CANDLES6_STATS = new UnitStats(CANDLES6_SPRITE, 9999, 1800, 0, 125, 50, EXPLOTION, CANDLES6_DESC);
    UnitStats BLOCK_FIELD_STATS = new UnitStats(BLOCK_FIELD_SPRITE, 1, 0, 0, 0, 0, -1, BLOCK_FIELD_DESC);
    // Too OP
    UnitStats NIKE_STATS = new UnitStats(NIKE_SPRITE, 1, 9999, 1, 0, 9999, EXPLOTION, NIKE_DESC);
    UnitStats GHOST_STATS = new UnitStats(GHOST_SPRITE, 1, 1290, 0, 300, 30, DEFENDER, GHOST_DESC);

}
