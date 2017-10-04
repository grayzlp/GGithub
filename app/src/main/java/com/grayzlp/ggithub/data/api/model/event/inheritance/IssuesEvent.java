package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.api.model.issue.Changes;
import com.grayzlp.ggithub.data.api.model.issue.Issue;
import com.grayzlp.ggithub.data.api.model.issue.Label;
import com.grayzlp.ggithub.data.api.model.user.User;

/**
 * Models a IssuesEvent.
 */

public class IssuesEvent extends BaseEvent {

    public IssueCommentPayload payload;

    public static class IssueCommentPayload {
        public String action;
        public Issue issue;
        public Changes changes;
        public String changestitlefrom;
        public String changesbodyfrom;
        public User assignee;
        public Label label;
    }

}
