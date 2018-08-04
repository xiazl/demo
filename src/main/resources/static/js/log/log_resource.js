$().ready(function () {

    var table = $('#resourceLogTable').DataTable(
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
            "bServerSide": true,         // 服务器处理分页
            "sAjaxSource": "/log/listResource", // 通过ajax加载数据
            "aoColumns": [
                {
                    "mData": "name"
                },
                {
                    "mData": "count"
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
            },
            "fnCreatedRow": function (nRow, aData, iDataIndex) {
                $('td:eq(1)', nRow).html("<a class='row-details row-details-close' data-id='" + aData.name + "'>&nbsp;&nbsp;&nbsp;&nbsp;</a>&nbsp;" + aData.count);
            }
        });

    var oTable = $('#resourceLogTable').dataTable();

    $('#resourceLogTable').on('click', ' tbody td .row-details', function () {
        var val = $(this).attr("data-id");
        var obj = $(this).parents('tr')[0];

        if (oTable.fnIsOpen(obj)) {
            $(this).addClass("row-details-close").removeClass("row-details-open");
            oTable.fnClose(obj);
        } else {
            $(this).addClass("row-details-open").removeClass("row-details-close");
            fnFormatDetails(obj,val);
        }
    });

    function fnFormatDetails(obj,val) {
        $.ajax({
            url: '/log/listResourceDetail',
            type: 'POST',
            dataType: 'json',
            data: { keyword : val},
            async: true,
            success: function (result) {
                var data = result.aaData;
                var append = '<table style="width:100%;" class="table table-striped">';
                for (var i = 0; i < data.length; i++) {
                    append += '<tr>';
                    append += '<td width="10%"></td><td width="55%">' + data[i].name + '</td>';
                    append += '<td width="35%">' + data[i].count + '</td>';
                    append += '</tr>'

                }
                append += '</table>';
                oTable.fnOpen(obj, append, 'details');

            },
            error: function () {//请求出错处理
                oTable.fnOpen(obj, '加载数据超时~', 'details');
            }
        });

    }

})