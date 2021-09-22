<?php
require 'DBUtils.php';
session_start();
echo "Students from group ". $_SESSION['group'] . ":";

$db = new DBUtils ();
$resultset = $db->selectGroup($_SESSION['group']);
#echo count($resultset);
?>

<html>
    <head>
        <style type="text/css">
        #input{
            display:block;
            padding-top: 20px;
        }
        #update{
            margin-top: 10px;
        }
        </style>
        //script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>

            $(document).ready(function(){
                var page = 0;
                var max = "<?php Print(count($resultset))?>"
                $("#button").click(function(){
                    $.get("select_php.php", { page: page, groupId: <?php Print($_SESSION['group']); ?>},
                        function(data,status){
                            $("#maindiv").html(data);
                        });
                });

                $("#update").click(function(){
                    $.post("update_php.php", { page: page, groupId: <?php Print($_SESSION['group']); ?>, Id: document.getElementById("id").value, Grade1: document.getElementById("grade1").value, Grade2: document.getElementById("grade2").value},
                        function(data,status){
                            $("#maindiv").html(data);
                        });
                });

                $("#next").click(function(){
                    if(max > page + 4)
                        page += 4    
                    $.get("select_php.php", { page: page, groupId: <?php Print($_SESSION['group']); ?>},
                        function(data,status){
                            $("#maindiv").html(data);
                        });              
                });

                $("#prev").click(function(){
                    if(page != 0)
                        page -= 4
                        $.get("select_php.php", { page: page, groupId: <?php Print($_SESSION['group']); ?>},
                        function(data,status){
                            $("#maindiv").html(data);
                        });
                });
            });
        </script>
    </head>
    <body>
        <div id = "maindiv"></div>
        <input id = "button" type = "button" value="get students" />
        <a id = "prev" href="#"><< prev</a>
        <a id = "next" href="#">next >></a>
        
        <div id = "input">
        Id: <input id="id" type="text" name="Id" />
        Grade1: <input id="grade1" type="text" name="Grade1" />
        Grade2: <input id="grade2" type="text" name="Grade2" />
        </div>
        <input id = "update" type = "button" value="update grades" />

        <form action='destroy_session.php' method='POST'>
        <input type='submit' value='Logout' name='logout' />
        </form>
    </body>
</html>