<!-- LoginPage.vue -->
<script setup lang="ts">
import {ref} from 'vue'
import {Input} from '@/components/ui/input'
import {Button} from '@/components/ui/button'
import {Card, CardHeader, CardTitle, CardContent, CardFooter} from '@/components/ui/card'
import {Label} from '@/components/ui/label'
import {Toaster} from '@/components/ui/sonner'
import 'vue-sonner/style.css'
import {toast} from 'vue-sonner'

const email = ref('')
const password = ref('')
const isLoading = ref(false)
const errorMessage = ref('')

const onLogin = async () => {
  isLoading.value = true
  errorMessage.value = ''

  await new Promise(resolve => setTimeout(resolve, 1000))

  if (email.value !== 'admin@example.com' || password.value !== '123456') {
    errorMessage.value = '邮箱或密码错误'
    toast({title: '登录失败', description: '邮箱或密码不正确', variant: 'destructive'})
  } else {
    toast({title: '登录成功', description: '欢迎回来！'})
    // 跳转逻辑
    // router.push('/')
  }

  isLoading.value = false
}
</script>

<template>
  <div
      class="flex  h-screen items-center justify-center bg-background text-foreground p-4">
    <Card class="w-full max-w-sm shadow-xl">
      <CardHeader>
        <CardTitle class="text-center text-2xl">异次元店铺</CardTitle>
      </CardHeader>
      <CardContent class="space-y-4">
        <div>
          <Label for="email">邮箱</Label>
          <Input id="email" type="email" v-model="email" placeholder="you@example.com"/>
        </div>

        <div>
          <Label for="password">密码</Label>
          <Input id="password" type="password" v-model="password" placeholder="******"/>
        </div>

        <p v-if="errorMessage" class="text-sm text-red-500">{{ errorMessage }}</p>
      </CardContent>

      <CardFooter>
        <Button class="w-full" :loading="isLoading" @click="onLogin">
          登录
        </Button>
      </CardFooter>
    </Card>
  </div>
  <Toaster/>
</template>
