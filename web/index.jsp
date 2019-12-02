<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/18
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>用户登录</title>
  </head>
  <body>
  <h1>用户的登录</h1>
  <form action="${pageContext.request.contextPath}/tomain" method="post">
      <select name="user">
          <option value="张三">张三</option>
          <option value="李四">李四</option>
          <option value="王五">王五</option>
          <input type="submit" value="登录"/>
      </select>

  </form>
  </body>
</html>
