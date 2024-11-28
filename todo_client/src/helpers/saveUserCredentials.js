import { setAuthToken } from "./setAuthToken";
import { deleteAuthToken } from "./deleteAuthToken";
import { processErrorMessage } from "./processErrorMessage";
import { redirect } from 'next/navigation';

export async function saveUserCredentials(response, redirectUrl) {
    if (response.status < 300 && response.status >= 200) {
        let token = await response.text();
        await setAuthToken(token);
        return redirect(redirectUrl || "/");
    } else {
        let error = await processErrorMessage(response);
        await deleteAuthToken();
        return error;
    }
}