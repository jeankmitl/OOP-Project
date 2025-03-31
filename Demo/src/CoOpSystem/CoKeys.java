/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package CoOpSystem;

/**
 *
 * @author anawi
 */
public interface CoKeys {
    String MSG_TEST = "msgTest";
    String IS_READY_PLAY = "isReadyPlay";
    
    // on StageSelector
    String STAGE_NAME = "stageName";
    String HOVER_XY = "hoverXY";
    String GET_GAME_PANEL = "getGamePanel";
    
    // on UnitSelector
    String GET_UNIT_SELECTOR = "getUnitSelector";
    String READY_UNIT_SELECTOR = "readyUnitSelector";
    
    ////** on GamePanel2Players
    String SET_P2_UNIT = "cliUnit";
    String START_GAME = "startGame";
    
    //send each Other
    String BOTH_SELCT_XY = "bothSelectXY";
    String BOTH_PLACE_XY = "bothPlaceXY";
    
    //send to Server
    String REQ_PLACE_UNIT = "reqPlaceUnit";
    
    //send to Client 
    String UPDATE_CLI = "updateCli";
    // use Big Data (to Client)
    String ALL_UNIT_ID = "allUnitID";
    String ALL_ENEMY_ID = "allEnemyID";
    String UNIT_ = "unit";
    String UNIT_TYPE_SVR_ = "unitTypeSvr";
    String UNIT_TYPE_CLI_ = "unitTypeCli";
    String ENEMY_ = "enemy";
    
}
