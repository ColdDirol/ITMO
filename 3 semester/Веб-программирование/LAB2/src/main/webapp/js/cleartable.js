document.getElementById('clear-table-button').addEventListener('click', clearTable);

function clearTable() {
    let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    // Очистка данных сессии на сервере
    const xhr = new XMLHttpRequest();
    const url = 'clearSession.php';

    xhr.open('GET', url, true);
    xhr.send();
}