import {createRouter, createWebHistory} from "vue-router";

import HomeView from "@/view/HomeView.vue";
import AboutView from "@/view/AboutView.vue";
import BeautiLogin from "@/view/BeautiLogin.vue";
import RegisterView from "@/view/RegisterView.vue";
import RechargePanel from "@/view/RechargePanel.vue";
import {checkCurrentUserLoginStatus} from "@/services/login.ts";

const routes = [
    { path: "/login", component: BeautiLogin, meta: { requiresAuth: false } },
    { path: "/about", component: AboutView, meta: { requiresAuth: true } },
    { path: "/home", component: HomeView, meta: { requiresAuth: true } },
    { path: "/register", component: RegisterView, meta: { requiresAuth: false } },
    { path: "/deposit", component: RechargePanel, meta: { requiresAuth: true } },
    { path: "/", redirect: "/login" },
];

export const router = createRouter({
    history: createWebHistory(),
    routes,
});


// 全局路由守卫
router.beforeEach(async (to, from, next) => {
    const requiresAuth = to.meta.requiresAuth;

    try {
        const isLoggedIn = await checkCurrentUserLoginStatus();

        if (requiresAuth && !isLoggedIn) {
            next("/login");
        } else if (!requiresAuth && isLoggedIn && to.path === "/login") {
            // 已登录用户不要再去 login 页面
            next("/home");
        } else {
            next();
        }
    } catch (err) {
        // 如果 API 错误，视为未登录
        if (requiresAuth) {
            next("/login");
        } else {
            next();
        }
    }
});
