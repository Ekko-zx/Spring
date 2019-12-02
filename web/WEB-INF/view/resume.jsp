<%--
  Created by IntelliJ IDEA.
  User: 82346
  Date: 2019/11/19
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/resume" theme="simple" method="post">
    <input type="hidden" name="jobType" value="entry"/>
    <input type="hidden" name="jobname" value="简历"/>
    <input type="hidden" name="user" value="${user}"/>
    <input type="hidden" name="money" value="0"/>
    <table width="90%" cellspacing="1" bgcolor="#6495ED">
        <tr bgcolor="white" align="center">
            <td colspan="2">入职申请</td>
        </tr>
        <tr bgcolor="white">
            <td align="right">入职说明：</td>
            <td ><input type="text" name="remark"/></td>
        </tr>
        <tr bgcolor="white">
            <td align="right">入职天数：</td>
            <td ><input type="text" name="days"/></td>
        </tr>
        <tr bgcolor="white">
            <td colspan="2" align="center">
                <input type="submit" value="保存">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
