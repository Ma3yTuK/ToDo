'use server';

import properties from "@/properties";
import getAuthHeader from "@/actions/getAuthHeader";
import { processErrorMessage } from "@/helpers/processErrorMessage";

export default async function getTasksAction() {
    let response = await fetch(new URL("/getTasks", properties.api_path), {
        method: "get",
        headers: await getAuthHeader()
    });

    let error;
    let tasks;

    if (response.status >= 300 || response.status < 200) {
        error = await processErrorMessage(response);
        tasks = null;
    } else {
        error = null;
        tasks = await response.json();
    }

    return [error, tasks];
}