<script setup lang="ts">
import {onBeforeUnmount, onMounted, ref, watch} from "vue";
import {requestLoginService} from "@/services/login.ts";
import {useRouter} from "vue-router";
import {loginEmailStore} from "@/stores/local.ts";

import {Toaster} from "vue-sonner";
import "vue-sonner/style.css";
import {loginFailedToast} from "@/utils/toast.ts";

const router = useRouter(); // 只能在Setup 层级中使用
const inputEmail = ref<string>("");
const inputPassword = ref<string>("");
const passwordError = ref("");
const emailError = ref("");
const remember = ref(true);
const loginEmail = loginEmailStore();
const showHiddenPass = (inputPass: string, inputIcon: string) => {
  const input = document.getElementById(inputPass) as HTMLInputElement;
  const iconEye = document.getElementById(inputIcon) as HTMLSpanElement;

  iconEye?.addEventListener("click", () => {
    if (input?.type === "password") {
      input.type = "text";
      iconEye.classList.add("ri-eye-line");
      iconEye.classList.remove("ri-eye-off-line");
    } else {
      input.type = "password";
      iconEye.classList.remove("ri-eye-line");
      iconEye.classList.add("ri-eye-off-line");
    }
  });
};

onMounted(() => {
  if (loginEmail.email) {
    inputEmail.value = loginEmail.email;
  }
  if (loginEmail.password) {
    inputPassword.value = loginEmail.password;
  }

  const savedEmail = localStorage.getItem("savedEmail");
  const savedPassword = localStorage.getItem("savedPassword");

  if (savedEmail && savedPassword) {
    inputEmail.value = savedEmail;
    inputPassword.value = savedPassword;
  }

  showHiddenPass("input-pass", "input-icon");
});



const validateForm = async () => {
  emailError.value = "";
  passwordError.value = "";

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(inputEmail.value)) {
    emailError.value = "请输入合法的邮箱地址";
  }

  if (inputPassword.value.length < 6) {
    passwordError.value = "密码长度不能少于 6 位";
  } else {
    const password = inputPassword.value.trim();
    const regex = /^(?=.*[a-zA-Z])(?=.*\d).+$/;
    if (!regex.test(password)) {
      passwordError.value = "密码必须包含字母和数字";
    }
  }
};

const loginEvent = async (e ?:  Event ) => {
  if (e) e.preventDefault()
  if (!emailError.value && !passwordError.value) {
    if (remember.value) {
      localStorage.setItem("savedEmail", inputEmail.value);
      localStorage.setItem("savedPassword", inputPassword.value);
    } else {
      localStorage.removeItem("savedEmail");
      localStorage.removeItem("savedPassword");
    }
    await requestLoginService(
        inputEmail.value,
        inputPassword.value,
        () => {
          loginEmail.setEmail(inputEmail.value);
          loginEmail.setPassword(inputPassword.value);
          router.push("/home");
        },
        () => {
          loginFailedToast();
        },
    );
  }
};

const handleKeydown = (e: KeyboardEvent) => {
  if (e.key === "Enter") {
    e.preventDefault();
    loginEvent();
  }
};

onMounted(() => {
  window.addEventListener("keydown", handleKeydown);
});

onBeforeUnmount(() => {
  window.removeEventListener("keydown", handleKeydown);
});


const registerEvent = () => {
  router.push("/register");
};

watch([() => inputEmail.value, () => inputPassword.value], () => {
  validateForm();
});

const loginWithGithub = () => {
  window.location.href = "http://localhost:33333/oauth2/authorization/github";
};

const loginWithGoogle = () => {
  window.location.href = "http://localhost:33333/oauth2/authorization/google";
};

// onMounted(async () => {
//   if (route.query.fromLogout === "true") return;
//
//   try {
//     const res = await checkCurrentUserLoginStatus();
//     if (res) {
//       console.log("当前会话已登录");
//       await router.push("/home");
//     }
//   } catch (err: any) {
//     if (err.response?.status === 401) {
//     } else {
//       console.warn("检查登录状态出错：", err);
//     }
//   }
// });


</script>

