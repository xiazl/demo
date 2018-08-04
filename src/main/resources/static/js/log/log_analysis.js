$().ready(function () {

    $("#analysis").validate({
        rules: {
            fileName: {
                required: true
            }
        },
        messages: {
            fileName: {
                required: "文件名不能为空"
            }
        }
    });

    $("#addFile").click(function() {
        if(!$("#analysis").valid()){
            return;
        }
        var fileName = $('#fileName').val();
        $.ajax({
            url: '/data/analysis',
            type: 'POST',
            dataType: 'json',
            data: {fileName:fileName},
            layerIndex:-1,
            beforeSend: function () { //插件加载前
                this.layerIndex = layer.load(0, { shade: [0.01, '#fff'] });
            },
            success: function (result) {
                if (result.code == "1000") {
                    layerMsg('成功',true);
                    $("#analysis")[0].reset();
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