function saveCode(form){
    if(form.appDesc.value==''){
        $.alert('Please input Description.');
        return false;
    }
    $.post('/admin/childCode/update',$('#codeForm').serialize(),function(response){
        $.alert({
            type:'green',
            title:'Message',
            content:response,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        window.location.replace("/admin/viewChild/"+form.masterAppCd.value);
                    }
                }
            }
        });
    })
}
function createChildCode(form){
    if(form.appCd.value.trim()==''){
       $.alert('Please input Child Code.');
       return false;
    }
    if(form.appDesc.value.trim()==''){
        $.alert('Please input Description.');
        return false;
    }
    $.post('/admin/childCode/create',{
        masterAppCd:form.masterAppCd.value,
        appCd:form.appCd.value.trim(),
        appDesc:form.appDesc.value.trim()
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
                        if(response.type=='green'){window.location.replace("/admin/viewChild/"+form.masterAppCd.value);};
                    }
                }
            }
        });
    })
}
function delCode(form){
    $.confirm({
        title:'Message',
        content:'Are you sure delete this child code?',
        buttons:{
            ok:{
                text:'Yes',
                btnClass:'btn-primary',
                action:function(){
                    $.get('/admin/childCode/delete/'+form.id.value,function(response){
                        $.alert({
                            type:response.type,
                            title:'Message',
                            content:response.msg,
                            buttons:{
                                ok:{
                                    text:'Ok',
                                    btnClass:'btn-info',
                                    action:function(){
                                        if(response.type=='green'){window.location.replace("/admin/viewChild/"+form.masterAppCd.value);};
                                    }
                                }
                            }
                        });
                    })
                }
            },
            cancel:{}
        }
    })
    console.log(form.id.value);
}