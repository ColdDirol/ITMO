<template>
  <video autoplay muted loop>
    <source src="./assets/background.mp4" type="video/mp4">
  </video>

  <div class="content">
    <h1 class="regular-text">Карташев Владимир P3215 1206</h1>

    <div class="wrapper">
      <div>
        <canvas ref="coordinatesCanvas" id="coordinates-canvas" width="300" height="300"></canvas>
      </div>

      <div>
        <div class="form-row">
          <label class="form-label">X:</label>
          <div class="radios">
            <input type="radio" id="x-negative-five" name="x-radio" value="-5">
            <label for="x-negative-five">-5</label>

            <input type="radio" id="x-negative-four" name="x-radio" value="-4">
            <label for="x-negative-four">-4</label>

            <input type="radio" id="x-negative-three" name="x-radio" value="-3">
            <label for="x-negative-three">-3</label>

            <input type="radio" id="x-negative-two" name="x-radio" value="-2">
            <label for="x-negative-two">-2</label>

            <input type="radio" id="x-negative-one" name="x-radio" value="-1">
            <label for="x-negative-one">-1</label>

            <input type="radio" id="x-zero" name="x-radio" value="0">
            <label for="x-zero">0</label>

            <input type="radio" id="x-one" name="x-radio" value="1">
            <label for="x-one">1</label>

            <input type="radio" id="x-two" name="x-radio" value="2">
            <label for="x-two">2</label>

            <input type="radio" id="x-three" name="x-radio" value="3">
            <label for="x-two">3</label>
          </div>
        </div>

        <div class="form-row">
          <label for="y-input" class="form-label">Y:</label>
          <input type="text" id="y-input" class="form-input" placeholder="Введите Y">
        </div>

        <div class="form-row">
          <label class="form-label">R:</label>
          <div class="radios">
            <input type="radio" id="r-negative-five" name="r-radio" value="-5" v-model="r">
            <label for="r-negative-five">-5</label>

            <input type="radio" id="r-negative-four" name="r-radio" value="-4" v-model="r">
            <label for="r-negative-four">-4</label>

            <input type="radio" id="r-negative-three" name="r-radio" value="-3" v-model="r">
            <label for="r-negative-three">-3</label>

            <input type="radio" id="r-negative-two" name="r-radio" value="-2" v-model="r">
            <label for="r-negative-two">-2</label>

            <input type="radio" id="r-negative-one" name="r-radio" value="-1" v-model="r">
            <label for="r-negative-one">-1</label>

            <input type="radio" id="r-zero" name="r-radio" value="0" v-model="r">
            <label for="r-zero">0</label>

            <input type="radio" id="r-one" name="r-radio" value="1" v-model="r">
            <label for="r-one">1</label>

            <input type="radio" id="r-two" name="r-radio" value="2" v-model="r">
            <label for="r-two">2</label>

            <input type="radio" id="r-three" name="r-radio" value="3" v-model="r">
            <label for="r-two">3</label>
          </div>
        </div>
      </div>
    </div>

    <div>
      <button id="check-button" class="button" @click="sendButton">check</button>
    </div>

    <div class="table-container">
      <table id="results-table">
        <thead>
        <tr>
          <th>x</th>
          <th>y</th>
          <th>r</th>
          <th>result</th>
        </tr>
        </thead>
        <tbody class="custom-tbody">
          <tr v-for="result in results" :key="result.x">
            <td>{{ result.x }}</td>
            <td>{{ result.y }}</td>
            <td>{{ result.r }}</td>
            <td>{{ result.result ? 'true' : 'false' }}</td>
          </tr>
        </tbody>
      </table>


    </div>

    <button id="clear-table-button" class="button clear-table-button" @click="deleteButton">clear</button>
    <button id="logout-button" class="button" @click="logOut">Log out</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      r: 0,
      coefficient: 0,
      token: localStorage.getItem('jwt') || null,
      results: [],
    };
  },
  watch: {
    r: 'drawElements',
  },
  mounted() {
    if (this.token === null) {
      window.location.href = 'startPage.html';
    }
    this.drawElements();
    document.getElementById("y-input").addEventListener("input", this.validateYInput);

    const canvas = this.$refs.coordinatesCanvas;
    canvas.addEventListener("click", this.canvasClick);

    this.getDataFromServer();
  },
  methods: {
    logOut() {
      localStorage.removeItem('jwt');
      window.location.href = 'startPage.html';
    },
    getDataFromServer() {
      const token = this.token;

      // Отправка GET-запроса на сервер
      fetch('http://localhost:8080/api/v1/result', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
      })
          .then(response => {
            if (response.status === 403) {
              localStorage.removeItem('jwt');
              window.location.href = 'startPage.html';
            }
            return response.json();
          })
          .then(data => {
            // Заполняем массив results данными из GET-запроса
            this.results = data;
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
    drawElements() {
      console.log('Drawing elements with r:', this.r);

      const canvas = this.$refs.coordinatesCanvas;
      const ctx = canvas.getContext('2d');

      const coefficient = (canvas.width - 20) / 10;
      this.coefficient = coefficient;

      // Очищаем canvas
      ctx.fillStyle = "rgb(256, 256, 256, 1)";
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      // Отрисовка координатных осей
      ctx.beginPath();
      ctx.moveTo(canvas.width / 2, 0);
      ctx.lineTo(canvas.width / 2, canvas.height);
      ctx.moveTo(0, canvas.height / 2);
      ctx.lineTo(canvas.width, canvas.height / 2);
      ctx.strokeStyle = 'black';
      ctx.stroke();

      // Перевод отрицательного R в положительное
      const radius = Math.abs(this.r) * coefficient;

      ctx.fillStyle = "rgb(51, 153, 255, 0.5)";

      // Рисуем прямоугольник
      ctx.fillRect(canvas.width / 2, canvas.height / 2, radius, -radius / 2);

      // Рисуем 1/4 круга
      ctx.beginPath();
      ctx.arc(canvas.width / 2, canvas.height / 2, radius, Math.PI, 3 * Math.PI / 2, false);
      ctx.lineTo(canvas.width / 2, canvas.height / 2);
      ctx.closePath();
      ctx.fill();

      // Рисуем треугольник
      ctx.beginPath();
      ctx.moveTo(canvas.width / 2, canvas.height / 2);
      ctx.lineTo(canvas.width / 2 + radius / 2, canvas.height / 2);
      ctx.lineTo(canvas.width / 2, canvas.height / 2 + radius);
      ctx.closePath();
      ctx.fill();

      ctx.fillStyle = "rgb(0, 0, 0, 1)";
      // Обозначаем значения на осях
      ctx.fontFamily = "Open Sans, sans-serif";
      let fontArgs = ctx.font.split(' ');
      let newSize = '14px';
      ctx.font = newSize + ' ' + fontArgs[fontArgs.length - 1];

      // Добавляем обозначения на оси X
      for (let i = -5; i <= 5; i++) {
        ctx.fillText(i, canvas.width / 2 + i * coefficient - 5, canvas.height / 2 + 14);
      }

      // Добавляем обозначения на оси Y
      for (let i = -5; i <= 5; i++) {
        ctx.fillText(i, canvas.width / 2 - 20, canvas.height / 2 - i * coefficient + 5);
      }
    },
    validateYInput() {
      let yInput = document.getElementById("y-input");
      let yValue = yInput.value;

      if (yValue !== '-' && (isNaN(yValue) || parseFloat(yValue) < -5 || parseFloat(yValue) > 3)) {
        // alert("Пожалуйста, введите корректное значение Y в диапазоне от -3 до 5.");
        yInput.value = '';
      }
    },
    canvasClick(event) {
      const canvas = this.$refs.coordinatesCanvas;
      const rect = canvas.getBoundingClientRect();
      const centerX = rect.width / 2;
      const centerY = rect.height / 2;

      const coefficient = this.coefficient;

      const xValue = (event.clientX - rect.left - centerX) / coefficient;
      const yValue = (centerY - (event.clientY - rect.top)) / coefficient;

      console.log('Canvas Clicked - Coordinates:', { xValue, yValue }, 'R:', this.r);
      this.sendDataToServer(xValue, yValue);
    },
    sendButton() {
      const xValue = document.querySelector('input[name="x-radio"]:checked').value;
      const yValue = document.getElementById("y-input").value;

      this.sendDataToServer(xValue, yValue);
    },
    sendDataToServer(xValue, yValue) {
      xValue = parseFloat(xValue).toFixed(2);
      yValue = parseFloat(yValue).toFixed(2);
      const rValue = this.r;

      const token = this.token

      // Отправка POST-запроса на сервер
      fetch('http://localhost:8080/api/v1/result', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          x: xValue,
          y: yValue,
          r: rValue,
        }),
      })
          .then(response => {
            if (response.status === 403) {
              localStorage.removeItem('jwt');
              window.location.href = 'startPage.html';
            }
            return response.json();
          })
          .then(data => {
            // Определение цвета точки в зависимости от значения result
            const pointColor = data.result ? 'green' : 'red';

            // Добавление точки на canvas
            this.drawPointOnCanvas(parseFloat(xValue), parseFloat(yValue), pointColor);

            this.results.push({
              x: xValue,
              y: yValue,
              r: rValue,
              results: data.result ? 'true' : 'false'
            })
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
    drawPointOnCanvas(x, y, color) {
      // Перерисовка координатных осей
      this.drawElements();

      const canvas = this.$refs.coordinatesCanvas;
      const ctx = canvas.getContext('2d');

      const coefficient = this.coefficient;

      // Определение координаты точки на canvas
      const canvasX = canvas.width / 2 + x * coefficient;
      const canvasY = canvas.height / 2 - y * coefficient;

      // Рисование точки
      ctx.beginPath();
      ctx.arc(canvasX, canvasY, 5, 0, 2 * Math.PI);
      ctx.fillStyle = color;
      ctx.fill();
      ctx.closePath();
    },
    deleteButton() {
      const token = this.token;

      // Отправка DELETE-запроса на сервер
      fetch('http://localhost:8080/api/v1/result', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
      })
          .then(response => {
            if (response.status === 403) {
              localStorage.removeItem('jwt');
              window.location.href = 'startPage.html';
            } else if (response.status === 200) {
              // Очистка данных в таблице
              const resultsTable = document.getElementById('results-table').getElementsByTagName('tbody')[0];
              resultsTable.innerHTML = '';
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
  },
};
</script>


<style scoped>
/* Десктопный */
@media screen and (min-width: 1212px) {
  .content {
    padding: 20px;
  }

  .wrapper {
    flex-direction: row; /* Горизонтальное расположение для десктопа */
  }

  #coordinates-canvas {
    margin-left: 10px;
  }
}

/* Планшетный */
@media screen and (min-width: 708px) and (max-width: 1211px) {
  .content {
    padding: 15px;
  }

  .wrapper {
    flex-direction: column; /* Вертикальное расположение для планшета */
  }

  #coordinates-canvas {
    margin-left: 5px;
  }
}

/* Мобильный */
@media screen and (max-width: 707px) {
  .content {
    padding: 10px;
  }

  .wrapper {
    flex-direction: column; /* Вертикальное расположение для мобильных устройств */
  }

  #coordinates-canvas {
    margin-left: 2px;
  }
}

body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

.content {
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  overflow: auto;
  padding: 20px;
}

video {
  position: fixed;
  top: 50%;
  left: 50%;
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  transform: translate(-50%, -50%);
  z-index: -1;
}

.regular-text {
  color: white;
  font-size: 55px;
  font-weight: bold;
  font-family: "Courier New", serif;
}

/* оболочка для добавления севлева направо */
.wrapper {
  display: flex;
  align-items: center;
}

#coordinates-canvas {
  display: flex;
  height: fit-content;
  width: fit-content;
  margin-left: 10px;
}

