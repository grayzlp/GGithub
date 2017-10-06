package com.grayzlp.ggithub.data.model.event.inheritance;

import com.grayzlp.ggithub.data.model.common.Organization;
import com.grayzlp.ggithub.data.model.user.User;

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
