document.getElementById('clear-table-button').addEventListener('click', clearTable);

async function clearTable() {
    try {
        const response = await fetch('/JAVAEElab2/session', {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        let tableBody = document.getElementById('results-table').getElementsByTagName('tbody')[0];
        tableBody.innerHTML = '';
    } catch (error) {
        console.error(`An error occured: ${error}`);
    }
}
