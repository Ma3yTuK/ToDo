'use server';

import { processErrorMessage } from "@/helpers/processErrorMessage";
import { cookies } from "next/headers";
import properties from "@/properties";
import { redirect } from 'next/navigation';

export default async function loginAction(userData, redirectUrl) {
    let response = await fetch(new URL("/token", properties.api_path), {
        method: "get",
        headers: {
            "Authorization": "Basic " + btoa(userData.username + ":" + userData.password)
        }
    });

    let cookieStore = cookies();
    let token;
    let error;

    if (response.status < 300 && response.status >= 200) {
        token = await response.text();
    } else {
        error = await processErrorMessage(response);
    }

    cookieStore = await cookieStore;
    cookieStore.set({
        name: "token",
        value: token,
        httpOnly: true,
        path: "/",
        maxAge: 60 * 60 * 10, // 10 hours
        sameSite: "strict"
    });

    if (error) {
        return error;
    }

    return redirect(redirectUrl || "/");
}