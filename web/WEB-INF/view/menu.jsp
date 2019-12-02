<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/18
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单页</title>
</head>
<body>
    <p>当前登录: ${sessionScope.user}</p>
    <ul>
        <li><a href="${pageContext.request.contextPath}/toupload" target="main">发布流程</a></li>
        <li><a href="<%=request.getContextPath()%>/list" target="main">查看流程</a></li>
        <li><a href="<%=request.getContextPath()%>/toresume" target="main">填写简历</a></li>
        <li><a href="<%=request.getContextPath()%>/allresume" target="main">我的简历</a></li>
        <li><a href="<%=request.getContextPath()%>/mytask" target="main">我的任务</a></li>
        <li><a href="<%=request.getContextPath()%>/logout" target="_top">退出登录</a></li>
    </ul>
</body>
</html>
