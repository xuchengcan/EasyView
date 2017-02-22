<?php

	date_default_timezone_set('Etc/GMT-8');

	/**
	* 
	*/
	class TodoBean
	{
		public $ID;
		public $Name;
		public $Title;
		public $Url;
		public $Type;
		public $StartTime;
		public $StopTime;
		public $CreatTime;
		public $IsComplete;
	}
	$data = $_POST["json"];
	if (!is_null($data)) {

		$list = json_decode($data);//第二个参数不填变为对象，json_decode($data，false)生产对象，json_decode($data，true)生成数组

		echo "更新成功。当前时间是：".date('Y-m-d H:i:s')."\n";

		foreach ($list as $key => $value) {
			$data= $value;
			// foreach ($sss as $key => $value) {
			// 	echo $key ."---".$value;
			// 	echo "\n";
			// }
			echo json_encode($data);
		}

	}

	$myfile = fopen("test.txt", "r") or die("Unable to open file!");
echo fread($myfile,filesize("test.txt"));
fclose($myfile);

	?>