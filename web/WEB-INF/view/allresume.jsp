<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/19
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table width="90%" cellspacing="1" bgcolor="#6495ED">
    <tr bgcolor="#2F4F4F">
        <th colspan="2"><font color="white">我的申请单</font></th>
    </tr>
    <tr bgcolor="#2F4F4F">
        <th><font color="white">单据编号</font></th>
        <th><font color="white">单据名称</font></th>
        <th><font color="white">入职天数</font></th>
        <th><font color="white">金额</font></th>
        <th><font color="white">申请人</font></th>
        <th><font color="white">申请时间</font></th>
        <th><font color="white">审批状态</font></th>
        <th><font color="white">申请说明</font></th>
        <th><font color="white">查看批注</font></th>
        <th>办理进度</th>
    </tr>
    <c:forEach items="${jobList}" var="t">
        <tr bgcolor="white">
            <td>${t.jobid }</td>
            <td>${t.jobname }</td>
            <td>${t.days }</td>
            <td>${t.money }</td>
            <td>${t.user }</td>
            <td>${t.jobdate}</td>
            <td>
                <c:if test="${t.state==1}">
                    审批中
                </c:if>
                <c:if test="${t.state==2}">
                    审批通过
                </c:if>
                <c:if test="${t.state==3}">
                    审批失败
                </c:if>
            </td>
            <td>${t.remark }</td>
            <td><a href="<%=request.getContextPath()%>/mycomment?jobid=${t.jobid}" >查看批注</a></td>
            <td><a href="<%=request.getContextPath()%>/taskImg?jobid=${t.jobid}" target="main">办理进度</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
