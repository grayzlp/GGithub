package com.grayzlp.ggithub.data.model.event.inheritance;

import com.grayzlp.ggithub.data.model.comment.Comment;
import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.issue.Changes;
import com.grayzlp.ggithub.data.model.issue.Issue;

/**
 * Models a IssueCommentEvent
 */

public class IssueCommentEvent extends BaseEvent {

    public IssueCommentEventPayload payload;

    public static class IssueCommentEventPayload {
        public String action;
        public Changes changes;
        public String changesbodyfrom;
        public Issue issue;
        public Comment comment;
    }
}
