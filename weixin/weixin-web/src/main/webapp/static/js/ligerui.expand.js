function getFormatDate(date, dateformat){
    var g = this, p = this.options;
    if (isNaN(date)) return null;
    var format = dateformat;
    var o = {
        "M+": date.getMonth() + 1,
        "d+": date.getDate(),
        "h+": date.getHours(),
        "m+": date.getMinutes(),
        "s+": date.getSeconds(),
        "q+": Math.floor((date.getMonth() + 3) / 3),
        "S": date.getMilliseconds()
    };
    if (/(y+)/.test(format))
    {
        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o)
    {
        if (new RegExp("(" + k + ")").test(format))
        {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

$.ligerDefaults.Grid.formatters['boolean'] = function(value, column) {
	if (value == "true" || value == "1"){
		return "<font color='green'>启用</font>";
	}else{
		return "<font color='red'>不启用</font>";
	}
};

$.ligerDefaults.Grid.formatters['yesno'] = function(value, column) {
	if (value == "true" || value == "1"){
		return "<font color='green'>是</font>";
	}else{
		return "<font color='red'>否</font>";
	}
};

$.ligerDefaults.Grid.formatters['currency'] = function(value, column) {
	if (!value)
		return "$0.00";
	value = value.toString().replace(/\$|\,/g, '');
	if (isNaN(value))
		value = "0.00";
	sign = (value == (value = Math.abs(value)));
	value = Math.floor(value * 100 + 0.50000000001);
	cents = value % 100;
	value = Math.floor(value / 100).toString();
	if (cents < 10)
		cents = "0" + cents;
	for ( var i = 0; i < Math.floor((value.length - (1 + i)) / 3); i++)
		value = value.substring(0, value.length - (4 * i + 3)) + ','
				+ value.substring(value.length - (4 * i + 3));
	return "$" + (((sign) ? '' : '-') + '' + value + '.' + cents);
};

$.ligerDefaults.Grid.formatters['datetime'] = function(timestamp, column) {

	if (!timestamp) return "";
	
	timestamp = new Date(timestamp);
	
	var format = column.format || this.options.dateFormat || "yyyy-MM-dd";
	
	return getFormatDate(timestamp, format);
    
};

$.extend($.ligerMethos.Grid, {
	selectCheckedRow : function(name) {
		var data = this.getSelectedRow();
		if (!data) {
			return "";
		} else{
			return (data[name]);
		}
	},
	selectCheckedRows : function(name) {
		var rows = this.getCheckedRows();
		var str = "";
		$(rows).each(function() {
			var row = this;
			str += (row[name]) + ",";
		});
		str = str.replace(/,+$/, "");
		return str;
	}
});