import axios from "axios";
import {copyToast} from "@/utils/toast.ts";

export async function getHelloMessage() {
    const message = await axios.get("/api/home/hello", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
    });

    console.log(message.data);
    await getEmailAddress();
}

export async function getEmailAddress(setLoginTypeCb  ?: Function) {
    try {
        const userInfo = await getLoginUserInformation();
        const loginType = userInfo.loginType;
        if (loginType && setLoginTypeCb) {
            setLoginTypeCb(loginType)
        }
        if (loginType === "email") {
            return userInfo.email;
        } else if (loginType === "oauth2_github") {
            return userInfo.username;

        } else if (loginType === "oauth2_google") {
            return userInfo.username;
        }
    } catch
        (error) {
        console.error("读取登录信息失败", error);
        const email = await axios.get("/api/home/email", {
            withCredentials: true,
            headers: {
                "Content-Type": "application/json",
            },
        });

        return email.data;
    }


}

export async function getLoginUserInformation() {
    const userInformation = await axios.get("/api/home/login_user_info", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
    });

    const data = userInformation.data;
    console.log(data);

    return data; // axios 自动返回JSON对象
}


export async function getSteamPrice(email: string) {
    console.log("current email: ", email);
    const steamPrice = await axios.get("/api/home/goods/steam", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            email: email,
        },
    });
    return steamPrice.data;
}

export async function getNetEaseCloudPrice(email: string) {
    console.log("current email: ", email);
    const netEaseCloudPrice = await axios.get("/api/home/goods/neteasecloud", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            email: email,
        },
    });
    return netEaseCloudPrice.data;
}

export async function getJbIdePrice(email: string) {
    console.log("current email: ", email);
    const jbIdeaPrice = await axios.get("/api/home/goods/jbide", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            email: email,
        },
    });
    return jbIdeaPrice.data;
}

export function copylinkToClipboard() {
    let url = window.location.href;
    navigator.clipboard.writeText(url).then(() => {
        copyToast();
    });
}

export async function getXieChengPrice(email: string) {
    const response = await axios.get("/api/home/goods/xiecheng", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            email: email,
        },
    });
    return response.data;
}

export async function getPiaoPrice(email: string) {
    const response = await axios.get("/api/home/goods/piao", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            email: email,
        },
    });
    return response.data;
}

export async function getGirlPhotoPrice(email: string) {
    const response = await axios.get("/api/home/goods/girl", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
        params: {
            email: email,
        },
    });
    return response.data;
}


export async function fetchPictures() {
    const pictures = await axios.get("/api/home/goods/girl-imgs-db", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        }
    })

    const data: string [] = pictures.data;
    return data;
}


export async function getGithubUserAvatar(username ?: string) {
    if (!username) {
    }
    const response = await axios.get("/api/oauth2/github/userinfo", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
    });

    const data = response.data;
    console.log("github user info: ", data);
    return data[1];
}


export async function getGoogleUserAvatar(username?: string) {
    if (!username) {
    }
    const response = await axios.get("/api/oauth2/google/userinfo", {
        withCredentials: true,
        headers: {
            "Content-Type": "application/json",
        },
    });
    const data = response.data;
    console.log("google user info: ", data);
    return data[1];

}
