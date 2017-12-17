package com.grayzlp.ggithub.data.model.gist;

import com.grayzlp.ggithub.data.model.user.User;

/**
 * Models a github gist.
 */

public final class Gist {
    public String id;
    public String description;
    public User owner;
    public String user;
    private int comments;
    // other fields..

}
