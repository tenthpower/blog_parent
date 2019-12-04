var stompClient = null;
var sid ="";
var name = "";

function guid() {
    return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function connect() {
    var socket = new SockJS('/socket'); //1
    stompClient = Stomp.over(socket);//2
    stompClient.connect({}, function(frame) {//3
        setConnected(true);
        console.log('开始进行连接Connected: ' + frame);
        stompClient.subscribe('/topic/notice', function(respnose){ //4
            showResponse(JSON.parse(respnose.body));
        });
    });
}

function setConnected(connected) {
    if (connected) {
        console.log("连接成功。。。");
        $('#createFileMModal').modal('show');
    }
}

// 关闭弹框， 获取输入值，然后执行逻辑
function loginSend(){
    $("#createFileMModal").modal("hide");
    name = $("#fileName").val();
    sid = guid();
    console.log("input file name : " + name);
    console.log("sid : " + sid);

    stompClient.send("/app/change-notice", {}, JSON.stringify({'sid':sid, 'name': name }));//

    document.getElementById('denglu_a').style.visibility = 'hidden';

    document.getElementById('wecomeInfo').innerText = "欢迎您：" + name;


};

function showResponse(data){
    if (data.sendSid == sid) {
        $("#chatTable tbody").append('<tr><td class="col-md-1"></td><td class="col-md-8">' + data.message + '</td><td class="col-md-1">' + name + '</td></tr>');
    } else {
        $("#chatTable tbody").append('<tr><td class="col-md-1">'+data.sendUserName+'</td><td class="col-md-8">'+data.message+'</td><td class="col-md-1"></td></tr>');
    }
}

function sendMessage(){
    if (sid == '') {
        alert("请先以游客身份登陆。");
    }
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
        success: function (res) {//res表示是否与服务器连接成功
            console.log(res);//json中的数据
            if (res.flag) {
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
