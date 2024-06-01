const canvas = document.getElementById("coordinates-canvas");
const ctx = canvas.getContext("2d");

// Определяем обозначения осей
const xAxisLabel = "X";
const yAxisLabel = "Y";

let xAxisScale;
let yAxisScale;

function draw() {
    if (canvas.getContext) {
        ctx.fillStyle = 'white';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        ctx.fillStyle = "black";
        ctx.strokeStyle = "black";

        // Определяем размеры рисунка
        let canvasWidth = canvas.width;
        let canvasHeight = canvas.height;

        xAxisScale = canvasWidth / 10;
        yAxisScale = canvasHeight / 10;

        // Определяем начальную точку
        let originX = canvasWidth / 2;
        let originY = canvasHeight / 2;

        // Рисуем ось x
        ctx.beginPath();
        ctx.moveTo(0, originY);
        ctx.lineTo(canvasWidth, originY);
        ctx.stroke();

        // Рисуем ось y
        ctx.beginPath();
        ctx.moveTo(originX, 0);
        ctx.lineTo(originX, canvasHeight);
        ctx.stroke();

        // Обозначаем значения на осях
        ctx.fontFamily = "Open Sans, sans-serif";
        let fontArgs = ctx.font.split(' ');
        let newSize = '14px';
        ctx.font = newSize + ' ' + fontArgs[fontArgs.length - 1];
        ctx.fillText(xAxisLabel, canvas.width - 15, canvas.height / 2 - 5);
        ctx.fillText(yAxisLabel, canvas.width / 2 + 5, 15);

        // Обозначаем метки на осях
        for (let i = -canvas.width / 2; i < canvas.width / 2; i += xAxisScale) {
            let scalePos = axesToCanvasCoordinates(i, 0, canvas);
            ctx.beginPath();
            ctx.moveTo(scalePos.x, scalePos.y - 5);
            ctx.lineTo(scalePos.x, scalePos.y + 5);
            ctx.stroke();
            ctx.fillText(rescaleXAxesCoordinate(i), scalePos.x - 10, scalePos.y + 20);
        }

        for (let j = -canvas.height / 2; j < canvas.height / 2; j += yAxisScale) {
            let scalePos = axesToCanvasCoordinates(0, j, canvas);
            ctx.beginPath();
            ctx.moveTo(scalePos.x - 5, scalePos.y);
            ctx.lineTo(scalePos.x + 5, scalePos.y);
            ctx.stroke();
            ctx.fillText(rescaleYAxesCoordinate(j), scalePos.x + 10, scalePos.y + 5);
        }
    }
}

function axesToCanvasCoordinates(xAxes, yAxes, canvas) {
    let originX = canvas.width / 2;
    let originY = canvas.height / 2;

    let canvasX = originX + xAxes;
    let canvasY = originY - yAxes;

    return { x: canvasX, y: canvasY };
}

function rescaleXAxesCoordinate(coordinate) {
    return coordinate / xAxisScale;
}

function rescaleYAxesCoordinate(coordinate) {
    return coordinate / yAxisScale;
}

function scaleXAxesCoordinate(coordinate) {
    return coordinate * xAxisScale;
}

function scaleYAxesCoordinate(coordinate) {
    return coordinate * yAxisScale;
}

export function drawShapesByR(r) {
    if (canvas.getContext) {
        // очищаем поле для рисунка
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        draw();

        let startPointInAxes = {x: 0, y: 0};
        let startPointInCanvas = axesToCanvasCoordinates(startPointInAxes.x, startPointInAxes.y, canvas);


        // рисуем квадрат в 1 четверти:
        let endPointInAxes = {x: r, y: r};
        let endScaledPointInAxes = {
            x: scaleXAxesCoordinate(endPointInAxes.x),
            y: scaleYAxesCoordinate(endPointInAxes.y)
        };

        ctx.fillStyle = "rgb(51, 153, 255, 0.5)";
        ctx.beginPath();
        ctx.fillRect(startPointInCanvas.x, startPointInCanvas.y, endScaledPointInAxes.x, -endScaledPointInAxes.y);

        // рисуем треугольник в 3 четверти:
        let secondTrianglePointInAxes = {x: -(r/2), y: 0};
        let thirdTrianglePointInAxes = {x: 0, y: -(r/2)};
        drawTriangle(ctx, startPointInAxes, secondTrianglePointInAxes, thirdTrianglePointInAxes);

        // рисуем часть 1/4 круга во 2 четверти
        let calculatedRadius = scaleXAxesCoordinate(r);

        ctx.beginPath();
        //ctx.arc(startPointInCanvas.x, startPointInCanvas.y, calculatedRadius, 3 * Math.PI / 2, Math.PI, true);
        ctx.arc(startPointInCanvas.x, startPointInCanvas.y, calculatedRadius, Math.PI, 3 * Math.PI / 2, false);
        ctx.fill();

        // рисуем недостающий треугольник до 1/4 круга во 2 четверти
        let secondTrianglePointInAxesM = {x: -r, y: 0};
        let thirdTrianglePointInAxesM = {x: 0, y: r};
        drawTriangle(ctx, startPointInAxes, secondTrianglePointInAxesM, thirdTrianglePointInAxesM);
    }
}

