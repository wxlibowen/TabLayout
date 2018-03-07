package com.example.tablayout.entitly;

import cn.bmob.v3.BmobUser;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.entily
 * 文件名：MyUser
 * 创建者：LBW
 * 创建时间：2017/12/2910:462017
 * 描述：用户属性
 */

public class MyUser extends BmobUser {
    private int age;
    private  boolean sex;
    private String intro;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
