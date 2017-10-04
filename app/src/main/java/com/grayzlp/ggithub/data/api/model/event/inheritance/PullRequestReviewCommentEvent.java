package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.comment.Comment;
import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.api.model.issue.Changes;
import com.grayzlp.ggithub.data.api.model.pulls.PullRequest;

/**
 * Models a PullRequestReviewCommentEvent
 */

public class PullRequestReviewCommentEvent extends BaseEvent{

    public PullRequestReviewCommentEventPayload payload;

    public static final class PullRequestReviewCommentEventPayload {
        public String action;
        public Changes changes;
        public String changesbodyfrom;
        public PullRequest pull_request;
        public Comment comment;
    }
}
