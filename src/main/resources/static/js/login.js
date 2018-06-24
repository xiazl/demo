$().ready(function() {
    // validate signup form on keyup and submit
    $("#loginForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: "请输入用户名",
            },
            password: {
                required: "请入密码",
            }
        }
    });
})