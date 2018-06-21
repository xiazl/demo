var table = $('#logTable')
    .dataTable(
        {
            "bPaginate" : true,//分页工具条显示
            //"sPaginationType" : "full_numbers",//分页工具条样式
            "bStateSave" : true, //是否打开客户端状态记录功能,此功能在ajax刷新纪录的时候不会将个性化设定回复为初始化状态
            "bScrollCollapse" : true, //当显示的数据不足以支撑表格的默认的高度
            "bLengthChange" : true, //每页显示的记录数
            "bFilter" : false, //搜索栏
            "bSort" : true, //是否支持排序功能
            "bInfo" : true, //显示表格信息
            "bAutoWidth" : true, //自适应宽度
            "bJQueryUI" : false,//是否开启主题
            "bDestroy" : true,
            "bProcessing" : true, //开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
            "bServerSide" : true,//服务器处理分页，默认是false，需要服务器处理，必须true
            "sAjaxDataProp" : "aData",//是服务器分页的标志，必须有
            "sAjaxSource" : "${basePath}pushEntity/getTableData",//通过ajax实现分页的url路径。
            "aoColumns" : [//初始化要显示的列
                {
                    "mDataProp" : "id",//获取列数据，跟服务器返回字段一致
                    "sClass" : "center",//显示样式
                    // "mRender" : function(data, type, full) {//返回自定义的样式
                    //     return "<label><input type='checkbox' class='ace' /><span class='lbl'></span></label>"
                    // }
                },
                {
                    "mDataProp" : "appName"
                },
                {
                    "mDataProp" : "apiKey"
                },
                {
                    "mDataProp" : "secretKey"
                },
                {
                    "mDataProp" : "createTime",
                    "mRender" : function(data, type, full) {
                        return new Date(data)//处理时间显示
                            .toLocaleString();
                    }
                }, {
                    "mDataProp" : "createTime",
                    "mRender" : function(data, type, full) {
                        return "<span class='label label-sm label-info arrowed arrowed-righ'>Sold</span>"
                    }
                },
                {
                    "mDataProp" : "createTime",
                    "mRender" : function(data, type, full) {
                        return "<div class='visible-md visible-lg hidden-sm hidden-xs action-buttons'><a class='blue' href='#'><i class='icon-zoom-in bigger-130'></i></a><a class='green' href='#'><i class='icon-pencil bigger-130'></i></a><a class='red' href='#'><i class='icon-trash bigger-130'></i></a></div>"
                    }
                } ],
            "aoColumnDefs" : [ {//用来设置列一些特殊列的属性
                "bSortable" : false,
                "aTargets" : [ 0 ]
                //第一列不排序
            }, {
                "bSortable" : false,
                "aTargets" : [ 5 ]
            }, {
                "bSortable" : false,
                "aTargets" : [ 6 ]
            } ],
            "oLanguage" : {//语言设置
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