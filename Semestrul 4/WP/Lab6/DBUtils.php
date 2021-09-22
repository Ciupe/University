<?php

class DBUtils {
	private $host = 'localhost';
	private $db   = 'lab6';
	private $user = 'root';
	private $pass = '';
	private $charset = 'utf8';	

	private $pdo;
	private $error;

	public function __construct () {
		$dsn = "mysql:host=$this->host;dbname=$this->db;charset=$this->charset";
		$opt = array(PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
			PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
			PDO::ATTR_EMULATE_PREPARES   => false);
		try {
			$this->pdo = new PDO($dsn, $this->user, $this->pass, $opt);		
		} // Catch any errors
		catch(PDOException $e){
			$this->error = $e->getMessage();
			echo "Error connecting to DB: " . $this->error;
		}
	}

	public function select() {
		$stmt = $this->pdo->query("SELECT * FROM Students");
		return $stmt->fetchAll(PDO::FETCH_ASSOC);
	}	

	public function selectGroup($groupId){
		$stmt = $this->pdo->query("SELECT * FROM Students Where StudentGroup=" . $groupId);
		return $stmt->fetchAll(PDO::FETCH_ASSOC);
	}

	public function insert($id, $value) {
		$affected_rows = $this->pdo->exec("INSERT into table values(" . $id . ",'" . $value ."');");
		//return $affected_rows;
	}

	public function delete ($id) {
		$affected_rows = $this->pdo->exec("DELETE from Students where Id=" . $id);
		//return $affected_rows;
	}

	public function update_grade1 ($id, $grade) {
		$affected_rows = $this->pdo->exec("UPDATE Students SET Grade_C1	=$grade where Id=" . $id);
	}

	public function update_grade2 ($id, $grade) {
		$affected_rows = $this->pdo->exec("UPDATE Students SET Grade_C2=$grade where Id=" . $id);
	}
}
 

?>