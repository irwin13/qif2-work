function loadBpListenerProperty() {
    jQuery.ajax({
        type: "post",
        url: '${web.context}/bpListener/bpListenerProperty',
        dataType: "text",
        data: $('#bpListenerPropertyForm').serialize(),
        success: function(data, textStatus) {
            if (textStatus == "success") {
                $('#child_bpListenerProperty').html(data);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            var html = textStatus + " " + errorThrown;
            $('#child_bpListenerProperty').html(html);
        }

    });
}

function addItem() {
    $("#bpListenerPropertyProperty").val('add');    
    loadBpListenerProperty();
}

function editItem(index) {
    $("#bpListenerPropertyIndex").val(index);
    $("#bpListenerPropertyProperty").val('edit');    
    $("input[name='bpListenerProperty_buttonAdd']")[0].value = "Update";
    $("input[name='bpListenerProperty_buttonReset']")[0].disabled = false;    
    loadBpListenerProperty();        
}

function removeItem(index) {
    $("#bpListenerPropertyIndex").val(index);
    $("#bpListenerPropertyProperty").val('remove');        
    $("input[name='bpListenerProperty_buttonReset']")[0].disabled = "disabled";
    loadBpListenerProperty();        
}

function resetItem() {
    $("#bpListenerProperty_propertyKey").val('');
    $("#bpListenerProperty_propertyValue").val('');
    $("#bpListenerProperty_description").val('');

    $("input[name='bpListenerPropertyIndex']")[0].value = "";

    $("input[name='bpListenerProperty_buttonAdd']")[0].value = "Add";    
    $("input[name='bpListenerProperty_buttonAdd']")[0].disabled = false;

    $("input[name='bpListenerProperty_buttonReset']")[0].disabled = true;

}
