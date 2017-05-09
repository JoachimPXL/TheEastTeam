<%--
  Created by IntelliJ IDEA.
  User: 11500555
  Date: 4/05/2017
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="UploadDownloadFileServlet" method="post" enctype="multipart/form-data">
    Select File to Upload:<input type="file" name="fileName">
    <br>
    <input type="submit" value="Upload">
</form>
</body>
</html>
