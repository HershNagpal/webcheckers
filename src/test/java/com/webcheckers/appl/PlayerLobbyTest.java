package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test suite for PlayerLobby class.
 *
 * @author Michael Kha
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    private static final String name = "test name";

    private PlayerLobby CuT;

    private Player player;

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
        String valid1 = "thisIsValid";
        String valid2 = "this is valid";
        String invalid1 = "";
        String invalid2 = "   ";
        String invalid3 = "thisIsInvalid!@#$%^&*()";
        String invalid4 = "!@#$%^&*()";

        assertTrue(CuT.isValidUsername(valid1));
        assertTrue(CuT.isValidUsername(valid2));
        assertFalse(CuT.isValidUsername(invalid1));
        assertFalse(CuT.isValidUsername(invalid2));
        assertFalse(CuT.isValidUsername(invalid3));
        assertFalse(CuT.isValidUsername(invalid4));
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
        assertEquals(CuT.size(), 1);
        CuT.signIn(player);
        assertEquals(size, CuT.size());
        CuT.signOut(player);
        assertEquals(CuT.size(), 0);
    }

    /**
     * Test when a player signs out.
     */
    @Test
    public void testSignOut() {

    }

    /**
     * Test that a player or null is retrieved.
     */
    @Test
    public void testGetPlayer() {

    }

    /**
     * Test that all the player names except the player are retrieved.
     */
    @Test
    public void testGetPlayerLobbyNames() {

    }

    /**
     * Test the size of the player lobby.
     */
    @Test
    public void testSize() {

    }
}
