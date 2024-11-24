'use server';

import { processErrorMessage } from "@/helpers/processErrorMessage";
import properties from "@/properties";
import getAuthHeader from "@/actions/getAuthHeader";
import { redirect } from 'next/navigation';

export default async function updateTaskAction(taskData, redirectUrl) {
    let response = await fetch(new URL(`/updateTask/${taskData.id}`, properties.api_path), {
        method: "put",
        body: JSON.stringify(taskData),
        headers: {
            ...await getAuthHeader(),
            "Content-Type": "application/json"
        }
    });

    if (response.status >= 300 || response.status < 200) 
        return await processErrorMessage(response);

    if (redirectUrl)
        return redirect(redirectUrl);
}