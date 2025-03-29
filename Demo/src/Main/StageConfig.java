/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Main;

/**
 *
 * @author anawi
 */
public interface StageConfig {
    int EASY = 0;
    int MEDIUM = 1;
    int HARD = 2;
    int BOSS = 3;
    int INFINITY = 4;
    
    StageStats STAGE_BETA = new StageStats("Stage Beta", INFINITY, 9999, "Stage1"); // <- Endless future
    StageStats STAGE_TUTORIAL = new StageStats("Stage Tutorial", EASY, 14, "bg_dark_zone");
   // StageStats STAGE_TUTORIAL = new StageStats("Stage Tutorial", EASY, 1, "bg_dark_zone"); // test ver
    StageStats STAGE_2 = new StageStats("Stage 2", EASY, 31, "bg_dark_zone");//31
    StageStats STAGE_3 = new StageStats("Stage 3", EASY, 34, "bg_dark_zone");
    StageStats STAGE_4 = new StageStats("Stage 4", EASY, 45, "bg_dark_greek");
    StageStats STAGE_5 = new StageStats("Stage 5", MEDIUM, 55, "bg_dark_greek");
    StageStats STAGE_6 = new StageStats("Stage 6", MEDIUM, 61, "cave");
    StageStats STAGE_7 = new StageStats("Stage 7", HARD, 73, "cave");
    StageStats STAGE_8 = new StageStats("Stage 8", HARD, 85, "cave");
    
    StageStats STAGE_BOSS = new StageStats("Together Unto Death", BOSS, 1, "bg_dark_greek");//stage 9
}
