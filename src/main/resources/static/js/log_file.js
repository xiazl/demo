var table = $('#fileLogTable').DataTable(
    {
        "bPaginate" : true,
        "bStateSave" : true,
        "bScrollCollapse" : true,
        "bLengthChange" : true,
        "bFilter" : true,  // 搜索栏
        "bSort" : true,    // 是否支持排序功能
        "bInfo" : true,
        "bAutoWidth" : true,
        "bDestroy" : true,
        "bProcessing" : true,
        // "bServerSide" : true,         // 服务器处理分页
        "sAjaxSource" : "/logFile/listDir", // 通过ajax加载数据
        "aoColumns" : [
            {
                "mDataProp" : "name",
                "sClass" : "center"
            },
            {
                "mDataProp" : "size"
            }],
        "oLanguage" : { // 中文替换
            "sProcessing" : "处理中...",
            "sLengthMenu" : "显示 _MENU_ 项结果",
            "sZeroRecords" : "没有匹配结果",
            "sInfo" : "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty" : "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix" : "",
            "sSearch" : "搜索:",
            "sUrl" : "",
            "sEmptyTable" : "表中数据为空",
            "sLoadingRecords" : "载入中...",
            "sInfoThousands" : ",",
            "oPaginate" : {
                "sFirst" : "首页",
                "sPrevious" : "上页",
                "sNext" : "下页",
                "sLast" : "末页"
            },
            "oAria" : {
                "sSortAscending" : ": 以升序排列此列",
                "sSortDescending" : ": 以降序排列此列"
            }
        }
    });

$().ready(function() {
    $("#batchButton").click(function() {
        $.ajax({
            url: '/data/analysisAll',
            type: 'POST',
            dataType: 'json',
            layerIndex:-1,
            beforeSend: function () { //插件加载前
                this.layerIndex = layer.load(0, { shade: [0.01, '#fff'] });
            },
            success: function (result) {
                if (result.code == "1000") {
                    layerMsg('成功',true);
                } else {
                    layerMsg(result.message,false,{time: 3000})
                }
            },
            error: function () {
                layerMsg("网络异常",false,{time: 3000})
            },
            complete: function () {
                layer.close(this.layerIndex); // 关闭loading
            }
        })
    })
})

