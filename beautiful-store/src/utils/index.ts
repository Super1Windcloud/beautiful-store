import md5 from "md5";

export function isChineseLocate(): boolean {
  const language = navigator.language;
  const chineseLangs = ["zh", "zh-CN", "zh-TW", "zh-HK", "zh-Hans", "zh-Hant"];
  return chineseLangs.includes(language);
}

export function isDev(): boolean {
  return import.meta.env.MODE === "development";
}

export enum GoodsCategory {
  games = "games",
  mediavip = "mediavip",
  software = "software",
  life = "life",
  books = "books",
  photoCollection = "photoCollection",
}

export const GamesGoods = ["Steam充值", "lol点卷", "王者皮肤", "成品账号"];

export const mediaVips = [
  "腾讯视频VIP",
  "爱奇艺VIP",
  "qq音乐会员",
  "网易云黑胶",
  "B站大会员",
  "Netflix会员",
];

export const softwares = [
  "Office激活密钥",
  "WPS会员",
  "Adobe激活",
  "JB-IDE全家桶激活",
  "IDA破解",
  "IOBIT破解",
  "Typora破解",
];

export const lifes = ["美团券", "饿了么券", "滴滴券", "京东券", "携程优惠卷"];

export const books = [
  "霍乱时期的爱情",
  "活着",
  "飘",
  "三体",
  "沉默的大多数",
  "围城",
  "双城记",
  "追风筝的人",
  "百年孤独",
  "1984",
];

export const photoCollection = [
  "摄影集",
  "萝莉",
  "动漫",
  "美女",
  "风景",
  "旅行",
];

export async function getGravatarUrl(email: string, size = 80) {
  const trimmedEmail = email.trim().toLowerCase();
  const md5Hash = md5(trimmedEmail); // 直接计算 MD5
  return `https://www.gravatar.com/avatar/${md5Hash}?s=${size}&d=identicon`;
}
