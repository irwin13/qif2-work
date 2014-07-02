function loadQifAdapterProperty() {
    jQuery.ajax({
        type: "post",
        url: '${web.context}/qifAdapter/qifAdapterProperty',
        dataType: "text",
        data: $('#qifAdapterPropertyForm').serialize(),
        success: function(data, textStatus) {
            if (textStatus == "success") {
                $('#child_qifAdapterProperty').html(data);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            var html = textStatus + " " + errorThrown;
            $('#child_qifAdapterProperty').html(html);
        }

    });
}

function addItem() {
    $("#qifAdapterPropertyAction").val('add');    
    loadQifAdapterProperty();
}

function editItem(index) {
    $("#qifAdapterPropertyIndex").val(index);
    $("#qifAdapterPropertyAction").val('edit');    
    $("input[name='qifAdapterProperty_buttonAdd']")[0].value = "Update";
    $("input[name='qifAdapterProperty_buttonReset']")[0].disabled = false;    
    loadQifAdapterProperty();        
}

function removeItem(index) {
    $("#qifAdapterPropertyIndex").val(index);
    $("#qifAdapterPropertyAction").val('remove');        
    $("input[name='qifAdapterProperty_buttonReset']")[0].disabled = "disabled";
    loadQifAdapterProperty();        
}

function resetItem() {
    $("#qifAdapterProperty_propertyKey").val('');
    $("#qifAdapterProperty_propertyValue").val('');
    $("#qifAdapterProperty_description").val('');

    $("input[name='qifAdapterPropertyIndex']")[0].value = "";

    $("input[name='qifAdapterProperty_buttonAdd']")[0].value = "Add";    
    $("input[name='qifAdapterProperty_buttonAdd']")[0].disabled = false;

    $("input[name='qifAdapterProperty_buttonReset']")[0].disabled = true;

}
