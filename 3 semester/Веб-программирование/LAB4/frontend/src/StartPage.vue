<template>
  <div>
    <video autoplay muted loop>
      <source src="./assets/background.mp4" type="video/mp4">
    </video>

    <div class="content">
      <h1>Владимир Карташев P3215 223</h1>

      <div class="wrapper">
        <button @click="setAction('login')">Login</button>
        <button @click="setAction('register')">Register</button>
      </div>

      <component :is="currentAction === 'login' ? 'LoginForm' : 'RegisterForm'" />
    </div>
  </div>
</template>

<script>
import LoginForm from '@/components/LoginForm.vue';
import RegisterForm from '@/components/RegisterForm.vue';

export default {
  data() {
    return {
      currentAction: 'login',
      token: localStorage.getItem('jwt') || null
    };
  },
  mounted() {
    if (this.token !== null) {
      window.location.href = 'mainpage.html';
      // localStorage.removeItem('jwt')
    }
  },
  methods: {
    setAction(action) {
      this.currentAction = action;
    },
  },
  components: {
    LoginForm,
    RegisterForm
  }
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
}

/* Планшетный */
@media screen and (min-width: 708px) and (max-width: 1211px) {
  .content {
    padding: 15px;
  }

  .wrapper {
    flex-direction: column; /* Вертикальное расположение для планшета */
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
  color: white;
}

.wrapper {
  display: flex;
  align-items: center;
}

button {
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
  margin: 10px;
}
</style>
