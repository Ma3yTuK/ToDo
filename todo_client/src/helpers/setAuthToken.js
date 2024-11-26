import { cookies } from "next/headers";

export async function setAuthToken(token) {
    let cookieStore = await cookies();

    cookieStore.set({
        name: "token",
        value: token,
        httpOnly: true,
        path: "/",
        maxAge: 60 * 60 * 10, // 10 hours
        sameSite: "strict"
    });
}