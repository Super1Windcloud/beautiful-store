<script setup lang="ts">
import NavigationMenuTitleBar from "@/components/NavigationMenuTitleBar.vue";
import { ref } from "vue";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogTitle,
  DialogDescription,
} from "@/components/ui/dialog";

const amount = ref("");
const paymentMethod = ref("wechat");
const showDialog = ref(false);

const confirmRecharge = () => {
  if (!amount.value || parseFloat(amount.value) <= 0) {
    alert("请输入有效金额");
    return;
  }
  showDialog.value = true;
};
</script>

<template>
  <NavigationMenuTitleBar />
  <div
    style="
      display: flex;
      flex-direction: column;
      align-items: stretch;
      justify-content: space-around;
      height: 500px;
    "
  >
    <h2 class="text-xl font-bold text-center">充值中心</h2>

    <div class="space-y-2">
      <label class="font-semibold text-gray-700">充值金额 (元)</label>
      <Input
        v-model="amount"
        type="number"
        placeholder="输入充值金额"
        class="w-full"
      />
    </div>

    <div class="space-y-2">
      <RadioGroup v-model="paymentMethod" class="flex gap-4">
        <label
          class="flex items-center gap-2 border p-2 rounded cursor-pointer"
          :class="{ 'border-teal-600 bg-teal-50': paymentMethod === 'wechat' }"
        >
          <RadioGroupItem value="wechat" />
          <img
            src="https://img.icons8.com/color/48/wechat.png"
            class="w-5 h-5"
            alt="wechat"
          />
          微信支付
        </label>

        <label
          class="flex items-center gap-2 border p-2 rounded cursor-pointer"
          :class="{ 'border-teal-600 bg-teal-50': paymentMethod === 'alipay' }"
        >
          <RadioGroupItem value="alipay" />
          <img
            src="https://img.icons8.com/color/48/alipay.png"
            class="w-5 h-5"
            alt="alipay"
          />
          支付宝
        </label>
      </RadioGroup>
    </div>

    <Dialog v-model:open="showDialog">
      <DialogTrigger as-child>
        <Button
          class="w-full bg-teal-600 hover:bg-teal-700 text-white font-bold"
          @click="confirmRecharge"
        >
          确认充值
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogTitle>请确认支付信息</DialogTitle>
        <DialogDescription>
          <p>
            充值金额：<strong>{{ amount }} 元</strong>
          </p>
          <p>
            支付方式：<strong>{{
              paymentMethod === "wechat" ? "微信" : "支付宝"
            }}</strong>
          </p>
        </DialogDescription>
      </DialogContent>
    </Dialog>
  </div>
</template>

<style scoped></style>
