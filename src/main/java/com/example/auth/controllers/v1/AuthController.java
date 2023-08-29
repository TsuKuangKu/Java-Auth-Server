package com.example.auth.controllers.v1;

public class AuthController {
    /*
    Authenticate
        user name
        password
        return a special "secret" auth token or error, if not found. The token is only validfor pre-configured time (2h)
    Invalidate
        auth token
        returns nothing. the token is no longer valid after the call. Handles correctly thecase of invalid token given as input
    Check role
        auth token
        role
        returns true if the user, identified by the token, belongs to the role. false otherwise: error if token is invalid expired etc
    All roles
        auth token
        returns all roles for the user. error if token is invalid
     */
}
