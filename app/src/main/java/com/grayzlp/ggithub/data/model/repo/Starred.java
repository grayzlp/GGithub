package com.grayzlp.ggithub.data.model.repo;

import com.google.gson.annotations.SerializedName;
import com.grayzlp.ggithub.data.model.user.User;

import java.util.Date;

/**
 * Models the star of repo.
 */

public class Starred {
    public long id;
    public User owner;
    public String name;
    public String full_name;
    public String description;
    @SerializedName("private")
    public boolean _private;
    public boolean fork;
    public String language;
    public int forks_count;
    public int stargazers_count;
    public int watchers_count;

    public Date pushed_at;
    public Date created_at;
    public Date updated_at;

}
