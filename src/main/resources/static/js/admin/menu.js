function updateMenu(form){
    var isMenu=form.isMenu.value;
    var menuUri=form.menuUri.value.trim();
    if(form.menuName.value==''){
        $.alert("Please input menu name.");
        return false;
    }
    if(form.description.value==''){
        $.alert('Please input Description.');
        return false;
    }
    if(form.sequence.value==''){
        $.alert('Please input sequence.');
        return false;
    }
    if(form.iconName.value==''){
        $.alert('Please input icon name.');
        return false;
    }
    $.post('/menu/update',$('#menuForm').serialize(),function(response){
        $.alert({
            type:'green',
            title:'Message',
            content:response.msg,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        window.location.replace("/menu/view/"+form.menuId.value);
                    }
                }
            }
        });
    })

}
function createMenu(form){
    var isMenu=form.isMenu.value;
    var menuUri=form.menuUri.value.trim();
    if(form.menuName.value==''){
        $.alert("Please input menu name.");
        return false;
    }
    if(form.description.value==''){
        $.alert('Please input Description.');
        return false;
    }
    if(form.sequence.value==''){
        $.alert('Please input sequence.');
        return false;
    }
    if(form.iconName.value==''){
        $.alert('Please input icon name.');
        return false;
    }
    $.post('/menu/create',$('#menuForm').serialize(),function(response){
        $.alert({
            type:response.type,
            title:'Message',
            content:response.msg,
            buttons:{
                ok:{
                    text:'Ok',
                    btnClass:'btn-info',
                    action:function(){
                        if(response.type=='green'){window.location.replace("/menu/viewMenuList")};
                    }
                }
            }
        });
    })
}
function delMenu(menuId){
$.confirm({
    title:'Message',
    content:'Are you sure you want to delete this Menu?',
    buttons:{
        ok:{
            text:'Yes',
            btnClass:'btn-primary',
            action:function(){
                $.get('/menu/delete/'+menuId,function(response){
                    $.alert({
                        type:'green',
                        title:'Message',
                        content:response.msg,
                        buttons:{
                            ok:{
                                text:'Ok',
                                btnClass:'btn-info',
                                action:function(){
                                    window.location.replace("/menu/viewMenuList");
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