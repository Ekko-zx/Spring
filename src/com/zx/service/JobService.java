package com.zx.service;

import com.zx.vo.Job;
import org.springframework.stereotype.Service;

import java.util.List;


public interface JobService {
    void addJob(Job job);//入职申请
    List<Job> all(String user);//所有申请
    Job selejob(int jobid);//查询
    void update(Job job);//修改单据信息
}
