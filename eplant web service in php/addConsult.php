<?php 
	error_reporting(0);
	include_once("connect.php");
	
	//$_GET['temp']
         	$desc=$_GET['d'];
		
			$stmt = $dbh->query(" INSERT INTO photodb (disc) VALUES('$desc')");
			$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
			echo "{\"item\":";   
			echo json_encode($data);
			echo "}";
		
?>
