'use server';

import { redirect } from 'next/navigation';
import { deleteAuthToken } from "@/helpers/deleteAuthToken";
import properties from '@/properties';

export default async function logoutAction() {
    await deleteAuthToken();
    return redirect(properties.routes.login);
}