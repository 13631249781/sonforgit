// This function check the current page is after login.
// If current page is after login, root is not null. Then forward to login page.
function checkWindowOpener() {
	if (window.opener) {
		window.opener.location = "../user/Logon.jsp";
		window.close();
	} else if (typeof root != "undefined")
		window.location = "../user/Logon.jsp";
}