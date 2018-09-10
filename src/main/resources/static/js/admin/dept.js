function updDept(form){
    if(form.deptName.value.trim()==''){
        $.alert('Please input description.');
        return false;
    }
    $.post('/dept/update',{
        deptId:form.deptId.value,
        deptName:form.deptName.value.trim()
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
                        if(response.type=='green'){window.location.replace("/dept/view/"+form.deptId.value)};
                    }
                }
            }
        });
    })
}
function createDept(form){
    if(form.deptId.value.trim()==''){
        $.alert('Please input department id.');
        return false;
    }
    if(form.deptName.value.trim()==''){
        $.alert('Please input description.');
        return false;
    }
    $.post('/dept/create',{
        deptId:form.deptId.value.trim(),
        deptName:form.deptName.value.trim()
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
                        if(response.type=='green'){window.location.replace("/dept/view/"+form.deptId.value)};
                    }
                }
            }
        });
    })
}
function delDept(form){
    $.get('/dept/delete/'+form.deptId.value,function(response){
        $.alert({
            type:response.type,
            title:'Message',
            content:response.msg,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        if(response.type=='green'){window.location.replace("/dept/viewDeptList/")};
                    }
                }
            }
        });
    })
}