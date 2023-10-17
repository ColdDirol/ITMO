import { drawPoint } from './graphics.js';

document.getElementById('check-button').addEventListener('click', checkValues);




// function getResultsFromSession() {
//     const xhr = new XMLHttpRequest();
//     const url = 'getResultsFromSession.php';
//
//     console.log(`${url}`);
//
//     xhr.open('GET', url, true);
//     xhr.onreadystatechange = function () {
//         if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
//             let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];
//             tableBody.innerHTML = xhr.responseText; // заменить содержимое таблицы на данные сессии
//
//             // Прокрутка таблицы до последней строки
//             const tableContainer = document.querySelector('.table-container');
//             tableContainer.scrollTop = tableContainer.scrollHeight;
//         }
//     };
//
//     xhr.send();
// }
//
// // Загружаем данные из сессии при загрузке страницы
// window.onload = getResultsFromSession;







const yInput = document.getElementById('y-input');
let yValue;
const yRegex = /^-?[0-9]+(\.[0-9]*)?$/; // Регулярное выражение для числа с плавающей точкой

yInput.addEventListener("input", () =>{
    yValue = yInput.value;

    if (yValue !== '-' && isNaN(yValue) || parseFloat(yValue) < -5 || parseFloat(yValue) > 3) {
        yInput.value = '';
    }
})


const rInputs = document.getElementsByName('r-checkbox');
let rValue;

for (const rInput of rInputs) {
    rInput.addEventListener('change', function () {
        if (this.checked && rValue !== this.value) {
            rValue = this.value;
            console.log(rValue)
            for (const otherInput of rInputs) {
                if (otherInput !== this) {
                    otherInput.checked = false;
                }
            }
        } else if (!this.checked && rValue === this.value) {
            rValue = undefined;
        }
    });
}



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

    if (!isChecked) {
        alert('Выберите значение для X');
        console.log('alert is HERE');
        return;
    }

    console.log('X:', xValue);
    console.log('Y:', yValue);
    console.log('R:', rValue);


    sendData(xValue, parseFloat(yValue), rValue);
}

async function sendRequest(url, body) {
    const fetchOptions = {
        headers: {
            "Content-Type": "application/json"
        },
        method: "POST",
        body: JSON.stringify(body)
    };

    const response = await fetch(url, fetchOptions);
    if (!response.ok) {
        throw new Error(`Request failed: ${response.status}`);
    }

    return response.json();
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
    const newRow = `<tr><td>${responseJson.x}</td><td>${responseJson.y}</td><td>${responseJson.R}</td><td>${responseJson.result}</td><td>${responseJson.compiled_in}</td></tr>`;

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