<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/28
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    .bar {
        margin-left: 20px;
        text-align: center;
        font-size: 40px
    }
    thead{
        background: cadetblue;
    }
</style>

<div id="myDivId" style="color: blueviolet;">
    HELLO WORLD

    <div class="myDivClass bar"> click</div>
    <div class="myDivClass"> click 2</div>
    <div class="myDivClass2" style="background: beige"> click 3</div>

    <a href="/main.do">点击</a>

    <div style="border: red 1px solid; padding: 2px">
        <table style="border: green 2px solid; padding: 1px">
            <thead>
            <tr>
                <td>测试</td>
                <td>测试2</td>
                <td>测试3</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>测试结果</td>
                <td>测试结果2</td>
                <td>测试结果3</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.min.js"></script>

<script>
    $(function () {
        console.log("this is a hello world !!!!!");

        function alertSomeThing(info) {
            alert(info);
        }

        function consoleSomeThing(v) {
            console.log($(v).text());

            var json = {
                id: 1,
                name: "fff",
                age: 12,
                page: 10,
                size: 50
            };

            $.ajax({
                url: "http://localhost:18090/api/products",
                type: "GET",
                error: function () {
                    alert("报错1");
                },
                success: function (result) {
                    console.log(result.code);
                    console.log(result.success);
                    console.log(result);
                },
                complete: function () {
                    console.log("完成1");
                }

            });
            console.log("AA");

            $.ajax({
                url: "http://localhost:18090/api/products",
                type: "POST",
                data: JSON.stringify(json),
                dataType: "json",
                contentType: "application/json",
                error: function () {
                    alert("报错2");
                },
                success: function (result) {
                    console.log(result.code);
                    console.log(result.success);
                    console.log(result);
                },
                complete: function () {
                    console.log("完成2");
                }

            });
            console.log("BB");

            $.get(
                "http://localhost:18090/api/products/1234/reviews",
                json,
                function (result) {
                    console.log(result);
                });
            console.log("CC");
        }

        $(".myDivClass").click(function () {
            alertSomeThing($(this).text());
        });

        $(".myDivClass2").click(function () {
            consoleSomeThing(this);
        })

    })
</script>
</body>
</html>
