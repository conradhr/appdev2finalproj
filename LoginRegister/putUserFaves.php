<?php
	require "DataBase.php";
	$db = new DataBase();
     if (isset($_POST['username']) && isset($_POST['car_id'])) {
          if ($db->dbConnect()) {
    	          if($db->putUserFave($_POST['username'], $_POST['car_id'])){
                    echo "Success";
               } else echo "Fail";
          }  else echo "Error: Database connection";   		
	} else echo "All fields are required";
?>
