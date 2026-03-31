<script setup lang="ts">
import { Button } from "@/components/ui/button";
import { Wallet } from "lucide-vue-next";
import { onMounted, ref } from "vue";
import NavigationMenuTitleBar from "@/components/NavigationMenuTitleBar.vue";
import { getCurrentBalance } from "@/services/about.ts";
import { loginEmailStore } from "@/stores/local.ts";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { useRouter } from "vue-router";

const userEmail = loginEmailStore();
const balanceMoney = ref(0.0);

onMounted(async () => {
  await getCurrentBalance(userEmail.getEmail).then((balance) => {
    if (balance) {
      balanceMoney.value = parseFloat(balance) || 0.0;
      console.log(balanceMoney.value);
    } else {
      balanceMoney.value = 0.0;
    }
  });
});

const router = useRouter();
const handleDepositClick = () => {
  router.push("/deposit");
};

const changeCurrentUserPassword = () => {
  console.log("changeCurrentUserPassword");
};
</script>

<template>
  <NavigationMenuTitleBar />
  <div
    style="
      width: 1400px;
      height: 300px;
      position: relative;
      border: 1px solid #ccc;
      background-color: darkgray;
      margin: 0 auto;
      padding: 20px;
      box-sizing: border-box;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-between;
    "
  >
    <div class="absolute top-4 right-4">
      <Wallet class="w-6 h-6 text-zinc-300" />
    </div>

    <h2 style="font-size: large; font-weight: bold">我的钱包</h2>

    <div class="flex items-end gap-2 mb-6">
      <span class="text-5xl font-bold">{{ balanceMoney }}</span>
      <span class="text-lg text-zinc-400 pb-1">CNY</span>
    </div>

    <Button
      variant="ghost"
      style="
        font-weight: bold;
        font-size: 18px;
        width: 150px;
        height: 50px;
        border-radius: 25px;
        padding: 0 10px;
        box-sizing: border-box;
        background-color: dodgerblue;
      "
      @click.stop="handleDepositClick"
      class="bg-teal-600 hover:bg-teal-700 text-white font-bold"
    >
      充值
    </Button>
  </div>
  <br />
  <br />

  <div
    style="
      width: 1400px;
      height: 400px;
      position: relative;
      border: 1px solid #ccc;
      background-color: darkgray;
      margin: 0 auto;
      padding: 20px;
      box-sizing: border-box;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-between;
    "
  >
    <h2 style="font-size: large; font-weight: bold">修改密码</h2>
    <div class="grid w-full max-w-sm items-center gap-3">
      <Label for="password" style="font-weight: bold; font-size: large"
        >旧密码</Label
      >
      <Input
        style="
          border-width: 2px;
          border-radius: 10px;
          border-spacing: revert;
          border-style: ridge;
          border-color: mediumpurple;
        "
        id="password"
        type="password"
        placeholder="Input Password"
      />
    </div>
    <div class="grid w-full max-w-sm items-center gap-3 mt-2">
      <Label style="font-weight: bold; font-size: large" for="password"
        >新密码</Label
      >
      <Input
        style="
          border-width: 2px;
          border-radius: 10px;
          border-spacing: revert;
          border-style: ridge;
          border-color: mediumpurple;
        "
        id="password"
        type="password"
        placeholder="Input Password"
      />
    </div>
    <div class="grid w-full max-w-sm items-center gap-3 mt-2">
      <Label style="font-weight: bold; font-size: large" for="password"
        >新密码</Label
      >
      <Input
        style="
          border-width: 2px;
          border-radius: 10px;
          border-spacing: revert;
          border-style: ridge;
          border-color: mediumpurple;
        "
        id="password"
        type="password"
        placeholder="Input Password"
      />
    </div>

    <Button
      variant="ghost"
      style="
        font-weight: bold;
        font-size: 18px;
        width: 150px;
        height: 50px;
        border-radius: 25px;
        padding: 0 10px;
        margin-top: 10px;
        box-sizing: border-box;
        background-color: rebeccapurple;
      "
      @click.stop="changeCurrentUserPassword"
    >
      确认修改
    </Button>
  </div>
</template>
