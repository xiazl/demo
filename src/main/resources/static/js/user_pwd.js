
$().ready(function () {
    /**
     * 修改密码
     */

    $('#updatePwdForm').validate({
        rules: {
            oldPassword: {
                required: true,
                minlength: 5,
                maxlength: 15
            },
            password: {
                required: true,
                minlength: 5,
                maxlength: 15
            },
            confirmPassword: {
                required: true,
                minlength: 5,
                maxlength: 15,
                equalTo: "#password"
            }
        },
        messages: {
            oldPassword: {
                required: "请入旧密码",
                minlength: "密码不能少于5个字符",
                maxlength: "密码不能多余15个字符"
            },
            password: {
                required: "请入新密码",
                minlength: "密码不能少于5个字符",
                maxlength: "密码不能多余15个字符"
            },
            confirmPassword: {
                required: "请输入密码确认",
                minlength: "密码不能少于5个字符",
                maxlength: "密码不能多余15个字符",
                equalTo: "两次密码输入不一致"
            }
        }

    });

    $("#pwdSave").click(function() {
        if (!$("#updatePwdForm").valid()) {
            return;
        }
        var oldPassword = $("#oldPassword").val();
        var password = $("#password").val();
        var confirmPassword = $("confirmPassword").val();

        $.ajax({
            url: '/user/updatePwd',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({oldPassword:oldPassword,password:password}),
            success: function (result) {
                if (result.code == "1000") {
                    layerMsg('修改成功',true);
                    window.location.href = '/user/index';
                } else {
                    layerMsg(result.message,false,{time: 3000})
                }
            },
            error: function () {
                layerMsg("网络异常",false,{time: 3000})
            }
        })
    })

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
})