<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/8/24
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>地区树</title>
    <jsp:include page="/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/common/nav-static.jsp"></jsp:include>
<div class="container" >

    <div class="row">
        <div class="panel panel-primary">
            <div class="panel-heading">地区树展示
                <button class="btn btn-info" type="button" onclick="addArea()"><i class="glyphicon glyphicon-plus"></i>新增</button>
                <button class="btn btn-success" type="button" olnclick="updateArea()"><i class="glyphicon glyphicon-pencil"></i>修改</button>
                <button class="btn btn-danger" type="button" onclick="deleteArea()"><i class="glyphicon glyphicon-trash"></i>删除</button>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-12">
                        <!-- zTree 是一个依靠 jQuery 实现的多功能 “树插件”。优异的性能、灵活的配置、多种功能的组合是 zTree 最大优点。
                        zTree 是开源免费的软件（MIT 许可证） -->
                        <ul id="tree" class="ztree" style="width:230px; overflow:auto;"></ul>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/script.jsp"></jsp:include>
<script>

    $(function() {
        findCityZtree();

    })

    //查询
    function findCityZtree(){
        $.post({
            url:"/area/findAreaList.gyh",
            data:"",
            dataType:"json",
            success:function(res){

                var setting = {

                    callback: {
                        //用于捕获节点被点击的事件回调函数
                        onClick: zTreeOnClick,
                        //编辑名称完毕（Input 失去焦点 或 按下 Enter 键），会触发 setting.callback.beforeRename
                        onRename : zTreeRename
                    },
                    //使用简单 Array 格式的数据
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    //需要显示 checkbox
                    check: {
                        enable: true,
                        //checkbox 勾选操作，只影响父级节点；取消勾选操作，只影响子级节点
                        //chkStyle: "checkbox",
                        //chkboxType: { "Y": "p", "N": "s" }
                        chkboxType: { "Y": "s", "N": "s" }
                    },
                    edit:{
                        enable:true,//设置树为编辑模式
                        showRemoveBtn: false,//设置是否显示删除按钮
                        showRenameBtn: true//设置是否显示编辑名称按钮
                    },
                };

                var nodes = res.data;

                var zTreeObj = $.fn.zTree.init($("#tree"), setting, nodes);

                //展开全部节点
                //var treeObj = $.fn.zTree.getZTreeObj("tree");
                //zTreeObj.expandAll(true);
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                var nodes = zTreeObj.getNodes();
                if (nodes.length>0) {
                    for(var i=0;i<nodes.length;i++){
                        zTreeObj.expandNode(nodes[i], true, false, false);
                    }
                }
            },
            error:function(res){
                alert("请求失败");
            }
        })
    };

    //点击事件 单击给父节点的input框赋值
    function zTreeOnClick(event, treeId, treeNode) {
        //alert(treeNode.id + ", " + treeNode.name);
        $("#pId").val(treeNode.id);

        if (treeNode.url.length > 0) {
            //跳转页面
            window.parent.rightFrame.location=treeNode.url;
        }
    };

    //新增节点
    function insertZtree(){
        var pId=$("#pId").val();
        var name=$("#name").val();
        var url=$("#url").val();
        $.ajax({
            url:"<%=request.getContextPath()%>/insertZtree.action",
            type:"post",
            dataType:"json",
            data:{"ztreeInfo.pid":pId,"ztreeInfo.name":name,"ztreeInfo.url":url},
            success:function(res){
                alert(res.success);
                window.location.reload();
            },
            error:function(res){
                alert("请求失败");
            }
        })
    }

    //删除选择项id
    function deleteById(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var ztreeArr = treeObj.transformToArray(treeObj.getCheckedNodes(true));

        var str="";
        for (var i = 0; i < ztreeArr.length; i++) {
            str+=ztreeArr[i].id+",";
        }
        if (str.length <= 0) {
            alert("请选择要删除的节点");
        }else{
            if (confirm("确定要删除已选择的节点以及子节点?")) {
                $.ajax({
                    url:"<%=request.getContextPath()%>/deleteZtreeById.action",
                    type:"post",
                    dataType:"json",
                    data:{"ids":str},
                    success:function(res){
                        alert(res.success);
                        window.location.reload();
                    },
                    error:function(res){
                        alert("请求失败");
                    }
                })
            }
        }
    };

    //失去焦点事件 修改节点名称
    function zTreeRename(event, treeId, treeNode){
        $.ajax({
            url:"<%=request.getContextPath()%>/updateZtreeName.action",
            type:"post",
            dataType:"json",
            data:{"ztreeInfo.id":treeNode.id,"ztreeInfo.name":treeNode.name,"ztreeInfo.pid":treeNode.pId,"ztreeInfo.url":treeNode.url},
            success:function(res){
                alert(res.success);
                window.location.reload();
            },
            error:function(res){
                alert("请求失败");
            }
        })


    }

</script>
</body>
</html>
