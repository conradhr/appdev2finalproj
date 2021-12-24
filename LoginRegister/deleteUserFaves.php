<?php
	require "DataBase.php";
	$db = new DataBase();
     if (isset($_POST['username']) && isset($_POST['car_id'])) {
          if ($db->dbConnect()) {
    	          if($db->deleteUserFave($_POST['username'], $_POST['car_id'])){
                    echo "Success";
               } echo "Fail";
          }  else echo "Error: Database connection";   		
	} else echo "All fields are required";
?>
