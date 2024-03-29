package it.polimi.se2018.view.comand_line;

import java.io.Serializable;

/**
 * @author Luciano
 * Enumeration class contains all the possible inputs
 * that can be activated by messages sent to the players
 */
public enum InputManager implements Serializable{

    INPUT_NEW_CONNECTION,
    INPUT_PLAYER_NAME,
    INPUT_OLD_PLAYER_NAME,
    INPUT_SCHEMA_CARD,
    INPUT_CHOOSE_MOVE,
    INPUT_CHOOSE_DIE,
    INPUT_CHOOSE_DIE_PLACE_DIE,
    INPUT_PLACE_DIE,
    INPUT_TOOL_PLACE_DIE,
    INPUT_MODIFY_DIE_VALUE,
    INPUT_INCREASE_DIE_VALUE,
    INPUT_MOVE_DIE_ON_WINDOW,
    INPUT_CHOOSE_VALUE,
    INPUT_POSITION_DRAFTPOOL_POSITION_ROUNDTRACK,
    INPUT_VOID_TOOL_CARD,
    INPUT_PLAYER_DISABLED

}
