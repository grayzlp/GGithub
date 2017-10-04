package com.grayzlp.ggithub.data.api.model.issue;

import com.google.gson.annotations.SerializedName;

/**
 * Models a label.
 */

public class Label {
    public long id;
    public String url;
    public String name;
    public String color;
    @SerializedName("default")
    public boolean _default;
}
