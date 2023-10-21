import { drawPoint } from './graphics.js';

document.getElementById('check-button').addEventListener('click', checkValues);

const yInput = document.getElementById('y-input');
let yValue;

yInput.addEventListener("input", () =>{
    yValue = yInput.value;
    console.log(yValue)

    if (yValue !== '-' && isNaN(yValue) || parseFloat(yValue) < -5 || parseFloat(yValue) > 3) {
        yInput.value = '';
    }
})

const rInput = document.getElementById('r-input');
let rValue;

rInput.addEventListener("input", () =>{
    rValue = rInput.value;
    console.log(rValue)

    if (rValue !== '-' && isNaN(rValue) || parseFloat(rValue) < 1 || parseFloat(rValue) > 4) {
        rInput.value = '';
    }
})



function checkValues() {
    const xInputs = document.getElementsByName('x-radio');
    let xValue;
    let isChecked = false;

    for (const xInput of xInputs) {
        if (xInput.checked) {
            xValue = xInput.value;
            isChecked = true;
            break;
        }
    }

    if (!isChecked || typeof yValue === 'undefined' || typeof rValue === 'undefined') {
        alert('Введите все значения');
        console.log('alert is HERE');
        return;
    }

    console.log('X:', xValue);
    console.log('Y:', yValue);
    console.log('R:', rValue);


    sendData(xValue, parseFloat(yValue), parseFloat(rValue));
}

async function sendRequest(url, data) {
    // Преобразование данных в JSON.
    const jsonData = JSON.stringify(data);

    try {
        // Выполнение запроса.
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        });

        // Если возникла ошибка, выбрасываем исключение.
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        // Преобразование ответа в JSON.
        const responseData = await response.json();

        // Возвращение данных.
        return responseData;

    } catch (error) {
        console.error(`An error occured: ${error}`);
        throw error;
    }
}

async function sendData(x, y, R) {
    // Формируем объект данных для отправки.
    const data = {
        x: x,
        y: y,
        R: R
    };

    try {
        // Отправляем запрос на сервер.
        const result = await sendRequest("/LAB2/controller", data);

        if (result) {
            // Получаем результаты и обрабатываем их.
            handleResponse(x, y, R, result);
        }

    } catch (e) {
        // Обработка ошибок запроса или других исключений.
        console.error(e);
    }
}


function handleResponse(x, y, R, responseJson) {
    console.log(responseJson);
    let checkButton = document.getElementById('check-button');
    let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];

    // Создаем новую строку таблицы на основе полученных данных.
    const newRow =
        `<tr>
            <td>${responseJson.x}</td>
            <td>${responseJson.y}</td>
            <td>${responseJson.R}</td>
            <td>${responseJson.result}</td>
            <td>${responseJson.compiled_in}</td>
        </tr>`;

    // Вставляем новую строку в таблицу на сайте.
    tableBody.insertAdjacentHTML('beforeend', newRow);

    if (responseJson.result === true) {
        checkButton.style.backgroundColor = 'green';
        checkButton.textContent = 'true';
        drawPoint(x, y, R, true);
    } else {
        checkButton.style.backgroundColor = 'red';
        checkButton.textContent = 'false';
        drawPoint(x, y, R, false);
    }

    // Прокрутка таблицы до последней строки.
    const tableContainer = document.querySelector('.table-container');
    tableContainer.scrollTop = tableContainer.scrollHeight;
}