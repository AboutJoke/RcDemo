package com.fantasy.rcdemo.domain;

/**
 * Created by Fantasy on 2016/4/25.
 */
public class DescribeItem {

    //人物头像
    private String per_icon;

    //描述文本
    private String per_desc;


    public String getPer_icon() {
        return per_icon;
    }

    public void setPer_icon(String per_icon) {
        this.per_icon = per_icon;
    }

    public String getPer_desc() {
        return per_desc;
    }

    public void setPer_desc(String per_desc) {
        this.per_desc = per_desc;
    }

    public DescribeItem(String per_desc) {
        this.per_desc = per_desc;
    }
}
