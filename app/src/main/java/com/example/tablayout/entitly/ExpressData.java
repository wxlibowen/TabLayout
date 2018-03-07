package com.example.tablayout.entitly;

/**
 * 项目名称：TabLayout
 * 包名：com.example.tablayout.entitly
 * 文件名：CourierData
 * 创建者：LBW
 * 创建时间：2018/1/99:182018
 * 描述：快递查询实体类
 */

public class ExpressData {
    //时间
    private String datetime;
    //状态
    private String remark;
    //城市
    private String zone;



    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }




    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }


    @Override
    public String toString() {
        return "CourierData{" +
                "datatime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
