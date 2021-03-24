package com.example.graduatetravell.Story;

import java.util.List;

public class StoryDetailBean {
    private String cover_image;
    private UserBean user;
    private List<DateBean> days;
    private List<String> city_slug_urls;

    public StoryDetailBean(String cover_image, UserBean user, List<DateBean> days) {
        this.cover_image = cover_image;
        this.user = user;
        this.days = days;
    }

    public StoryDetailBean() {
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<DateBean> getDays() {
        return days;
    }

    public void setDays(List<DateBean> days) {
        this.days = days;
    }

    public List<String> getCity_slug_urls() {
        return city_slug_urls;
    }

    public void setCity_slug_urls(List<String> city_slug_urls) {
        this.city_slug_urls = city_slug_urls;
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

    public class DateBean{
        private String date;
        private String day;
        private List<WayPointBean> waypoints;

        public String getDate() {
            return date;
        }

        public String getDay() {
            return day;
        }

        public List<WayPointBean> getWaypoints() {
            return waypoints;
        }
    }

    public class WayPointBean {
        private String photo;
        private String text;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

