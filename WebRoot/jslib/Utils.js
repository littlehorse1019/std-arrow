var formatRQ = "YYYY-MM-DD";

function formatDate(value, format) {
    var date = new Date(value);
    if (arguments.length < 2 && !date.getTime) {
        format = date;
        date = new Date();
    }
    typeof format != 'string' && (format = 'YYYY年MM月DD日 hh时mm分ss秒');
    var week = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday',
        'Friday', 'Saturday', '日', '一', '二', '三', '四', '五', '六'];
    return format.replace(/YYYY|YY|MM|DD|hh|mm|ss|星期|周|www|week/g, function (a) {
        switch (a) {
            case "YYYY":
                return date.getFullYear();
            case "YY":
                return (date.getFullYear() + "").slice(2);
            case "MM":
                return date.getMonth() + 1;
            case "DD":
                return date.getDate();
            case "hh":
                return date.getHours();
            case "mm":
                return date.getMinutes();
            case "ss":
                return date.getSeconds();
            case "星期":
                return "星期" + week[date.getDay() + 7];
            case "周":
                return "周" + week[date.getDay() + 7];
            case "week":
                return week[date.getDay()];
            case "www":
                return week[date.getDay()].slice(0, 3);
        }
    });
}

function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function isUnsignedInteger(strInteger) {
    var newPar = /^\d+$/
    return newPar.test(strInteger);
}