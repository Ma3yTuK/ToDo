'use server';

import properties from "@/properties";
import getAuthHeader from "@/actions/getAuthHeader";

export default async function getTaskAction(taskId) {
    let response = await fetch(new URL(`/getTask/${taskId}`, properties.api_path), {
        method: "get",
        headers: await getAuthHeader()
    });

    let error;
    let task;

    if (response.status >= 300 || response.status < 200) {
        error = await processErrorMessage(response);
        task = null;
    } else {
        error = null;
        task = await response.json();
    }

    return [error, task];
}