<!-- src/components/RegisterForm.vue -->
<template>
  <div>
    <h2>Register</h2>
    <form @submit.prevent="register">
<!--      <label>First Name: </label>-->
      <input v-model="firstname" required placeholder="First name"/>
      <br />
<!--      <label>Last Name: </label>-->
      <input v-model="lastname" required placeholder="Last name"/>
      <br />
<!--      <label>Email: </label>-->
      <input v-model="email" type="email" required placeholder="Email"/>
      <br />
<!--      <label>Password: </label>-->
      <input v-model="password" type="password" required placeholder="Password"/>
      <br />
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      firstname: '',
      lastname: '',
      email: '',
      password: ''
    };
  },
  methods: {
    async register() {
      try {
        const response = await fetch('http://localhost:8080/api/v1/auth/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            firstname: this.firstname,
            lastname: this.lastname,
            email: this.email,
            password: this.password
          })
        });

        const data = await response.json();

        localStorage.setItem('jwt', data.token);
        window.location.href = 'mainPage.html';
      } catch (error) {
        console.error('Error during registration:', error);
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
