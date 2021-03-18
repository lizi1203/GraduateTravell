package com.example.graduatetravell.Relax;

import java.util.List;

public class RelaxDetailBean {

    private SpotBean spot;
    private tripBean trip;

    public SpotBean getSpot() {
        return spot;
    }

    public void setSpot(SpotBean spot) {
        this.spot = spot;
    }

    public tripBean getTrip() {
        return trip;
    }

    public class SpotBean {
        private String text;
        private List<CommentBean> comments;
        private List<DetailBean> detail_list;
        private String cover_image;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<CommentBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentBean> comments) {
            this.comments = comments;
        }

        public List<DetailBean> getDetail_list() {
            return detail_list;
        }

        public void setDetail_list(List<DetailBean> detail_list) {
            this.detail_list = detail_list;
        }

        public String getCover_image() {
            return cover_image;
        }

        public void setCover_image(String cover_image) {
            this.cover_image = cover_image;
        }
    }

    public class CommentBean {
        private String comment;
        private String date_added;
        private userBean user;

        public String getComment() {
            return comment;
        }

        public String getDate_added() {
            return date_added;
        }

        public userBean getUser() {
            return user;
        }
    }

    public class userBean {
        private String name;
        private String avatar_l;

        public String getName() {
            return name;
        }

        public String getAvatar_l() {
            return avatar_l;
        }
    }

    public class DetailBean {
        private String text;
        private String photo;

        public String getText() {
            return text;
        }

        public String getPhoto() {
            return photo;
        }
    }

    public class tripBean {
        private userBean user;

        public userBean getUser() {
            return user;
        }
    }
}
