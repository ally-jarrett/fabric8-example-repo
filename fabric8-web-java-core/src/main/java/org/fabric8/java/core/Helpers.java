package org.fabric8.java.core;

import org.fabric8.java.web.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 *
 */
public class Helpers {

    private static final transient Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    private static final String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";

    public static void doForbidden(HttpServletResponse response) {
        try {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentLength(0);
            response.flushBuffer();
        } catch (IOException ioe) {
            LOG.debug("Failed to send forbidden response: {}", ioe);
        }
    }

    public static void doAuthPrompt(String realm, HttpServletResponse response) {
        // request authentication
        try {
            response.setHeader(HEADER_WWW_AUTHENTICATE, Authenticator.AUTHENTICATION_SCHEME_BASIC + " realm=\"" + realm + "\"");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentLength(0);
            response.flushBuffer();
        } catch (IOException ioe) {
            LOG.debug("Failed to send auth response: {}", ioe);
        }

    }
}