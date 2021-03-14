package com.example.graduatetravell.Story;

import java.util.ArrayList;
import java.util.List;

public class StoryResultBean {
    private List<DataBean> elements;
    private long next_start;

    public StoryResultBean(List<DataBean> elements, long next_start) {
        this.elements = elements;
        this.next_start = next_start;
    }

    public StoryResultBean() {
    }

    public List<DataBean> getElements() {
        return elements;
    }

    public void setElements(List<DataBean> elements) {
        this.elements = elements;
    }

    public long getNext_start() {
        return next_start;
    }

    public void setNext_start(long next_start) {
        this.next_start = next_start;
    }


    public class DataBean{
        private List<StoryBean> data;

        public List<StoryBean> getData() {
            return data;
        }

        public void setData(List<StoryBean> data) {
            this.data = data;
        }
    }

    public class StoryBean{
        //此处的name是文章标题
        private String name;
        private UserBean user;
        private String cover_image_default;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getCover_image_default() {
            return cover_image_default;
        }

        public void setCover_image_default(String cover_image_default) {
            this.cover_image_default = cover_image_default;
        }
    }

    public class UserBean{
        private String name;
        private String avatar_l;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar_l() {
            return avatar_l;
        }

        public void setAvatar_l(String avatar_l) {
            this.avatar_l = avatar_l;
        }
    }
}
