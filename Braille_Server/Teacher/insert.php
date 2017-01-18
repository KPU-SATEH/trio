<?php

function unistr_to_xnstr($str){
	return preg_replace('/\\\u([a-z0-9]{4})/i', "&#x\\1;", $str);
}
$con=mysqli_connect("127.0.0.1","root","eh405405","db");
mysqli_select_db($con,"db");
mysqli_set_charset($con,"utf8");


if(mysqli_connect_errno($con))
{
	echo "Failed to connect to MySQL:".mysqli_connect_error();
}

$array1 = $_POST['array1'];
$array2 = $_POST['array2'];
$array3 = $_POST['array3'];
$letter = $_POST['letter'];
$room = $_POST['room'];
if(!strcmp($room, '0')){
	$result = mysqli_query($con, "insert into users (id) values (null)");
	$result_get = mysqli_query($con, "select * from users order by id desc limit 1");
	while($n = mysqli_fetch_assoc($result_get)){
		$num= $n[id];
	}
	$result2 = mysqli_query($con, "create table abc_$num(id int auto_increment,array1 varchar(3) not null, array2 varchar(3) not null, array3 varchar(3) not null, letter varchar(3) not null, foreign key(id) references users(id))");

	$result3 = mysqli_query($con, "insert into abc_$num(id,array1,array2,array3,letter) values ('0','$array1','$array2','$array3','$letter')");
	$select = mysqli_query($con, "select * from abc_$num");
	$res = array();
	while($row=mysqli_fetch_array($select)){
		array_push($res, array('id'=>$row[1]));
	}
	$json=json_encode(array("result"=>$res));
	echo $json;
}

else{
	$result = mysqli_query($con, "insert into abc_$room(id,array1,array2,array3,letter) values ('0','$array1','$array2','$array3','$letter')");
	echo $room;
}
mysqli_close($con);
?>
