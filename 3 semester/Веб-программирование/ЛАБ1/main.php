<?php
date_default_timezone_set('Europe/Moscow');
// Получаем значения x, y, r из GET запроса
$x = isset($_GET['x']) ? (double) $_GET['x'] : 0.0;
$y = isset($_GET['y']) ? (double) $_GET['y'] : 0.0;
$R = isset($_GET['r']) ? (double) $_GET['r'] : 0.0;

if (checkData($x, $y, $R)) {
    // Проверка для прямоугольника
    $isInRectangle = ($y >= 0 && $y <= $R && $x >= 0 && $x <= $R);

    // Проверка для круга
    $isInCircle = checkIsInCircle($x, $y, $R);

    // Проверка для треугольника
    $isInTriangle = checkIsInTriangle($x, $y, $R);

    // Получаем время компиляции и выполнения скрипта
    $compilationTime = date('Y-m-d H:i:s');
    $executionStartTime = microtime(true);

    // Возвращаем результат клиенту
    $data = [
        'x' => $x,
        'y' => $y,
        'r' => $R,
        'result' => ($isInRectangle || $isInCircle || $isInTriangle) ? 'true' : 'false',
        'compiled_in' => $compilationTime,
        'execution_time' => '',
    ];

    $executionEndTime = microtime(true);
    $executionTime = round($executionEndTime - $executionStartTime, 6);
    $data['execution_time'] = $executionTime;

    // Отправляем данные в формате JSON
    header('Content-Type: application/json');
    echo json_encode($data);
} else {
    echo "error";
}

// функция для проверки значений по заданию
function checkData($x, $y, $R) {
    return in_array($x, array(-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2)) &&
        is_numeric($y) && ($y >= -5 && $y <= 3) &&
        in_array($R, array(1, 1.5, 2, 2.5, 3));
}

//функция для проверки круга
function checkIsInCircle($x, $y, $R) {
    if($x >= 0 && $y <= 0) {
        $lengthFromNull = sqrt(pow($x, 2) + pow($y, 2));
        return $lengthFromNull <= $R / 2;
    } else return false;
}

// Функция для проверки треугольника
function checkIsInTriangle($x, $y, $R) {
    $x1 = -$R / 2;
    $x2 = 0;
    $x3 = 0;
    $y1 = 0;
    $y2 = 0;
    $y3 = -$R;

    $d1 = ($x - $x2) * ($y3 - $y2) - ($x3 - $x2) * ($y - $y2);
    $d2 = ($x - $x3) * ($y1 - $y3) - ($x1 - $x3) * ($y - $y3);
    $d3 = ($x - $x1) * ($y2 - $y1) - ($x2 - $x1) * ($y - $y1);

    $hasNegatives = ($d1 < 0) || ($d2 < 0) || ($d3 < 0);
    $hasPositives = ($d1 > 0) || ($d2 > 0) || ($d3 > 0);

    return !($hasNegatives && $hasPositives);
}
?>