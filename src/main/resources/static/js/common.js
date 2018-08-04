function layerMsg(msg, isSuccess, opts) {
    var time;
    try {
        time = ($('<div>' + msg + '</div>').text().length / 4 + 1) * 1000;
        if (!$.isNumeric(time)) {
            time = (msg.length / 4 + 1) * 1000;
        }
    } catch (err) {
        time = (msg.length / 4 + 1) * 1000;
    }

    var options = {time: time, offset: '40%'};
    if (isSuccess) {
        options = $.extend(true, options, {icon: 1, closeBtn: 1}, opts || {});
    } else {
        options = $.extend(true, options, {icon: 0, /*shade: 0.3, shadeClose: true, */closeBtn: 1}, opts || {});
    }
    layer.msg(msg, options);
}

function serverData(data){
    return {"iDisplayStart":data.iDisplayStart,"iDisplayLength":data.iDisplayLength,
        "sSearch":data.sSearch,"keyword":data.keyword};
}

function sortColumnName(i) {
    switch (i){
        case 0:
            return "referer";
        case 1:
            return "targetUrl";
        default:
            break
    }
}