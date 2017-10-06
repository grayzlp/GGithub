package com.grayzlp.ggithub.data.model.event.inheritance;

import com.grayzlp.ggithub.data.model.event.BaseEvent;

/**
 * Models a CreateEvent.
 */

public class CreateEvent extends BaseEvent {

    public CreateEventPayload payload;

    public static class CreateEventPayload {
        public String ref_type;
        public String ref;
        public String master_branch;
        public String description;

    }

}
