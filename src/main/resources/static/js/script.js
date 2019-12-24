// FUNÇOES AJAX
function openQueueElementModal(id){
	$('.toast').toast('show');
	$.ajax({
		url:"/recepcao/listqueue/"+id,
		success: function(data){
			$("#modalQueueElementView").html(data);
			$('.toast').toast('hide');
			$("#modalQueueElement").modal();
		}
	});
}

function openViewQueueElementModal(id){
	$('.toast').toast('show');
	$.ajax({
		url:"/recepcao/viewQueueElement/"+id,
		success: function(data){
			$("#modalViewQueueElement").html(data);
			$('.toast').toast('hide');
			$("#modalQueueElement").modal('hide');
			$("#modal3").modal();
		}
	});
}

function deleteQueueElement(id){
	$.ajax({
		url:"/recepcao/queueElement/cancel?id="+id,
		success: function(data){
			console.log(status);
//			alert('Elemento removido com Sucesso');
//			location.reload();
			window.location.href = '/recepcao';
			$('.toast').toast('hide');
		}
	});
}

function saveQueueElement(id){
	$.ajax({
		url:"/recepcao/queueElement/confirm?id="+id,
		success: function(data){
//			alert('Elemento Salvo com Sucesso');
//			location.reload();
			window.location.href = '/recepcao';
			$('.toast').toast('hide');
		}
	});
}

// FIM FUNÇOES AJAX

function onClickCheckbox(){
	var phoneCheck = document.getElementById("phoneCheck");
	var clientPhone = document.getElementById("clientPhone");
	var randomNumber;
	
	if(phoneCheck.checked) {
		clientPhone.classList.add("d-none");
		randomNumber = "NI" + Math.floor(Math.random() * 10000000000) + 1;
		clientPhone.value = randomNumber;
		
		document.getElementById("clientName").classList.add("col-12");
	}
	else{
		clientPhone.value = "";
		clientPhone.classList.remove("d-none");
		document.getElementById("clientName").classList.remove("col-12");
	}
}

function showSide (){
	document.getElementById("mysidebar").style.right = "0";
	document.getElementById("mysidebar").style.display = "block";
	document.getElementById("restante").style.display = "block";
	document.getElementById("mysidebar").classList.add("show");
}

function hideSide (){
	document.getElementById("mysidebar").classList.remove("show");
	document.getElementById("collapseExample").classList.remove("show");
	document.getElementById("mysidebar").style.right = "-250";
	document.getElementById("mysidebar").style.display = "none";
	document.getElementById("restante").style.display = "none";
}

function showElements(id, peq){
	var aux = peq + 3;
	for(aux; aux>3; aux--){
		var aux2 = "row" + id + aux;
		console.log(aux2);
		document.getElementById(aux2).classList.remove("d-none");
	}
	document.getElementById(id+"arrowDown").classList.add("d-none");
	document.getElementById(id+"arrowUp").classList.remove("d-none");
}

function hideElements(id, peq){
	var aux = peq + 3;
	for(aux; aux>3; aux--){
		var aux2 = "row" + id + aux;
		console.log(aux2);
		document.getElementById(aux2).classList.add("d-none");
	}
	document.getElementById(id+"arrowUp").classList.add("d-none");
	document.getElementById(id+"arrowDown").classList.remove("d-none");
}

function showPhoneNumber(id) {
	var clientPhoneNumber = document.getElementById("number" + id).innerHTML;
	var substringNumber = clientPhoneNumber.substring(0,2);
	
	document.getElementById("name" + id).classList.add("d-none");

	if(substringNumber == "NI") {
		document.getElementById("number" + id).innerHTML = "<i class=\"fas fa-phone-slash\"></i>"
	}
	
	document.getElementById("number" + id).classList.remove("d-none");
}

function showName(id) {
	document.getElementById("name" + id).classList.remove("d-none");
	document.getElementById("number" + id).classList.add("d-none");
}

function changeImg(value) {
	if(value == 'Preferencial'){
		document.getElementById("img").src="/img/volunteer.png";		
	}
	if(value == 'Convencional'){
		document.getElementById("img").src="/img/line.png";		
	}
}

function confirmDelete(id, tipo) {
    console.log("entrou aqui!1!");
	if(tipo == "modal"){
		console.log("modal",id);
	}else{
		console.log("Outro",id);
	}    
}

if(document.getElementById("clientPage")){
	var timer = setInterval(function() {
	  window.location.reload(); 
	}, 15000);
}

if(document.getElementById("receptionPage")){
	var timer = setInterval(function() {
	  window.location.reload(); 
	}, 15000);
}

function stopReload(){
	clearInterval( timer );
	setInterval(function() {
		  window.location.reload(); 
		}, 30000);
}


