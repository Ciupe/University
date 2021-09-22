<?php
class Item
{
    public $id;
    public $column1;
    public $column2;
    public $column3;
    public $column4;

    public function __construct($id, $column1, $column2, $column3, $column4)
    {
        $this->id = $id;
        $this->column1 = $column1;
        $this->column2 = $column2;
        $this->column3 = $column3;
        $this->column4 = $column4;
    }
}