package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for PlayerLobby class.
 *
 * @author Michael Kha
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    /**
     * A default player name
     */
    private static final String name = "test name";

    /**
     * Component under test
     */
    private PlayerLobby CuT;

    /**
     * friendly objects
     */
    private Player player;

    /**
     * Setup the objects for each test
     */
    @BeforeEach
    public void setup() {
        CuT = new PlayerLobby();
        player = new Player(name);
    }

    /**
     * Test valid and invalid usernames.
     */
    @Test
    public void testValidUsername() {
        // letters
        String valid1 = "thisIsValid";
        // letters, numbers, spaces
        String valid2 = "this is valid 123";
        // blank
        String invalid1 = "";
        // only spaces
        String invalid2 = "   ";
        // letters and special characters
        String invalid3 = "thisIsInvalid!@#$%^&*()";
        // special characters
        String invalid4 = "!@#$%^&*()";
        // letters, spaces, special characters
        String invalid5 = "this is invalid !@#";

        assertTrue(CuT.isValidUsername(valid1));
        assertTrue(CuT.isValidUsername(valid2));
        assertFalse(CuT.isValidUsername(invalid1));
        assertFalse(CuT.isValidUsername(invalid2));
        assertFalse(CuT.isValidUsername(invalid3));
        assertFalse(CuT.isValidUsername(invalid4));
        assertFalse(CuT.isValidUsername(invalid5));
    }

    /**
     * Test when a username has already been taken.
     */
    @Test
    public void testUsernameTaken() {
        assertFalse(CuT.isUsernameTaken(name));
        CuT.signIn(player);
        assertTrue(CuT.isUsernameTaken(name));
        CuT.signOut(player);
        assertFalse(CuT.isUsernameTaken(name));
    }

    /**
     * Test when a player signs in.
     */
    @Test
    public void testSignIn() {
        CuT.signIn(player);
        int size = CuT.size();
        assertEquals(1, CuT.size());
        CuT.signIn(player);
        assertEquals(size, CuT.size());
    }

    /**
     * Test when a player signs out.
     */
    @Test
    public void testSignOut() {
        CuT.signIn(player);
        assertEquals(1, CuT.size());
        CuT.signOut(player);
        assertEquals(0, CuT.size());
    }

    /**
     * Test that a player or null is retrieved.
     */
    @Test
    public void testGetPlayer() {
        assertNull(CuT.getPlayer(name));
        CuT.signIn(player);
        assertEquals(player, CuT.getPlayer(name));
        assertNull(CuT.getPlayer("fake"));
    }

    /**
     * Test that all the player names except the player are retrieved.
     */
    @Test
    public void testGetPlayerLobbyNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Player player = new Player("" + i);
            CuT.signIn(player);
            names.add("" + i);
        }
        CuT.signIn(player);
        assertEquals(names, CuT.getPlayerLobbyNames(player));
    }

    /**
     * Test that the list of spectators is updated correctly.
     */
    @Test
    public void testSpectating() {
        Player spectator = new Player("spec");
        CuT.startSpectating(spectator);
        assertTrue(CuT.isSpectating(spectator));
        CuT.stopSpectating(spectator);
        assertFalse(CuT.isSpectating(spectator));
    }

    /**
     * Test that the list of players replaying is updated correctly.
     */
    @Test
    public void testReplaying() {
        Player replayer = new Player("replay");
        CuT.startReplaying(replayer);
        assertTrue(CuT.isReplaying(replayer));
        CuT.stopReplaying(replayer);
        assertFalse(CuT.isReplaying(replayer));
    }

}
