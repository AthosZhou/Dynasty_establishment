package com.zhr.athos.dynasty_establishment.Bean;

import java.util.List;

public class VideoInfo {

    private List<VideoInfoBean> VideoInfo;

    public List<VideoInfoBean> getVideoInfo() {
        return VideoInfo;
    }

    public void setVideoInfo(List<VideoInfoBean> VideoInfo) {
        this.VideoInfo = VideoInfo;
    }

    public static class VideoInfoBean {
        /**
         * TeaNo : 9201375
         * TeaName : 董老师
         * VideoName : 上海
         * UpTime : 2019-02-28T00:00:00
         * VideoSK : 0
         */

        private int TeaNo;
        private String TeaName;
        private String VideoName;
        private String UpTime;
        private int VideoSK;

        public int getTeaNo() {
            return TeaNo;
        }

        public void setTeaNo(int TeaNo) {
            this.TeaNo = TeaNo;
        }

        public String getTeaName() {
            return TeaName;
        }

        public void setTeaName(String TeaName) {
            this.TeaName = TeaName;
        }

        public String getVideoName() {
            return VideoName;
        }

        public void setVideoName(String VideoName) {
            this.VideoName = VideoName;
        }

        public String getUpTime() {
            return UpTime;
        }

        public void setUpTime(String UpTime) {
            this.UpTime = UpTime;
        }

        public int getVideoSK() {
            return VideoSK;
        }

        public void setVideoSK(int VideoSK) {
            this.VideoSK = VideoSK;
        }
    }
}
