<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Spring Demo Example</title>
</head>
<body>
<h1>Welcome!</h1>
<h1>hello worlds</h1>
<form action="/upload/img" method="post" enctype="multipart/form-data">
    <p><input type="file" name="file"></p>
    <p><input type="submit" value="submit"></p>
</form>
</body>
</html>