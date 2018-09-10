function updGroup(groupId,form){
    var groupName = form.groupName.value.trim();
    var description = form.description.value.trim();
    if(groupName ==''){
        $.alert("Please input the group name.");
        return false;
    }
    if(description ==''){
        $.alert("Please input the description.");
        return false;
    }
    $.post('/group/update/',{
        groupId:groupId,
        groupName:groupName,
        description:description
    },function(response,status){
        $.alert({
            type:response.type,
            title:'Message',
            content:response.msg,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        if(response.type =='green'){
                            window.location.replace("/group/view/"+groupId);
                        }
                    }
                }
            }
        });

    })
}

function delGroup(groupId){
$.confirm({
    title:'Message',
    content:'Are you sure you want to delete this group?',
    buttons:{
        ok:{
            text:'Yes',
            btnClass:'btn-primary',
            action:function(){
                $.get('/group/delete/'+groupId,function(response){
                     $.alert({
                         type:response.type,
                         title:'Message',
                         content:response.msg,
                         buttons:{
                             ok:{
                                 text:'Ok',
                                 btnClass:'btn-info',
                                 action:function(){
                                     if(response.type=='green'){
                                         window.location.replace("/group/viewGroupList");
                                     }
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
function createGroup(form){
    var groupName =form.groupName.value.trim();
    var description = form.description.value.trim();
    if(groupName ==''){
        $.alert("Please input the group name.");
        return false;
    }
    if(description ==''){
        $.alert("Please input the description.");
        return false;
    }
    $.post('/group/create',{
        groupName:groupName,
        description:description
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
                         if(response.type =='green'){
                             window.location.replace("/group/viewGroupList");
                         }
                     }
                 }
             }
         });
    })
}