'use server';

import properties from "@/properties";
import getAuthHeader from "@/actions/getAuthHeader";
import { processErrorMessage } from "@/helpers/processErrorMessage";

export default async function getTasksAction(searchParams) {
    let url = new URL("/getTasks", properties.api_path);
    url.search = searchParams;
    let response = await fetch(url, {
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