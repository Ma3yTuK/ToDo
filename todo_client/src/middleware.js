import { NextResponse } from 'next/server';
import properties from './properties';

export async function middleware(request) {
    let token = request.cookies.get('token');

    if (!token?.value) {
        const loginUrl = new URL(properties.routes.login, request.url);
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