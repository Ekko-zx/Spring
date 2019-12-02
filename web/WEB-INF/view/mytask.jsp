<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/21
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的任务</title>
</head>
<body>
<table>
    <tr bgcolor="#2F4F4F">
        <th colspan="6"><font color="white">我的任务列表</font></th>
    </tr>
    <tr bgcolor="#2F4F4F">
        <th><font color="white">任务ID</font></th>
        <th><font color="white">实例ID</font></th>
        <th><font color="white">流程ID</font></th>
        <th><font color="white">任务名称</font></th>
        <th><font color="white">任务执行人</font></th>
        <th><font color="white">任务时间</font></th>
        <th>查看详情</th>
        <th>办理进度</th>
    </tr>
    <c:forEach items="${tasklist}" var="t">
        <tr bgcolor="white">
            <td>${t.id}</td>
            <td>${t.processInstanceId}</td>
            <td>${t.processDefinitionId}</td>
            <td>${t.name}</td>
            <td>${t.assignee}</td>
            <td>${t.createTime}</td>
            <td><a href="<%=request.getContextPath()%>/taskDetaill?taskId=${t.id}&instanceid=${t.processInstanceId}">查看详情</a></td>
            <td><a href="<%=request.getContextPath()%>/taskImg?&instanceid=${t.processInstanceId}">办理进度</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
