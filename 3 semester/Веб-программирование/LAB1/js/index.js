import { drawPoint } from './graphics.js';

document.getElementById('check-button').addEventListener('click', checkValues);




function getResultsFromSession() {
    const xhr = new XMLHttpRequest();
    const url = 'getResultsFromSession.php';

    console.log(`${url}`);

    xhr.open('GET', url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = xhr.responseText; // заменить содержимое таблицы на данные сессии

            // Прокрутка таблицы до последней строки
            const tableContainer = document.querySelector('.table-container');
            tableContainer.scrollTop = tableContainer.scrollHeight;
        }
    };

    xhr.send();
}

// Загружаем данные из сессии при загрузке страницы
window.onload = getResultsFromSession;







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

function sendData(x, y, R) {
    // Отправляем данные на сервер
    const xhr = new XMLHttpRequest();
    const url = 'main.php';
    const params = `x=${x}&y=${y}&r=${R}`;

    console.log(`${url}?${params}`);

    xhr.open('GET', `${url}?${params}`, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            handleResponse(x, y, R, xhr.responseText);
        }
    };

    xhr.send();
}

function handleResponse(x, y, R, responseHtml) {
    console.log(`${responseHtml}`);
    let checkButton = document.getElementById('check-button');
    let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];

    // Вставляем пришедшую строку HTML с ответом сервера в таблицу на сайте
    tableBody.insertAdjacentHTML('beforeend', responseHtml);

    // Извлекаем значение result для изменения стиля кнопки проверки
    let resultValueCell = tableBody.lastElementChild.getElementsByTagName('td')[3].innerText;

    if (resultValueCell === 'true') {
        checkButton.style.backgroundColor = 'green';
        checkButton.textContent = 'true';
        drawPoint(x, y, R, true);
    } else {
        checkButton.style.backgroundColor = 'red';
        checkButton.textContent = 'false';
        drawPoint(x, y, R, false);
    }

    // Прокрутка таблицы до последней строки
    const tableContainer = document.querySelector('.table-container');
    tableContainer.scrollTop = tableContainer.scrollHeight;
}