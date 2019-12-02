<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>流程展示</title>
</head>
<body>
    <table>
        <tr bgcolor="#2F4F4F">
            <th colspan="6"><font color="white">流程定义列表</font></th>
        </tr>
        <tr bgcolor="#2F4F4F">
            <th><font color="white">流程ID</font></th>
            <th><font color="white">流程名称</font></th>
            <th><font color="white">流程KEY</font></th>
            <th><font color="white">删除流程</font></th>
            <th><font color="white">查看流程图</font></th>
            <th><font color="white">下载流程图</font></th>
        </tr>
        <c:forEach items="${listProcessDefinition}" var="pd">
            <tr bgcolor="white">
                <td>${pd.id }</td>
                <td>${pd.name }</td>
                <td>${pd.key }</td>
                <td><a href="delProgressDefine?id=${pd.deploymentId}" onclick="return confirm('确认删除?')">删除流程</a></td>
                <td><a href="selectProgressDefineimg?did=${pd.deploymentId}&imageName=${pd.diagramResourceName}">查看流程图</a>
                <td><a href="toExport?id=${pd.id}">下载流程图</a>
                </td>
            </tr>

        </c:forEach>
    </table>
</body>
</html>
