import { NextResponse } from 'next/server';

export async function middleware(request) {
    let token = request.cookies.get('token');

    if (!token || !token.value) {
        const loginUrl = new URL('/authentication/login', request.url);
        loginUrl.searchParams.set('from', request.nextUrl.pathname);
        return NextResponse.redirect(loginUrl);
    }

    return NextResponse.next();
}

export const config = {
    matcher: [
        '/((?!api|_next/static|_next/image|authentication|favicon.ico|sitemap.xml|robots.txt).*)',
    ],
}