package com.grayzlp.ggithub.data.model.event.inheritance;

import com.grayzlp.ggithub.data.model.event.BaseEvent;
import com.grayzlp.ggithub.data.model.issue.Changes;
import com.grayzlp.ggithub.data.model.pulls.PullRequest;

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
