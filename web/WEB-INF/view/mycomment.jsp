<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/22
  Time: 8:10
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
        <th colspan="2"><font color="white">审批记录</font></th>
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
        <td>${t.time}</td>
        <td>${t.userId }</td>
        <td>${t.fullMessage }</td>
    </tr>
    </c:forEach>
</table>
<a href="<%=request.getContextPath()%>/allresume" target="main">返回我的简历</a>
</body>
</html>
