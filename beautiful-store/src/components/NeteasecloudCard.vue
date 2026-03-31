<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import { copylinkToClipboard, getNetEaseCloudPrice } from "@/services/home.ts";
import { loginEmailStore } from "@/stores/local.ts";
import { Toaster } from "vue-sonner";

const neteasePrice = ref(0);
const inputCode = ref("");
const captchaError = ref("");
const emailStore = loginEmailStore();
const realCode = () => {
  return Math.floor(Math.random() * 10000);
};

const captchaCode = ref(0);
onMounted(async () => {
  captchaCode.value = realCode();
  neteasePrice.value = await getNetEaseCloudPrice(emailStore.getEmail);
});

const buyCount = ref(1);
const totalMoney = computed(() => {
  return buyCount.value * neteasePrice.value;
});

function handleWechatPay() {
  if (captchaCode.value !== parseInt(inputCode.value)) {
    captchaError.value = "验证码错误";
  }
}

function handleAlipayPay() {
  if (captchaCode.value !== parseInt(inputCode.value)) {
    captchaError.value = "验证码错误";
  }
}

watch(inputCode, () => {
  captchaError.value = "";
});
</script>

<template>
  <div class="product-page">
    <div class="left">
      <h2 class="title">商品演示图片</h2>
      <img src="../assets/netease.jpg" alt="商品图片" class="product-image" />
    </div>

    <div class="right">
      <h2 class="product-title">网易云黑胶VIP</h2>
      <div class="price">
        商品单价：<span class="text-green-500">¥{{ neteasePrice }}</span>
      </div>
      <div class="status">
        发货方式：<span class="status-tag">在线发货</span>
      </div>
      <div class="share" @click.stop="copylinkToClipboard">
        <span>分享链接给朋友</span>
      </div>
      <div class="form-group">
        <label>购买数量：</label>
        <input v-model="buyCount" pattern="\d*" type="number" class="input" />
      </div>
      <div class="form-group">
        <label>联系电话：</label>
        <input type="text" class="input" />
      </div>

      <div class="form-group">
        <label>绑定账号：</label>
        <input type="text" class="input" />
      </div>

      <div class="form-group">
        <label>人机验证：</label>
        <input
          v-model="inputCode"
          type="text"
          pattern="[0-9]*"
          style="width: 30%"
          class="input"
        />
        <span class="captcha" style="width: 100px">{{ captchaCode }}</span>
        <span style="color: darkred; font-weight: bold">{{
          captchaError
        }}</span>
      </div>

      <div class="total" style="margin-top: 20px">
        <span
          style="
            font-style: normal;
            font-weight: bold;
            font-size: 18px;
            color: lightseagreen;
          "
          >订单金额：</span
        ><strong class="text-pink-500 text-xl">¥{{ totalMoney }}</strong>
      </div>

      <div class="pay-buttons">
        <button @click.stop="handleWechatPay" class="pay-btn wechat">
          <img src="../assets/wechat.png" alt="微信" class="icon" />
          微信支付
        </button>
        <button @click.stop="handleAlipayPay" class="pay-btn alipay">
          <img src="../assets/alipay.png" alt="支付宝" class="icon" />
          支付宝支付
        </button>
      </div>
    </div>
  </div>
  <Toaster richColors />
</template>

<style scoped>
.product-page {
  display: flex;
  padding: 20px;
  color: #fff;
  width: 100%;
  height: 100%;
  background-color: transparent;
  flex-direction: row;
  justify-content: start;
  align-items: start;
  align-self: start;
}

.left {
  width: 40%;
  flex-direction: column;
  justify-content: start;
  align-items: start;
  align-self: start;
  text-align: center;

  .title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 16px;
  }
}

.right {
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  align-self: start;
  width: 60%;
  padding-left: 24px;
  text-align: left;
}

.share {
  color: deepskyblue;
  cursor: pointer;

  :hover {
    text-decoration: underline;
  }
}

.product-image {
  width: 100%;
  border-radius: 12px;
}

.product-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.status-tag {
  background: red;
  padding: 2px 8px;
  border-radius: 10px;
}

.form-group {
  margin-top: 10px;
  max-width: 100%;
  display: flex;
  align-items: center;
  width: 100%;
  justify-content: flex-start;
  border-style: outset;
}

.input {
  background: #222;
  border: 1px solid #555;
  border-radius: 4px;
  padding: 4px 8px;
  color: white;
  width: 60%;
}

.captcha {
  display: inline-block;
  background: whitesmoke;
  color: black;
  padding: 4px 8px;
  text-align: center;
  margin-left: 8px;
  border-radius: 4px;
}

.pay-buttons {
  display: flex;
  gap: 16px;
  margin-top: 20px;
}

.pay-btn {
  display: flex;
  align-items: center;
  padding: 10px 16px;
  border: none;
  border-radius: 8px;
  font-weight: bold;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.pay-btn:hover {
  transform: scale(1.05);
}

.pay-btn .icon {
  width: 24px;
  height: 24px;
  margin-right: 8px;
}

.pay-btn.wechat {
  background-color: #1aad19;
  color: white;
}

.pay-btn.alipay {
  background-color: #007aff;
  color: white;
}

label {
  font-weight: bold;
  font-size: 16px;
  font-style: italic;
  color: floralwhite;
  opacity: 0.8;
}
</style>
