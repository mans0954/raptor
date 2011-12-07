<!--

    Copyright (C) 2010 Cardiff University, Wales <smartp@cf.ac.uk>

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>RaptorWeb Login</title>

</head>
<body onload='document.f.j_username.focus();'>
	<div id="content">
		<div class="themeHeader">

			<div class="banner">

				<div id="logoContainer">
					<center>					
						<a href="http://iam.cf.ac.uk/trac/RAPTOR"><img
							src="<%=request.getContextPath()%>/templates/raptor/images/simple-logo.png"
							alt="Raptor" border="0" />
						</a>
					</center>

				</div>

			</div>

		</div>
		<div id="nav">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td align="right">
						<table border="0" cellspacing="0" cellpadding="5">

							<tr>
								<td width="10"></td>
							</tr>

						</table></td>
				</tr>
			</table>
		</div>
		<div class="floatLeft">
			<br />

			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"></td>
					<td width="80%" align="center" valign="top">
						<div id="content" class="clearfix spring">

							<h3>Please login with your user name and password</h3>
							<form name='f' action='/raptor/j_spring_security_check'
								method="post">
								<table>
									<tr>
										<td>User:</td>
										<td><input type='text' name='j_username' value='' />
										</td>
									</tr>
									<tr>
										<td>Password:</td>
										<td><input type='password' name='j_password' />
										</td>
									</tr>
									<tr>
										<td colspan='2' align="center"><input name="submit"
											type="submit" value="Log in" />
										</td>
									</tr>
								</table>								
							</form>
							<c:if test="${not empty param.login_error and param.login_error eq 1}"> <div class="error"><p style="color:red">Invalid Username or Password</p></div></c:if>
						</div></td>
					<td width="10%"></td>

				</tr>
			</table>
			<br />
		</div>

	</div>


</body>
</html>
