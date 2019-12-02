package com.zx.service.impl;

import com.zx.dao.BaseDao;
import com.zx.service.JobService;
import com.zx.vo.Job;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobServiceImpl implements JobService{
    @Resource
    BaseDao dao;
    @Override
    public void addJob(Job job) {
        dao.insetr(job);
    }

    @Override
    public List<Job> all(String user) {
        return dao.select("from Job j where j.user = '"+user+"' ");
    }

    @Override
    public Job selejob(int jobid) {
        return (Job) dao.getObject(Job.class,jobid);
    }

    @Override
    public void update(Job job) {
        dao.update(job);
    }
}