<template>
  <div class="container">
    <div class="login__content">
      <img src="../assets/bg.png" alt="login image" class="login__img"/>
      <form action="" class="login__form" @submit="loginEvent">
        <div>
          <h1 class="login__title">
            <span style="color: lightpink; font-weight: bold"
            >Welcome Back</span
            >
          </h1>
          <p
              style="color: #d36e9a; font-weight: bold; font-size: large"
              class="login__description"
          >
            美丽店铺
          </p>
        </div>

        <div>
          <div class="login__inputs">
            <div>
              <input
                  type="email"
                  v-model="inputEmail"
                  placeholder="电子邮箱地址"
                  required
                  class="login__input"
                  id="input-email"
              />
              <p
                  v-if="emailError"
                  class="error-msg"
                  style="font-size: small; color: darkred"
              >
                {{ emailError }}
              </p>
            </div>

            <div>
              <div class="login__box">
                <input
                    type="password"
                    v-model="inputPassword"
                    placeholder="密码"
                    required
                    class="login__input"
                    id="input-pass"
                />
                <i class="ri-eye-off-line login__eye" id="input-icon"></i>
              </div>
              <p
                  v-if="passwordError"
                  class="error-msg"
                  style="font-size: small; color: darkred"
              >
                {{ passwordError }}
              </p>
            </div>
          </div>

          <div class="login__check">
            <input
                type="checkbox"
                checked
                style="color: white; border-color: white; border-width: 1px"
                v-model="remember"
                class="login__check-input"
                id="input-check"
            />
            <label for="input-check" class="login__check-label">记住我</label>
          </div>
        </div>

        <div class="login__oauth">
          <button class="oauth-button github" @click="loginWithGithub">
            <i class="ri-github-fill"></i> 使用 GitHub 登录
          </button>
          <button class="oauth-button google" @click="loginWithGoogle">
            <i class="ri-google-fill"></i> 使用 Google 登录
          </button>
        </div>

        <div>
          <div class="login__buttons">
            <button class="login__button" @click.prevent="loginEvent">
              登录
            </button>
            <button
                class="login__button login__button-ghost"
                @click.prevent="registerEvent"
            >
              注册
            </button>
          </div>

          <a href="#" class="login__forgot">忘记密码?</a>
        </div>
      </form>
    </div>
  </div>
  <Toaster richColors closeButton/>
</template>

<style scoped>
.oauth-button {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  margin-bottom: 0.5rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  justify-content: center;
}

.oauth-button.github {
  background-color: #333;
  color: #fff;
}

.oauth-button.google {
  background-color: #fff;
  border: 1px solid #ccc;
  color: #333;
}

#input-email,
#input-pass {
  border: 2px solid red;
}

html,
body {
  height: 100%;
  overflow: hidden;
}

:root {
  --first-color: hsl(244, 75%, 57%);
  --second-color: hsl(249, 64%, 47%);
  --title-color: hsl(244, 12%, 12%);
  --text-color: hsl(244, 4%, 36%);
  --body-color: hsl(208, 97%, 85%);
  --body-font: "Poppins", sans-serif;
  --h2-font-size: 1.25rem;
  --small-font-size: 0.813rem;
  --smaller-font-size: 0.75rem;
  --font-medium: 500;
  --font-semi-bold: 600;
}

@media screen and (min-width: 1024px) {
  :root {
    --h2-font-size: 1.75rem;
    --normal-font-size: 1rem;
    --small-font-size: 0.875rem;
    --smaller-font-size: 0.813rem;
  }
}

* {
  box-sizing: border-box;
  padding: 0;
  margin: 0;
}

body {
  background-color: var(--body-color);
  font-family: var(--body-font), serif;
  color: var(--text-color);
}

input,
button {
  font-family: var(--body-font), "Cascadia Mono", serif;
  border: none;
  outline: none;
}

img {
  max-width: 100%;
  height: auto;
}

/*=============== LOGIN FORM ===============*/
.login__content,
.login__form,
.login__inputs {
  display: grid;
}

.login__content {
  position: relative;
  height: 100%;
  align-items: center;
}

.login__img {
  position: absolute;
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center;
}

.login__form {
  position: relative;
  background-color: hsla(244, 16%, 92%, 0.6);
  border: 2px solid hsla(244, 16%, 92%, 0.75);
  margin-inline: 1.5rem;
  row-gap: 1.25rem;
  backdrop-filter: blur(20px);
  padding: 2rem;
  border-radius: 1rem;
}

.login__title {
  color: var(--title-color);
  font-size: var(--h2-font-size);
  margin-bottom: 0.5rem;
}

.login__title span {
  color: var(--first-color);
}

.login__description {
  font-size: var(--small-font-size);
}

.login__inputs {
  row-gap: 0.75rem;
  margin-bottom: 0.5rem;
}

.login__label {
  display: block;
  color: var(--title-color);
  font-size: var(--small-font-size);
  font-weight: var(--font-semi-bold);
  margin-bottom: 0.25rem;
}

