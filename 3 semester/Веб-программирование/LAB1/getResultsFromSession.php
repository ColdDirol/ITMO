<?php
session_start(); // Добавляем это в начало для начала сессии

// Проверяем, есть ли данные в сессии и отправляем их обратно в виде строки html таблицы
if(isset($_SESSION['results'])) {
    header('Content-Type: text/html; charset=utf-8');
    $table = '<table>';
    foreach($_SESSION['results'] as $data) {
        $table .= '<tr>';
        $table .= "<td>{$data['x']}</td>";
        $table .= "<td>{$data['y']}</td>";
        $table .= "<td>{$data['r']}</td>";
        $table .= "<td>{$data['result']}</td>";
        $table .= "<td>{$data['compiled_in']}</td>";
        $table .= "<td>{$data['execution_time']}</td>";
        $table .= '</tr>';
    }
    $table .= '</table>';
    echo $table;
} else {
    echo ""; // если данных нет, возвращаем пустую строку
}
?>