package com.grayzlp.ggithub.data.model.comment;

import com.grayzlp.ggithub.data.model.user.User;

import java.util.Date;

/**
 * Models payload.
 */

public class Comment {
    public String url;
    public String html_url;
    public long id;
    public User user;
    public String position;
    public String line;
    public String path;
    public String commit_id;
    public Date created_at;
    public Date updated_at;
    public String body;
}
