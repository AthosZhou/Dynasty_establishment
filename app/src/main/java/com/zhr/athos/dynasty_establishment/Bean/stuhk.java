package com.zhr.athos.dynasty_establishment.Bean;

import java.util.List;

public class stuhk {

    private List<YesBean> Yes;
    private List<NoBean> No;

    public List<YesBean> getYes() {
        return Yes;
    }

    public void setYes(List<YesBean> Yes) {
        this.Yes = Yes;
    }

    public List<NoBean> getNo() {
        return No;
    }

    public void setNo(List<NoBean> No) {
        this.No = No;
    }

    public static class YesBean {
        /**
         * TeaNo : 9201375
         * startdate : 2019-03-11T00:00:00
         * chapter : 1
         */

        private int TeaNo;
        private String startdate;
        private int chapter;

        public int getTeaNo() {
            return TeaNo;
        }

        public void setTeaNo(int TeaNo) {
            this.TeaNo = TeaNo;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public int getChapter() {
            return chapter;
        }

        public void setChapter(int chapter) {
            this.chapter = chapter;
        }
    }

    public static class NoBean {
        /**
         * TeaNo : 9201375
         * startdate : 2019-03-12T00:00:00
         * chapter : 1
         */

        private int TeaNo;
        private String startdate;
        private int chapter;

        public int getTeaNo() {
            return TeaNo;
        }

        public void setTeaNo(int TeaNo) {
            this.TeaNo = TeaNo;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public int getChapter() {
            return chapter;
        }

        public void setChapter(int chapter) {
            this.chapter = chapter;
        }
    }
}
