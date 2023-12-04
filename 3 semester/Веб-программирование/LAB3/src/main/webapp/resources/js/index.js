import { drawPoint } from './graphics.js';
import { clearCanvas } from './graphics.js';


document.addEventListener("DOMContentLoaded", function() {
    const formId = document.forms[0].id;
    const xInput = document.getElementById(formId + ':x-input');
    const yInput = document.getElementById(formId + ':y-input');
    const rInput = document.getElementById(formId + ':r-input');
    const checkButton = document.getElementById(formId + ":check-button");
    const clearTableButton = document.getElementById(formId + ":clear-table-button");

    const audioTrue = document.getElementById("audioTrue");
    const audioFalse = document.getElementById("audioFalse");


    // initializing code
    window.onload = async function () {
        await getJWTToken();
        await initializePage();
    };

    async function getJWTToken() {
        const loginData = {
            username: "admin",
            password: "admin"
        };

        // Преобразование данных в JSON.
        const jsonLoginData = JSON.stringify(loginData);
    }

    async function initializePage() {
        var buttons = document.querySelectorAll('.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only.ui-state-active');
        buttons.forEach(function(button) {
            button.classList.remove('ui-state-active');
        });

        yInput.value = '';
    }


    function drawArea(x, y, r, result) {
        if (result === "true") {
            audioTrue.load();
            audioTrue.play();

            checkButton.style.backgroundColor = 'green';
            checkButton.value = 'true';
            drawPoint(x, y, r, true);
        } else {
            audioFalse.load();
            audioFalse.play();

            checkButton.style.backgroundColor = 'red';
            checkButton.value = 'false';
            drawPoint(x, y, r, false);
        }
    }

    // Функция drawFuckingPoint теперь глобальная
    window.drawFuckingPoint = function() {
        const resultsTable = document.getElementById(formId + ":results-table");
        if (resultsTable) {
            let rows = resultsTable.tBodies[0].rows;
            let columns = rows[rows.length - 1].children;
            console.log(columns);
            drawArea(
                columns[0].textContent,
                columns[1].textContent,
                columns[2].textContent,
                columns[3].textContent
            );
        }
    };

    function added() {
        console.log("checkButton - click");
    }
    checkButton.addEventListener("click", added);

    function clean() {
        console.log("clearTableButton - click");
        clearCanvas()
    }
    clearTableButton.addEventListener("click", clean);
});