<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
    <script type="text/javascript" src="/js/commons/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/js/bootstrap-3.3.7-dist/css/bootstrap.min.css">


    <style type="text/css">
        body{
            background: url("/js/img/3.jpg");
            animation-name:myfirst;
            animation-duration:12s;
            /*变换时间*/
            animation-delay:3s;
            /*动画开始时间*/
            animation-iteration-count:infinite;
            /*下一周期循环播放*/
            animation-play-state:running;
            /*动画开始运行*/
        }
        @keyframes myfirst
        {
            0%   {background:url("/js/img/3.jpg");}
            16%  {background:url("/js/img/1.jpg");}
            36%  {background:url("/js/img/2.jpg");}
            58% {background:url("/js/img/4.jpg");}
            84% {background:url("/js/img/5.jpg");}
            100% {background:url("/js/img/6.jpg");}
        }
        .form{background: rgba(255,255,255,0.2);width:400px;margin:120px auto;}
        /*阴影*/
        .fa{display: inline-block;top: 27px;left: 6px;position: relative;color: #ccc;}
        input[type="text"],input[type="password"]{padding-left:26px;}
        .checkbox{padding-left:21px;}
    </style>



    <script type="text/javascript">
        //登陆方法
        function login(){
            var userName = $("#userName").val();
            var password = $("#password").val();
            var param =  {"userName":userName,"password":password};
            $.ajax({
                url:"/user/login.gyh",
                type:"post",
                dataType:"json",
                data:param,
                success:function(result){
                    if(result.code == 200){
                        location.href="/admin/toIndex.gyh";
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="container">
    <div class="form row">
        <div class="form-horizontal col-md-offset-3" id="login_form">
            <h3 class="form-title">商品后台登陆</h3>
            <div class="col-md-9">
                <div class="form-group">
                    <i class="fa fa-user fa-lg"></i>
                    <input class="form-control required" type="text" placeholder="用户名" id="userName" name="userName" autofocus="autofocus" maxlength="20"/>
                </div>
                <div class="form-group">
                    <i class="fa fa-lock fa-lg"></i>
                    <input class="form-control required" type="password" placeholder="密码" id="password" name="password" maxlength="8"/>
                </div>
                <div class="form-group">
                    <label class="checkbox">
                        <input type="checkbox" name="remember" value="1"/>记住我
                    </label>
                </div>
                <div class="form-group col-md-offset-9">
                    <button type="button" class="btn btn-success pull-right" onclick="login()">登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>