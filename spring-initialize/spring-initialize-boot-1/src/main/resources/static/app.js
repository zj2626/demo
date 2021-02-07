var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    // if (connected) {
    //     $("#conversation").show();
    // }
    // else {
    //     $("#conversation").hide();
    // }
    // $("#listBody").html("");
}

function connect() {
    var socket = new SockJS('/gs/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/subscribeData/userList', function (greeting) {
            showUser(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function doLogin() {
    $("#name").attr("disabled", true);
    $("#login").attr("disabled", true);

    stompClient.send("/communication/login", {}, JSON.stringify({'name': $("#name").val()}));

    stompClient.subscribe('/user/' + $("#name").val() + "/receiveMsg", function (greeting) {
        $("#greetings").append("<tr><td style='text-align: left;'>" + greeting.body + "</td></tr>");
    });

}

function sendMsg() {
    $("#greetings").append("<tr><td style='text-align: right;'>" + $("#message").val() + "</td></tr>");

    stompClient.send("/communication/sendMsg", {}, JSON.stringify({'sendFrom': $("#name").val(), 'sendTo': $("#sendTo").val(), 'msg': $("#message").val()}));
}

function showUser(list) {
    $("#listBody").html("");
    for (var i = 0; i < list.length; i++) {
        $("#listBody").append("<tr><td class = \"userInfo\">" + list[i].name + "</td></tr>");
    }

    $(".userInfo").on("click", function (data) {
        if ($("#name").val() !== $(this).text()) {
            $("#sendTo").val($(this).text());
        }
    });
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#login").click(function () {
        doLogin();
    });
    $("#send").click(function () {
        sendMsg();
    });
});

