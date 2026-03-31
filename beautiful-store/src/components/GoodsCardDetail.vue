<script setup lang="ts">
import {
  books,
  GamesGoods,
  lifes,
  mediaVips,
  photoCollection,
  softwares,
} from "@/utils";
import { onMounted } from "vue";
import SteamCard from "@/components/SteamCard.vue";
import NeteasecloudCard from "@/components/NeteasecloudCard.vue";
import JbIdeCard from "@/components/JbIdeCard.vue";
import MeiTuanCard from "@/components/MeiTuanCard.vue";
import PiaoCard from "@/components/PiaoCard.vue";
import CuteGirlCard from "@/components/CuteGirlCard.vue";
import { Toaster } from "vue-sonner";

const props = defineProps({
  GoodsName: {
    type: String,
    required: true,
  },
});

onMounted(() => {
  document.documentElement.scrollTop = document.body.scrollHeight;
});
</script>

<template>
  <div class="steam" v-if="props.GoodsName === GamesGoods[0]">
    <SteamCard />
  </div>
  <div class="lol" v-if="props.GoodsName === GamesGoods[1]">
    <SteamCard />
  </div>
  <div class="wangzhe" v-if="props.GoodsName === GamesGoods[2]">
    <SteamCard />
  </div>
  <div class="gameAccount" v-if="props.GoodsName === GamesGoods[3]">
    <SteamCard />
  </div>

  <div class="tencent" v-if="props.GoodsName === mediaVips[0]">
    <NeteasecloudCard />
  </div>
  <div class="aqiyi" v-if="props.GoodsName === mediaVips[1]">
    <NeteasecloudCard />
  </div>
  <div class="qqmusic" v-if="props.GoodsName === mediaVips[2]">
    <NeteasecloudCard />
  </div>
  <div class="neteasecloud" v-if="props.GoodsName === mediaVips[3]">
    <NeteasecloudCard />
  </div>
  <div class="bilibili" v-if="props.GoodsName === mediaVips[4]">
    <NeteasecloudCard />
  </div>
  <div class="netflix" v-if="props.GoodsName === mediaVips[5]">
    <NeteasecloudCard />
  </div>

  <div class="office" v-if="props.GoodsName === softwares[0]">
    <JbIdeCard />
  </div>
  <div class="wps" v-if="props.GoodsName === softwares[1]">
    <JbIdeCard />
  </div>
  <div class="adobe" v-if="props.GoodsName === softwares[2]">
    <JbIdeCard />
  </div>
  <div class="jbide" v-if="props.GoodsName === softwares[3]">
    <JbIdeCard />
  </div>
  <div class="ida" v-if="props.GoodsName === softwares[4]">
    <JbIdeCard />
  </div>
  <div class="iobit" v-if="props.GoodsName === softwares[5]">
    <JbIdeCard />
  </div>
  <div class="typora" v-if="props.GoodsName === softwares[6]">
    <JbIdeCard />
  </div>

  <div class="meituan" v-if="props.GoodsName === lifes[0]">
    <MeiTuanCard />
  </div>
  <div class="eleme" v-if="props.GoodsName === lifes[1]">
    <MeiTuanCard />
  </div>
  <div class="didi" v-if="props.GoodsName === lifes[2]">
    <MeiTuanCard />
  </div>
  <div class="jindong" v-if="props.GoodsName === lifes[3]">
    <MeiTuanCard />
  </div>
  <div class="xiecheng" v-if="props.GoodsName === lifes[4]">
    <MeiTuanCard />
  </div>

  <div class="霍乱时期的爱情" v-if="props.GoodsName === books[0]">
    <PiaoCard />
  </div>
  <div class="活着" v-if="props.GoodsName === books[1]">
    <PiaoCard />
  </div>
  <div class="飘" v-if="props.GoodsName === books[2]">
    <PiaoCard />
  </div>
  <div class="三体" v-if="props.GoodsName === books[3]">
    <PiaoCard />
  </div>
  <div class="沉默的大多数" v-if="props.GoodsName === books[4]">
    <PiaoCard />
  </div>
  <div class="围城" v-if="props.GoodsName === books[5]">
    <PiaoCard />
  </div>
  <div class="双城记" v-if="props.GoodsName === books[6]">
    <PiaoCard />
  </div>
  <div class="追风筝的人" v-if="props.GoodsName === books[7]">
    <PiaoCard />
  </div>
  <div class="百年孤独" v-if="props.GoodsName === books[8]">
    <PiaoCard />
  </div>
  <div class="1984" v-if="props.GoodsName === books[9]">
    <PiaoCard />
  </div>

  <div class="photo" v-if="props.GoodsName === photoCollection[0]">
    <CuteGirlCard />
  </div>
  <div class="girls" v-if="props.GoodsName === photoCollection[1]">
    <CuteGirlCard />
  </div>
  <div class="animation" v-if="props.GoodsName === photoCollection[2]">
    <CuteGirlCard />
  </div>
  <div class="hotgirl" v-if="props.GoodsName === photoCollection[3]">
    <CuteGirlCard />
  </div>
  <div class="nature" v-if="props.GoodsName === photoCollection[4]">
    <CuteGirlCard />
  </div>
  <div class="travel" v-if="props.GoodsName === photoCollection[5]">
    <CuteGirlCard />
  </div>
  <Toaster richColors />
</template>

<style scoped>
.steam,
.lol,
.wangzhe,
.gameAccount,
div {
  position: relative;
  margin-top: 50px;
  width: 100%;
  height: 600px;
  background-color: transparent;
  border-radius: 10px;
  display: flex;
  border-style: outset;
  border-width: 2px;
  border-color: #ccc;
  box-shadow: 0 0 10px forestgreen;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}
</style>
