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
    
    // All sprite sheet ️🎥
    EnemySpriteSheets NINJA_SPRITE = new EnemySpriteSheets("ninja-walk", "ninja-att");
    EnemySpriteSheets BANDIT_SPRITE = new EnemySpriteSheets("Bandit", "Bandit");
    EnemySpriteSheets SORCERER_SPRITE = new EnemySpriteSheets("Sorcerer", "SorcererATK");
    EnemySpriteSheets LITTLE_RED_HOOD_SPRITE = new EnemySpriteSheets("little_Red_Hood_Idle", "little_Red_Hood_ATK");
    EnemySpriteSheets RC_BOMBER_SPRITE = new EnemySpriteSheets("rc_bomber", "rc_bomber");
    EnemySpriteSheets ROBOT_MONOWHEEL_SPRITE = new EnemySpriteSheets("robot_monowheel_idle", "robot_monowheel_atk");
    
    // All Unit stats 💡
    EnemyStats NINJA_STATS = new EnemyStats(NINJA_SPRITE, 335, 100, 1, 0.11, NORMAL);
    EnemyStats BANDIT_STATS = new EnemyStats(BANDIT_SPRITE, 181, 100, 1, 0.11, LONG_RANGE);
    EnemyStats SORCERER_STATS = new EnemyStats(SORCERER_SPRITE, 181, 100, 1, 0.11, NORMAL);
    EnemyStats LITTLE_RED_HOOD_STATS = new EnemyStats(LITTLE_RED_HOOD_SPRITE, 181, 150, 1, 0.11, NORMAL);
    EnemyStats RC_BOMBER_STATS = new EnemyStats(RC_BOMBER_SPRITE, 50, 2000, 1, 0.5, NORMAL);
    EnemyStats ROBOT_MONOWHEEL_STATS = new EnemyStats(ROBOT_MONOWHEEL_SPRITE, 300, 150, 1, 0.12, NORMAL);
}
