window.onload = function() {
	loadNavbar();
	loadHomePageView();
}

function loadNavbar() {
	// Use AJAX to grab the navbar.html fragment
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("navbar").innerHTML = xhr.responseText;
			// these listeners are the same for employees and managers
			document.getElementById('logout').addEventListener('click', logout,
					false);
			document.getElementById('homepage').addEventListener('click',
					loadHomePageView, false);
			var srElement = document.getElementById('sr');
			if (srElement != null) {
				// if there is an sr element, then this is an employee, so add
				// the employee listeners
				srElement.addEventListener('click', loadSrView, false);
				document.getElementById('pending').addEventListener('click',
						loadPendingView, false);
				document.getElementById('resolved').addEventListener('click',
						loadResolvedView, false);
				document.getElementById('update').addEventListener('click',
						loadEmployeeUpdateView, false);
			} else {
				// add manager listeners
				document.getElementById('pending').addEventListener('click',
						loadAllPendingView, false);
				document.getElementById('resolved').addEventListener('click',
						loadAllResolvedView, false);
				document.getElementById('employees').addEventListener('click',
						loadAllEmployeesView, false);
			}
		}
	}
	xhr.open("GET", "navbar", true);
	xhr.send();
}

function logout() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			window.location.href = "login.html";
		}
	}
	xhr.open("GET", "logout", true);
	xhr.send();
}

function loadHomePageView() {
	// Use AJAX to grab the homepage view fragment based on role
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			getReimbursementUserInfo();
		}
	}
	xhr.open("GET", "viewHomepage", true);
	xhr.send();
}

function getReimbursementUserInfo() {
	// Use AJAX to grab the JSON from server with reimbursement user data
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var userDTO = JSON.parse(xhr.responseText);
			document.getElementById('ersUser').innerHTML = userDTO.firstName
					+ " " + userDTO.lastName;
			//document.getElementById('email').innerHTML = userDTO.email;
		}
	}
	xhr.open("GET", "viewUserInfo", true);
	xhr.send();
}

function loadSrView() {
	// Use AJAX to grab the submit request page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			document.getElementById('submit').addEventListener('click',
					processNewReimbursement, false);
			loadSrData();
		}
	}
	xhr.open("GET", "viewSubmitpage", true);
	xhr.send();
}

function loadSrData() {
	// Use AJAX to grab the JSON from server with list of reimbursement types
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var reimburseTypes = JSON.parse(xhr.responseText);
			var typeListElement = document.getElementById('reimbursementtypes');
			for (i = 0; i < reimburseTypes.length; i++) {
				var option = document.createElement('option');
				option.text = reimburseTypes[i]["reimbursementType"];
				typeListElement.add(option);
			}
		}
	}
	xhr.open("GET", "viewReimbursementTypes", true);
	xhr.send();
}

function loadPendingView() {
	// Use AJAX to grab the pending requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			loadPendingData();
		}
	}
	xhr.open("GET", "viewPendingpage", true);
	xhr.send();
}

function loadPendingData() {
	// Use AJAX to grab the JSON from server with list of pending requests
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var reimburseRequests = JSON.parse(xhr.responseText);
			var tableElement = document.getElementById('pendingTable');
			document.getElementById('name').innerHTML = reimburseRequests[0]["employeeName"];
			for (i = 0; i < reimburseRequests.length; i++) {
				// create the row
				var row = document.createElement('tr');
				// create the cells
				var tdId = document.createElement('td');
				var tdEmployeeName = document.createElement('td');
				var tdAmount = document.createElement('td');
				var tdType = document.createElement('td');
				var tdSubmitted = document.createElement('td');
				var tdDescription = document.createElement('td');
				var tdReceipt = document.createElement('td');
				if (reimburseRequests[i]["receipt"] != null) {
					var img = document.createElement('button');
					img.setAttribute('id',
							reimburseRequests[i]["reimbursementId"]);
					img.innerHTML = 'View Receipt';
					img.addEventListener('click', getReceipt, false);
					img.setAttribute('class', 'btn btn-info');
					tdReceipt.appendChild(img);
				}
				// give values to the cells
				tdId.innerHTML = reimburseRequests[i]["reimbursementId"];
				tdEmployeeName.innerHTML = reimburseRequests[i]["employeeName"];
				tdAmount.innerHTML = reimburseRequests[i]["amount"];
				tdType.innerHTML = reimburseRequests[i]["reimbursementType"];
				tdSubmitted.innerHTML = reimburseRequests[i]["submitted"];
				tdDescription.innerHTML = reimburseRequests[i]["description"];
				// add the cells to the row
				row.appendChild(tdId);
				row.appendChild(tdEmployeeName);
				row.appendChild(tdAmount);
				row.appendChild(tdType);
				row.appendChild(tdSubmitted);
				row.appendChild(tdDescription);
				row.appendChild(tdReceipt);
				// add the row to the table
				tableElement.appendChild(row);
			}
		}
	}
	xhr.open("GET", "viewPendingRequests", true);
	xhr.send();
}

