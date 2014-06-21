function loadBpAdapterProperty() {
    jQuery.ajax({
        type: "post",
        url: '${web.context}/bpAdapter/bpAdapterProperty',
        dataType: "text",
        data: $('#bpAdapterPropertyForm').serialize(),
        success: function(data, textStatus) {
            if (textStatus == "success") {
                $('#child_bpAdapterProperty').html(data);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            var html = textStatus + " " + errorThrown;
            $('#child_bpAdapterProperty').html(html);
        }

    });
}

function addItem() {
    $("#bpAdapterPropertyAction").val('add');    
    loadBpAdapterProperty();
}

function editItem(index) {
    $("#bpAdapterPropertyIndex").val(index);
    $("#bpAdapterPropertyAction").val('edit');    
    $("input[name='bpAdapterProperty_buttonAdd']")[0].value = "Update";
    $("input[name='bpAdapterProperty_buttonReset']")[0].disabled = false;    
    loadBpAdapterProperty();        
}

function removeItem(index) {
    $("#bpAdapterPropertyIndex").val(index);
    $("#bpAdapterPropertyAction").val('remove');        
    $("input[name='bpAdapterProperty_buttonReset']")[0].disabled = "disabled";
    loadBpAdapterProperty();        
}

function resetItem() {
    $("#bpAdapterProperty_propertyKey").val('');
    $("#bpAdapterProperty_propertyValue").val('');
    $("#bpAdapterProperty_description").val('');

    $("input[name='bpAdapterPropertyIndex']")[0].value = "";

    $("input[name='bpAdapterProperty_buttonAdd']")[0].value = "Add";    
    $("input[name='bpAdapterProperty_buttonAdd']")[0].disabled = false;

    $("input[name='bpAdapterProperty_buttonReset']")[0].disabled = true;

}
