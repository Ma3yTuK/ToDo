'use server';

import { cookies } from "next/headers";
import { redirect } from 'next/navigation';

export default async function logoutAction() {
    let cookieStore = await cookies();
    cookieStore.delete("token");

    return redirect("/authentication/login");
}