function loadAllPendingView() {
	// Use AJAX to grab the all pending requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			loadAllPendingData();
		}
	}
	xhr.open("GET", "viewAllPendingpage", true);
	xhr.send();
}

function loadAllPendingData() {
	// Use AJAX to grab the JSON from server with list of all pending requests
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var reimburseRequests = JSON.parse(xhr.responseText);
			var tableElement = document.getElementById('allpendingTable');
			for (var i = 0; i < reimburseRequests.length; i++) {
				// create the row
				var row = document.createElement('tr');
				// create the cells
				var tdId = document.createElement('td');
				var tdName = document.createElement('td');
				var tdAmount = document.createElement('td');
				var tdType = document.createElement('td');
				var tdSubmitted = document.createElement('td');
				var tdDescription = document.createElement('td');
				var tdResolve = document.createElement('td');
				var buttonApprove = document.createElement('button');
				var buttonDeny = document.createElement('button');
				var tdReceipt = document.createElement('td');
				if (reimburseRequests[i]["receipt"] != null) {
					var img = document.createElement('button');
					img.setAttribute('id',
							reimburseRequests[i]["reimbursementId"]);
					img.innerHTML = 'View Receipt';
					img.addEventListener('click', getReceipt, false);
					img.setAttribute('class', 'btn btn-info');
					tdReceipt.appendChild(img);
				}
				// give values to the cells
				tdId.innerHTML = reimburseRequests[i]["reimbursementId"];
				tdName.innerHTML = reimburseRequests[i]["employeeName"];
				tdAmount.innerHTML = reimburseRequests[i]["amount"];
				tdType.innerHTML = reimburseRequests[i]["reimbursementType"];
				tdSubmitted.innerHTML = reimburseRequests[i]["submitted"];
				tdDescription.innerHTML = reimburseRequests[i]["description"];
				buttonApprove.innerHTML = "Approve";
				buttonDeny.innerHTML = "Deny";
				buttonApprove.addEventListener('click', approveRequest, false);
				buttonApprove.setAttribute('id', 'A'
						+ reimburseRequests[i]["reimbursementId"]);
				buttonApprove.setAttribute('class', 'btn btn-success');
				buttonDeny.addEventListener('click', denyRequest, false);
				buttonDeny.setAttribute('id', 'D'
						+ reimburseRequests[i]["reimbursementId"]);
				buttonDeny.setAttribute('class', 'btn btn-danger');
				tdResolve.appendChild(buttonApprove);
				tdResolve.appendChild(buttonDeny);
				// add the cells to the row
				row.appendChild(tdId);
				row.appendChild(tdName);
				row.appendChild(tdAmount);
				row.appendChild(tdType);
				row.appendChild(tdSubmitted);
				row.appendChild(tdDescription);
				row.appendChild(tdReceipt);
				row.appendChild(tdResolve);
				// add the row to the table
				tableElement.appendChild(row);
			}
		}
	}
	xhr.open("GET", "viewAllPendingRequests", true);
	xhr.send();
}

function loadAllEmployeesView() {
	// Use AJAX to grab the all employees page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			loadAllEmployeesData();
		}
	}
	xhr.open("GET", "viewAllEmployeespage", true);
	xhr.send();
}

