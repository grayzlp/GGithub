package com.grayzlp.ggithub.data.api.model.issue;

import com.grayzlp.ggithub.data.api.model.repo.Repository;
import com.grayzlp.ggithub.data.api.model.pulls.PullRequest;
import com.grayzlp.ggithub.data.api.model.user.User;

import java.util.Date;

/**
 * Models an issue.
 */

public class Issue {
    public long id;
    public String url;
    public String repository_url;
    public String labels_url;
    public String comments_url;
    public String events_url;
    public String html_url;
    public int number;
    public String state;
    public String title;
    public String body;
    public User user;
    public Label[] labels;
    public User assignee;
    public Milestone milestone;
    public boolean locked;
    public int comments;
    public PullRequest pull_request;
    public Date closed_at;
    public Date created_at;
    public Date updated_at;
    public Repository repository;


}
