import {toast} from "vue-sonner";

export const loginFailedToast = () => {
    return toast.error("Login failed", {
        description: "Please check your credentials and try again",
        position: "top-center",
        duration: 3000,
    });
};

export const copyToast = () => {
    return toast.success("Copied to clipboard", {
        description: "链接已复制到剪贴板",
        position: "top-center",
        duration: 1000,
    });
};

export const accountNotExistsToast = () => {
    return toast.error("Account not exists", {
        description: "账号不存在，请检查邮箱或注册新账号",
        position: "top-center",
        duration: 3000,
    });
}


export const passwordFailedToast = () => {
    return toast.error("Password failed", {
        description: "密码错误，请重新输入",
        position: "top-center",
        duration: 3000,
    });
};

export const logoutFailedToast = () => {
    return toast.error("Logout failed", {
        description: "Logout failed, please try again later",
        position: "top-center",
        duration: 3000,
    });
};
