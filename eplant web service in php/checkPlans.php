<?php 
	error_reporting(0);
	include_once("connect.php");
	
	$stmt = $dbh->query("SELECT * FROM plans" );
			$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
			echo "{\"item\":";   
			echo json_encode($data);
			echo "}";
	
	/*try
	{
		if(isset($_GET['format']) && isset($_GET['passCategorie']) )
		{
			$formatVar=$_GET['format'];
			$passCategorieVar=$_GET['passCategorie'];

			$stmt = $dbh->query("SELECT categorie,img_sources,company_name,tel,wilaya,commune,longitude,latitude,id FROM $formatVar Where  categorie='$passCategorieVar'" );
			$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
			echo "{\"item\":";   
			echo json_encode($data);
			echo "}";
		}
		else if(isset($_GET['format']))
		{
			$formatVar=$_GET['format'];
			$stmt = $dbh->query("SELECT categorie,img_sources,latitude,longitude,tel FROM $formatVar GROUP BY categorie" );
			$data = $stmt->fetchAll(PDO::FETCH_ASSOC);
			echo "{\"item\":";   
			echo json_encode($data);
			echo "}";
		}
	}
	catch(Exception $e){die('Erreur:' .$e->getMessage());}*/
?>
	

	