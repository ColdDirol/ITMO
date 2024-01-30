import { createRouter, createWebHistory } from 'vue-router';
import StartPage from './StartPage.vue';
import MainPage from './MainPage.vue';

const routes = [
    { path: '/startpage.html', component: StartPage },
    { path: '/mainpage.html', component: MainPage },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

createApp(App).use(router).mount('#app');
