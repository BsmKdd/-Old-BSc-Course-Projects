<link href="bootstrap-4.3.1-dist/css/bootstrap.css" rel="stylesheet" />
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet" />
<link href="signup.css" rel="stylesheet"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<title>Login</title>
</head>
<body>
	<div class="container">
		<div id="loginDiv"  class="shadow text-center mx-auto mt-sm-5 mt-1 p-5 col-lg-6 col-md-8 col-sm-10 col-12">	
				<h3 class="font-weight-bold mb-5">Log In</h3>
			<form action="loginAction.php" method="POST">
				<div class="row form-group">
					<div class="col-12"><input name="email" type="email" class="form-control"   placeholder="Enter email" required></div>
				</div>
				<div class="row form-group">
					<div class="col-12"><input name="password" type="password" class="form-control"  placeholder="Password" required></div>
				</div>
				<button type="submit" class="btn btn-info col-12">Log In</button>
			</form>

			<hr>
			<p>Don't have an account? <a href="signup.html">Sign Up</a></p>
		</div>
	</div>
</body>
</html>
