package com.grayzlp.ggithub.data.model.event.inheritance;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.pulls.PullRequest;
import com.grayzlp.ggithub.data.model.pulls.Review;

/**
 * Models a PullRequestReviewEvent.
 */

public class PullRequestReviewEvent extends BaseEvent{


    public static class PullRequestReviewEventPayload {
        public String action;
        public PullRequest pull_request;
        public Review review;
        public String changesbodyfrom;
    }
}
