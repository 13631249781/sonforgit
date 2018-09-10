function updRole(form,roleId){
    var selectValue =form.allowAction.value;
    if(selectValue=='Add'){
        addAction='Y';
        updateAction='N';
        deleteAction='N';
    }else if(selectValue=='All'){
        addAction='Y';
        updateAction='Y';
        deleteAction='Y';
    }else{
        addAction='N';
        updateAction='N';
        deleteAction='N';
    }
    if(form.roleName.value.trim()==''){
        $.alert('Please input role name.');
        return false;
    }
    if(form.description.value.trim()==''){
        $.alert('Please input description.');
        return false;
    }
    $.post('/role/update',{
        roleId:roleId,
        roleName:form.roleName.value.trim(),
        description:form.description.value.trim(),
        viewAction:'Y',
        addAction:addAction,
        updateAction:updateAction,
        deleteAction:deleteAction
    },function(response){
        $.alert({
            type:response.type,
            title:'Message',
            content:response.msg,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        if(response.type=='green'){window.location.replace("/role/view/"+roleId)};
                    }
                }
            }
        });
    })
}
function createRole(form){
    if(form.roleName.value.trim()==''){
        $.alert('Please input role name.');
        return false;
    }
    if(form.description.value.trim()==''){
        $.alert('Please input description.');
        return false;
    }
    var selectValue =form.allowAction.value;
    if(selectValue=='Add'){
        addAction='Y';
        updateAction='N';
        deleteAction='N';
    }else if(selectValue=='All'){
        addAction='Y';
        updateAction='Y';
        deleteAction='Y';
    }else{
        addAction='N';
        updateAction='N';
        deleteAction='N';
    }
    $.post('/role/create',{
        roleName:form.roleName.value.trim(),
        description:form.description.value.trim(),
        viewAction:'Y',
        addAction:addAction,
        updateAction:updateAction,
        deleteAction:deleteAction
    },function(response){
        $.alert({
            type:response.type,
            title:'Message',
            content:response.msg,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        if(response.type=='green'){window.location.replace("/role/viewRoleList")};
                    }
                }
            }
        });
    })
}


function delRole(roleId){
$.confirm({
    title:'Message',
    content:'Are you sure you want to delete this role?',
    buttons:{
        ok:{
            text:'Yes',
            btnClass:'btn-primary',
            action:function(){
                $.get('/role/delete/'+roleId,function(response){
                    $.alert({
                        type:response.type,
                        title:'Message',
                        content:response.msg,
                        buttons:{
                            ok:{
                                text:'Ok',
                                btnClass:'btn-info',
                                action:function(){
                                    if(response.type=='green'){window.location.replace("/role/viewRoleList")};
                                }
                            }
                        }
                    });
                });
            }
        },
        cancel:{}
    }
})

}
