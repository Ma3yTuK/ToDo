"use client";

import { React, useState, useActionState } from "react";
import loginAction from "@/actions/loginAction";
import { useSearchParams } from 'next/navigation'

export default function Page() {
    const searchParams = useSearchParams();
    const [state, action] = useActionState(loginAction, null);
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });

    return (
        <main style={{ padding: '50px' }}>
            <h1>Login </h1>
            <br />
    
            <form action={action}>
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

            <input
                type="hidden"
                name="from_url"
                value={searchParams.get('from') || '/'}
            />

            <p aria-live="polite">{state}</p>
            <br />
            
            <button type="submit">Login</button>
            </form>
    
        </main>
    )
}