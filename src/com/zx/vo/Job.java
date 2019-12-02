package com.zx.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobid;//主键
    private String jobname;//申请类型名称
    private String jobType;//申请类型
    private int days;//天数
    private float money;//金额
    private Date jobdate;//申请日期
    private int state;//状态：1、审核中2、通过
    private String user;//登录用户
    private String remark;//申请说明

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Date getJobdate() {
        return jobdate;
    }

    public void setJobdate(Date jobdate) {
        this.jobdate = jobdate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Job{" +
                "jobid=" + jobid +
                ", jobname='" + jobname + '\'' +
                ", jobType='" + jobType + '\'' +
                ", days=" + days +
                ", money=" + money +
                ", jobdate=" + jobdate +
                ", state=" + state +
                ", user='" + user + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