function loadAllEmployeesData() {
	// Use AJAX to grab the JSON from server with list of all employees
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var allEmployees = JSON.parse(xhr.responseText);
			var tableElement = document.getElementById('allemployeesTable');
			for (i = 0; i < allEmployees.length; i++) {
				// create the row
				var row = document.createElement('tr');
				// create the cells
				var tdId = document.createElement('td');
				var tdFirstName = document.createElement('td');
				var tdLastName = document.createElement('td');
				var tdEmail = document.createElement('td');
				// give values to the cells
				tdId.innerHTML = allEmployees[i]["ers_id"];
				tdFirstName.innerHTML = allEmployees[i]["ers_fn"];
				tdLastName.innerHTML = allEmployees[i]["ers_ln"];
				tdEmail.innerHTML = allEmployees[i]["ers_email"];
				// add the cells to the row
				row.appendChild(tdId);
				row.appendChild(tdFirstName);
				row.appendChild(tdLastName);
				row.appendChild(tdEmail);
				// allow clicking anywhere on row to bring up employee
				row.addEventListener('click', getEmployee, false);
				row.setAttribute('id', allEmployees[i]["userId"]);
				// add the row to the table
				tableElement.appendChild(row);
			}
		}
	}
	xhr.open("GET", "viewAllEmployees", true);
	xhr.send();
}

function processNewReimbursement() {
	var amountRequested = document.getElementById('amount');
	var rTypes = document.getElementById("reimbursementtypes");
	var selectedType = rTypes.options[rTypes.selectedIndex];
	var requestDesc = document.getElementById('description');
	var file = document.getElementById("receipt").files[0];
	var reader = new FileReader();

	reader.onload = function() {

		var imgData = reader.result;
		var reimbursement = {
			amount : amountRequested.value,
			reimbursementType : selectedType.value,
			description : requestDesc.value,
			receipt : imgData
		}
		reimbursement = JSON.stringify(reimbursement);
		var xhr = new XMLHttpRequest();
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				amountRequested.value = '';
				requestDesc.value = '';
			}
		}
		xhr.open("POST", "processNewReimburse", true);
		xhr.setRequestHeader("key", reimbursement); // not required
		xhr.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xhr.send(reimbursement);
	};
	if (file) {
		reader.readAsDataURL(file);
	}
}

function loadEmployeeUpdateView() {
	// Use AJAX to grab the all employees page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			loadEmployeeUpdateData();
		}
	}
	xhr.open("GET", "viewUpdateEmployeepage", true);
	xhr.send();
}

function loadEmployeeUpdateData() {
	// Use AJAX to grab the JSON from server with reimbursement user data
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var userDTO = JSON.parse(xhr.responseText);
			document.getElementById('firstname').value = userDTO.firstName;
			document.getElementById('lastname').value = userDTO.lastName;
			document.getElementById('username').value = userDTO.userName;
			document.getElementById('email').value = userDTO.email;
			document.getElementById('submit').addEventListener('click',
					updateEmployeeData, false);
		}
	}
	xhr.open("GET", "viewUserInfo", true);
	xhr.send();
}

function updateEmployeeData() {
	var firstName = document.getElementById('firstname');
	var lastName = document.getElementById("lastname");
	var email = document.getElementById('email');
	var userName = document.getElementById('username');
	var password = document.getElementById('password');

	var userUpdate = {
		firstName : firstName.value,
		lastName : lastName.value,
		email : email.value,
		userName : userName.value,
		password : password.value
	}
	userUpdate = JSON.stringify(userUpdate);

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			loadHomePageView();
		}
	}
	xhr.open("POST", "updateEmployeeInfo", true);
	xhr.setRequestHeader("key", userUpdate); // not required
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(userUpdate);
}

function getEmployee() {
	var employeeID = this.id;
	// Use AJAX to grab the employee requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			getEmployeeRequests(employeeID);
		}
	}
	xhr.open("GET", "viewPendingpage", true);
	xhr.send();
}

function getEmployeeWithId(employeeID) {
	// Use AJAX to grab the employee requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			getEmployeeRequests(employeeID);
		}
	}
	xhr.open("GET", "viewPendingpage", true);
	xhr.send();
}

