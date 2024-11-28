import { cookies } from "next/headers";

export async function deleteAuthToken() {
    let cookieStore = await cookies();
    cookieStore.delete("token");
}