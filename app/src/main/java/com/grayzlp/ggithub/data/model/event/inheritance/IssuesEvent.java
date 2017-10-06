package com.grayzlp.ggithub.data.model.event.inheritance;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.issue.Changes;
import com.grayzlp.ggithub.data.model.issue.Issue;
import com.grayzlp.ggithub.data.model.issue.Label;
import com.grayzlp.ggithub.data.model.user.User;

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
