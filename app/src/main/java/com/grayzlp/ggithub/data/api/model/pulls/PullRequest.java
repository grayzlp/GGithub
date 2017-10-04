package com.grayzlp.ggithub.data.api.model.pulls;

import com.grayzlp.ggithub.data.api.model.issue.Milestone;
import com.grayzlp.ggithub.data.api.model.user.User;

import java.util.Date;

/**
 * Models a pull request.
 */

public class PullRequest {
    public long id;
    public String url;
    public String html_url;
    public String diff_url;
    public String patch_url;
    public String issue_url;
    public String commits_url;
    public String review_comments_url;
    public String review_comment_url;
    public String comments_url;
    public String statuses_url;
    public int number;
    public String state;
    public String title;
    public String body;
    public User assignee;
    public Milestone milestone;
    public boolean locked;
    public Date created_at;
    public Date updated_at;
    public Date closed_at;
    public Date merged_at;
    public User user;

    // TODO Fix other field
    // i have not understand what it is represent for.
//    public Object head;
//    public Object base;
//    public Object _links;
}
