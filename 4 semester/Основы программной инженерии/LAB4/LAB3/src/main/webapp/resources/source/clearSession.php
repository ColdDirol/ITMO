<?php
session_start();

if(isset($_SESSION['results'])) {
    unset($_SESSION['results']);
}
?>