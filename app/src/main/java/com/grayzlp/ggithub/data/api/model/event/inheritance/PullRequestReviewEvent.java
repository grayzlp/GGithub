package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.api.model.pulls.PullRequest;
import com.grayzlp.ggithub.data.api.model.pulls.Review;

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
