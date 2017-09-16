package com.grayzlp.ggithub.data.api.model.feed;

/**
 * Models the feeds. See https://developer.github.com/v3/activity/feeds/
 */

public class Feeds {

    public String timeline_url;
    public String user_url;
    public String current_user_public_url;
    public String current_user_url;
    public String current_user_actor_url;
    public String current_user_organization_url;
    public String[] current_user_organization_urls;

    public FeedLinks _links;

}
