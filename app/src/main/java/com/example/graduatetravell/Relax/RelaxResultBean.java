package com.example.graduatetravell.Relax;

import com.example.graduatetravell.Story.StoryResultBean;

import java.util.List;

public class RelaxResultBean {

    private List<DataBean> hot_spot_list;

    public List<DataBean> getHot_spot_list() {
        return hot_spot_list;
    }

    public void setHot_spot_list(List<DataBean> hot_spot_list) {
        this.hot_spot_list = hot_spot_list;
    }

    public class DataBean{
        private String text;
        private String index_cover;
        private UserBean user;
        private String spot_id;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIndex_cover() {
            return index_cover;
        }

        public void setIndex_cover(String index_cover) {
            this.index_cover = index_cover;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getSpot_id() {
            return spot_id;
        }

        public void setSpot_id(String spot_id) {
            this.spot_id = spot_id;
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
