import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';
import { setAuthToken } from './helpers/setAuthToken';

export async function middleware(request) {
    let cookieStore = await cookies();
    let token = cookieStore.get('token');

    if (!token.value) {
        const loginUrl = new URL('/authentication/login', request.url);
        loginUrl.searchParams.set('from', request.nextUrl.pathname);
        return NextResponse.redirect(loginUrl);
    }

    setAuthToken(token.value);

    return NextResponse.next();
}

export const config = {
    matcher: [
        '/((?!api|_next/static|_next/image|authentication|favicon.ico|sitemap.xml|robots.txt).*)',
    ],
}