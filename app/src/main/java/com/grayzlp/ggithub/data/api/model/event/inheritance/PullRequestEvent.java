package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.event.BaseEvent;
import com.grayzlp.ggithub.data.api.model.issue.Changes;
import com.grayzlp.ggithub.data.api.model.pulls.PullRequest;

/**
 * Models a PullRequestEvent.
 */

public class PullRequestEvent extends BaseEvent {

    public PullRequestEventPayload payload;

    public static class PullRequestEventPayload {
        public String action;
        public int number;
        public Changes changes;
        public String changestitlefrom;
        public String changesbodyfrom;
        public PullRequest pull_request;
    }
}
