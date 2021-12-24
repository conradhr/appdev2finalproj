<?php
	require "DataBase.php";
	$db = new DataBase();
     if (isset($_POST['car_id'])) {     
          if ($db->dbConnect()) {
    	          $var = $db->getCar($_POST['car_id']);
               echo $var["car_id"].",";
               echo $var["car_brand"].",";
               echo $var["car_model"].",";             
               echo $var["car_color"].",";
               echo $var["car_year"].",";
               echo $var["type"].",";
               echo $var["description"].",";
               echo ($var["filename"] == null) ? "null," : $var["filename"].",";
               echo ($var["availability"]) ? "true" : "false";
               echo "<br>";
          }  else echo "Error: Database connection";   		
	} else echo "All fields are required";
?>
