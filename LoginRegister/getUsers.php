<?php
	require "DataBase.php";
	$db = new DataBase();
	if ($db->dbConnect()) {
    	$var = $db->getAll("users");
		foreach($var as $value)
		{
     		echo $value["id"].",";
     		echo $value["fullname"].",";
     		echo $value["username"].",";
     		echo $value["password"].",";
     		echo $value["email"].",";
     		echo ($value["role"] == null) ? "user" : $value["role"];
       		echo "<br>";
		}	
	} else echo "Error: Database connection";
?>
