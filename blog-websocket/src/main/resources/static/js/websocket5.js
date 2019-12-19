var stompClient = null;
var sid,telNo,name = "";
var currentMenu = "publicChat";
var headers={};

function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect(headers, function(frame) {
        console.log('订阅在线人数变更广播[/topic/online]');
        stompClient.subscribe('/topic/online', function(respnose){
            onlineUpdate(JSON.parse(respnose.body));
        });
        console.log('订阅公聊消息广播[/topic/notice]');
        stompClient.subscribe('/topic/notice', function(respnose){
            showResponse(JSON.parse(respnose.body));
        });
    });
}

function telNoOnBlur() {
    var telNumber = $("#telNo").val();
    if (telNumber.length != 11 || !isPoneAvailable(telNumber)) {
        return;
    }
    telNo = telNumber;
    $.ajax({
        url: "/api/chat/getInfoByTelNo/" + telNumber,
        type: "GET",
        data: {},
        success: function (res) {
            console.log("消息发送结果:" + res);
            if (res.flag) {
                sid = res.data.sid;
                if (res.data.isLogin) {
                    name = res.data.name;
                    var flags = confirm("当前手机号已经登陆，是否直接登陆?");
                    if (flags) {
                        login(true);
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

function resetConnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }

    sid = getCookie("sid");
    if (sid == null || sid == "") {
        $('#loginModal').modal('show');
        return;
    }
    $.ajax({
        url: "/api/chat/getInfoBySid/" + sid,
        type: "GET",
        data: {},
        success: function (res) {
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
                    login(true);
                } else {
                    $('#loginModal').modal('show');
                }
            } else {
                console.log("获取sid对应的人信息无果。。。");
                $('#loginModal').modal('show');
            }
        },
        error: function (e) {
            console.log("获取sid对应的人信息异常。。。");
            $('#loginModal').modal('show');
        }
    });
}

function login(autoLogin){
    var telNumber = $("#telNo").val();
    if (!isPoneAvailable(telNumber)) {
        alert("请输入正确的手机号");
        return;
    }
    name = $("#name").val();
    var isHiddenTelNo = 0;
    if ($("#isHiddenTelNo").val()) {
        isHiddenTelNo = 1;
    }
    console.log("name : " + name);
    console.log("sid : " + sid);
    console.log("telNo : " + telNo);
    headers.username = sid;
    headers.password = telNo;
    connect();
    /*try {
        stompClient.send(
            "/app/change-notice",
            {},
            JSON.stringify({'sid':sid,"telNo": telNo, 'name': name,'isHiddenTelNo':isHiddenTelNo})
        );
    } catch(err) {
        debugger
        console.log("change-notice："+err);
    }*/

    setCookie("sid",sid);
    setCookie("telNo",telNo);
    setCookie("name",name);
    $('#loginModal').modal('hide');
    setView(true);
};


function sendMessage(){
    if (sid == null || sid == '') {
        $('#loginModal').modal('show');
        return;
    }
    var messageInput = $("#messageInput").val();
    if (messageInput == "") {
        alert("发送消息不能为空哦");
        return;
    }
    var sendTargetType = "public";// 群聊组id，私聊好友id
    var toId = "public";
    $.ajax({
        url: "/api/chat/sendMessage",
        type: "post",
        data: {
            "sendSid": sid,
            "sendMessage": messageInput,
            "sendTargetType": sendTargetType,
            "toId": toId
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


function onlineUpdate(data) {
    $("#publicChat").text('公聊('+data.onlineCount +'/'+data.count+')');
    $("#chatListDiv").empty();
    if (currentMenu == 'publicChat') {
        data.userInfos.forEach(function iForEach(item, index) {
            var btnVar =
                "<button id='"+item.sid+"' class='ui fluid button teacher' onclick='_on()'>" +
                "<i class='green user icon'>"+item.name+"</i>" +
                "</button>";
            $("#chatListDiv").append(btnVar);
        })
    }
}

function showResponse(data){
    var name ="";
    if (data.sendSid == sid) {
        name = "我";
    } else {
        name= data.sendUserName;
    }
    var message =
        '<div class="event">' +
        '<div class="label"><i class="green user icon"></i></div>' +
        '<div class="summary"><a>'+ name +'</a><span class="summary">'+ data.sendDate +'</span></div>'+
        '<div class="extra text">'+ data.sendMessage + '</div>' +
        '</div>';
    $("#chatFeedDiv").append(message);
}
function setView(isConnect) {
    if (isConnect) {
        document.getElementById('myContent').style.display = 'block';
        document.getElementById('wecomeInfo').innerText = "欢迎您：" + name;
    } else {
        document.getElementById('myContent').style.display = 'none';
        document.getElementById('wecomeInfo').innerText = "";
    }
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