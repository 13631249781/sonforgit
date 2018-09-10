function checkStaffNoUserName(form){
    var availabilityNode = $('#availabilityNode');
    availabilityNode.empty();
    if(form.staffNum.value =="" || form.userName.value==""){
        $("#userPanel").attr('class', 'panel panel-warning');
        availabilityNode.append("<i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i> Please input staff number and user name.<br>");
        return false;
    }
    if(form.staffNum.value!=form.userName.value){
        $("#userPanel").attr('class', 'panel panel-warning');
        availabilityNode.append("<i class=\"fa fa-exclamation-triangle\" aria-hidden=\"true\"></i> Staff number and user name must be the same.<br>");
        return false;
    }
    var loading =$('#loading');
    loading.css({visibility:'visible'});
    $.post(
        '/user/checkAvailability',
        {userName:form.userName.value,staffNum:form.staffNum.value},
        function(result){
            loading.css({visibility:'hidden'});
            if (result.msg == "failed") {
                $("#userPanel").attr('class', 'panel panel-danger');
                availabilityNode.append("<i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i> User name has been taken.<br>");
//            } else if (result.msg == "") {
//                $("#userPanel").attr('class', 'panel panel-success');
//                availabilityNode.append("<i class=\"fa fa-check-circle\" aria-hidden=\"true\"></i> User name is available.<br>");
            } else {
                $("#userPanel").attr('class', 'panel panel-success');
                var userInfo = result.userInfo;
                if (result.isResigned == "Y") {
                    availabilityNode.append("<i class=\"fa fa-times-circle\" aria-hidden=\"true\"></i> " + userInfo.lastName+','+userInfo.firstName+' '+userInfo.chineseName
                    + " is resigned already.<br>");
                } else {
                    availabilityNode.append("<i class=\"fa fa-check-circle\" aria-hidden=\"true\"></i> User name is available.<br>");

                    form.firstName.value = mapNull2Empty(userInfo.firstName);
                    form.lastName.value = mapNull2Empty(userInfo.lastName);
                    form.chineseName.value = mapNull2Empty(userInfo.chineseName);
                    form.msMailAddress.value = mapNull2Empty(userInfo.msMailAddress);
                    form.jobTitle.value = mapNull2Empty(userInfo.jobTitle);
                    form.company.value = mapNull2Empty(userInfo.company);
                    form.branch.value = mapNull2Empty(userInfo.branch);
                    form.location.value = mapNull2Empty(userInfo.location);
                    form.officePhone.value = trimAreaCode(mapNull2Empty(userInfo.officePhone));
                    form.officeFax.value = trimAreaCode(mapNull2Empty(userInfo.officeFax));
                    form.mobile.value = trimAreaCode(mapNull2Empty(userInfo.mobile));
                    form.ccc.value = trimAreaCode(mapNull2Empty(userInfo.ccc));
                }
            }
        }
    )
}
function submitUserInfo(form){
    if(form.staffNum.value==''){
        $.alert("Please input the staff number.");
        return false;
    }
    if(form.userName.value==''){
        $.alert("Please input the user name.");
        return false;
    }
    if(form.staffNum.value != form.userName.value){
        $.alert("Staff number and user name must be the same.");
        return false;
    }
    if(form.role.value==''){
        $.alert("Please input the role.");
        return false;
    }
    if(form.group.value==''){
        $.alert("Please input the group.");
        return false;
    }
    if(form.department.value==''){
        $.alert("Please input the department.");
        return false;
    }
    if(form.revokeDate.value==''){
        $.alert("Please input the revoke date.");
        return false;
    }
    if(form.staffNum.value.search(/[^0-9a-zA-Z]+/) !=-1){
        $.alert("Staff Number can contain alphabetical and numerical character only.");
        return false;
    }
    if(form.userName.value.search(/[^0-9a-zA-Z]+/) !=-1){
        $.alert("User Name can contain alphabetical and numerical character only.");
        return false;
    }
    if(form.msMailAddress.value != "" && form.msMailAddress.value.search(/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/) ==-1){
        $.alert("Office email is invalid.");
        return false;
    }
    if(form.email.value !="" && form.email.value.search(/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/) ==-1){
        $.alert("Email is invalid.");
        return false;
    }
    $.post('/user/createUser',$('#userForm').serialize(),function(response){
        var type ='green';
        if(response.result==false){type='orange'};
        $.alert({
            type:type,
            title:'Message',
            content:response.msg,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        if(response.result==true){window.location.replace("/user/viewUserList");}
                    }
                }
            }
        })
    })

}


function mapNull2Empty(value) {
    return value == null ? "" : value;
}
function trimAreaCode(phoneNumber) {
    if (phoneNumber.substring(0, 1) == "+")
        return phoneNumber.substring(phoneNumber.indexOf(" ") + 1);
    return phoneNumber;
}

function checkFrom(form){
    console.log(form);
}