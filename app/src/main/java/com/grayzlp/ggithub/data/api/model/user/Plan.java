package com.grayzlp.ggithub.data.api.model.user;

/**
 * Models plan of user
 */

public class Plan {

    public final String name;
    public final int space;
    public final int private_repos;
    public final int collaborators;

    public Plan(String name, int space, int private_repos, int collaborators) {
        this.name = name;
        this.space = space;
        this.private_repos = private_repos;
        this.collaborators = collaborators;
    }
}
