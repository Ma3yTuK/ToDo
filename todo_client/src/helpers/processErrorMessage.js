export async function processErrorMessage(response) {
    if (response.status === 401)
        return "Access denied!";
    
    let responseData = await response.json();
    
    if (responseData.errors !== undefined)
        return responseData.errors[0].defaultMessage;
    
    return responseData.message;
}