
$().ready(function () {
    $("#userAddForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 5,
                maxlength: 15
            }
        },
        messages: {
            username: {
                required: "用户名不能唯空",
                minlength: "用户名不能少于5个字符",
                maxlength: "用户名不能多余15个字符"
            }
        }
    });

    var table = $('#userTable').DataTable(
        {
            "bPaginate": true,
            "bStateSave": true,
            "bScrollCollapse": true,
            "bLengthChange": true,
            "bFilter": true,  // 搜索栏
            "bSort": true,    // 是否支持排序功能
            "bInfo": true,
            "bAutoWidth": true,
            "bDestroy": true,
            "bProcessing": true,
            "bServerSide": true,        // 服务器处理分页
            "sAjaxSource": "/user/list", // 通过ajax加载数据
            "aoColumns": [
                {
                    "mDataProp": "id",
                    "sClass": "center"
                },
                {
                    "mDataProp": "username"
                },
                {
                    "mDataProp": "cellphone"
                },
                {
                    "mDataProp": "createTime"
                },
                {
                    "mDataProp": null,
                    "sClass" : "center",
                    "mRender": editButton

                }
            ],
            "oLanguage": { // 中文替换
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            }
        });

    /**
     * 是否显示编辑按钮
     * @returns {*}
     */
    function editButton(){
        if($('#myModalBtn').length > 0){
            return "<span class='center'><a class='fa fa-edit' title='编辑'>&nbsp;&nbsp;</a>" +
                "<a class='glyphicon glyphicon-trash' title='删除'></a></span>";
        } else {
            return "<span class='glyphicon glyphicon-ban-circle'></span>";
        }
    }

    // 操作列点击事件
    $('#userTable tbody').on('click', 'a.glyphicon', function (args) {
        var dataObj = table.row($(this).parents('tr')).data();
        var data  = {id: dataObj.id};
        var url = '/user/delete';
        layer.confirm('确认删除？', {icon: 3, title: '提示', offset: '30%'}, function (index) {
            submitWithAjax(url,data);
            layer.close(index);
        });
        // $("#confirmModal").modal('show');
    });

    function submitWithAjax(url,data){
        $.ajax({
            url: url,
            type: 'POST',
            // dataType: 'json',
            data: data,
        }).done(function (result) {
            if (result.code == "1000") {
                layerMsg("删除成功",true)
                table.ajax.reload();
            } else {
                layerMsg(result.message,false,{time: 3000})
            }
        }).fail(function () {
                layerMsg("网络异常",false,{time: 3000})
            });
    }

    $("#myModalBtn").click(function() {
        $('#myModalLabel').text('添加用户');
        $("#userAddForm")[0].reset();
        $('#username').attr('disabled',false);
        $('.modal-body .form-group').eq(1).removeClass('hidden')
        $('.modal-body .form-group').eq(3).removeClass('hidden')
    })

    $("#addSave").click(function() {
        if(!$("#userAddForm").valid()){
            return
        }
        var username = $('#username').val();
        var cellphone = $('#cellphone').val();
        var role = $('input[name="role"]:checked').val();
        var id = $('#id').val();
        $.ajax({
            url: '/user/add',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({id:id,username:username,role: role, cellphone:cellphone}),
            success: function (result) {
                if (result.code == "1000") {
                    layerMsg('保存成功',true);
                    $("#myModal").modal('hide');
                    table.ajax.reload();
                } else {
                    layerMsg(result.message,false,{time: 3000})
                }
            },
            error: function () {
                layerMsg("网络异常",false,{time: 3000})
            }
        })
    })

    $('#userTable tbody').on('click', 'a.fa-edit', function (args) {
        var dataObj = table.row($(this).parents('tr')).data();
        $("#myModal").modal('show');
        $('.modal-body .form-group').eq(1).addClass('hidden')
        $('.modal-body .form-group').eq(3).addClass('hidden')
        $('#myModalLabel').text('修改用户');
        $('#username').attr('disabled',true);
        $('#username').val(dataObj.username);
        $('#cellphone').val(dataObj.cellphone);
        $('#id').val(dataObj.id);
    });

})