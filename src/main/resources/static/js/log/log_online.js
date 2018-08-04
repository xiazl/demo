$().ready(function () {
    var condition,cacheCondition=JSON.stringify($('#search-form').serialize());

    var time = $("input[name='timeInit']").val();

    $.fn.datetimepicker.defaults = {
        //默认选择格式
        language : 'zh-CN',
        format: "yyyy-mm-dd hh:ii:ss",
        autoclose: true,
        todayBtn: true,
        minView: 0,
        maxView: "decade",
        startDate:new Date(time),
        endDate : new Date(),
        //选择板所在输入框位置
        pickerPosition: "bottom-left"
    };
    var picker1 = $('#startTime').datetimepicker();
    var picker2 = $("#endTime").datetimepicker();

    picker1.on('changeDate', function (e) {
        picker2.datetimepicker('setStartDate', e.date);
    });

    picker2.on('changeDate', function (e) {
        picker1.datetimepicker('setEndDate', e.date);
    });

    var table = $('#logTable').DataTable(
        {
            "bPaginate": true,
            "bStateSave": true,
            "bScrollCollapse": true,
            "bLengthChange": true,
            "bFilter": false,  // 搜索栏
            "bSort": false,    // 是否支持排序功能
            "bInfo": true,
            "bAutoWidth": true,
            "bDestroy": true,
            "bProcessing": true,
            "bServerSide": true,         // 服务器处理分页
            "sAjaxSource": "/logFile/listOnline", // 通过ajax加载数据
            "fnServerParams": function (aoData) {
                aoData.push(
                    { "name": "startTime", "value": $("#startTime").val()},
                    { "name": "endTime", "value": $("#endTime").val()},
                    { "name": "domain", "value": $("#domain").val()}
                );
            },
            "fnServerData" : function(sSource, aDataSet, fnCallback) {
                $.ajax({
                    "dataType" : 'json',
                    "type" : "post",
                    "url" : sSource,
                    "data" : aDataSet,
                    "success" : function(resp){
                        fnCallback(resp);
                    }
                });
            },
            "aoColumns": [
                {
                    "mDataProp": null,
                    "sClass": "center",
                    "mRender": function(data, type, row){
                        return "<input type ='checkbox' name='select' class='checkbox_select' value=''>"
                    }
                },
                {
                    "mDataProp": "startTime",
                    "sClass": "center"
                },
                {
                    "mDataProp": "endTime",
                    "sClass": "center"
                },
                {
                    "mDataProp": "logName",
                    "sClass": "center"
                },
                {
                    "mDataProp": "logSize",
                    "sClass": "center"
                }, {
                    "mDataProp": "logPath",
                    "sClass": "center",
                    "bVisible": false
                }, {
                    "mDataProp": "status",
                    "sClass": "center",
                    "bVisible": false
                }, {
                    "sClass": "center",
                    "mRender": function (data, type, row) {
                        if (row.status == 1) {
                            return "<span>已下载</span>";
                        } else {
                            return "<a class='' title='下载到服务器'>下载到服务器</a>";
                        }
                    }
                }],
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

    $("#query").bind("click", function () {
        table.draw(true);

        // 缓存查询条件
        cacheCondition = JSON.stringify($('#search-form').serialize());
    });


    $('#select-all').on('click', function () {
        if (this.checked) {
            $('.checkbox_select').each(function () {
                this.checked = true;
            });
        } else {
            $('.checkbox_select').each(function () {
                this.checked = false;
            });
        }
    });

    /**
     * 批量下载
     */
    $('#batchDownload').on('click', function () {
        var data = [];
        $('.checkbox_select:checked').each(function () {
            var dataObj = table.row($(this).parents('tr')).data();
            data.push({logPath:dataObj.logPath,logName:dataObj.logName,logSize:dataObj.logSize});
        });
        if(data.length == 0){
            layerMsg("请至少选中一个文件下载",false,{time: 3000})
            return;
        }
        $.ajax({
            url: '/logFile/downloadBatch',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data),
            layerIndex:-1,
            beforeSend: function () { // 插件加载前
                this.layerIndex = layer.load(0, { shade: [0.01, '#fff'] });
            },
            success: function (result) {
                if (result.code == "1000") {
                    layerMsg('下载进行中，请稍后手动刷新查看结果',true);
                    table.draw(false);

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

    /**
     * 全量下载
     */
    $('#allDownload').on('click', function () {
        condition = JSON.stringify($('#search-form').serialize());
        if(condition != cacheCondition){
            layerMsg('查询条件已改变，请先点击查询', false);
            return;
        }

        var startTime = $('#startTime').val();
        var endTime = $('#endTime').val();
        var domain = $('#domain').val()
        var data = {startTime:startTime,endTime:endTime,domain:domain};
        layer.confirm('将根据查询条件下载所有未下载过的日志文件', {icon: 3, title: '提示', offset: '30%'}, function (index) {
            allDownload(data);
            layer.close(index);
        });
    })

    function allDownload(data){
        $.ajax({
            url: '/logFile/downloadAll',
            type: 'POST',
            dataType: 'json',
            data: data,
            layerIndex:-1,
            beforeSend: function () { // 插件加载前
                this.layerIndex = layer.load(0, { shade: [0.01, '#fff'] });
            },
            success: function (result) {
                if (result.code == "1000") {
                    layerMsg('下载进行中，请稍后手动刷新查看结果',true);
                    table.draw(false);

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
    }

    $('#logTable tbody').on('click', 'a', function (args) {
        var dataObj = table.row($(this).parents('tr')).data();
        var logPath = dataObj.logPath;
        var logName = dataObj.logName;
        var logSize = dataObj.logSize;
        $.ajax({
            url: '/logFile/download',
            type: 'POST',
            dataType: 'json',
            data: {logPath:logPath,logName:logName,logSize:logSize},
            success: function (result) {
                if (result.code == "1000") {
                    layerMsg('下载进行中，请稍后手动刷新查看结果',true);
                    table.draw(false);

                } else {
                    layerMsg(result.message,false,{time: 3000})
                }
            },
            error: function () {
                layerMsg("网络异常",false,{time: 3000})
            }
        })
    });

});

