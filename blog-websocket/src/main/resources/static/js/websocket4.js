var stompClient = null;
var sid = "";
var telNo ="";
var name = "";

/*监听手机号输入事件*/
function telNoOnBlur() {
    var telNumber = $("#telNo").val();
    if (telNumber.length != 11 || !isPoneAvailable(telNumber)) {
        return;
    }
    telNo = telNumber;
    // 通过手机号 获取信息，看有没有登陆信息[返回sid, 是否有登陆]
    $.ajax({
        url: "/api/chat/getInfoByTelNo/" + telNumber,
        type: "GET",
        data: {},
        success: function (res) {/* {flag:true,data:{isLogin:true,sid:'aa',name:'aa'}} */
            console.log("消息发送结果:" + res);
            if (res.flag) {
                sid = res.data.sid;
                if (res.data.isLogin) {/*登陆*/
                    name = res.data.name;
                    /*对话框，是否要直接进行连接*/
                    var flags = confirm("当前手机号已经登陆，是否直接登陆?");
                    if (flags) {
                        login(true);/*进行登陆*/
                    }
                } else {
                    console.log("当前手机号未登陆。。。");
                }
            } else {console.log("获取手机号信息失败。。。");}
        },
        error: function (e) {
            console.log("获取手机号信息异常。。。");
        }
    });
};

/*页面加载/刷新，重置连接*/
function resetConnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    consoleConnected(false);

    /*重新连接*/
    connect();

    /* 加载cookie 数据中的sid,telNo，name */
    sid = getCookie("sid");
    if (sid == null || sid == "") {
        return;
    }
    $.ajax({
        url: "/api/chat/getInfoBySid/" + sid,
        type: "GET",
        data: {},
        success: function (res) {/* {flag:true,} */
            console.log("消息发送结果:" + res);
            if (res.flag
                && res.data != null
                && res.data.telNo != null
                && res.data.telNo != ''){
                /*对话框，是否要直接进行连接*/
                var flags = confirm("当前浏览器存在连接，是否直接进行连接?");
                if (flags) {
                    name = res.data.name;
                    telNo = res.data.telNo;
                    $("#name").val(telNo);
                    $("#telNo").val(telNo);
                    /*进行登陆*/
                    login(true);
                }
            } else {
                console.log("获取sid对应的人信息无果。。。");
            }
        },
        error: function (e) {
            console.log("获取sid对应的人信息异常。。。");
        }
    });
}

function consoleConnected(connected) {
    if (connected) {
        console.log("连接成功。。。");
    } else {
        console.log("连接关闭。。。");
    }
}

function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        consoleConnected(true);
        console.log('订阅[/topic/notice]');
        stompClient.subscribe('/topic/notice', function(respnose){
            showResponse(JSON.parse(respnose.body));
        });
    });
}

function loginChat() {
    $('#createFileMModal').modal('show');
}

function setView(isConnect) {
    if (isConnect) {
        document.getElementById('loginBut').style.visibility = 'hidden';// 进入的按钮隐藏
        document.getElementById('ChatTab').style.display = 'block';// 聊天tab页
        document.getElementById('publicChatTable').style.display = 'block';// 公聊屏
        document.getElementById('sendMessageDiv').style.display = 'block';// 发消息块
        document.getElementById('wecomeInfo').innerText = "欢迎您：" + name;
    } else {
        document.getElementById('loginBut').style.visibility = 'disable';
        document.getElementById('ChatTab').style.display = 'none';// 聊天tab页
        document.getElementById('publicChatTable').style.display = 'none';// 公聊屏
        document.getElementById('sendMessageDiv').style.display = 'none';// 发消息块
        document.getElementById('wecomeInfo').innerText = "";
    }
}

/*进行登陆*/
function login(autoLogin){
    var telNumber = $("#telNo").val();
    if (!isPoneAvailable(telNumber)) {
        alert("请输入正确的手机号");
        return;
    }
    name = $("#name").val();
    console.log("name : " + name);
    console.log("sid : " + sid);
    console.log("telNo : " + telNo);

    /*进行连接*/
    try {
        stompClient.send(
            "/app/change-notice",
            {},
            JSON.stringify({'sid':sid,"telNo": telNo, 'name': name })
        );
    } catch(err) {
        console.log("订阅异常："+err);
    }

    setCookie("sid",sid);
    setCookie("telNo",telNo);
    setCookie("name",name);
    setView(true);
    $('#createFileMModal').modal('hide');

};

function showResponse(data){
    if (data.sendSid == sid) {
        $("#publicChatTable tbody").append('<tr><td class="col-md-1"></td><td class="col-md-8">' + data.message + '</td><td class="col-md-1">' + name + '</td></tr>');
    } else {
        $("#publicChatTable tbody").append('<tr><td class="col-md-1">'+data.sendUserName+'</td><td class="col-md-8">'+data.message+'</td><td class="col-md-1"></td></tr>');
    }
}


function sendMessage(){
    if (sid == null || sid == '') {
        alert("请先进入。");
        return;
    }
    debugger
    var messageInput = $("#messageInput").val();
    if (messageInput == "") {
        alert("发送消息不能为空哦");
    }
    $.ajax({
        url: "/api/chat/sendMessage",
        type: "post",
        data: {
            "sid": sid,
            "name": name,
            "message": messageInput
        },
        success: function (res) {
            console.log("消息发送结果:" + res);
            if (res.flag) {
                $("#messageInput").val("");
                console.log("消息发送成功。");
            } else {
                alert('消息发送失败');
            }
        },
        error: function (e) {
            alert('消息发送异常');
        }
    })
}
function isPoneAvailable(telNumber) {
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(telNumber)) {
        return false;
    } else {
        return true;
    }
}
/*get cookie*/
function getCookie(name) {
    var arr,reg = new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}
/*set cookie*/
function setCookie(name,value) {
    var Days = 30;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}