<?php 
	error_reporting(0);
	include_once("connect.php");
	
	
	try
	{
		if(isset($_GET['username']))
		{
			$username=$_GET['username'];
			$stmt = $dbh->query("SELECT password FROM login_table WHERE username='$username' " );
					$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
					echo "{\"item\":";   
					echo json_encode($data);
					echo "}";
		}
		else if(isset($_GET['format']))
		{
		
		}
	}
	catch(Exception $e){die('Erreur:' .$e->getMessage());}
?>
	

	