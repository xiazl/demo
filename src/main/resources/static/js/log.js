$(document).ready(function() {
    $('#table_id_example').DataTable({
        "bProcessing" : false, //是否显示加载
        "sAjaxSource" : '/log/list', //请求资源路径
        "serverSide": true, //开启服务器处理模式
        /*
        使用ajax，在服务端处理数据
        sSource:即是"sAjaxSource"
        aoData:要传递到服务端的参数
        fnCallback:处理返回数据的回调函数
        */
        "fnServerData": function(sSource, aoData, fnCallback){
            $.ajax( {
                'type' : 'post',
                "url": sSource,
                "dataType": "json",
                "data": { "aodata" : JSON.stringify(aoData) },
                "success": function(resp) {
                    fnCallback(resp);
                }
            });
        },
        "columns" : [
            { data : "id" },
            { data : "dateTime" },
            { data : "ip" },
            { data : "referer" },
            { data : "targetUrl" },
        ]
    });
})