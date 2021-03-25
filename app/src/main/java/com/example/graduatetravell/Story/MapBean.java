package com.example.graduatetravell.Story;

import java.util.List;

public class MapBean {
    public ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public class ResultBean {
        public LocationBean location;

        public LocationBean getLocation() {
            return location;
        }
    }

    public class LocationBean{
        public String lng;
        public String lat;

        public String getLng() {
            return lng;
        }

        public String getLat() {
            return lat;
        }
    }
}
