package com.example.bloodbank;

import java.util.Date;

public class donorinfo {
    String name;
    String blood;
    String phone;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    Date date;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public donorinfo(String name, String phone, String blood, String amount,Date date) {
        this.name = name;
        this.blood = blood;
        this.amount = amount;
        this.phone = phone;
        this.date=date;

    }
    public donorinfo(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    String amount;

}
