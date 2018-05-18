/**
 * Scripts Generales: Colegio de Administracion
 */

$(function (){
	$( ".export-excel" ).text("Exportar");
	$( ".paginate-first" ).text("<<");
	$( ".paginate-previous" ).text("<");
	$( ".paginate-next" ).text(">");
	$( ".paginate-last" ).text(">>");
	
	$(".paginate-first").css({
		"padding-right": "5px"
	});
	
	$(".paginate-previous").css({
		"padding-right": "5px"
	});
	
	$(".paginate-next").css({
		"padding-left": "5px"
	});

	$(".paginate-last").css({
		"padding-left": "5px"
	});

});

$(function () {
    $("#date").datepicker({ dateFormat: 'yy/mm/dd'});
});

function goBack() {
    window.history.back();
}