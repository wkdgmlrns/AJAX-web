<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<title>Insert title here</title>
<script type="text/javascript">
	var searchRequest = new XMLHttpRequest();
	var regsterRequest = new XMLHttpRequest();
	function searchFunction() {
		searchRequest.open("Post","./UserSearchSevlet?user_Name=" + encodeURIComponent(document.getElementById('user_Name').value), true);
		searchRequest.onreadystatechange = searchProcess;
		searchRequest.send(null);
	}
	function searchProcess() {
		var table = document.getElementById('ajaxTable');
		table.innerHTML = "";
		if (searchRequest.readyState == 4 && searchRequest.status == 200) {
			var object = eval('(' + searchRequest.responseText + ')');
			var result = object.result;
			for (var i = 0; i < result.length; i++) {
				var row = table.insertRow(0);
				for (var j = 0; j < result[i].length; j++) {
					var cell = row.insertCell(j);
					cell.innerHTML = result[i][j].value;
				}
			}
		}
	}
	function registerFunction() {
		regsterRequest.open("Post","./UserRegsterServlet?user_Name=" + encodeURIComponent(document.getElementById('regsterName').value) + 
				"&user_Age="+encodeURIComponent(document.getElementById('regsterName').value) +
				"&user_Gender=" + encodeURIComponent($('input[name=regsterGender]:checked').val) +
				"&user_Email=" + encodeURIComponent(document.getElementById('regsterEmail').value), true);
				regsterRequest.onreadystatechange = regsterProcess;
				regsterRequest.send(null);
	}
	function regsterProcess(){
		if(searchRequest.readyState == 4 && searchRequest.status == 200){
			var result = regsterRequest.responseText;
			if(result ==-1){
				alert("등록실패");
			}
			else{
				var user_Name = document.getElementById('user_Name');
				var registerName = document.getElementById('regsterName');
				var regsterAge = document.getElementById('regsterAge');
				var regsterEmail = document.getElementById('regsterEmail');
				registerName.value="";
				regsterAge.value="";
				regsterEmail.value="";
				user_Name.value="";
				searchFunction();
			}
		}
	}
	window.onload = function() {
		searchFunction();
	}
</script>
</head>
<body>
	<br>
	<div class="container">
		<div class="form-group row pull-right">
			<div class="col-xs-8">
				<input type="form-control" type="text" size="20"
					onkeyup="searchFunction()" id="user_Name">
			</div>
			<div class="col-xs-2">
				<button class="btn btn-primary" type="button"
					onclick="searchFunction();">검색</button>
			</div>
		</div>
		<table class="table"
			style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th style="background-color: #fafafa; text-align: center">이름</th>
					<th style="background-color: #fafafa; text-align: center">나이</th>
					<th style="background-color: #fafafa; text-align: center">성별</th>
					<th style="background-color: #fafafa; text-align: center">이메일</th>
				</tr>
			</thead>
			<tbody id="ajaxTable">
				<tr>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="container">
		<table class="table"
			style="text-align: center; border: 1px solid #dddddd;">
			<thead>
				<tr>
					<th colspan="2"
						style="background-color: #fafafa; text-align: center">회원등록양식</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="background-color: #fafafa; text-align: center;">이름</td>
					<td><input class="form-control" type="text" id="regsterName"
						size="20"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align: center;">나이</td>
					<td><input class="form-control" type="text" id="regsterAge"
						size="20"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align: center;">성별</td>
					<td>
						<div class="form-group"
							style="text-align: center; margin: 0 auto;">
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary active"> <input
									type="radio" name="regsterGender" autocomplete="off" value="남자"
									checked>남자
								</label> <label class="btn btn-primary"> <input type="radio"
									name="regsterGender" autocomplete="off" value="여자" checked>여자
								</label>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align: center;">이메일</td>
					<td><input class="form-control" type="email" id="regsterEmail"
						size="20"></td>
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary pull-right"
							onclick="registerFunction();" type="button">등록</button></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>