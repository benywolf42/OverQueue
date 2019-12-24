function teste(){
	var langOption = document.getElementById("locales").innerText;
	var selectedOption = langOption == "English" ? 'en' : 'pt_BR';
	var teste = window.location.href;
	
//	alert(teste);
	
	if(document.getElementById("clientPage")){
		console.log('Entrou aqui1');
		window.location.replace(window.location.href.split('&')[0] + '&lang=' + selectedOption);
	}else{
		console.log('Entrou aqui2');
		window.location.replace('?lang=' + selectedOption);
	}
	
}
//$(document).ready(function() {
//	$("#locales").change(function() {
//		var selectedOption = $('#locales').val();
//		if (selectedOption != '') {
//			console.log('Entrou aqui');
//			window.location.replace('?lang=' + selectedOption);
//		}
//		if(document.getElementById("clientPage")){
//			window.location.replace(window.location.href + '&lang=' + selectedOption);
//		}
//	});
//});