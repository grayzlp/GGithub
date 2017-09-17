package com.grayzlp.ggithub.data.api.model.event;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Base github event return from api, but ignore the event payload now, we may add it
 * probably in the future.
 * See https://developer.github.com/v3/activity/events/
 *
 */
public class BaseEvent {

    public String type;
    @SerializedName("public")
    public boolean _public;
    public Repository repo;
    public Actor actor;
    public Organization org;
    public Date created_at;
    public long id;

}
