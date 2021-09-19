package view;

import model.Board;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BoardDisplay extends JPanel {

    public static final int BOARD_SIDE_LENGTH = 600;
    public static final int TILES_SIDE_LENGTH = BOARD_SIDE_LENGTH/Board.NUM_OF_TILES_PER_SIDE;

    // Underlying board model
    private Board board;

    private TileDisplay[][] tileDisplays = new TileDisplay[7][7];
    private JLabel[] treasureViews;
    private JLabel[] playerViews;

    /**
     * Constructor
     *
     * @param board board model
     */
    public BoardDisplay(Board board) {
        this.board = board;
        treasureViews = new JLabel[board.getTreasures().length];
        playerViews = new JLabel[board.getPlayers().length];

        // Properties setup
        setSize(BOARD_SIDE_LENGTH, BOARD_SIDE_LENGTH);
        setLayout(new GridLayout(7,7));
        setVisible(true);
        setBackground(new Color(150, 220, 220));

        setupTileDisplays();
        setupPlayerViews();
    }

    /**
     * Setup board tiles graphics
     */
    private void setupTileDisplays() {
        for(int row = 0; row < Board.NUM_OF_TILES_PER_SIDE; row++){
            for(int col = 0; col < Board.NUM_OF_TILES_PER_SIDE; col++){
                tileDisplays[row][col] = new TileDisplay();
                tileDisplays[row][col].update(board.getTiles()[row][col]);

                add(tileDisplays[row][col]);
            }
        }
    }

    /**
     * Setup player pieces on the board
     */
    private void setupPlayerViews() {
        Player[] players = board.getPlayers();

        for(int i = 0; i < players.length; i++) {
            Player player = players[i];

            // Create new player image
            playerViews[i] = new JLabel(new ImageIcon(getClass().getResource("../resources/game/players/player" + i + ".png")));

            // Properties of playerView
            playerViews[i].setVisible(true);
            playerViews[i].setSize(33,33);
            playerViews[i].setLocation(26 ,29);

            // Add playerView to tile the player is on
            tileDisplays[player.getRow()][player.getCol()].add(playerViews[i], JLayeredPane.DRAG_LAYER);
        }
    }

    /**
     * Updates board to match model
     */
    public void updateBoard(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                tileDisplays[i][j].update(board.getTiles()[i][j]);
            }
        }
    }

    public void updateRowTiles(int row) {
        for(int col = 0; col < tileDisplays.length; col++) {
            tileDisplays[row][col].update(board.getTiles()[row][col]);
        }
    }

    public void updateColTiles(int col) {
        for(int row = 0; row  < tileDisplays.length; row++) {
            tileDisplays[row][col].update(board.getTiles()[row][col]);
        }
    }

    /**
     * Removes player images from their parent tiles and
     * adds them their new {@code tileDisplays}
     */
    public void updatePlayerViews() {
        for(int i = 0; i < playerViews.length; i++) {
            // Remove playerView from old tileDisplay
            TileDisplay oldTileDisplay = (TileDisplay) playerViews[i].getParent();
            oldTileDisplay.remove(playerViews[i]);
            oldTileDisplay.revalidate();
            oldTileDisplay.repaint();

            // Add playerView to new tileDisplay
            tileDisplays[board.getPlayers()[i].getRow()][board.getPlayers()[i].getCol()].add(playerViews[i], JLayeredPane.DRAG_LAYER);
        }
    }

    /**
     * Add ability to listen to user input for board movement
     *
     * @param moveListener user input listener for tile click
     */
    public void addMoveListeners(ActionListener moveListener) {
        for(TileDisplay[] tileDisplayRow: tileDisplays) {
            for(TileDisplay tileDisplay: tileDisplayRow) {
                tileDisplay.addMoveListener(moveListener);
            }
        }
    }

    // Getters and Setters
    public TileDisplay[][] getTileDisplays() {
        return tileDisplays;
    }
}