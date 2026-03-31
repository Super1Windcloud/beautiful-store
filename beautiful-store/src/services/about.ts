import axios from "axios";

export async function getCurrentBalance(email: string) {
  const response = await axios.get("/api/about/balance", {
    params: {
      email,
    },
    withCredentials: true,
  });

  const data: string = response.data;
  console.log(data);
  return data;
}
