'use server';

import properties from "@/properties";
import { saveUserCredentials } from "@/helpers/saveUserCredentials";

export default async function loginAction(userData, redirectUrl) {
    let response = await fetch(new URL("/token", properties.api_path), {
        method: "get",
        headers: {
            "Authorization": "Basic " + btoa(userData.username + ":" + userData.password)
        }
    });

    return await saveUserCredentials(response, redirectUrl);
}