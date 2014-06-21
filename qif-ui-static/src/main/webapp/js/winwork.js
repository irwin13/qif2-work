// global variable
var model;

function setModel(modelName) {
    model = modelName;
}

var contextUrl;

function setContextUrl(contextUrlParam) {
    contextUrl = contextUrlParam;
}

// LIST PAGE COMMON SCRIPT
function doCreate() {
    document.location.href = contextUrl + '/' + model + '/page/create';
}

function doDetail(id) {
	jQuery.ajax({
		type: "GET",
		url: contextUrl + '/' + model + '/page/detail/' + id,
		dataType: "text",
		data: {id:id}, 
		success: function(data, textStatus) {
			if (textStatus == "success") {
				$( "#dialogDetail" ).dialog({
        			show: "blind",
        			hide: "explode",
					height: 400,
					width: 800,
					resizable: false,
					modal: true,
					position: "center",
					title: "Detail "+model,
					buttons: {
						"Edit": function() {
							window.location = contextUrl + '/' + model + '/page/edit/' + id;
						},
						"Delete": function() {
							doDelete(contextUrl, id);
						},
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
        		});
				$("#dialogDetail").html(data);
				$("#dialogDetail").dialog('open','center');
				
			}					  																				  		
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var html = textStatus + " " + errorThrown;
		}																									

	});

}

function deleteConfirmation(id) {
    $('#deleteId').val(id);
    $("#delete-confirm").modal('show');
}

function doDelete() {
    $.ajax({
        type: 'GET',
        url: contextUrl + '/' + model + '/delete?id=' + $('#deleteId').val(),
        success:function() {
            $("#delete-confirm").modal('hide');
            loadList();
        }
    });
}


function closeDetail(id) {
	$("#detail_" + id).toggle();
}

function doSearch() {
	document.forms.listForm.pageStart.value = 0;
	loadList();
}

function doChangePage(start){
	document.forms.listForm.pageStart.value = start;
	loadList();
}

function doReset(){
	document.forms.listForm.searchKeyword.value = "";
	document.forms.listForm.pageStart.value = 0;
	document.forms.listForm.sortProperty.value = "";
	document.forms.listForm.sortMethod.value = "";
	loadList();
}

function loadList(){
	jQuery.ajax({
		type: "GET",
		url: contextUrl + '/' + model + '/listAjax',
		dataType: "text",
		data : $('#listForm').serialize(),
		beforeSend : function (xhr) {
			var loading = "<p align='center'><img alt='Loading' src='${web.static}/img/searching.gif' /></p>";
			$('#listContent').html(loading);
		},
		success: function(data, textStatus) {						  			
  			if (textStatus == "success") {										
		  		$('#listContent').html(data);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			var html = textStatus + " " + errorThrown;
			$('#listContent').html(html);
		}																	
		
	});
	$('#searchKeyword').focus();
}

$('#searchKeyword').keypress(function(e) {
	var key = -1;
	if (e.which) {
		key = e.which;
	} else if (e.keyCode) {
		key = e.keyCode;
	}
	if (key == 13) {
        doSearch();
    }
});

// CREATE PAGE COMMON SCRIPT
function doCancelCreate() {
	document.location.href="list";
}

function doSaveCreate() {
	document.createForm.submit();

}

//EDIT PAGE COMMON SCRIPT
function doCancelEdit(linkUrl) {
	window.location = linkUrl;
}

function doSaveEdit() {
	document.editForm.submit();
}

//Validasi hanya Angka
function isNumberKey(evt) {
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function inNumberComma(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!=44)
        return false;

    return true;
}