package com.grayzlp.ggithub.data.api.github.model;

import java.util.Date;

/**
 * Models a github user.
 */

public class User {

    public final String login;
    public final long id;
    public final String avatar_url;
    public final String gravatar_id;
    public final String url;
    public final String html_url;
    public final String followers_url;
    public final String following_url;
    public final String gists_url;
    public final String starred_url;
    public final String subscriptions_url;
    public final String organizations_url;
    public final String repos_url;
    public final String events_url;
    public final String received_events_url;
    public final String type;
    public final boolean site_admin;
    public final String name;
    public final String company;
    public final String blog;
    public final String location;
    public final String email;
    public final boolean hireable;
    public final String bio;
    public final int public_repos;
    public final int public_gists;
    public final int followers;
    public final int following;
    public final Date created_at;
    public final Date updated_at;

    /* Field can only get from authenticated use */
    public final int total_private_repos;
    public final int owned_private_repos;
    public final int private_gists;
    public final int disk_usage;
    public final int collaborators;
    public final boolean two_factor_authentication;
    public final Plan plan;


    public User(String login,
                long id,
                String avatar_url,
                String gravatar_id,
                String url,
                String html_url,
                String followers_url,
                String following_url,
                String gists_url,
                String starred_url,
                String subscriptions_url,
                String organizations_url,
                String repos_url,
                String events_url,
                String received_events_url,
                String type,
                boolean site_admin,
                String name,
                String company,
                String blog,
                String location,
                String email,
                boolean hireable,
                String bio,
                int public_repos,
                int public_gists,
                int followers,
                int following,
                Date created_at,
                Date updated_at,
                int total_private_repos,
                int owned_private_repos,
                int private_gists,
                int disk_usage,
                int collaborators,
                boolean two_factor_authentication,
                Plan plan) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.gravatar_id = gravatar_id;
        this.url = url;
        this.html_url = html_url;
        this.followers_url = followers_url;
        this.following_url = following_url;
        this.gists_url = gists_url;
        this.starred_url = starred_url;
        this.subscriptions_url = subscriptions_url;
        this.organizations_url = organizations_url;
        this.repos_url = repos_url;
        this.events_url = events_url;
        this.received_events_url = received_events_url;
        this.type = type;
        this.site_admin = site_admin;
        this.name = name;
        this.company = company;
        this.blog = blog;
        this.location = location;
        this.email = email;
        this.hireable = hireable;
        this.bio = bio;
        this.public_repos = public_repos;
        this.public_gists = public_gists;
        this.followers = followers;
        this.following = following;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.total_private_repos = total_private_repos;
        this.owned_private_repos = owned_private_repos;
        this.private_gists = private_gists;
        this.disk_usage = disk_usage;
        this.collaborators = collaborators;
        this.two_factor_authentication = two_factor_authentication;
        this.plan = plan;
    }

    public static class Builder {
        private String login;
        private long id;
        private String avatar_url;
        private String gravatar_id;
        private String url;
        private String html_url;
        private String followers_url;
        private String following_url;
        private String gists_url;
        private String starred_url;
        private String subscriptions_url;
        private String organizations_url;
        private String repos_url;
        private String events_url;
        private String received_events_url;
        private String type;
        private boolean site_admin;
        private String name;
        private String company;
        private String blog;
        private String location;
        private String email;
        private boolean hireable;
        private String bio;
        private int public_repos;
        private int public_gists;
        private int followers;
        private int following;
        private Date created_at;
        private Date updated_at;

        /* Field can only get from authenticated use */
        private int total_private_repos;
        private int owned_private_repos;
        private int private_gists;
        private int disk_usage;
        private int collaborators;
        private boolean two_factor_authentication;
        private Plan plan;

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
            return this;
        }

        public Builder setGravatar_id(String gravatar_id) {
            this.gravatar_id = gravatar_id;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setHtml_url(String html_url) {
            this.html_url = html_url;
            return this;
        }

        public Builder setFollowers_url(String followers_url) {
            this.followers_url = followers_url;
            return this;
        }

        public Builder setFollowing_url(String following_url) {
            this.following_url = following_url;
            return this;
        }

        public Builder setGists_url(String gists_url) {
            this.gists_url = gists_url;
            return this;
        }

        public Builder setStarred_url(String starred_url) {
            this.starred_url = starred_url;
            return this;
        }

        public Builder setSubscriptions_url(String subscriptions_url) {
            this.subscriptions_url = subscriptions_url;
            return this;
        }

        public Builder setOrganizations_url(String organizations_url) {
            this.organizations_url = organizations_url;
            return this;
        }

        public Builder setRepos_url(String repos_url) {
            this.repos_url = repos_url;
            return this;
        }

        public Builder setEvents_url(String events_url) {
            this.events_url = events_url;
            return this;
        }

        public Builder setReceived_events_url(String received_events_url) {
            this.received_events_url = received_events_url;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setSite_admin(boolean site_admin) {
            this.site_admin = site_admin;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCompany(String company) {
            this.company = company;
            return this;
        }

        public Builder setBlog(String blog) {
            this.blog = blog;
            return this;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setHireable(boolean hireable) {
            this.hireable = hireable;
            return this;
        }

        public Builder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder setPublic_repos(int public_repos) {
            this.public_repos = public_repos;
            return this;
        }

        public Builder setPublic_gists(int public_gists) {
            this.public_gists = public_gists;
            return this;
        }

        public Builder setFollowers(int followers) {
            this.followers = followers;
            return this;
        }

        public Builder setFollowing(int following) {
            this.following = following;
            return this;
        }

        public Builder setCreated_at(Date created_at) {
            this.created_at = created_at;
            return this;
        }

        public Builder setUpdated_at(Date updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public Builder setTotal_private_repos(int total_private_repos) {
            this.total_private_repos = total_private_repos;
            return this;
        }

        public Builder setOwned_private_repos(int owned_private_repos) {
            this.owned_private_repos = owned_private_repos;
            return this;
        }

        public Builder setPrivate_gists(int private_gists) {
            this.private_gists = private_gists;
            return this;
        }

        public Builder setDisk_usage(int disk_usage) {
            this.disk_usage = disk_usage;
            return this;
        }

        public Builder setCollaborators(int collaborators) {
            this.collaborators = collaborators;
            return this;
        }

        public Builder setTwo_factor_authentication(boolean two_factor_authentication) {
            this.two_factor_authentication = two_factor_authentication;
            return this;
        }

        public Builder setPlan(Plan plan) {
            this.plan = plan;
            return this;
        }

        public User build() {
            return new User(login,
                    id,
                    avatar_url,
                    gravatar_id,
                    url,
                    html_url,
                    followers_url,
                    following_url,
                    gists_url,
                    starred_url,
                    subscriptions_url,
                    organizations_url,
                    repos_url,
                    events_url,
                    received_events_url,
                    type,
                    site_admin,
                    name,
                    company,
                    blog,
                    location,
                    email,
                    hireable,
                    bio,
                    public_repos,
                    public_gists,
                    followers,
                    following,
                    created_at,
                    updated_at,
                    total_private_repos,
                    owned_private_repos,
                    private_gists,
                    disk_usage,
                    collaborators,
                    two_factor_authentication,
                    plan);
        }
    }

}
