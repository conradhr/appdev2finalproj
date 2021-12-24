<?php
	require "DataBase.php";
	$db = new DataBase();
     if (isset($_POST['username']) && isset($_POST['car_id']) && isset($_POST['pickuplocation']) && isset($_POST['dropofflocation']) && isset($_POST['pickupdate']) && isset($_POST['dropoffdate']) && isset($_POST['price'])) {
          if ($db->dbConnect()) {
    	          if($db->putUserRes($_POST['username'], $_POST['car_id'], $_POST['pickuplocation'], $_POST['dropofflocation'], $_POST['pickupdate'], $_POST['dropoffdate'], $_POST['price'])){
                    echo "Success";
               } else echo "Fail";
          }  else echo "Error: Database connection";   		
	} else echo "All fields are required";
?>
