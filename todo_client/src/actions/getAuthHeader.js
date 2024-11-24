"use server"

import { cookies } from "next/headers";

export default async function getAuthHeader() {
    let token = (await cookies()).get("token").value;
    return { 
        'Authorization': `Bearer ${token}`
    };
}