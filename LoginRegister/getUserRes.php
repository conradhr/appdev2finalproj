<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username'])) {
    if ($db->dbConnect()) {
        $var = $db->getUserRes($_POST['username']);
		foreach($var as $value)
		{
     		echo json_encode($value);
     		echo "<br>";
		}        
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
