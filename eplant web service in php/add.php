<?php 
	error_reporting(0);
	include_once("connect.php");
	
	//$_GET['temp']
         	$temp=$_GET['t'];
        	$gas=$_GET['g'];
         	$water=$_GET['w'];
         	$hum=$_GET['h'];
         	$light=$_GET['l'];
			
     		$stmt = $dbh->query("UPDATE plantdata  SET 
			                    temp=$temp,gas=$gas,water=$water,humidity=$hum,light=$light
								WHERE id='0' " );
			$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
			echo "{\"item\":";   
			echo json_encode($data);
			echo "}";
			
			$stmt = $dbh->query(" INSERT INTO plantstat (temp,humidity,light,water,gas) VALUES ($temp,$hum,$light,$water,$gas) " );
								
								
								 
			$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
			echo "{\"item\":";   
			echo json_encode($data);
			echo "}";
			/*,
								
								
								*/
		
?>
