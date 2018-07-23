$(function() {
    var areaChart = echarts.init(document.getElementById('area-chart'));
    var lineChart = echarts.init(document.getElementById('line-chart'));

    // 根据窗口大小自适应图表
    window.addEventListener("resize", function () {
        areaChart.resize();
        lineChart.resize();
    });

    // 指定图表的配置项和数据
    function initLine(keys,data) {
        var size = keys.length-1;
        var series = [];
        for(var i = 0; i < size; i++){
            series.push({type: 'line'})
        }
        lineChart.setOption({
            title: {
                text: '折线分布图'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: legendShow(keys),
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            dataset: {
                dimensions: keys,
                source: data
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series: series
        });
    }

    // 指定图表的配置项和数据
    function initArea(keys,data) {
        var size = keys.length-1;
        var series = [];
        for(var i = 0; i < size; i++){
            series.push({type: 'line',stack: '总量', areaStyle: {normal: {}}})
        }
        areaChart.setOption({
            title: {
                text: '堆叠区域图'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: legendShow(keys),
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            dataset: {
                dimensions: keys,
                source: data

            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series: series
        });
    }


// 使用刚指定的配置项和数据显示图表。

    var defaultIndex = '/log/listByPlatAndDate';
    $("ul[name='dropdown-menu1'] li").click(function(i,v){
        var index =$(this).index();
        if(index == 0) {
            doAjax(0, defaultIndex);
        } else {
            doAjax(0, '/log/listByPlatAndMonth');
        }
    })

    $("ul[name='dropdown-menu2'] li").click(function(){
        var index =$(this).index();
        if(index == 0) {
            doAjax(1, defaultIndex);
        } else {
            doAjax(1, '/log/listByPlatAndMonth');
        }
    })

    // 此一次初始化
    doAjax(-1,defaultIndex);

    // 加载表数据
    function doAjax(index,url) {
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            success: function (result) {
                if (result.code == "1000") {
                    var data = result.data;
                    if (index == -1) {
                        initChart(data.keys, data.data);
                    } else if(index == 0){
                        areaSet(data.keys, data.data);
                    }else if(index == 1){
                        lineSet(data.keys, data.data);
                    }
                }
            },
            error: function () {
                layerMsg("网络异常", false, {time: 3000})
            }
        })
    }

    function initChart(keys,data){
        initLine(keys,data);
        initArea(keys,data);
    }

    function lineSet(keys,data) {
        lineChart.setOption({
            dataset: {
                dimensions: keys,
                source: data
            }
        })
    }

    function areaSet(keys,data) {
        areaChart.setOption({
            dataset: {
                dimensions: keys,
                source: data
            }
        })
    }

    function legendShow(keys) {
        var legend = null;
        if(keys.length <= 15){
            legend = {};
        }
        return legend;
    }
});
