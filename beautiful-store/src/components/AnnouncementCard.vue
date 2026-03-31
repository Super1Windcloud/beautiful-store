<script setup lang="ts">
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
</script>

<template>
  <div class="page-container">
    <Card
      style="
        width: 80dvw;
        height: 300px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: stretch;
        background-color: #fff;

        filter: drop-shadow(0px 4px 4px lightpink);
      "
    >
      <CardHeader>
        <CardTitle> 📢 公告</CardTitle>
        <CardDescription
          style="font-size: 16px; font-weight: bold; color: lightgreen"
          >Github<a
            href="https://github.com/super1windcloud/beauti-store"
            target="_blank"
            class="underline"
          >
            https://github.com/super1windcloud/beauti-store
          </a></CardDescription
        >
      </CardHeader>
      <CardContent
        class="holographic-container"
        style="font-weight: bold; font-size: large"
      >
        <div class="holographic-card">欢迎来到Beauti-Store 个人商城</div>
      </CardContent>
      <CardFooter class="hover-underline"
        ><a href="https://github.com/super1windcloud" target="_blank">
          SuperWindCloud
        </a></CardFooter
      >
    </Card>
  </div>
</template>

<style scoped>
.page-container {
  display: flex;
  position: relative;
  margin-top: 50px;
  justify-content: start;
  align-items: flex-start;
  align-self: flex-start;
  height: inherit;
  background-color: transparent;
}

.hover-underline {
  font-size: 2rem;
  color: #ffffff;
  position: relative;
  display: inline-block;
}

.hover-underline::after,
.hover-underline::before {
  content: "";
  position: absolute;
  width: 100%;
  height: 2px;
  background: linear-gradient(to right, #ff0000, #00ffff);
  bottom: -5px;
  left: 0;
  transform: scaleX(0);
  transform-origin: right;
  transition: transform 0.4s ease-out;
}

.hover-underline::before {
  top: -5px;
  transform-origin: left;
}

.hover-underline:hover::after,
.hover-underline:hover::before {
  transform: scaleX(1);
}

.holographic-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: inherit;
  background: transparent;
}

.holographic-card {
  width: 300px;
  height: 100px;
  background: transparent;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
  border-radius: 15px;
  transition: all 0.5s ease;
}

.holographic-card h2 {
  color: #0ff;
  font-size: 2rem;
  position: relative;
  z-index: 2;
}

.holographic-card::before {
  content: "";
  position: absolute;
  top: -50%;
  left: -50%;
  width: 200%;
  height: 200%;
  background: linear-gradient(
    0deg,
    transparent,
    transparent 30%,
    rgba(0, 255, 255, 0.3)
  );
  transform: rotate(-45deg);
  transition: all 0.5s ease;
  opacity: 0;
}

.holographic-card:hover {
  transform: scale(1.05);
  box-shadow: 0 0 20px rgba(0, 255, 255, 0.5);
}

.holographic-card:hover::before {
  opacity: 1;
  transform: rotate(-45deg) translateY(100%);
}
</style>
