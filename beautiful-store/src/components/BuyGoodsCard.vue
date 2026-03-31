<script setup lang="ts">
import {
  books,
  GamesGoods,
  GoodsCategory,
  lifes,
  mediaVips,
  photoCollection,
  softwares,
} from "@/utils";
import { Button } from "@/components/ui/button";
import { ref, watch } from "vue";
import GoodsCardDetail from "@/components/GoodsCardDetail.vue";
const selectedCategory = ref<GoodsCategory | null>(null);
const goodsSelected = ref<string>("");
const handleCategoryClick = (category: GoodsCategory) => {
  selectedCategory.value =
    selectedCategory.value === category ? null : category;
};

watch(selectedCategory, () => {
  goodsSelected.value = "";
});
</script>

<template>
  <div class="card-container">
    <div style="font-size: 20px; font-weight: bold; margin-left: 10px">
      🪝购买
    </div>
    <div style="font-size: medium; font-weight: bold; margin-left: 10px">
      请选择商品分类
    </div>
    <div
      class="category-container"
      style="
        display: flex;
        flex-wrap: wrap;
        gap: 12px;
        justify-content: space-around;
        align-items: center;
        margin: 10px 20px 0 10px;
      "
    >
      <div
        class="category-game"
        @click="handleCategoryClick(GoodsCategory.games)"
      >
        <Button
          class="category-button"
          :class="selectedCategory === GoodsCategory.games ? 'selected' : ''"
          variant="destructive"
          >游戏道具/皮肤
        </Button>
      </div>
      <div
        class="category-media"
        @click="handleCategoryClick(GoodsCategory.mediavip)"
      >
        <Button
          class="category-button"
          :class="selectedCategory === GoodsCategory.mediavip ? 'selected' : ''"
          variant="destructive"
          >影音娱乐会员
        </Button>
      </div>
      <div
        class="category-software"
        @click="handleCategoryClick(GoodsCategory.software)"
      >
        <Button
          class="category-button"
          :class="selectedCategory === GoodsCategory.software ? 'selected' : ''"
          variant="destructive"
          >软件/工具
        </Button>
      </div>
      <div
        class="category-life"
        @click="handleCategoryClick(GoodsCategory.life)"
      >
        <Button
          class="category-button"
          :class="selectedCategory === GoodsCategory.life ? 'selected' : ''"
          variant="destructive"
          >生活服务
        </Button>
      </div>

      <div
        class="category-books"
        @click="handleCategoryClick(GoodsCategory.books)"
      >
        <Button
          class="category-button"
          :class="selectedCategory === GoodsCategory.books ? 'selected' : ''"
          variant="destructive"
        >
          图书/资料
        </Button>
      </div>
      <div
        class="category-photoCollection"
        @click="handleCategoryClick(GoodsCategory.photoCollection)"
      >
        <Button
          class="category-button"
          :class="
            selectedCategory === GoodsCategory.photoCollection ? 'selected' : ''
          "
          variant="destructive"
          >美女写真
        </Button>
      </div>
    </div>
    <div
      style="
        font-size: medium;
        font-weight: bold;
        margin-left: 10px;
        margin-top: 10px;
      "
    >
      请选择商品
    </div>
    <div
      class="category-container-goods"
      style="
        display: flex;
        flex-wrap: wrap;
        gap: 12px;
        justify-content: space-around;
        align-items: center;
        margin: 10px 20px 0 10px;
      "
      v-if="selectedCategory === GoodsCategory.games"
    >
      <Button
        @click="goodsSelected = item"
        :class="goodsSelected === item ? 'selected' : ''"
        v-for="(item, index) in GamesGoods"
        :key="index"
      >
        {{ item }}
      </Button>
    </div>
    <div
      class="category-container-goods"
      style="
        display: flex;
        flex-wrap: wrap;
        gap: 12px;
        justify-content: space-around;
        align-items: center;
        margin: 10px 20px 0 10px;
      "
      v-if="selectedCategory === GoodsCategory.mediavip"
    >
      <Button
        @click="goodsSelected = item"
        :class="goodsSelected === item ? 'selected' : ''"
        v-for="(item, index) in mediaVips"
        :key="index"
      >
        {{ item }}
      </Button>
    </div>
    <div
      class="category-container-goods"
      style="
        display: flex;
        flex-wrap: wrap;
        gap: 12px;
        justify-content: space-around;
        align-items: center;
        margin: 10px 20px 0 10px;
      "
      v-if="selectedCategory === GoodsCategory.software"
    >
      <Button
        @click="goodsSelected = item"
        :class="goodsSelected === item ? 'selected' : ''"
        v-for="(item, index) in softwares"
        :key="index"
      >
        {{ item }}
      </Button>
    </div>
    <div
      class="category-container-goods"
      style="
        display: flex;
        flex-wrap: wrap;
        gap: 12px;
        justify-content: space-around;
        align-items: center;
        margin: 10px 20px 0 10px;
      "
      v-if="selectedCategory === GoodsCategory.life"
    >
      <Button
        @click="goodsSelected = item"
        :class="goodsSelected === item ? 'selected' : ''"
        v-for="(item, index) in lifes"
        :key="index"
      >
        {{ item }}
      </Button>
    </div>
    <div
      class="category-container-goods"
      style="
        display: flex;
        flex-wrap: wrap;
        gap: 12px;
        justify-content: space-around;
        align-items: center;
        margin: 10px 20px 0 10px;
      "
      v-if="selectedCategory === GoodsCategory.books"
    >
      <Button
        @click="goodsSelected = item"
        :class="goodsSelected === item ? 'selected' : ''"
        v-for="(item, index) in books"
        :key="index"
      >
        {{ item }}
      </Button>
    </div>
    <div
      class="category-container-goods"
      style="
        display: flex;
        flex-wrap: wrap;
        gap: 12px;
        justify-content: space-around;
        align-items: center;
        margin: 10px 20px 0 10px;
      "
      v-if="selectedCategory === GoodsCategory.photoCollection"
    >
      <Button
        @click="goodsSelected = item"
        :class="goodsSelected === item ? 'selected' : ''"
        v-for="(item, index) in photoCollection"
        :key="index"
      >
        {{ item }}
      </Button>
    </div>
  </div>

  <GoodsCardDetail v-if="goodsSelected" :GoodsName="goodsSelected" />
</template>

<style scoped>
.card-container {
  display: flex;
  height: 200px;
  margin-top: 50px;
  position: relative;
  flex-direction: column;
  align-items: flex-start;
  align-self: flex-start;
  justify-content: flex-start;
  background-color: transparent;
  border: 2px groove white;
  width: inherit;
  filter: drop-shadow(0px 2px 2px hotpink);
  border-radius: 15px;
  box-shadow: 0 0 10px hotpink;
}

.category-books,
.category-game,
.category-media,
.category-software,
.category-life,
.category-photoCollection,
.category-container,
.category-container-goods {
  :hover {
    transform: scale(1.1);
  }
}

.category-button.selected {
  border: 2px dashed forestgreen !important;
}

.category-button,
.category-container,
.category-container-goods {
  border: none !important;
}

.category-container-goods {
  .selected {
    border: 2px dashed deepskyblue !important;
  }
}
</style>
