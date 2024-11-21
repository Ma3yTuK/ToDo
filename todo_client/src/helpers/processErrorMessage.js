export function processErrorMessage(response) {
    if (response.status === 401) {
        return "Invalid credentials";
    } else if (response.data.errors !== undefined) {
        return response.data.errors[0].defaultMessage;
    } else {
        return response.data.message;
    }
}