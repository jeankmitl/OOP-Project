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
    
    // on GamePanel2Players
    String SET_P2_UNIT = "cliUnit";
    String START_GAME = "startGame";
    
    String BOTH_SELCT_XY = "bothSelectXY";
    String BOTH_PLACE_XY = "bothPlaceXY";
    
    String PLACE_UNIT_XY = "";
    String ALL_UNITS_NAME = "allUnitsName";
    String ALL_UNITS_ROWCOL = "allUnitsRowCol";
    String ALL_UNITS_HEALTH = "allUnitsHealth";
}
