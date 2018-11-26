package com.webcheckers.ui;

import spark.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Sign-in page.
 *
 * @author Michael Kha
 */
public class GetSignInRoute implements Route {

    /**
     * Attribute for the title of the page
     */
    static final String TITLE_ATTR = "title";

    /**
     * The actual title for the page
     */
    static final String TITLE = "Sign In";

    /**
     * Attribute for the view name
     */
    static final String VIEW_NAME = "signin.ftl";

    private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

    /**
     * The template engine used to render the page
     */
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignInRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        LOG.config("GetSignInRoute is initialized.");
    }

    /**
     * Render the WebCheckers Sign In page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign In page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSignInRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put(TITLE_ATTR, TITLE);
        return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
    }
}