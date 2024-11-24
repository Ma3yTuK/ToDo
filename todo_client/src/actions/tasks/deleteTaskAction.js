'use server';

import { processErrorMessage } from "@/helpers/processErrorMessage";
import properties from "@/properties";
import getAuthHeader from "@/actions/getAuthHeader";
import { redirect } from 'next/navigation';

export default async function deleteTaskAction(taskId, redirectUrl) {
    let response = await fetch(new URL(`/deleteTask/${taskId}`, properties.api_path), {
        method: "delete",
        headers: await getAuthHeader()
    });

    if (response.status >= 300 || response.status < 200) 
        return await processErrorMessage(response);

    if (redirectUrl)
        return redirect(redirectUrl);
}