function drawTriangle (ctx, startPointInAxes, secondTrianglePointInAxes, thirdTrianglePointInAxes) {
    if (canvas.getContext) {
        let startPointInCanvas = axesToCanvasCoordinates(startPointInAxes.x, startPointInAxes.y, canvas);
        let secondScaledTrianglePointInAxes = {
            x: scaleXAxesCoordinate(secondTrianglePointInAxes.x),
            y: scaleYAxesCoordinate(secondTrianglePointInAxes.y)
        }
        let thirdScaledTrianglePointInAxes = {
            x: scaleXAxesCoordinate(thirdTrianglePointInAxes.x),
            y: scaleYAxesCoordinate(thirdTrianglePointInAxes.y)
        };
        let secondTrianglePointInCanvas = axesToCanvasCoordinates
        (secondScaledTrianglePointInAxes.x, secondScaledTrianglePointInAxes.y, canvas);
        let thirdScaledTrianglePointInCanvas = axesToCanvasCoordinates
        (thirdScaledTrianglePointInAxes.x, thirdScaledTrianglePointInAxes.y, canvas);

        ctx.beginPath();
        ctx.moveTo(startPointInCanvas.x, startPointInCanvas.y);
        ctx.lineTo(secondTrianglePointInCanvas.x, secondTrianglePointInCanvas.y);
        ctx.lineTo(thirdScaledTrianglePointInCanvas.x, thirdScaledTrianglePointInCanvas.y);
        ctx.fill();
    }
}

// рисуем точку с полученными координатами
export function drawPoint(x, y, r, result) {
    if (canvas.getContext) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        draw();
        drawShapesByR(r);

        const pointSize = 4;

        let scaledPoint = {x: scaleXAxesCoordinate(x), y: scaleYAxesCoordinate(y)};
        let pointOnCanvas = axesToCanvasCoordinates(scaledPoint.x, scaledPoint.y, canvas);

        if(result === true) {
            ctx.fillStyle = "rgb(0, 255, 0)"
        } else {
            ctx.fillStyle = "rgb(255, 0, 0)";
        }
        ctx.beginPath();
        ctx.fillRect(pointOnCanvas.x - pointSize / 2, pointOnCanvas.y - pointSize / 2, pointSize, pointSize);
    }
}

draw();

canvas.addEventListener("click", function (event) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = event.clientX - rect.left;
    const mouseY = event.clientY - rect.top;

    // Преобразование координаты canvas в координаты осей
    const axesCoordinates = canvasToAxesCoordinates(mouseX, mouseY, canvas);

    // Получение текущее значение R из HTML-элемента
    const rInput = document.querySelector("input[name='formId:r-input']:checked");
    const currentR = rInput ? rInput.value : undefined;

    if (currentR === null || currentR === undefined) {
        alert("Please enter a valid value for R.");
        return; // прерывание
    }

    // Выведите значения в консоль
    console.log("Кликнуто по (x, y, R):", axesCoordinates.x, axesCoordinates.y, currentR);

    sendPointData(axesCoordinates.x, axesCoordinates.y, currentR);
});

// Отправка данных на сервер
function sendPointData(x, y, r) {
    // Вызов удаленной команды для отправки данных в управляемый бин
    sendPointRemoteCommand([{name:'x', value:x}, {name:'y', value:y}, {name:'r', value:r}]);
}

// Преобразование координат canvas в координаты осей
function canvasToAxesCoordinates(canvasX, canvasY, canvas) {
    let originX = canvas.width / 2;
    let originY = canvas.height / 2;

    let axesX = canvasX - originX;
    let axesY = originY - canvasY;

    return { x: rescaleXAxesCoordinate(axesX), y: rescaleYAxesCoordinate(axesY) };
}

export function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    draw();
}