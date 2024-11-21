'use server';

import { processErrorMessage } from "@/helpers/processErrorMessage";
import { setAuthToken } from "@/helpers/setAuthToken";
import { cookies } from "next/headers";
import properties from "@/properties";
import axios from "axios";
import { redirect } from 'next/navigation';

export default async function registerAction(prev, formData) {
    let response = axios.post(new URL("/register", properties.api_path), Object.fromEntries(formData), { validateStatus: false });

    let cookieStore = cookies();
    let token;
    let error;

    response = await response;
    if (response.status < 300 && response.status >= 200) {
        token = response.data;
    } else {
        error = processErrorMessage(response);
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
    setAuthToken(token);

    if (error) {
        return error;
    }

    return redirect("/");
}