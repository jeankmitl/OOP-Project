/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Entities.Enemies;

/**
 *
 * @author anawi
 */
public interface EnemyConfig {
    int NORMAL = 0;
    int LONG_RANGE = 1;
    
    double WALK_SLOW = 0.13;
    double WALK_NORMAL  = 0.17;
    double WALK_FAST = 0.2;
    double WALK_CAR = 0.4;
    
    // All sprite sheet ️🎥
    EnemySpriteSheets NINJA_SPRITE = new EnemySpriteSheets("ninja-walk", "ninja-att");
    EnemySpriteSheets BANDIT_SPRITE = new EnemySpriteSheets("hum-walk", "hum-att");
    EnemySpriteSheets BANDIT_SPRITEV2 = new EnemySpriteSheets("hum2-walk", "hum2-att");
    EnemySpriteSheets BANDIT_SPRITEV3 = new EnemySpriteSheets("hum3-walk", "hum3-att");
    EnemySpriteSheets SORCERER_SPRITE = new EnemySpriteSheets("Sorcerer", "SorcererATK");
    EnemySpriteSheets LITTLE_RED_HOOD_SPRITE = new EnemySpriteSheets("little_Red_Hood_Idle", "little_Red_Hood_ATK");
    EnemySpriteSheets RC_BOMBER_SPRITE = new EnemySpriteSheets("rc_bomber", "rc_bomber");
    EnemySpriteSheets ROBOT_MONOWHEEL_SPRITE = new EnemySpriteSheets("robot_monowheel_idle", "robot_monowheel_atk");
    EnemySpriteSheets TANK_SPRITE = new EnemySpriteSheets("tank", "tank");
    
    // All Unit stats 💡
    EnemyStats NINJA_STATS = new EnemyStats(NINJA_SPRITE, 335, 125, 1, WALK_NORMAL, NORMAL);
    EnemyStats BANDIT_STATS = new EnemyStats(BANDIT_SPRITE, 181, 100, 1, WALK_NORMAL, LONG_RANGE);
    EnemyStats BANDIT_STATSV2 = new EnemyStats(BANDIT_SPRITEV2, 551, 100, 1, WALK_NORMAL, LONG_RANGE);
    EnemyStats BANDIT_STATSV3 = new EnemyStats(BANDIT_SPRITEV3, 1290, 100, 1, WALK_NORMAL, LONG_RANGE);
    EnemyStats SORCERER_STATS = new EnemyStats(SORCERER_SPRITE, 301, 100, 1, WALK_SLOW, NORMAL);
    EnemyStats LITTLE_RED_HOOD_STATS = new EnemyStats(LITTLE_RED_HOOD_SPRITE, 801, 125, 1, WALK_FAST, NORMAL);
    EnemyStats RC_BOMBER_STATS = new EnemyStats(RC_BOMBER_SPRITE, 50, 2000, 1, WALK_CAR, NORMAL);
    EnemyStats ROBOT_MONOWHEEL_STATS = new EnemyStats(ROBOT_MONOWHEEL_SPRITE, 300, 150, 1, WALK_NORMAL, NORMAL);
    EnemyStats TANK_STATS = new EnemyStats(TANK_SPRITE, 1000, 20, 0.1, WALK_SLOW, NORMAL);
}
