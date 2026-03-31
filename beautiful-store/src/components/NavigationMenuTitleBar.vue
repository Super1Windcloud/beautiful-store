<script setup lang="ts">
import {onMounted, ref, watch} from "vue";
import {useRouter} from "vue-router";
import {
  DropdownMenu,
  DropdownMenuTrigger,
  DropdownMenuContent,
  DropdownMenuItem,
} from "@/components/ui/dropdown-menu";
import {User, LogOut} from "lucide-vue-next";
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar";
import {loginEmailStore, loginUserTypeStore} from "@/stores/local.ts";
import {getEmailAddress, getGithubUserAvatar, getGoogleUserAvatar} from "@/services/home.ts";
import {getGravatarUrl} from "@/utils";
import {logoutAccount} from "@/services/login.ts";
import {logoutFailedToast} from "@/utils/toast.ts";

const userEmail = ref("");
const emailStore = loginEmailStore();
const router = useRouter();
const userAvator = ref("");
const loginUserType = loginUserTypeStore();
const logout = async () => {
  localStorage.removeItem("savedEmail");
  localStorage.removeItem("savedPassword");
  const result = await logoutAccount(loginUserType.getUserType);
  if (!result) {
    logoutFailedToast();
    return;
  }
  await router.push({path: "/login", query: {fromLogout: "true"}});
};

const goProfile = () => {
  router.push("/about");
};

onMounted(async () => {
  userEmail.value = emailStore.getEmail;

  if (!userEmail.value.trim()) {
    try {
      const result = await getEmailAddress(
          (loginType: string) => loginUserType.setUserType(loginType));
      if (result) {
        console.log("username : ", result);
        userEmail.value = result;
        emailStore.setEmail(result);
      } else {
        console.error("email not found  ,result : " + result);
      }
    } catch (err) {
      console.error(err);
    }
  }
});


const skipToHome = () => {
  router.push("/home");
};

watch(
    () => loginUserType.userType,
    async () => {

      if (loginUserType.getUserType === "email") {
        userAvator.value = await getGravatarUrl(userEmail.value);
        console.log("email avatar: ", userAvator.value);
      } else if (loginUserType.getUserType === "oauth2_github") {
        userAvator.value = await getGithubUserAvatar(userEmail.value);
      } else if (loginUserType.getUserType === "oauth2_google") {
        userAvator.value = await getGoogleUserAvatar(userEmail.value);
      }

    },
);
</script>

<template>
  <div
      class="fixed top-0 right-0 left-0 z-50 flex justify-between items-center"
      style="height: 60px; background-color: #2a7472; padding-right: 120px"
  >
    <div class="flex items-center gap-2" @click.stop="skipToHome">
      <img
          src="../assets/logo.png"
          alt="Logo"
          style="height: 60px; width: 60px"
      />
      <span style="color: white; font-size: 18px; font-weight: bold">
        Beauti Store
      </span>
    </div>

    <DropdownMenu>
      <DropdownMenuTrigger class="trigger-button">
        <Avatar>
          <AvatarImage :src="userAvator" alt="@unovue"/>
          <AvatarFallback>CN</AvatarFallback>
        </Avatar>
        {{ userEmail }}
        <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
          <path
              fill-rule="evenodd"
              d="M5.23 7.21a.75.75 0 011.06.02L10 11.188l3.71-3.957a.75.75 0 011.08 1.04l-4.25 4.53a.75.75 0 01-1.08 0L5.25 8.27a.75.75 0 01-.02-1.06z"
              clip-rule="evenodd"
          />
        </svg>
      </DropdownMenuTrigger>
      <DropdownMenuContent
          style="
          min-width: 300px;
          background-color: #2a7472;
          border-radius: 8px;
          margin-top: 8px;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
        "
      >
        <DropdownMenuItem
            @click="goProfile"
            style="padding: 10px 16px; cursor: pointer; min-width: inherit"
            @mouseover="
            (e) => (e.currentTarget.style.backgroundColor = '#1e5d5b')
          "
            @mouseout="
            (e) => (e.currentTarget.style.backgroundColor = 'transparent')
          "
        >
          <div
              style="
              display: flex;
              align-items: center;
              gap: 10px;
              font-size: 16px;
              font-weight: bold;
              color: white;
              min-width: 200px;
            "
          >
            <User class="w-4 h-4"/>
            个人中心
          </div>
        </DropdownMenuItem>

        <DropdownMenuItem
            @click="logout"
            style="padding: 10px 16px; cursor: pointer"
            @mouseover="
            (e) => (e.currentTarget.style.backgroundColor = '#1e5d5b')
          "
            @mouseout="
            (e) => (e.currentTarget.style.backgroundColor = 'transparent')
          "
        >
          <div
              style="
              display: flex;
              align-items: center;
              gap: 10px;
              font-size: 16px;
              font-weight: bold;
              color: white;
            "
          >
            <LogOut class="w-4 h-4" @click.stop="logout"/>
            登出
          </div>
        </DropdownMenuItem>
      </DropdownMenuContent>
    </DropdownMenu>
  </div>
</template>

<style scoped>
.trigger-button {
  background-color: inherit;
  color: whitesmoke;
  font-weight: bold;
  font-size: large;
  width: 20%;
  height: 80%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.trigger-button:hover {
  background-color: darkgreen;
  cursor: pointer;
}
</style>
