/* Function to clear a form of all it's values */
function clearForm(frmObj) {
	for (var i = 0; i < frmObj.elements.length; i++) {
		var element = frmObj.elements[i];

		if (element.type.indexOf("text") == 0 || element.type.indexOf("password") == 0) {
			element.value = "";
		} else if (element.type.indexOf("radio") == 0) {
			element.checked = false;
		} else if (element.type.indexOf("checkbox") == 0) {
			element.checked = false;
		} else if (element.type.indexOf("select") == 0) {
			if (element.options.length > 0) {
				for (var j = 0; j < element.options.length; j++) {
					element.options[j].selected = false;
				}

				if (element.options[0].value == "")
					element.options[0].selected = true;
				else if (element.options.length == 1)
					element.remove(0);
			}
		}
	}
}

/* Function to get a form's values in a string */
function getFormAsString(frmObj) {
	var query = "";
	for (var i = 0; i < frmObj.elements.length; i++) {
		var element = frmObj.elements[i];
		if (element.type.indexOf("checkbox") == 0 || element.type.indexOf("radio") == 0) {
			if (element.checked) {
				query += element.name + '=' + escape(element.value) + "&";
			}
		} else if (element.type.indexOf("select") == 0) {
			for (var j = 0; j < element.length; j++) {
				if (element.options[j].selected) {
					query += element.name + '=' + escape(element.value) + "&";
				}
			}
		} else {
			query += element.name + '=' + escape(element.value) + "&";
		}
	}
	return query;
}

/* This function is to open job detail in a pop-up window */
function openWindow(url) {
	var ScreenWidth = window.screen.width;
	var ScreenHeight = window.screen.height;
	var movefromedge = 0;
	placementx = (ScreenWidth / 2) - ((1050) / 2);
	placementy = (ScreenHeight / 2) - ((600 + 50) / 2);
	var PopUpUrl = ".." + url;
	WinPop = window.open(PopUpUrl, "",
		"width=1050,height=600,toolbar=0,location=0,directories=0,status=0,scrollbars=1,menubar=0,resizable=1,left=" + placementx + ",top="
		+ placementy + ",screenX=" + placementx + ",screenY=" + placementy + ",");
}

function openWindow2(url, width, height) {
	var ScreenWidth = window.screen.width;
	var ScreenHeight = window.screen.height;
	var movefromedge = 0;
	placementx = (ScreenWidth / 2) - ((width) / 2);
	placementy = (ScreenHeight / 2) - ((height + 50) / 2);
	var PopUpUrl = ".." + url;
	WinPop = window.open(PopUpUrl, "", "width=" + width + ",height=" + height
		+ ",toolbar=0,location=0,directories=0,status=0,scrollbars=1,menubar=0,resizable=1,left=" + placementx + ",top=" + placementy
		+ ",screenX=" + placementx + ",screenY=" + placementy + ",");
}

// The following highlight row functions only work for display tag table.
// Doesn't work for dataTables.bootstrap since rows are hidden in other pages, browser does not refresh and call these highlight function again.
// Use the link in first column as onclick event.
function highlightTableRows(tableId) {
	highlightTable(tableId, 0);
}

// Use the link in second column as onclick event.
function highlightTableRows2(tableId) {
	highlightTable(tableId, 1);
}

// Use the link in 'columnIndex' column as onclick event.
function highlightTable(tableId, columnIndex) {
	var previousClass = null;
	var table = document.getElementById(tableId);

	if (table != null) {
		var startRow = 0;
		// workaround for Tapestry not using thead
		if (!table.getElementsByTagName("thead")[0]) {
			startRow = 1;
		}
		var tbody = table.getElementsByTagName("tbody")[0];
		var rows = tbody.getElementsByTagName("tr");
		// add event handlers so rows light up and are clickable
		for (i = startRow; i < rows.length; i++) {
			rows[i].onmouseover = function() {
				previousClass = this.className;
				this.className = 'over';
				this.style.cursor = "pointer";
			};
			rows[i].onmouseout = function() {
				this.className = previousClass;
			};
			rows[i].onclick = function() {
				var cell = this.getElementsByTagName("td")[columnIndex];
				var link = cell.getElementsByTagName("a")[0];
				if (link.onclick) {
					call = link.getAttribute("onclick");
					if (call.indexOf("return ") == 0) {
						call = call.substring(7);
					}
					// this will not work for links with onclick handlers that
					// return false
					eval(call);
				} else {
					location.href = link.getAttribute("href");
				}
				// this.style.cursor="wait";
				return false;
			};
		}
	}
}

// The following function works with bootstrap.min.js and bootstrap-notify.js.
function showSuccess(message) {
	showNotification("success", "fa fa-check-circle text-green", message);
}

function showInfo(message) {
	showNotification("info", "fa fa-info-circle text-blue", message);
}

function showWarning(message) {
	showNotification("warning", "fa fa-exclamation-triangle text-orange", message);
}

function showError(message) {
	showNotification("danger", "fa fa-times-circle text-red", message);
}

function closeNotification() {
	$(".alert").alert('close');
}

function showNotification(type, icon, message) {
	var messageLength = message.length;

	if (messageLength > 0) {
		var template = '<div data-notify="container" class="col-xs-11 col-sm-4 alert alert-{0}" role="alert"><button type="button" aria-hidden="true" class="close" data-notify="dismiss">&times;</button><i data-notify="icon" class=""></i><span data-notify="title">{1}</span> <span data-notify="message">{2}</span><div class="progress" data-notify="progressbar"><div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div></div><a href="{3}" target="{4}" data-notify="url"></a></div>';

		if (message.length > 60)
			template = '<div data-notify="container" class="col-xs-11 col-sm-11 alert alert-{0}" role="alert"><button type="button" aria-hidden="true" class="close" data-notify="dismiss">&times;</button><i data-notify="icon" class=""></i><span data-notify="title">{1}</span> <span data-notify="message">{2}</span><div class="progress" data-notify="progressbar"><div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div></div><a href="{3}" target="{4}" data-notify="url"></a></div>';

		// delay : 0 = Never auto-close notification.
		$.notify({
			icon : icon,
			message : message
		}, {
			template : template,
			type : type,
			delay : 5000,
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
}