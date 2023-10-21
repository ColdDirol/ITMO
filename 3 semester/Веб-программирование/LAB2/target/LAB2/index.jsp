<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>LAB1</title>

    <meta name="description" content="Описание страницы Описание страницы Описание страницы Описание страницы">
    <meta name="keywords" content="I, hate, HTML, CSS, JavaScript">

    <link rel="icon" href="https://se.ifmo.ru/o/favicon/">
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<video autoplay muted loop>
    <source src="source/background.mp4" type="video/mp4">
</video>

<div class="content">
    <h1 class="regular-text">Vladimir Kartashev P3215 888499</h1>

    <div class="wrapper">
        <div>
            <canvas id="coordinates-canvas" width="300" height="300"></canvas>
        </div>

        <div>
            <div class="form-row">
                <label class="form-label">X:</label>
                <div class="radios">
                    <input type="radio" id="x-negative-two" name="x-radio" value="-2">
                    <label for="x-negative-two">-2</label>

                    <input type="radio" id="x-negative-one-half" name="x-radio" value="-1.5">
                    <label for="x-negative-one-half">-1.5</label>

                    <input type="radio" id="x-negative-one" name="x-radio" value="-1">
                    <label for="x-negative-one">-1</label>

                    <input type="radio" id="x-negative-half" name="x-radio" value="-0.5">
                    <label for="x-negative-half">-0.5</label>

                    <input type="radio" id="x-zero" name="x-radio" value="0">
                    <label for="x-zero">0</label>

                    <input type="radio" id="x-half" name="x-radio" value="0.5">
                    <label for="x-half">0.5</label>

                    <input type="radio" id="x-positive-one" name="x-radio" value="1">
                    <label for="x-positive-one">1</label>

                    <input type="radio" id="x-positive-one-half" name="x-radio" value="1.5">
                    <label for="x-positive-one-half">1.5</label>

                    <input type="radio" id="x-positive-two" name="x-radio" value="2">
                    <label for="x-positive-two">2</label>
                </div>
            </div>

            <div class="form-row">
                <label for="y-input" class="form-label">Y:</label>
                <input type="text" id="y-input" class="form-input" placeholder="Enter Y">
            </div>

            <div class="form-row">
                <label for="r-input" class="form-label">R:</label>
                <input type="text" id="r-input" class="form-input" placeholder="Enter R">
            </div>
        </div>
    </div>

    <div>
        <button id="check-button" class="button">check</button>
    </div>

    <div class="table-container">
        <table id="results-table">
            <thead>
            <tr>
                <th>x</th>
                <th>y</th>
                <th>r</th>
                <th>result</th>
                <th>completed_in</th>
            </tr>
            </thead>
            <tbody>
            <!-- Добавить строки с результатами сюда -->
            </tbody>
        </table>


    </div>

    <button id="clear-table-button" class="button clear-table-button">clear</button>
</div>

<script type="module" src="js/index.js"></script>
<script type="module" src="js/graphics.js"></script>
<script type="module" src="js/cleartable.js"></script>
</body>
</html>