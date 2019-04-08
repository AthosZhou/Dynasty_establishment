package com.zhr.athos.dynasty_establishment.Bean;

import java.util.List;

public class subject {


    private List<SjBean> sj;

    public List<SjBean> getSj() {
        return sj;
    }

    public void setSj(List<SjBean> sj) {
        this.sj = sj;
    }

    public static class SjBean {
        /**
         * No_s_1 : 10
         * No_s_2 : 11
         * No_s_3 : 9
         * No_s_4 : 7
         * No_s_5 : 1
         */

        private int No_s_1;
        private int No_s_2;
        private int No_s_3;
        private int No_s_4;
        private int No_s_5;

        public int getNo_s_1() {
            return No_s_1;
        }

        public void setNo_s_1(int No_s_1) {
            this.No_s_1 = No_s_1;
        }

        public int getNo_s_2() {
            return No_s_2;
        }

        public void setNo_s_2(int No_s_2) {
            this.No_s_2 = No_s_2;
        }

        public int getNo_s_3() {
            return No_s_3;
        }

        public void setNo_s_3(int No_s_3) {
            this.No_s_3 = No_s_3;
        }

        public int getNo_s_4() {
            return No_s_4;
        }

        public void setNo_s_4(int No_s_4) {
            this.No_s_4 = No_s_4;
        }

        public int getNo_s_5() {
            return No_s_5;
        }

        public void setNo_s_5(int No_s_5) {
            this.No_s_5 = No_s_5;
        }
    }
}
