<script setup lang="ts">
import {Card, CardContent} from "@/components/ui/card";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";
import {onMounted, ref} from "vue";
import {isDev} from "@/utils";

const pictures = ref<string[]>([]);
import Autoplay from "embla-carousel-autoplay";
import {fetchPictures} from "@/services/home.ts";

const plugin = Autoplay({
  delay: 2000,
  stopOnMouseEnter: true,
  stopOnInteraction: false,
});

onMounted(async () => {
  const modules = isDev() ? import.meta.glob("../assets/girl*.jpg", {
    eager: true, // 静态导入资源
    import: 'default',
  }) : null;
  if (!modules) {
    try {
      pictures.value = await fetchPictures();
    } catch (error) {
      console.error("Error fetching pictures", error);
    }
    return;
  }
  if (isDev()) {
    console.log(modules);
  }
  for (let path in modules) {
    path = new URL(path, import.meta.url).href;
    pictures.value = [...pictures.value, path];
  }
  console.log(pictures.value);
});
</script>

<template>
  <Carousel
      style="margin-left: 50px"
      class="relative w-full max-w-xs"
      :plugins="[plugin]"
      @mouseenter="plugin.stop"
      @mouseleave="[plugin.reset(), plugin.play(), console.log('Running')]"
  >
    <CarouselContent>
      <CarouselItem v-for="(img, index) in pictures" :key="index">
        <Card class="border-0">
          <CardContent
              class="flex aspect-square items-center justify-center p-0"
          >
            <span class="text-4xl font-semibold">
              <img
                  :src="img"
                  :alt="index.toString()"
                  class="w-full h-full object-cover product-image"
              />
            </span>
          </CardContent>
        </Card>
      </CarouselItem>
    </CarouselContent>
    <CarouselPrevious/>
    <CarouselNext/>
  </Carousel>
</template>

<style scoped>
.product-image {
  width: inherit;
  height: inherit;
  object-fit: cover;
  object-position: center;
  display: block;
}
</style>