.form-row {
  display: flex;
  margin-bottom: 10px;
  margin-left: 20px;
}

.form-label {
  display: inline-block;
  width: 60px;
  font-size: 20px;
  font-weight: bold;
  font-family: 'Courier New', serif;
  margin-right: 10px;
  color: white;
}

.radios {
  color: white;
}

.form-input {
  flex: 1;
  padding: 5px 10px;
  font-size: 20px;
  border-radius: 10px;
  border: 1px solid #ccc;
  font-family: 'Courier New', serif;
}

.button {
  background-color: #1a237e;
  color: #fff;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  font-size: 1em;
  cursor: pointer;
  font-family: 'Courier New', serif;
  font-weight: bold;
  margin-top: 20px;
}

.table-container {
  margin-top: 15px;
  max-height: 185px; /* Высота контейнера таблицы */
  overflow: auto; /* Прокрутка при необходимости */
}

.table-container::-webkit-scrollbar {
  width: 8px;
  border-radius: 5px;
}

.table-container::-webkit-scrollbar-track {
  background-color: #1a237e; /* Цвет фона ползунка */
}

.table-container::-webkit-scrollbar-thumb {
  background-color: #3949ab; /* Цвет ползунка */
  border-radius: 5px; /* Округление углов ползунка */
}

.table-container::-webkit-scrollbar-thumb:hover {
  background-color: #5c6bc0; /* Цвет ползунка при наведении */
}

#results-table {
  border-collapse: collapse;
  font-family: 'Courier New', serif;
  font-size: 18px;
  color: white;
}

#results-table th {
  background-color: #1a237e;
  color: #fff;
  font-weight: bold;
  padding: 10px;
  text-align: center;
}

#results-table td {
  background-color: #3f51b5;
  padding: 8px;
  text-align: center;
}

#results-table tbody tr:nth-child(2n) {
  background-color: #3949ab;
}

.custom-tbody {
  background-color: #3949ab;
  text-align: center;
}

#results-table tbody tr:nth-child(2n+1) {
  background-color: #303f9f;
}

#results-table th,
#results-table td {
  border: 1px solid #1a237e;
}

.clear-table-button {
  display: block;
  margin-top: 15px;
  margin-left: auto;
  margin-right: auto;
}
</style>