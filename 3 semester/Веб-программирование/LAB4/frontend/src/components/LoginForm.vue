<!-- src/components/LoginForm.vue -->
<template>
  <div>
    <h2>Login</h2>
    <form @submit.prevent="login">
<!--      <label>Email: </label>-->
      <input v-model="email" type="email" required placeholder="Email"/>
      <br />
<!--      <label>Password: </label>-->
      <input v-model="password" type="password" required placeholder="Password"/>
      <br />
      <button type="submit">Login</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async login() {
      try {
        const response = await fetch('http://localhost:8080/api/v1/auth/authenticate', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password
          })
        });

        const data = await response.json();
        window.location.href = 'mainPage.html';
        
        localStorage.setItem('jwt', data.token);
      } catch (error) {
        console.error('Error during login:', error);
      }
    }
  }
};
</script>

<style>
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
}

input {
  flex: 1;
  padding: 5px 10px;
  font-size: 20px;
  border-radius: 10px;
  border: 1px solid #ccc;
  font-family: 'Courier New', serif;
  margin-bottom: 20px;
}
</style>