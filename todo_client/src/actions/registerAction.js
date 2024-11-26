'use server';

import { processErrorMessage } from "@/helpers/processErrorMessage";
import { setAuthToken } from "@/helpers/setAuthToken";
import properties from "@/properties";
import { redirect } from 'next/navigation';

export default async function registerAction(userData, redirectUrl) {
    let response = await fetch(new URL("/register", properties.api_path), {
        method: "post",
        body: JSON.stringify(userData),
        headers: {
            "Content-Type": "application/json"
        }
    });

    let token;
    let error;

    if (response.status < 300 && response.status >= 200) {
        token = await response.text();
    } else {
        error = await processErrorMessage(response);
    }

    setAuthToken(token);

    if (error) {
        return error;
    }

    return redirect(redirectUrl || "/");
}