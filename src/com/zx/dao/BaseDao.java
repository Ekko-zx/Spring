package com.zx.dao;

import com.zx.vo.PageVo;
import org.hibernate.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
//用来写公用的基础增删查改
public class BaseDao {
    @Resource
    private SessionFactory sessionFactory;
    //hql对象的查询列表
    public List select(String hql){
        Session session = sessionFactory.openSession();
        List list = session.createQuery(hql).list();
        session.close();
        return list;
    }
    //sql查询列表
    public List selectBysql(String sql){
        Session session = sessionFactory.openSession();
        SQLQuery sqlquery = session.createSQLQuery(sql);
        sqlquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);//把结果变形成map集合
        List list =  sqlquery.list();
        session.close();
        return list;
    }

    //sql查询带分页
    public List selectBysqlPage(String sql, PageVo page){
        Session session = sessionFactory.openSession();
        SQLQuery sqlquery =session .createSQLQuery(sql);
        sqlquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);//把结果变形为 List<Map>
        sqlquery.setFirstResult((page.getPagenum() - 1) * page.getPagesize());//开始行数
        sqlquery.setMaxResults(page.getPagesize());//每页行数(每次查几行)
        List list = sqlquery.list();
        session.close();
        return  list;
    }

    //查询数据的总条数
    public int selectcount(String sql){
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(sql);
        int i = Integer.parseInt(query.uniqueResult().toString()+"");
        session.close();
        return i;
    }


    /*
    *
    * 对单个对象的增删改
    * **/
    public List select(Object obj){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from "+obj.getClass().getSimpleName());
        List list = query.list();
        session.flush();
        session.close();
        return list;
    }

    public Object getObject(Class cla, Integer id) {
        Session session = sessionFactory.openSession();
        Object obj=  session.get(cla, id);
        session.close();
        return obj;
    }

    public void insetr(Object obj){
        System.out.println("新增");
        Session session = sessionFactory.openSession();
        session.save(obj);
        session.flush();
        session.close();
    }

    public void delete(Object obj){
        Session session = sessionFactory.openSession();
        session.delete(obj);
        session.flush();
        session.close();
    }

    public void update(Object obj){
        Session session = sessionFactory.openSession();
        session.update(obj);
        session.flush();
        session.close();
    }

    /**
     * 三合一增删改
     * */
    public void execteSql(String sql){
        Session session = sessionFactory.openSession();
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.executeUpdate();
        session.close();
    }
}
