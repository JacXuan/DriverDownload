<%--
  Created by IntelliJ IDEA.
  User: zxc85
  Date: 2018/1/31
  Time: 9:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>下载中心</title>
</head>
<body>
<form method="POST" action="http://localhost:8080/downloadController/downloadFile">
    文件下载地址：<input type="text" id="srcFile" name="srcFile" value=""/><br/>
    文件保存地址：<input type="text" id="saveFile" name="saveFile" value=""/><br/>
    文件移动地址：<input type="text" id="rmFile" name="rmFile" value=""/><br/>
    <input type="submit" value="提交"/>
</form>

<form method="POST" action="http://localhost:8080/downloadController/listFiles">
    文件下载地址：<input type="text" id="srcFile" name="srcFile" value=""/><br/>
    <input type="submit" value="提交"/>
</form>
</body>

</html>
