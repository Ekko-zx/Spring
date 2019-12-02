<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/21
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>任务详情</title>
</head>
<body>
<h2 align="center">审核入职申请</h2>
<hr/>
<form action="<%=request.getContextPath()%>/ratify" theme="simple" method="post">
    <input type="hidden" name="jobid" value="${job.jobid}"/>
    <input type="hidden" name="taskId" value="${taskId}"/>
    <table width="90%" cellspacing="1" bgcolor="#6495ED">
        <tr>
            <td align="right">入职人</td>
            <td>${job.user}</td>
        </tr>
        <tr bgcolor="white">
            <td align="right">入职天数：</td>
            <td >${job.days}</td>
        </tr>
        <tr bgcolor="white">
            <td align="right">填单时间：</td>
            <td >${job.jobdate} </td>
        </tr>
        <tr bgcolor="white">
            <td align="right">入职职位：</td>
            <td >${job.remark}</td>
        </tr>
        <tr bgcolor="white">
            <td align="right">是否批准：</td>
            <td >
                <select name="flow">
                    <%--<c:forEach items="${plist}" var="p">
                        <option value="${p.name}">${p.name}</option>
                    </c:forEach>--%>
                        <option value="审批">审批</option>
                        <option value="拒绝">拒绝</option>
                </select>
            </td>
        </tr>
        <tr bgcolor="white">
            <td align="right">备注：</td>
            <td ><textarea name="comment"></textarea></td>
        </tr>
        <tr bgcolor="white">
            <td colspan="2" align="center">
                <input type="submit" value="审批">
            </td>
        </tr>
    </table>
</form>

<hr/>
<table width="90%" cellspacing="1" bgcolor="#6495ED">
    <tr bgcolor="#2F4F4F">
        <th colspan="2"><font color="white">历史审批信息</font></th>
    </tr>
    <tr bgcolor="#2F4F4F">
        <th><font color="white">ID</font></th>
        <th><font color="white">审批时间</font></th>
        <th><font color="white">审批人</font></th>
        <th><font color="white">批注内容</font></th>
    </tr>
    <c:forEach items="${commentList}" var="t">
        <tr bgcolor="white">
            <td>${t.id }</td>
            <td>${t.time} </td>
            <td>${t.userId }</td>
            <td>${t.fullMessage }</td>
        </tr>
    </c:forEach>
</table>
</body>
</body>
</html>
