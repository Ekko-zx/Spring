<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/18
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>发布流程</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/uploadProcess" method="post" enctype="multipart/form-data">
        <input type="file" name="zipfile">&nbsp;&nbsp;&nbsp;
        <input type="submit" value="上传"/>
    </form>
</body>
</html>
