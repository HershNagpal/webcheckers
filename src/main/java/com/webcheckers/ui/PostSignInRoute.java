package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.Objects;

public class PostSignInRoute implements Route {

    static final String NAME_PARAM = "myName";

    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;

    public PostSignInRoute(PlayerLobby playerLobby, TemplateEngine templateEngine) {

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

    @Override
    public Object handle(Request request, Response response) {
        return null;
    }
}