.login__input {
  width: 100%;
  padding: 14px 12px;
  border-radius: 6px;
  border: 2px solid var(--text-color);
  background-color: hsla(244, 16%, 92%, 0.6);
  color: var(--title-color);
  font-size: var(--smaller-font-size);
  font-weight: var(--font-medium);
  transition: border 0.4s;
}

.login__input::placeholder {
  color: var(--text-color);
}

.login__input:focus,
.login__input:valid {
  border: 2px solid var(--first-color);
}

.login__box {
  position: relative;
}

.login__box .login__input {
  padding-right: 36px;
}

.login__eye {
  width: max-content;
  height: max-content;
  position: absolute;
  right: 0.75rem;
  top: 0;
  bottom: 0;
  margin: auto 0;
  font-size: 1.25rem;
  cursor: pointer;
}

.login__check {
  display: flex;
  column-gap: 0.5rem;
  align-items: center;
}

.login__check-input {
  appearance: none;
  width: 16px;
  height: 16px;
  border: 2px solid var(--text-color);
  background-color: hsla(244, 16%, 92%, 0.2);
  border-radius: 0.25rem;
}

.login__check-input:checked {
  background: var(--first-color);
}

.login__check-input:checked::before {
  content: "✔";
  display: block;
  color: #fff;
  font-size: 0.75rem;
  transform: translate(1.5px, -2.5px);
}

.login__check-label {
  font-size: var(--small-font-size);
}

.login__buttons {
  display: flex;
  column-gap: 0.75rem;
}

.login__button {
  width: 100%;
  padding: 14px 2rem;
  border-radius: 6px;
  color: #fff;
  box-shadow: 0 8px 32px rgba(255, 182, 193, 0.5);
  margin-bottom: 1rem;
  cursor: pointer;
  text-align: center;
  font-size: medium;
  font-weight: bold;
}

.login__button:hover {
  transform: scale(1.1); /* 放大按钮 */
  background: linear-gradient(180deg, #ff9eb3, #ff6e9c); /* 改成粉色渐变 */
  box-shadow: 0 10px 36px rgba(255, 182, 193, 0.7); /* 更亮的阴影 */
  transition: all 0.3s ease;
}

.login__button-ghost:hover {
  transform: scale(1.1);
  background: rgba(255, 240, 245, 0.8); /* 粉色背景 */
  color: #d36e9a; /* 粉色文字 */
  border-color: #ff6e9c;
  box-shadow: 0 8px 32px rgba(255, 182, 193, 0.5);
  transition: all 0.3s ease;
}

.login__button-ghost {
  background: hsla(244, 16%, 92%, 0.6);
  border: 2px solid var(--first-color);
  color: var(--first-color);
  cursor: pointer;
  box-shadow: 0 6px 24px rgba(255, 182, 193, 0.5);
}

.login__forgot {
  font-size: var(--smaller-font-size);
  font-weight: var(--font-semi-bold);
  color: var(--first-color);
  text-decoration: none;
}

/*=============== BREAKPOINTS ===============*/
/* For small devices */
@media screen and (max-width: 360px) {
  .login__buttons {
    flex-direction: column;
  }
}

/* For medium devices */
@media screen and (min-width: 576px) {
  .login__form {
    width: 450px;
    justify-self: center;
  }
}

/* For large devices */
@media screen and (min-width: 1064px) {
  .container {
    height: 100%;
    display: grid;
    place-items: center;
  }

  .login__content {
    width: 1024px;
    height: 600px;
  }

  .login__img {
    border-radius: 3.5rem;
    box-shadow: 0 24px 48px hsla(244, 75%, 36%, 0.45);
  }

  .login__form {
    justify-self: flex-end;
    margin-right: 4.5rem;
  }
}

@media screen and (min-width: 1200px) {
  .login__content {
    height: 700px;
  }

  .login__form {
    row-gap: 2rem;
    padding: 3rem;
    border-radius: 1.25rem;
    border: 2.5px solid hsla(244, 16%, 92%, 0.75);
  }

  .login__description,
  .login__label,
  .login__button {
    font-size: var(--normal-font-size);
  }

  .login__inputs {
    row-gap: 1.25rem;
    margin-bottom: 0.75rem;
  }

  .login__input {
    border: 2.5px solid var(--text-color);
    padding: 1rem;
    font-size: var(--small-font-size);
  }

  .login__input:focus,
  .login__input:valid {
    border: 2.5px solid var(--first-color);
  }

  .login__button {
    padding-block: 1rem;
    margin-bottom: 1.25rem;
  }

  .login__button-ghost {
    border: 2.5px solid var(--first-color);
  }
}
</style>
