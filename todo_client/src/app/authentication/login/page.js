"use client";

import { React, useTransition, useState } from "react";
import loginAction from "@/actions/loginAction";
import { useSearchParams } from 'next/navigation';
import Link from "next/link";

export default function Page() {
    const [isPending, startTransition] = useTransition();
    const searchParams = useSearchParams();
    const [error, setError] = useState(null);
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });

    function handleSubmit(event) {
        event.preventDefault();
        startTransition(async () => {
            setError(await loginAction(formData, searchParams.get('from')));
        })
    }

    return (
        <main style={{ padding: '50px' }}>
            <h1>Login </h1>
            <br />
    
            <form onSubmit={handleSubmit}>
            <input
                value={formData.username}
                type="text"
                placeholder="Username"
                name="username"
                onChange={(e) => setFormData({...formData, username: e.target.value})}
            />
            <br />
            <br />
    
            <input
                value={formData.password}
                type="password"
                placeholder="Password"
                name="password"
                onChange={(e) => setFormData({...formData, password: e.target.value})}
            />
            <br />
            <br />

            <p aria-live="polite">{error}</p>
            <br />
            
            <button type="submit" disabled={isPending}>Login</button>

            <Link href={{
                pathname: '/authentication/registration',
                query: searchParams
            }}>
                <button>Register</button>
            </Link>

            </form>
        </main>
    )
}