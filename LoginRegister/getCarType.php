<?php
	require "DataBase.php";
	$db = new DataBase();
    if (isset($_POST['type'])) {	
		if ($db->dbConnect()) {
			$var = $db->getCarType($_POST['type']);
			foreach($var as $value)
			{
		 		echo $value["car_id"].",";
		 		echo $value["car_brand"].",";
		 		echo $value["car_model"].",";     		
		 		echo $value["car_color"].",";
		 		echo $value["car_year"].",";
		 		echo $value["type"].",";
		 		echo $value["description"].",";
				echo ($value["filename"] == null) ? "null," : $value["filename"].",";
		 		echo ($value["availability"]) ? "true" : "false";
		 		echo "<br>";
			}	
		} else echo "Error: Database connection";
	} else echo "All fields are required";
?>
