var ws = null;

function WebSocketTest() {
    if ("WebSocket" in window) {

        // Let us open a web socket
        ws = new WebSocket("ws://localhost:8025/websockets/game");

        ws.onopen = function() {
            // Web Socket is connected, send data using send()
            ws.send("init");
        };

        ws.onmessage = function(evt) {
            var received_msg = evt.data;
            //var header = received_msg.slice(0, received_msg.indexOf(":"));
            //var data = received_msg.slice(received_msg.indexOf(":") + 1, received_msg.length);
            var data = received_msg.split(":");

            switch (data[0]) {
                case "totalDamage":
                    updateDamage(data[1]);
                    break;
                case "SkillDamage":
                    skillDamage(data[1], data[2]);
                    break;
                case "skillAvailable":
                    skillAvailable(data[1]);
                    break;
                case "skillUnavailable":
                    countdown(data[1], data[2]);
                    break;
                case "effectAvailable":
                    effectAvailable(data[1]);
                    break;
                case "effectUnavailable":
                    effectUnavailable(data[1]);
                    break;
                case "IceOrbit":
                    changeItem(data[0], data[1]);
                    break;
                case "Ember":
                    changeItem(data[0], data[1]);
                    break;
                case "damagePerSecond":
                    showDamagePerSecond(data[1]);
                    break;
            }
        };

        ws.onclose = function() {
            // websocket is closed.
            alert("Connection is closed...");
            ws.send("stop");
        };
    } else {
        // The browser doesn't support WebSocket
        alert("WebSocket NOT supported by your Browser!");
    }
};

var FDDTimer = null;
var FFFTimer = null;
var FDBTimer = null;
var ONETimer = null;
var TWOTimer = null;
var LTimer = null;
var RTimer = null;
var XTimer = null;
var CTimer = null;
var VTimer = null;

var timers = {};
timers['FDD'] = FDDTimer;
timers['FFF'] = FFFTimer;
timers['FDB'] = FDBTimer;
timers['ONE'] = ONETimer;
timers['TWO'] = TWOTimer;
timers['L'] = LTimer;
timers['R'] = RTimer;
timers['X'] = XTimer;
timers['C'] = CTimer;
timers['V'] = VTimer;

function startCalculating() {
    //if (!!ws) alert("STARTING");
    ws.send("start");
    $("#start").prop("disabled",true);
    $("#stop").prop("disabled",false);
}

function stopCalculating() {
    //if (!!ws) alert("CLOSING");
    ws.send("stop");
    clearAllTimers();
    clearAllCountdowns();
    $("#start").prop("disabled",false);
    $("#stop").prop("disabled",true);
}

function skillAvailable(skill) {
    if (!!timers[skill]) {
        window.clearInterval(timers[skill]);
    }
    $('#' + skill + 'countdown').remove();
}

function effectAvailable(effect) {
    $("#" + effect + "Unavailable").hide();
}

function effectUnavailable(effect) {
    $("#" + effect + "Unavailable").show();
}

function countdown(skill, cooldown) {
    //alert("test");
    if (skill === 'BURN' || skill === 'BUFF') {
        return;
    }

    var time = cooldown / 1000;
    $("#" + skill).prepend('<div id="' + skill + 'countdown" class="skill-image-countdown"></div>');

    var interval = function() {
        $('#' + skill + 'countdown').html(time);
        if (time > 0) {
            time--;
        }
    }

    interval();
    timers[skill] = window.setInterval(interval, 1000);
}

function clearAllTimers() {
    Object.keys(timers).forEach(function(elem) {
        window.clearInterval(timers[elem]);
    });
}

function clearAllCountdowns() {
    Object.keys(timers).forEach(function(elem) {
        $('#' + elem + 'countdown').remove();
    });
    $("#BUFFUnavailable").show();
    $("#BURNUnavailable").show();
}

function skillDamage(skill, damage) {
    $("#" + skill + "Damage").prepend('<div id="' + skill + 'DamageContent">+' + damage + '</div>')
    $("#" + skill + "DamageContent").animate({
        opacity: 0,
        top: '-50px',
        bottom: '-50px'
    }, "slow");
}

function changeItem(item, itemNum) {
    var itemId;
    var max;
    if (item === 'Ember') {
        itemId = '#ember';
        max = 5;
    } else if (item === 'IceOrbit') {
        itemId = '#iceOrbit';
        max = 3;
    }
    if (itemNum > max) return;
    for (var i = 1; i <= itemNum; i++) {
        $(itemId + i).show();
    }
    for (var i = itemNum + 1; i <= max; i++) {
        $(itemId + i).hide();
    }
}

function updateDamage(damage) {
    $("#totalDamage").html(damage);
}

function showDamagePerSecond(damagePerSecond) {
    $('#damagePerSecond').html(damagePerSecond);
    $('#myModal').modal('show');
}

function test() {
    //$('#myModal').modal('show');
    showDamagePerSecond(300);
}