<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['username'];
            $dbpassword = $row['password'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $fullname, $email, $username, $password)
    {
        $fullname = $this->prepareData($fullname);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($email);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (fullname, username, password, email) VALUES ('" . $fullname . "','" . $username . "','" . $password . "','" . $email . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function getAll($table)
    {
        $this->sql = "select * from " . $table;
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) == 0) return ["empty"];        
        while($row = $result->fetch_assoc()){
            $rows[] = $row;
        }
        return $rows;
    }    

    function getCarType($type)
    {
        $this->sql = "select * from cars where type ='" . $type . "'";
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) == 0) return ["empty"];        
        while($row = $result->fetch_assoc()){
            $rows[] = $row;
        }
        return $rows;
    }   

    function getCar($id)
    {
        $this->sql = "select * from cars where car_id = " . $id;
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) == 0) return ["does not exist"];        
        $row = $result->fetch_assoc();
        return $row;
    }   

    function getUserFaves($username)
    {
        $username = $this->prepareData($username);
        $this->sql = "select * from user_faves where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) == 0) return ["empty"];                
        while($row = $result->fetch_assoc()){
            $rows[] = $row;
        }
        return $rows;
    }

    function getUserRes($username)
    {
        $username = $this->prepareData($username);
        $this->sql = "select * from user_reservations where username = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        if (mysqli_num_rows($result) == 0) return ["empty"];                
        while($row = $result->fetch_assoc()){
            $rows[] = $row;
        }
        return $rows;
    }  

    function putUserFave($username, $car_id)
    {
        $username = $this->prepareData($username);
        $car_id = $this->prepareData($car_id);
        $this->sql =
            "INSERT INTO user_faves (username, car_id) VALUES ('" . $username . "','" . $car_id . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function deleteUserFave($username, $car_id)
    {
        $username = $this->prepareData($username);
        $car_id = $this->prepareData($car_id);
        $this->sql =
            "DELETE from user_faves WHERE username ='" . $username  . "' AND car_id = '" . $car_id . "'";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function putUserRes($username, $car_id, $pickuplocation, $dropofflocation, $pickupdate, $dropoffdate, $price)
    {
        $username = $this->prepareData($username);
        $car_id = $this->prepareData($car_id);
        $pickuplocation = $this->prepareData($pickuplocation);
        $dropofflocation = $this->prepareData($dropofflocation);
        $pickupdate = $this->prepareData($pickupdate);
        $dropoffdate = $this->prepareData($dropoffdate);
        $price = $this->prepareData($price);
        $this->sql = "INSERT INTO user_reservations (username, car_id, pickup_location, dropoff_location, pickup_date, dropoff_date, total_price) VALUES ('" . $username . "','" . $car_id . "','" . $pickuplocation . "','" . $dropofflocation . "','" . $pickupdate . "','" . $dropoffdate . "','" . $price . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }          
}
?>
