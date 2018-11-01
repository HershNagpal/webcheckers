package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

/**
 * Unit test suite for GameCenter\
 *
 * @author Michael Kha
 */
@Tag("Application-tier")
public class GameCenterTest {

    /**
     * Component under test
     */
    private GameCenter gameCenter;

    /**
     * friendly objects
     */
    private Game game;

    /**
     * mock objects
     */
    private Messenger messenger;
    private Player player;
    private Player opponent;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        messenger = mock(Messenger.class);
        player = mock(Player.class);
        opponent = mock(Player.class);

        gameCenter = new GameCenter();
        game = gameCenter.createGame(player, opponent);
    }

    /**
     * Test that the given player is already in a game or not.
     */
    @Test
    public void testPlayerInGame() {

    }

    /**
     * Test that a game can be found given a player.
     */
    @Test
    public void testGetGame() {

    }

    /**
     * Test that a game can be created with two players.
     */
    @Test
    public void testCreateGame() {

    }

    /**
     * Test that a game can be removed from the game center.
     */
    @Test
    public void testRemoveGame() {

    }

    /**
     * Test that the turn status of a player can be checked.
     */
    @Test
    public void testCheckTurn() {

    }

    /**
     * Test that a move made by a player is valid or invalid.
     */
    @Test
    public void testValidateMove() {

    }

    /**
     * Test that submitting a turn was successful or not.
     */
    @Test
    public void testSubmitTurn() {

    }

    /**
     * Test that backing up a move made this turn is successful or not.
     */
    @Test
    public void testBackupMove() {

    }

}
