import { drawPoint } from './graphics.js';

const xInputs = document.getElementsByName('x-radio');
const yInput = document.getElementById('y-input');
const rInput = document.getElementById('r-input');

const audioTrue = document.getElementById("audioTrue");
const audioFalse = document.getElementById("audioFalse");

let JWTToken;

async function initializePage() {
    await getJWTToken();
    await getSessionData();
}

async function getJWTToken() {
    const loginData = {
        username: "admin",
        password: "admin"
    };

    // Преобразование данных в JSON.
    const jsonLoginData = JSON.stringify(loginData);

    try {
        // Выполнение запроса.
        const response = await fetch('/LAB2/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonLoginData
        });

        if (response.ok) {
            const data = await response.json();
            if (data.token) {
                JWTToken = data.token;
                // Используйте переменную JWTToken для необходимой обработки
                console.log("JWT Token:", JWTToken);
            } else {
                console.log("Token not found in JSON response");
            }
        } else {
            console.log("Request failed with status:", response.status);
        }

        // Если возникла ошибка, выбрасываем исключение.
        // if (!response.ok) {
        //     throw new Error(`HTTP error! status: ${response.status}`);
        // }
        //
        // JWTToken = response.body;
        // console.log(JWTToken)

    } catch (error) {
        console.error(`An error occured: ${error}`);
        throw error;
    }
}

// Получить данные сессии и установить в таблицу результатов
// Получить данные сессии и установить в таблицу результатов
async function getSessionData() {
    for (let i = 0; i < xInputs.length; i++) {
        xInputs[i].checked = false;
    }
    yInput.value = '';
    rInput.value = '';

    // Отправляем запрос на сервер
    const response = await fetch('/LAB2/session', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${JWTToken}`
        }
    })

    // Если возникла ошибка, выбрасываем исключение
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    // Извлечение данных JSON
    const responseData = await response.json()

    console.log(responseData)

    let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];

    // Очистка таблицы
    tableBody.innerHTML = ''

    // Заполнение таблицы данными сессии
    for(let result of responseData) {
        const newRow =
            `<tr>
                <td>${result.x}</td>
                <td>${result.y}</td>
                <td>${result.R}</td>
                <td>${result.result}</td>
                <td>${result.compiled_in}</td>
            </tr>`;

        tableBody.insertAdjacentHTML('beforeend', newRow);
    }
}

window.onload = initializePage;

document.getElementById('check-button').addEventListener('click', checkValues);

let yValue;

yInput.addEventListener("input", () =>{
    yValue = yInput.value;

    if (yValue !== '-' && isNaN(yValue) || parseFloat(yValue) < -3 || parseFloat(yValue) > 3) {
        yInput.value = '';
    }
})

let rValue;

rInput.addEventListener("input", () =>{
    rValue = rInput.value;

    if (rValue !== '-' && isNaN(rValue) || parseFloat(rValue) < 1 || parseFloat(rValue) > 4) {
        rInput.value = '';
    }
})



function checkValues() {
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
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${JWTToken}`
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
        // audioSource.src = "../source/true.mp3";
        // audio.load();
        audioTrue.load();
        audioTrue.play();

        checkButton.style.backgroundColor = 'green';
        checkButton.textContent = 'true';
        drawPoint(x, y, R, true);
    } else {
        // audioSource.src = "../source/false.mp3";
        // audio.load();
        audioFalse.load();
        audioFalse.play();

        checkButton.style.backgroundColor = 'red';
        checkButton.textContent = 'false';
        drawPoint(x, y, R, false);
    }

    // Прокрутка таблицы до последней строки.
    const tableContainer = document.querySelector('.table-container');
    tableContainer.scrollTop = tableContainer.scrollHeight;
}