//FUNÇOES JQUERY
$(document).ready(function() {
	
	// Fim Page Relatórios Admin //
	if(document.getElementById("adminRelPage")){
		console.log('Entrou na relAdmin');
	}
	// Fim Page Relatórios Admin //
	
	// Page Admin User//
	if(document.getElementById("userPage")){
		console.log('Entrou na userPage');
		$(".btnEditUser").click(function(event) {
			$('.toast').toast('show');
			event.preventDefault();
			
			var href1 = $(this).attr('href');	
			$.get(href1, function(user, status) {
				console.log(user);
				$('#editNameUser').val(user.name);
				$('#editPhoneNumberUser').val(user.phoneNumber);
				$('#editStatusUser').val(user.status);
				$('#editIdUser').val(user.id);
				$('#editUsernameUser').val(user.username);
				$('#editPasswordUser').val(user.password);
				
				let roles = user.roles.map(e=>e.name).join(', ');
				$('#editRolesUser').val(roles);
				
				if(roles.indexOf('ROLE_ADMIN')>=0){
					$('option[value=ADMIN]').prop('selected', true);
				}else if(roles.indexOf('ROLE_USER')>=0){
					$('option[value=USER]').prop('selected', true);
				}else{
					alert('Deu ruim no roles', roles);
				}	
				

				$('#editName').val(user.name);
				$('#editPhoneNumber').val(user.phoneNumber);
				$('#editStatus').val(user.status);
				$('#editRoles').val(user.roles.map(e=>e.name).join(', '));
				$('#editUserModal').modal();
				$('.toast').toast('hide');
				$("#editUserModal").modal();

				
				//Select do STATUS quando abre
				let statusActive = user.status==1?'option[value=ON]':'option[value=OFF]';
				$(statusActive).prop('selected', true);
				
				if(statusActive=='option[value=ON]'){
					document.getElementById("selStatus").classList.remove("bg-danger");
					document.getElementById("selStatus").classList.add("bg-success");
				}else{
					document.getElementById("selStatus").classList.remove("bg-success");
					document.getElementById("selStatus").classList.add("bg-danger");
				}
				
				
				//Select do STATUS quando clica
				$('#selStatus').on('change', function() {
					if(this.value=='ON'){
						$('#editStatusUser').val(1);
						document.getElementById("selStatus").classList.remove("bg-danger");
						document.getElementById("selStatus").classList.add("bg-success");
					}else{
						$('#editStatusUser').val(0);
						document.getElementById("selStatus").classList.remove("bg-success");
						document.getElementById("selStatus").classList.add("bg-danger");
					}
				});
			});
		});
		
		$(".btnDeleteUser").click(function(event) {
			$('.toast').toast('show');
			event.preventDefault();
			
			var href2 = $(this).attr('href');	
			$.get(href2, function(user, status) {
				console.log('USER DEL',user);
				$('#delNameUser').val(user.name);
				$('#delPhoneNumberUser').val(user.phoneNumber);
				$('#delStatusUser').val(user.status);
				$('#delIdUser').val(user.id);
				$('#delUsernameUser').val(user.username);
				$('#delPasswordUser').val(user.password);
				$('#editRolesUser').val(user.roles);
				
				$('.toast').toast('hide');
				$("#deleteUserModal").modal();
				
			});
		});
	}
	// Fim Page Admin User //
	
	// Inicio Page Recepcao //
	if(document.getElementById("receptionPage")){
		console.log('Entrou na recepcao');
		console.log($('#locales').val());
		$('.count').val(1);
		
		//Aumentar Contador Nova Fila	
		$(document).on('click', '.plus', function() {
			$('.count').val(parseInt($('.count').val()) + 1);
		});
	
		//Diminuir Contador Nova Fila
		$(document).on('click', '.minus', function() {
			$('.count').val(parseInt($('.count').val()) - 1);
			if ($('.count').val() == 0) {
				$('.count').val(1);
			}
		});
		
		$(".btnDeleteQE").click(function(event) {
//			event.preventDefault();
			var href = $(this).attr('href');	
			$.get(href, function(id, status) {
//				document.getElementById("asdfasd").innerText = id;
				$("#btnDelQE").click(function() {
					$('.toast').toast('show');
					deleteQueueElement(id);
				});
			});
		});
		
		$(".btnEditUser").click(function(event) {
			event.preventDefault();
			var href = $(this).attr('href');	
			$.get(href, function(id, status) {
				$("#SaveModal").modal();
				$("#btnSaveQE").click(function() {
					$('.toast').toast('show');
					saveQueueElement(id);
				});
			});
		});
	}
	// Fim Page Recepcao //
	
	// Inicio Admin/Fila //
	if(document.getElementById("filaPage")){
		//Aumentar Contador Nova Fila	
		$(document).on('click', '.plus', function() {
			$('.count').val(parseInt($('.count').val()) + 1);
		});
	
		//Diminuir Contador Nova Fila
		$(document).on('click', '.minus', function() {
			$('.count').val(parseInt($('.count').val()) - 1);
			if ($('.count').val() == 0) {
				$('.count').val(1);
			}
		});
	
		//Abrir Modal Edit Layout Fila
		$(".modal-btn").click(function(event) {
			$('.toast').toast('show');
			document.getElementsByClassName("addPerson a")[0].classList.add("d-none");
			document.getElementsByClassName("addPerson b")[0].classList.remove("d-none");
			document.getElementsByClassName("addPerson c")[0].classList.remove("d-none");
			
			var href = $(this).attr('href');	
			$.get(href, function(queue, status) {
				$('#chairsNumberOnTableEdit').val(queue.chairsNumberOnTable);
				$('#chairsEdit').val(queue.id);
				$('#chairsUpdate').val(queue.id);
				$('.toast').toast('hide');
				$("#modalNewQueue").modal();
			});
		});
		
		//Abrir Modal Novo Layout Fila
		$(".btn-NewModal").click(function(event) {
			document.getElementsByClassName("addPerson a")[0].classList.remove("d-none");
			document.getElementsByClassName("addPerson b")[0].classList.add("d-none");
			document.getElementsByClassName("addPerson c")[0].classList.add("d-none");
			
			$('#chairsNumberOnTableNew').val('0');
			$('#chairsNew').val('0');
			$("#modalNewQueue").modal();
		});
		
		$(".btn-delModal").click(function(event) {
			$("#delModal").modal();
		});
	}
	// Fim Admin/Fila //

	//Adiciona Footer
			if(document.getElementById("homePage") || 
			document.getElementById("clientPage")
			){
				document.getElementById("footer").classList.remove("d-none");
				console.log(document.getElementById("footer"));
			}
	
	// Inicio Page AddUser //
	if(document.getElementById("registrationPage") || document.getElementById("resetPassword")){
	
		var password = document.getElementById("psw");
		var confirmPassword = document.getElementById("psw2");
		var letter = document.getElementById("letter");
		var capital = document.getElementById("capital");
		var number = document.getElementById("number");
		var length = document.getElementById("length");
		var usrname = document.getElementById("usrname");
		
		//function to validate username lenght
		if(document.getElementById("registrationPage")){
		usrname.onkeyup = function(){
			if(usrname.value.length >= 4 && usrname.value.length <= 15) {
				 userlength.classList.remove("invalid");
				 userlength.classList.add("valid");
				  } else {
					 userlength.classList.remove("valid");
					 userlength.classList.add("invalid");
				  }
		}
	}
		
		password.onkeyup = function() {
		  // Validate lowercase letters
			
		if (password.value == confirmPassword.value && password.value!="") {
			document.getElementById('submit').disabled = false;
			document.getElementById('submit').classList.remove("btn-dark");
			document.getElementById('submit').classList.add("btn-primary");
			} else {
				document.getElementById('submit').classList.remove("btn-primary");
			    document.getElementById('submit').classList.add("btn-dark");
			    document.getElementById('submit').disabled = true;
			}
			
		  var lowerCaseLetters = /[a-z]/g;
		  if(password.value.match(lowerCaseLetters)) {  
		    letter.classList.remove("invalid");
		    letter.classList.add("valid");
		  } else {
		    letter.classList.remove("valid");
		    letter.classList.add("invalid");
		  }
		  
		  // Validate capital letters
		  var upperCaseLetters = /[A-Z]/g;
		  if(password.value.match(upperCaseLetters)) {  
		    capital.classList.remove("invalid");
		    capital.classList.add("valid");
		  } else {
		    capital.classList.remove("valid");
		    capital.classList.add("invalid");
		  }

		  // Validate numbers
		  var numbers = /[0-9]/g;
		  if(password.value.match(numbers)) {  
		    number.classList.remove("invalid");
		    number.classList.add("valid");
		  } else {
		    number.classList.remove("valid");
		    number.classList.add("invalid");
		  }
		  
		  // Validate length
		  if(password.value.length >= 8 && password.value.length <= 30) {
		    length.classList.remove("invalid");
		    length.classList.add("valid");
		  } else {
		    length.classList.remove("valid");
		    length.classList.add("invalid");
		  }
		  
		}
		
		confirmPassword.onkeyup = function(){
		    if (password.value == confirmPassword.value && password.value!="") {
		        document.getElementById('submit').disabled = false;
		        document.getElementById('submit').classList.remove("btn-dark");
		        document.getElementById('submit').classList.add("btn-primary");
		    } else {
		    	document.getElementById('submit').classList.remove("btn-primary");
		        document.getElementById('submit').classList.add("btn-dark");
		        document.getElementById('submit').disabled = true;
		    }
		}
	}
	// FIM Page AddUser //
	
});
//FIM FUNÇOES JQUERY

