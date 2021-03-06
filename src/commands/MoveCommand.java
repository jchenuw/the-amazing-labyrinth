package commands;

import algorithm.GraphSearch;
import model.Board;
import model.Player;
import model.tiles.Tile;

public class MoveCommand implements Command {

    private Board board;
    private Tile[][] tiles;
    private Player player;
    private int targetRow;
    private int targetCol;

    private GraphSearch graphSearch;

    /**
     * Constructor
     *
     * @param board board model
     * @param graphSearch graph search algorithm to use
     */
    public MoveCommand(Board board, GraphSearch graphSearch) {
        this.board = board;
        this.tiles = board.getTiles();
        this.graphSearch = graphSearch;
    }

    /**
     * Moves player on the board to the target location
     */
    @Override
    public void execute() {
        board.movePlayer(player, targetRow, targetCol);
    }

    /**
     * Determine if player can move to target location by using graph search
     * to find target tile
     *
     * @return if tile can be reached
     */
    @Override
    public boolean isLegal() {
        return graphSearch.reachable(tiles, player.getRow(), player.getCol(), targetRow, targetCol);
    }

    // Getters and Setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    public int getTargetCol() {
        return targetCol;
    }

    public void setTargetCol(int targetCol) {
        this.targetCol = targetCol;
    }
}
