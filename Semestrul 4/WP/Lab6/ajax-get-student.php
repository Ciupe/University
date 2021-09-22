<?php
session_start();
echo "My id is ". $_SESSION['student_id'] . ":"
?>

<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function(){
                $("#button").click(function(){
                    $.get("select_grades.php", { student_id: <?php Print($_SESSION['student_id']); ?>},
                        function(data,status){
                            $("#maindiv").html(data);
                        });
                });
            });
        </script>
    </head>
    <body>
        <div id = "maindiv"></div>
        <input id = "button" type = "button" value="See grades" />
    </body>

    <form action='destroy_session.php' method='POST'>
        <input type='submit' value='Logout' name='logout' />
        </form>
</html>