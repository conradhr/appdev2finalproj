<?php
	require "DataBase.php";
	$db = new DataBase();
	if ($db->dbConnect()) {
    	$var = $db->getAll("locations");
		foreach($var as $value)
		{
     		echo json_encode($value);
     		echo "<br>";
		}	
	} else echo "Error: Database connection";
?>
