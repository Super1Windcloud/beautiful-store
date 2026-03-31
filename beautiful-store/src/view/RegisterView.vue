<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { isDev } from "@/utils";
import {
  getRealEmailCode,
  registerAccount,
  requestVerifyEmailCode,
} from "@/services/login.ts";
import { loginEmailStore } from "@/stores/local.ts";

const inputEmail = isDev() ? ref("ss1178933440@gmail.com") : ref("");
const inputPassword = isDev() ? ref("ss1178933440@gmail.com") : ref("");
const confirmPassword = isDev() ? ref("ss1178933440@gmail.com") : ref("");
const remember = isDev() ? ref(true) : ref(false);

const emailError = ref("");
const passwordError = ref("");
const confirmError = ref("");
const captchaError = ref("");
const emailCodeError = ref("");

const inputEmailCode = ref("");

const sendBtnText = ref("发送验证码");
const sendBtnDisabled = ref(false);
const countdown = ref(60);

let timer: ReturnType<typeof setInterval> | null = null;

async function sendEmailCode() {
  if (!inputEmail.value.includes("@")) {
    emailError.value = "请输入有效邮箱地址";
    return;
  }

  getRealEmailCode(inputEmail.value);
  if (isDev()) {
    console.log(`验证码已发送到邮箱：${inputEmail.value}\n`);
  }

  sendBtnDisabled.value = true;
  countdown.value = 60;
  sendBtnText.value = `重新发送(${countdown.value}s)`;

  timer && clearInterval(timer);
  timer = setInterval(() => {
    countdown.value--;
    sendBtnText.value = `重新发送(${countdown.value}s)`;
    if (countdown.value <= 0) {
      clearInterval(timer!);
      sendBtnText.value = "发送验证码";
      sendBtnDisabled.value = false;
    }
  }, 1000);
}

const registerEvent = async () => {
  emailError.value = "";
  passwordError.value = "";
  confirmError.value = "";
  captchaError.value = "";
  emailCodeError.value = "";

  if (!remember.value) {
    emailCodeError.value = "请先同意注册协议";
    return;
  }
  if (!inputEmail.value.includes("@")) {
    emailError.value = "请输入有效邮箱";
  }

  if (inputPassword.value.length < 6) {
    passwordError.value = "密码不能少于6位";
  }

  if (inputPassword.value !== confirmPassword.value) {
    confirmError.value = "两次密码输入不一致";
  }

  if (!inputEmailCode.value) {
    emailCodeError.value = "验证码不能为空";
    return;
  }
  const verifySuccess = await requestVerifyEmailCode(
    inputEmailCode.value.trim(),
    inputEmail.value.trim(),
  );
  if (!verifySuccess) {
    emailCodeError.value = "验证码不正确";
    return;
  }

  if (
    !emailError.value &&
    !passwordError.value &&
    !confirmError.value &&
    !emailCodeError.value &&
    verifySuccess
  ) {
    await goLoginAfterRegister();
  }
};
const router = useRouter();
const loginEmail = loginEmailStore();
const goLogin = () => {
  router.push("/").then(() => {
    loginEmail.setEmail(inputEmail.value);
    loginEmail.setPassword(inputPassword.value);
  });
};

const goLoginAfterRegister = async () => {
  const result = await registerAccount(
    inputEmail.value,
    inputPassword.value,
    () => {
      emailCodeError.value = "邮箱已注册，请登录";
    },
  );
  if (!result) {
    return;
  }
  router.push("/").then(() => {
    loginEmail.setEmail(inputEmail.value);
    loginEmail.setPassword(inputPassword.value);
  });
};
</script>

<template>
  <div class="container">
    <div class="login__content">
      <img src="../assets/bg.png" alt="register image" class="login__img" />
      <form action="" class="login__form">
        <div>
          <h1 class="login__title">
            <span style="color: lightpink; font-weight: bold"
              >Create Account</span
            >
          </h1>
          <p
            style="color: #d36e9a; font-weight: bold; font-size: large"
            class="login__description"
          >
            美丽店铺注册
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
              <input
                type="password"
                v-model="inputPassword"
                placeholder="密码"
                required
                class="login__input"
                id="input-pass"
              />
              <p
                v-if="passwordError"
                class="error-msg"
                style="font-size: small; color: darkred"
              >
                {{ passwordError }}
              </p>
            </div>

            <div>
              <input
                type="password"
                v-model="confirmPassword"
                placeholder="确认密码"
                required
                class="login__input"
                id="confirm-pass"
              />
              <p
                v-if="confirmError"
                class="error-msg"
                style="font-size: small; color: darkred"
              >
                {{ confirmError }}
              </p>
            </div>
          </div>

          <div>
            <div
              class="login__inputs"
              style="
                display: flex;
                align-items: stretch;
                justify-content: space-between;
              "
            >
              <input
                type="text"
                v-model="inputEmailCode"
                placeholder="输入验证码"
                class="login__input"
                id="login-code"
                style="flex: 7; height: 100%"
              />
              <button
                type="button"
                class="login__button"
                id="send-code"
                :disabled="sendBtnDisabled"
                @click="sendEmailCode"
                style="flex: 3; white-space: nowrap"
              >
                {{ sendBtnText }}
              </button>
            </div>
            <p v-if="emailCodeError" style="font-size: small; color: red">
              {{ emailCodeError }}
            </p>
          </div>

          <div class="login__check">
            <input
              type="checkbox"
              checked
              v-model="remember"
              class="login__check-input"
              id="input-check"
            />
            <label for="input-check" class="login__check-label"
              >同意注册协议</label
            >
          </div>
        </div>

        <div>
          <div class="login__buttons">
            <button class="login__button" @click.prevent="registerEvent">
              注册
            </button>
            <button
              class="login__button login__button-ghost"
              @click.prevent="goLogin"
            >
              已有账号？去登录
            </button>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
#input-email,
#input-pass {
  border: 2px solid red;
}

#send-code {
  background-color: #6b1a3e;
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
  font-family: var(--body-font), serif;
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
