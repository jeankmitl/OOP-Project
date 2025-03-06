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
    EnemySpriteSheets NINJA_SPRITE = new EnemySpriteSheets("Ninja", "NinjaATK");
    EnemySpriteSheets BANDIT_SPRITE = new EnemySpriteSheets("Bandit", null);
    EnemySpriteSheets SORCERER_SPRITE = new EnemySpriteSheets("Sorcerer", "SorcererATK");
    EnemySpriteSheets LITTLE_RED_HOOD_SPRITE = new EnemySpriteSheets("little_Red_Hood_Idle", "little_Red_Hood_ATK");
    
    // All Unit stats 💡
    EnemyStats NINJA_STATS = new EnemyStats(NINJA_SPRITE, 80, 20, 1, 0.25, NORMAL);
    EnemyStats BANDIT_STATS = new EnemyStats(BANDIT_SPRITE, 100, 20, 1, 0.25, LONG_RANGE);
    EnemyStats SORCERER_STATS = new EnemyStats(SORCERER_SPRITE, 80, 40, 1, 0.25, NORMAL);
    EnemyStats LITTLE_RED_HOOD_STATS = new EnemyStats(LITTLE_RED_HOOD_SPRITE, 70, 20, 0.5, NORMAL, NORMAL);
}
