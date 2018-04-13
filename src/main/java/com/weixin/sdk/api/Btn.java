package com.weixin.sdk.api;

import java.util.List;

/**
 * Created by Administrator on 2017-10-27.
 */
public class Btn {
    private String url;
    private String media_id;
    private String name;
    private String key;
    private String type;
    private List<Btn> sub_button;

    public Btn() {
    }

    public static Btn newClick(String name, String key) {
        Btn btn = new Btn();
        btn.name = name;
        btn.key = key;
        btn.type = "click";
        return btn;
    }

    public static Btn newView(String name, String url) {
        Btn btn = new Btn();
        btn.name = name;
        btn.url = url;
        btn.type = "view";
        return btn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Btn> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<Btn> sub_button) {
        this.sub_button = sub_button;
    }
}
