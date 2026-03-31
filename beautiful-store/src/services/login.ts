import axios from "axios";
import {accountNotExistsToast, passwordFailedToast} from "@/utils/toast.ts";

export async function requestLoginService(
    email: string,
    password: string,
    cb: () => void,
    loginToastCb: () => void,
) {
    let result = await loginAccount(email.trim(), password.trim());
    if (!result) {
        loginToastCb();
    } else {
        console.log("login success");
        cb();
    }
}

// 代理URL,必须加/前缀
export async function getRealEmailCode(email: string) {
    const response = await axios.get("/api/register/email", {
        params: {email},
        withCredentials: false,
        responseType: "json",
    });

    const data = response.data;
    if (response.status !== 200) {
        console.error(data.statusText);
        return "";
    }
    console.log(data);
    return data;
}

export async function requestVerifyEmailCode(code: string, email: string) {
    const response = await axios.get("/api/register/email/verify", {
        params: {email, code},
        responseType: "json",
    });
    const data = response.data;
    if (response.status !== 200) {
        console.error(response.statusText);
        return false;
    }
    console.log(data);

    return data === "验证成功";
}

export async function registerAccount(
    email: string,
    password: string,
    cb: () => void,
) {
    const response = await axios.post("/api/register/account", {
        email,
        password,
    });
    const data = response.data;
    if (response.status !== 200) {
        console.error(data.statusText);
        return false;
    }
    console.log(data);
    if (data === "email already exists") {
        cb();
        return false;
    } else if (data === "success create account") {
        return true;
    }
}

export async function loginAccount(email: string, password: string) {
    try {
        const response = await axios.post(
            "/api/login/account",
            {
                email,
                password,
            },
            {
                headers: {
                    Accept: "application/json",
                },
                withCredentials: true,
            },
        );
        const data = response.data.message;
        return data === "登录成功";
    } catch (error: any) {
        if (error.response) {
            const {status, data} = error.response;
            if (status === 404) {
                accountNotExistsToast();
            } else if (status === 401) {

                passwordFailedToast();
            } else {
                alert("登录失败: " + (data.message || "未知错误"));
            }
        } else {
            alert("网络错误，请检查连接");
        }
        throw error;
    }
}

export async function logoutAccount( userType : string ) {
    if (!userType) { return false;  }
    try {
        if ( userType==='email') {
            const response = await axios.post("/api/logout/account");
            const data = response.data;
            if (response.status !== 200) {
                console.error(data.statusText);
                return false;
            }
            console.log("logout email : " + data);
            return true;
        } else {
            const response = await axios.get("/api/logout/oauth2");
            const data = response.data;
            if (response.status !== 200) {
                console.error(data.statusText);
                return false;
            }
            console.log("logout oauth2 : " + data);
            return true;
        }

    } catch (error: any) {
        console.error(error);
        return false;
    }

}


export async function checkCurrentUserLoginStatus() {
    try {
        const response = await axios.get("/api/login/check_status", {withCredentials: true});
        const data = response.data;
        if (data.loggedIn) {
            console.log("✅ 已登录");
            console.log("登录方式:", data.loginType); // "email" 或 "oauth2"
            console.log("用户名:", data.username);
            if (data.provider) {
                console.log("OAuth 提供方:", data.provider); // github 或 google
            }
            return true;
        } else {
            console.log("❌ 未登录");
            return false;
        }

    } catch (error: any) {
        console.error("请求出错:", error);
        return false;
    }
}

