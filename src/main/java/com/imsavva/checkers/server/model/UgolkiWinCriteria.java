package com.imsavva.checkers.server.model;

import com.imsavva.checkers.server.beans.Board;
import com.imsavva.checkers.server.beans.Figure;
import com.imsavva.checkers.server.beans.Player;
import com.imsavva.checkers.server.beans.Point;

import java.util.List;

/**
 * @author Savva Kodeikin
 */
public class UgolkiWinCriteria implements WinCriteria {

    private List<Point> blacksWinPoints = UgolkiGame.CheckersParams.getWhitesStartPoints();
    private List<Point> whitesWinPoints = UgolkiGame.CheckersParams.getBlacksStartPoints();
    private Player whitesPlayer;
    private boolean fightForDeadHeat = false;

    /**
     * Check game status. If all black figures are placed to whites start positions,
     * the active player wins. However, if all white figures are placed to blacks
     * start positions, the player who plays black has one more turn to finish the game,
     * as "whites" player started first. In case when "blacks" player places all his
     * figures to whites start positions, it is the dead heat.
     *
     * @param board a game board
     * @param activePlayer the current player
     * @return WinCheckResponse response containing game status
     */
    public WinCheckResponse checkForWinner(Board board, Player activePlayer) {
        List<Point> playerFiguresPoints = board.getFiguresPoints(activePlayer.getColor());
        List<Point> winPositions = getWinPositions(activePlayer);
        WinCheckResponse response = new WinCheckResponse(WinCheckResponse.Status.IN_PROGRESS);

        if (activePlayer.getColor() == Figure.Color.BLACK) {
            if (fightForDeadHeat) {
                if (playerFiguresPoints.containsAll(winPositions)) {
                    response.setStatus(WinCheckResponse.Status.DEAD_HEAT);
                } else {
                    response.setStatus(WinCheckResponse.Status.FINISHED);
                    response.setWinner(whitesPlayer);
                }
            } else {
                if (playerFiguresPoints.containsAll(winPositions)) {
                    response.setStatus(WinCheckResponse.Status.FINISHED);
                    response.setWinner(activePlayer);
                }
            }
        } else {
            if (playerFiguresPoints.containsAll(winPositions)) {
                whitesPlayer = activePlayer;
                fightForDeadHeat = true;
            }
        }

        return response;
    }

    private List<Point> getWinPositions(Player activePlayer) {
        return activePlayer.getColor() == Figure.Color.WHITE
                ? whitesWinPoints : blacksWinPoints;
    }
}
