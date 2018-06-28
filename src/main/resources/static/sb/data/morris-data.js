$(function() {
    $.ajax({
        url: '/log/listByPlatAndDate',
        type: 'POST',
        dataType: 'json',
        success: function (result) {
            if (result.code == "1000") {
                var data = result.data;
                initTotalChart(data.data,data.xkey,data.ykeys,data.labels);
                initSimpleChart(data.data,data.xkey,data.ykeys,data.labels);
            }
        },
        error: function () {
            layerMsg("网络异常",false,{time: 3000})
        }
    })

    function  initTotalChart(data,xkey,ykeys,labels) {
        Morris.Area({
            element: 'total-area-chart',
            data: data,
            xkey: xkey,
            ykeys: ykeys,
            labels: labels,
            pointSize: 2,
            hideHover: 'auto',
            resize: true
        });
    }

    function  initSimpleChart(data,xkey,ykeys,labels) {
        Morris.Line({
            element: 'simple-area-chart',
            data: data,
            xkey: xkey,
            ykeys: ykeys,
            labels: labels,
            // pointSize: 2,
            // hideHover: 'auto',
            // resize: true
        });
    }

});
