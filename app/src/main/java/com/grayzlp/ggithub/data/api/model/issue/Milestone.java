package com.grayzlp.ggithub.data.api.model.issue;

import com.grayzlp.ggithub.data.api.model.user.User;

import java.util.Date;

/**
 * Models a Milestone.
 */

public class Milestone {
    public String url;
    public String html_url;
    public String labels_url;
    public long id;
    public int number;
    public String state;
    public String title;
    public String description;
    public User creator;
    public int open_issues;
    public int closed_issues;
    public Date created_at;
    public Date updated_at;
    public Date closed_at;
    public Date due_on;
}