function getEmployeeRequests(employeeID) {
	// Use AJAX to grab the JSON from server with list of pending requests
	var xhr = new XMLHttpRequest();
	var userUpdate = {
		userId : employeeID
	}
	userUpdate = JSON.stringify(userUpdate);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var reimburseRequests = JSON.parse(xhr.responseText);
			var tableElement = document.getElementById('pendingTable');
			document.getElementById('name').innerHTML = reimburseRequests[0]["employeeName"];
			for (i = 0; i < reimburseRequests.length; i++) {
				// create the row
				var row = document.createElement('tr');
				// create the cells
				var tdId = document.createElement('td');
				var tdAmount = document.createElement('td');
				var tdType = document.createElement('td');
				var tdSubmitted = document.createElement('td');
				var tdDescription = document.createElement('td');
				var tdResolve = document.createElement('td');
				var buttonApprove = document.createElement('button');
				var buttonDeny = document.createElement('button');
				// give values to the cells
				tdId.innerHTML = reimburseRequests[i]["reimbursementId"];
				tdAmount.innerHTML = reimburseRequests[i]["amount"];
				tdType.innerHTML = reimburseRequests[i]["reimbursementType"];
				tdSubmitted.innerHTML = reimburseRequests[i]["submitted"];
				tdDescription.innerHTML = reimburseRequests[i]["description"];
				buttonApprove.innerHTML = "Approve";
				buttonDeny.innerHTML = "Deny";
				buttonApprove.addEventListener('click', approveRequest, false);
				buttonApprove.setAttribute('id', employeeID + 'A'
						+ reimburseRequests[i]["reimbursementId"]);
				buttonApprove.setAttribute('class', 'btn btn-success');
				buttonDeny.addEventListener('click', denyRequest, false);
				buttonDeny.setAttribute('id', employeeID + 'D'
						+ reimburseRequests[i]["reimbursementId"]);
				buttonDeny.setAttribute('class', 'btn btn-danger');
				tdResolve.appendChild(buttonApprove);
				tdResolve.appendChild(buttonDeny);
				// add the cells to the row
				row.appendChild(tdId);
				row.appendChild(tdAmount);
				row.appendChild(tdType);
				row.appendChild(tdSubmitted);
				row.appendChild(tdDescription);
				row.appendChild(tdResolve);
				// add the row to the table
				tableElement.appendChild(row);
			}
		}
	}
	xhr.open("POST", "viewPendingRequests", true);
	xhr.setRequestHeader("key", userUpdate); // not required
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(userUpdate);
}

function approveRequest() {
	var reimbursementID = this.id;
	var start = reimbursementID.indexOf("A");
	var employeeID = 0;
	reimbursementID = this.id.substring(start + 1, this.id.length);
	var requestUpdate = {
		reimbursementId : reimbursementID
	}
	requestUpdate = JSON.stringify(requestUpdate);
	if (start > 1) {
		employeeID = parseInt(this.id.substring(0, start));
	}
	// Use AJAX to grab the employee requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			if (employeeID > 0) {
				getEmployeeWithId(employeeID);
			} else {
				loadAllPendingView();
			}
		}
	}
	xhr.open("POST", "approveRequest", true);
	xhr.setRequestHeader("key", requestUpdate); // not required
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(requestUpdate);
}

function denyRequest() {
	var reimbursementID = this.id;
	var start = reimbursementID.indexOf("D");
	var employeeID = 0;
	reimbursementID = this.id.substring(start + 1, this.id.length);
	var requestUpdate = {
		reimbursementId : reimbursementID
	}
	requestUpdate = JSON.stringify(requestUpdate);
	if (start > 1) {
		employeeID = parseInt(this.id.substring(0, start));
	}
	// Use AJAX to grab the employee requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			if (employeeID > 0) {
				getEmployeeWithId(employeeID);
			} else {
				loadAllPendingView();
			}
		}
	}
	xhr.open("POST", "denyRequest", true);
	xhr.setRequestHeader("key", requestUpdate); // not required
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(requestUpdate);
}

function loadResolvedView() {
	// Use AJAX to grab the resolved requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			loadResolvedData();
		}
	}
	xhr.open("GET", "viewResolvedpage", true);
	xhr.send();
}

