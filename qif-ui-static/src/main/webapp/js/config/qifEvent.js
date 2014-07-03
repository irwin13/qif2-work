function loadQifEventProperty() {
    jQuery.ajax({
        type: "post",
        url: '${web.context}/qifEvent/qifEventProperty',
        dataType: "text",
        data: $('#qifEventPropertyForm').serialize(),
        success: function(data, textStatus) {
            if (textStatus == "success") {
                $('#child_qifEventProperty').html(data);
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            var html = textStatus + " " + errorThrown;
            $('#child_qifEventProperty').html(html);
        }

    });
}

function addItem() {
    $("#qifEventPropertyAction").val('add');
    loadQifEventProperty();
}

function editItem(index) {
    $("#qifEventPropertyIndex").val(index);
    $("#qifEventPropertyAction").val('edit');
    $("input[name='qifEventProperty_buttonAdd']")[0].value = "Update";
    $("input[name='qifEventProperty_buttonReset']")[0].disabled = false;    
    loadQifEventProperty();        
}

function removeItem(index) {
    $("#qifEventPropertyIndex").val(index);
    $("#qifEventPropertyAction").val('remove');
    $("input[name='qifEventProperty_buttonReset']")[0].disabled = "disabled";
    loadQifEventProperty();        
}

function resetItem() {
    $("#qifEventProperty_propertyKey").val('');
    $("#qifEventProperty_propertyValue").val('');
    $("#qifEventProperty_description").val('');

    $("input[name='qifEventPropertyIndex']")[0].value = "";

    $("input[name='qifEventProperty_buttonAdd']")[0].value = "Add";    
    $("input[name='qifEventProperty_buttonAdd']")[0].disabled = false;

    $("input[name='qifEventProperty_buttonReset']")[0].disabled = true;

}
