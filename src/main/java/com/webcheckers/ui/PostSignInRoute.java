package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostSignInRoute implements Route {

    static final String TITLE_ATTR = "title";
    static final String ERROR_ATTR = "error";
    static final String PLAYER_ATTR = "player";
    static final String NAME_PARAM = "myName";
    static final String VIEW_NAME = "signin.ftl";

    static final String TITLE = "Sign In";
    static final String INVALID_NAME = "Name must have at least one alphanumeric character.";
    static final String NAME_TAKEN = "Name has already been taken.";

    private final PlayerLobby playerLobby;
    private final TemplateEngine templateEngine;

    public PostSignInRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {

        Objects.requireNonNull(playerLobby, "playerLobby must not be null");
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");

        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
    }

    /**
     * Make an error message when the user enters invalid input
     */
    public String badArgMessge(final String badargs){
        return String.format("The name you entered '%s' is invalid or already in use", badargs);
    }

    /**
     * Handle the sign in POST request.
     * TODO: only allow a single session to log in as one user
     * @param request the HTTP request
     * @param response the HTTP response
     * @return
     */
    @Override
    public Object handle(Request request, Response response) {
        final Map<String, Object> vm = new HashMap<>();
        final Session session = request.session();

        vm.put(TITLE_ATTR, TITLE);
        final String name = request.queryParams(NAME_PARAM);

        System.out.println(name);
        if (!playerLobby.isValidUsername(name)) {
            vm.put(ERROR_ATTR, INVALID_NAME);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }

        if (playerLobby.isUsernameTaken(name)) {
            vm.put(ERROR_ATTR, NAME_TAKEN);
            return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
        }

        Player player = new Player(name);
        playerLobby.signIn(player);
        session.attribute(PLAYER_ATTR, player);
        // Go back to home page
        response.redirect("/");
        return null;
    }
}
