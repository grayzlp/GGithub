package com.grayzlp.ggithub.data.api.model.event.inheritance;

import com.grayzlp.ggithub.data.api.model.common.Organization;
import com.grayzlp.ggithub.data.api.model.user.User;

/**
 * Models a OrgBlockEvent.
 */

public class OrgBlockEvent {


    public static class OrgBlockEventPayload {
        public String action;
        public User blocked_user;
        public Organization organization;
        public User sender;
    }
}
