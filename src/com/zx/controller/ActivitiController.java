package com.zx.controller;

import com.zx.service.JobService;
import com.zx.vo.Job;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Controller
public class ActivitiController {
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private TaskService taskService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private HistoryService historyService;
    @Resource
    private RepositoryService repositoryService;

    @Resource
    private JobService service;

    @RequestMapping(value = "/menu")
    public String menu(){//菜单
        return "menu";
    }

    @RequestMapping(value = "/tomain")
    public String tomain(HttpSession session,String user){//去首页
        System.out.println("登录用户:"+user);
        //保存到user
        session.setAttribute("user",user);
        return "main";
    }

    @RequestMapping(value = "/toupload")
    public String toupload(){
        return "uploadProcess";
    }

    @RequestMapping(value = "/uploadProcess")
    public String upload(MultipartFile zipfile){//上传流程zip包
        try {
            File file = File.createTempFile("tmp",null);//创建临时file对象
            zipfile.transferTo(file);//转换成io.file
            //部署zip文件，将流程上传到Activiti框架
            Deployment deployment = repositoryService.createDeployment().addZipInputStream(new ZipInputStream(new FileInputStream(file))).deploy();
            System.out.println("id:"+deployment.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "uploadProcess";
    }

    @RequestMapping(value = "/list")
    public String list(Model model){//查看所有流程
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        query.orderByProcessDefinitionVersion().desc();
        List<ProcessDefinition> list = query.list();
        model.addAttribute("listProcessDefinition",list);
        return "list";
    }

    @RequestMapping(value = "/delProgressDefine")
    public String delete(String id){//删除相关所有流程
        repositoryService.deleteDeployment(id,true);
        return "redirect:list";
    }

    @RequestMapping(value = "/toExport")//下载流程压缩包
    public String toExport(String id, HttpServletResponse response){
        try {

            //设置response对象的头参数，attachment就是附件，filename=文件名称
            response.setHeader("Content-disposition","attachment;filename=" +id+".zip" );
            //下载的文件类型是zip文件
            response.setContentType("application/x-zip-compressed");

            //----------------------------------------------------------------------------

            //流程定义对象
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .processDefinitionId(id).singleResult();
            //部署id
            String deploymentId = processDefinition.getDeploymentId();

            //bpmn资源文件名称
            String resourceName_bpmn = processDefinition.getResourceName();
            //bpmn资源文件输入流
            InputStream inputStream_bpmn = repositoryService.getResourceAsStream(deploymentId, resourceName_bpmn);
            //png文件名称
            String resourceName_png = processDefinition.getDiagramResourceName();
            //png资源文件输入流
            InputStream inputStream_png = repositoryService.getResourceAsStream(deploymentId, resourceName_png);

            //------创建输出流，绑定到response对象-------------------------------------------------------
            OutputStream out = response.getOutputStream();
            //创建ZIP输出对象，绑定到输出流
            ZipOutputStream zipo = new ZipOutputStream(out);

            //流复制
            byte[] b = new byte[1024];
            int len = -1;

            //定义zip压缩包中的文件对象（zip实体）
            ZipEntry ze = new ZipEntry(resourceName_bpmn)   ;
            //把创建的实体对象放到压缩包中
            zipo.putNextEntry(ze);
            //文件内容拷贝
            while((len = inputStream_bpmn.read(b,0,1024)) != -1){
                zipo.write(b,0,b.length);
            }
            zipo.closeEntry();
            //---------------
            ZipEntry ze1 = new ZipEntry(resourceName_png);
            zipo.putNextEntry(ze1);
            while((len = inputStream_png.read(b,0,1024)) != -1){
                zipo.write(b,0,b.length);
            }
            //关闭流
            inputStream_bpmn.close();
            inputStream_png.close();
            zipo.flush();
            zipo.close();
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/selectProgressDefineimg")
    public String img(String did,String imageName,HttpServletResponse response) {//查看流程图
        InputStream in = repositoryService.getResourceAsStream(did,imageName);
        try {
            OutputStream out = response.getOutputStream();
            // 把图片的输入流程写入resp的输出流中
            byte[] b = new byte[1024];
            for (int len = -1; (len= in.read(b))!=-1; ) {
                out.write(b, 0, len);
            }
            // 关闭流
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @RequestMapping(value = "/toresume")
    public String toaddEmp(){
        return "resume";
    }

    @RequestMapping(value = "/resume")//提交简历
    public String addEmp(Job job){
        job.setJobdate(new Date(new java.util.Date().getTime()));
        job.setState(1);

        service.addJob(job);//保存单据

        //设置流程实例变量集合
        Map<String,Object> variables = new HashMap<>();
        variables.put("user",job.getUser());//用户名称
        variables.put("day",job.getDays());//天数
        variables.put("jobid",job.getJobid());//单据ID

        //动态办理人 根据用户设置第一个办理人
        variables.put("assignee","王五");

        //启动实例（通过流程定义的key来启动一个实例）
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(job.getJobType(),variables);
        System.out.println("流程实例 "+processInstance.getId());

        //根据流程实例ID获取当前实例正在执行的任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).orderByProcessInstanceId().desc().singleResult();
        System.out.println("任务ID： "+task.getId());

        //完成任务(通过任务ID完成该任务)
        taskService.complete(task.getId(),variables);

        return "redirect: toresume";
    }
    @RequestMapping(value = "/allresume")//当前用户所有的简历
    public String allEmp(HttpSession session,Model model){
        String user = (String) session.getAttribute("user");
        System.out.println("user"+user);
        List list = service.all(user);
        model.addAttribute("jobList",list);
        return "allresume";
    }

    @RequestMapping(value = "/taskImg")//查看流程图执行节点（红色框高亮）
    public String logout(String jobid,Model model,String instanceid){
        //System.out.println("进来"+jobid);
        String processInstanceId = "";//流程实例ID
        if(jobid!=null&&!"".equals(jobid)){
            //通过单据id查找实例对象
            HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery().variableValueEquals("jobid", Integer.parseInt(jobid)).singleResult();
            //通过历史流程变量查询变量对象(获取流程实例ID)
            processInstanceId = hvi.getProcessInstanceId();
        }
        //我的任务（查看办理进度）
        if(instanceid!=null&&!"".equals(instanceid)){
            processInstanceId=instanceid;
        }
        //获取历史任务实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();

        if(historicProcessInstance!=null){
            //获取流程定义信息
            ProcessDefinition pd = repositoryService.getProcessDefinition(historicProcessInstance.getProcessDefinitionId());
            // 获取流程定义的实体（包含了流程中的任务节点信息，连线信息）
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)pd;
            // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId).orderByHistoricActivityInstanceId().asc().list();
            // 已经激活的节点ID集合
            //激活的节点（1.任务已经完成；2.任务已经开始，但还未结束）
            List mapList = new ArrayList();
            //获取已经激活的节点ID
            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                //getActivityId方法获取已经激活的节点id
                ActivityImpl activityImpl = processDefinition.findActivity(activityInstance.getActivityId());
                //获取当前节点在图片中的坐标位置，左上角坐标及长宽
                int x = activityImpl.getX();
                int y = activityImpl.getY();
                int height = activityImpl.getHeight();
                int width = activityImpl.getWidth();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("x", x);
                map.put("y", y);
                map.put("height", height);
                map.put("width", width);
                mapList.add(map);
            }
            model.addAttribute("pd",pd);
            model.addAttribute("mapList",mapList);
        }

        return "img";
    }
    @RequestMapping(value = "/logout")//注销
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect: index.jsp";
    }

    @RequestMapping(value = "/mytask")//我的任务
    public String mytask(HttpSession session,Model model){
        String user = (String) session.getAttribute("user");
        //通过办理人查询任务集合
        List<Task> mytask = taskService.createTaskQuery().taskAssignee(user).list();
        model.addAttribute("tasklist",mytask);
        return "mytask";
    }

    @RequestMapping(value = "/taskDetaill")//查看任务详情
    public String taskDetaill(Model model,String taskId,String instanceid){
        //根据流程实例ID查询流程实例
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(instanceid).singleResult();
        //根据任务ID 查询任务实例
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //历史审批信息
        List<Comment> commentList = taskService.getProcessInstanceComments(taskId);
        //获取流程定义id
        String processDefineId = task.getProcessDefinitionId();
        //查询流程定义实体对象
        ProcessDefinitionEntity pdentity = (ProcessDefinitionEntity)processEngine.getRepositoryService().getProcessDefinition(processDefineId);
        //获取当前活动id
        String activeId = pi.getActivityId();
        System.out.println("当前活动ID "+activeId);
        //获取当前活动(usertask2)
        ActivityImpl impl = pdentity.findActivity(activeId);
        //获取当前活动的连线
        List<PvmTransition> pvmlist = impl.getOutgoingTransitions();
        List plist = new ArrayList();
        for(PvmTransition pvm:pvmlist){
            Map map = new HashMap();
            if(pvm.getProperty("name")==null){//如果没有设置连线名称，给一个默认的选项
                map.put("id",0);
                map.put("name","审批");
            }else{
                map.put("id",pvm.getId());
                map.put("name",pvm.getProperty("name"));
            }

            plist.add(map);
        }

        //获取jobid
        int jobid = Integer.parseInt(taskService.getVariable(taskId,"jobid").toString());
        //根据id查询对象
        Job job = service.selejob(jobid);
        model.addAttribute("taskId",taskId);//批注list
        model.addAttribute("commentList",commentList);//
        model.addAttribute("plist",plist);
        model.addAttribute("job",job);
        return "detail";
    }

    @RequestMapping(value = "/ratify")//审批任务
    public String taskDetaill(HttpSession session,String jobid,String taskId,String flow,String comment){
        //根据任务id得到任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //通过任务对象获取流程实例id
        String processInstId = task.getProcessInstanceId();
        //根据单据id查询单据对象
        Job job = service.selejob(Integer.parseInt(jobid));
        //获得用户
        String user = (String) session.getAttribute("user");
        //设置当前任务办理人
        Authentication.setAuthenticatedUserId(user);
        //设置备注信息(任务ID，实例ID，页面上的备注)
        taskService.addComment(taskId,processInstId,comment);
        //添加任务变量
        Map mflow = new HashMap();
        mflow.put("flow",flow);
        //动态办理人，设置以后下一个办理人
        mflow.put("assignee","李四");

        //完成当前任务
        taskService.complete(taskId,mflow);
        //根据流程实例获取实例对象(完成流程的实例依然会存放在数据库中 但是查询出来是null的)
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        if(pi == null){
            if(flow.equals("拒绝")){
                //修改
                job.setState(3);
                service.update(job);
            }else {
                job.setState(2);
                service.update(job);
            }
        }
        return "redirect: mytask";
    }
    //查看批注
    @RequestMapping(value = "/mycomment")
    public String mycomment(int jobid,Model model){
        //通过jobId查询历史变量对象
        HistoricVariableInstance hvi = historyService.createHistoricVariableInstanceQuery().variableValueEquals("jobid", jobid).singleResult();
        System.out.println("实例id"+hvi.getProcessInstanceId());
        System.out.println("批注"+taskService.getProcessInstanceComments(hvi.getProcessInstanceId()).size());
        //获取流程实例id （查询历史批注）
        List<Comment> commentList = taskService.getProcessInstanceComments(hvi.getProcessInstanceId());
        model.addAttribute("commentList",commentList);
        return "mycomment";
    }

}
