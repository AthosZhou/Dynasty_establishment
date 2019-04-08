package com.zhr.athos.dynasty_establishment.Bean;

public class Msg {

    /**
     * code : 200
     * info : {"token":"xx","accid":"xx","name":"xx"}
     */



    private int code;
    private InfoBean info;
    /**
     * desc : check 1520870
     */

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static class InfoBean {
        /**
         * token : xx
         * accid : xx
         * name : xx
         */

        private String token;
        private String accid;
        private String name;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
