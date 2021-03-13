package com.example.graduatetravell.News;

import java.io.Serializable;
import java.util.List;

public class NewsResultBean implements Serializable {
    private String date;
    private List<StoryBean> stories;
    private List<StoryBeanT> top_stories;

    public NewsResultBean(String date, List<StoryBean> stories, List<StoryBeanT> top_stories) {
        this.date = date;
        this.stories = stories;
        this.top_stories = top_stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoryBean> getStories() {
        return stories;
    }

    public void setStories(List<StoryBean> stories) {
        this.stories = stories;
    }

    public List<StoryBeanT> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<StoryBeanT> top_stories) {
        this.top_stories = top_stories;
    }

    public class StoryBean {
        private String title ;
        private String url;
        private String hint;
        private List<String> images;

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getHint() {
            return hint;
        }

        public List<String> getImages() {
            return images;
        }
    }

    public class StoryBeanT{
        private String title ;
        private String url;
        private String hint;
        private String image;

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getHint() {
            return hint;
        }

        public String getImage() {
            return image;
        }
    }
}
