package com.zhr.athos.dynasty_establishment.Bean;

import java.util.List;

public class Contact {

    private List<ContactBean> Contact;

    public List<ContactBean> getContact() {
        return Contact;
    }

    public void setContact(List<ContactBean> Contact) {
        this.Contact = Contact;
    }

    public static class ContactBean {
        /**
         * Num : 9201375
         * Nick : 董老师
         */

        private int Num;
        private String Nick;

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }

        public String getNick() {
            return Nick;
        }

        public void setNick(String Nick) {
            this.Nick = Nick;
        }
    }
}