function loadResolvedData() {
	// Use AJAX to grab the JSON from server with list of resolved requests
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var reimburseRequests = JSON.parse(xhr.responseText);
			var tableElement = document.getElementById('resolvedTable');
			document.getElementById('name').innerHTML = reimburseRequests[0]["employeeName"];
			for (i = 0; i < reimburseRequests.length; i++) {
				// create the row
				var row = document.createElement('tr');
				// create the cells
				var tdId = document.createElement('td');
				var tdAmount = document.createElement('td');
				var tdType = document.createElement('td');
				var tdSubmitted = document.createElement('td');
				var tdResolved = document.createElement('td');
				var tdManager = document.createElement('td');
				var tdResolution = document.createElement('td');
				var tdDescription = document.createElement('td');
				var tdReceipt = document.createElement('td');
				if (reimburseRequests[i]["receipt"] != null) {
					var img = document.createElement('button');
					img.setAttribute('id',
							reimburseRequests[i]["reimbursementId"]);
					img.innerHTML = 'View Receipt';
					img.addEventListener('click', getReceipt, false);
					img.setAttribute('class', 'btn btn-info');
					tdReceipt.appendChild(img);
				}
				// give values to the cells
				tdId.innerHTML = reimburseRequests[i]["reimbursementId"];
				tdAmount.innerHTML = reimburseRequests[i]["amount"];
				tdType.innerHTML = reimburseRequests[i]["reimbursementType"];
				tdSubmitted.innerHTML = reimburseRequests[i]["submitted"];
				tdResolved.innerHTML = reimburseRequests[i]["resolved"];
				tdManager.innerHTML = reimburseRequests[i]["managerName"];
				tdResolution.innerHTML = reimburseRequests[i]["status"];
				tdDescription.innerHTML = reimburseRequests[i]["description"];
				// add the cells to the row
				row.appendChild(tdId);
				row.appendChild(tdAmount);
				row.appendChild(tdType);
				row.appendChild(tdSubmitted);
				row.appendChild(tdResolved);
				row.appendChild(tdManager);
				row.appendChild(tdResolution);
				row.appendChild(tdDescription);
				row.appendChild(tdReceipt);
				// add the row to the table
				tableElement.appendChild(row);
			}
		}
	}
	xhr.open("GET", "viewResolvedRequests", true);
	xhr.send();
}

function loadAllResolvedView() {
	// Use AJAX to grab the resolved requests page
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById("view").innerHTML = xhr.responseText;
			loadAllResolvedData();
		}
	}
	xhr.open("GET", "viewAllResolvedpage", true);
	xhr.send();
}

function loadAllResolvedData() {
	// Use AJAX to grab the JSON from server with list of resolved requests
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var reimburseRequests = JSON.parse(xhr.responseText);
			var tableElement = document.getElementById('allressolvedTable');
			for (i = 0; i < reimburseRequests.length; i++) {
				// create the row
				var row = document.createElement('tr');
				// create the cells
				var tdId = document.createElement('td');
				var tdEmployee = document.createElement('td');
				var tdAmount = document.createElement('td');
				var tdType = document.createElement('td');
				var tdSubmitted = document.createElement('td');
				var tdResolved = document.createElement('td');
				var tdManager = document.createElement('td');
				var tdResolution = document.createElement('td');
				var tdDescription = document.createElement('td');
				var tdReceipt = document.createElement('td');
				if (reimburseRequests[i]["receipt"] != null) {
					var img = document.createElement('button');
					img.setAttribute('id',
							reimburseRequests[i]["reimbursementId"]);
					img.innerHTML = 'View Receipt';
					img.addEventListener('click', getReceipt, false);
					img.setAttribute('class', 'btn btn-info');
					tdReceipt.appendChild(img);
				}
				// give values to the cells
				tdId.innerHTML = reimburseRequests[i]["reimbursementId"];
				tdEmployee.innerHTML = reimburseRequests[i]["employeeName"];
				tdAmount.innerHTML = reimburseRequests[i]["amount"];
				tdType.innerHTML = reimburseRequests[i]["reimbursementType"];
				tdSubmitted.innerHTML = reimburseRequests[i]["submitted"];
				tdResolved.innerHTML = reimburseRequests[i]["resolved"];
				tdManager.innerHTML = reimburseRequests[i]["managerName"];
				tdResolution.innerHTML = reimburseRequests[i]["status"];
				tdDescription.innerHTML = reimburseRequests[i]["description"];
				// add the cells to the row
				row.appendChild(tdId);
				row.appendChild(tdEmployee);
				row.appendChild(tdAmount);
				row.appendChild(tdType);
				row.appendChild(tdSubmitted);
				row.appendChild(tdResolved);
				row.appendChild(tdManager);
				row.appendChild(tdResolution);
				row.appendChild(tdDescription);
				row.appendChild(tdReceipt);
				// add the row to the table
				tableElement.appendChild(row);
			}
		}
	}
	xhr.open("GET", "viewAllResolvedRequests", true);
	xhr.send();
}

function getReceipt() {
	var receiptID = this.id;
	window.open("viewReceipt?id=" + receiptID);
}
