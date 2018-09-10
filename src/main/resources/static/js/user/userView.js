function checkInfoForm(aFrom) {
    var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
    var email = aFrom.elements["msMailAddress"].value;
    if (email != "" && email.search(filter) == -1) {
        $.alert("The email is invalid.");
        return false;
    }
    return true;
}
function updateUser(){
    checkInfoForm($("#updateUserForm")[0]);
    $.post('/user/updateUser',$("#updateUserForm").serialize(),function(response){
        if(response=='Success'){
            $.alert({
                type:'green',
                title:'Message',
                content:'User information is updated.'
            });
        }/*else{
            $.alert({
                type:'red',
                title:'Message',
                content:'Error occured.'
            });
        }*/
    })
}

function editUser(form){
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
    if(form.msMailAddress.value != "" && form.msMailAddress.value.search(/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/) ==-1){
        $.alert("Office email is invalid.");
        return false;
    }
    if(form.email.value !="" && form.email.value.search(/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/) ==-1){
        $.alert("Email is invalid.");
        return false;
    }
    $.post('/user/editUser',$('#editUserForm').serialize(),function(response){
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
                        if(response.result==true){window.location.replace("/user/view/"+form.staffNum.value);}
                    }
                }
            }
        })
    })
}

function delUser(staffNum){
    $.get('/user/deleteUser/'+staffNum,function(response){
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
                        if(response.result==true){window.location.replace("/user/view/"+staffNum);}
                    }
                }
            }
        })
    })
}
