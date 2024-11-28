'use server';

import properties from "@/properties";
import { saveUserCredentials } from "@/helpers/saveUserCredentials";

export default async function registerAction(userData, redirectUrl) {
    let response = await fetch(new URL("/register", properties.api_path), {
        method: "post",
        body: JSON.stringify(userData),
        headers: {
            "Content-Type": "application/json"
        }
    });

    return await saveUserCredentials(response, redirectUrl);
}