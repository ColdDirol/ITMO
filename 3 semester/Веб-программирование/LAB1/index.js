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

    const yInput = document.getElementById('y-input');
    let yValue = yInput.value.trim(); // Удаляем лишние пробелы

    // Проверка введенного значения с помощью регулярного выражения
    const yRegex = /^-?[0-9]+(\.[0-9]*)?$/; // Регулярное выражение для числа с плавающей точкой
    if (!yValue.match(yRegex) || parseFloat(yValue) < -5 || parseFloat(yValue) > 3) {
        alert('Введите число от -5 до 3 в поле Y');
        console.log('alert is HERE');
        return;
    }

    yValue = parseFloat(yValue); // Преобразуем значение Y в число

    const rInputs = document.getElementsByName('r-checkbox');
    const rValues = [];

    let checkedCount = 0;

    for (const rInput of rInputs) {
        if (rInput.checked) {
            rValues.push(rInput.value);
            checkedCount++;
        }
    }

    if (checkedCount !== 1) {
        alert('Выберите одну и только одну галочку для поля R');
        console.log('alert is HERE');
        return;
    }

    console.log('X:', xValue);
    console.log('Y:', yValue);
    console.log('R:', rValues.at(0));

    sendData(xValue, yValue, rValues);
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
            const response = JSON.parse(xhr.responseText);
            handleResponse(response);
        }
    };

    xhr.send();
}

function handleResponse(response) {
    let checkButton = document.getElementById('check-button');
    let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];

    let newRow = document.createElement('tr');

    let xCell = document.createElement('td');
    xCell.innerText = response.x;
    newRow.appendChild(xCell);

    let yCell = document.createElement('td');
    yCell.innerText = response.y;
    newRow.appendChild(yCell);

    let rCell = document.createElement('td');
    rCell.innerText = response.r;
    newRow.appendChild(rCell);

    let resultCell = document.createElement('td');
    resultCell.innerText = response.result;
    console.log(response.result)
    newRow.appendChild(resultCell);

    let completedCell = document.createElement('td');
    completedCell.innerText = response.compiled_in;
    newRow.appendChild(completedCell);

    let executionCell = document.createElement('td');
    executionCell.innerText = response.execution_time;
    newRow.appendChild(executionCell);

    tableBody.appendChild(newRow);


    if (response.result === 'true') {
        checkButton.style.backgroundColor = 'green';
        checkButton.textContent = 'true';
        console.log(response);
    } else {
        checkButton.style.backgroundColor = 'red';
        checkButton.textContent = 'false';
        console.log(response);
    }


    // Прокрутка таблицы до последней строки
    const tableContainer = document.querySelector('.table-container');
    tableContainer.scrollTop = tableContainer.scrollHeight;
}