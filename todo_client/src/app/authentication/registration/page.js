"use client";

import { React, useTransition, useState } from "react";
import registerAction from "@/actions/registerAction";
import { useSearchParams } from 'next/navigation';

export default function Page() {
    const [isPending, startTransition] = useTransition();
    const searchParams = useSearchParams();
    const [error, setError] = useState(null);
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        repeat_password: ''
    });

    function handleSubmit(event) {
        event.preventDefault();
        startTransition(async () => {
            if (formData.password !== formData.repeat_password) {
                setError("Passwords do not match");
                return;
            }
            setError(await registerAction(formData, searchParams.get('from')));
        })
    }

    return (
        <main style={{ padding: '50px' }}>
            <h1>Registration </h1>
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

            <input
                value={formData.repeat_password}
                type="password"
                placeholder="Repeat password"
                name="repeat_password"
                onChange={(e) => setFormData({...formData, repeat_password: e.target.value})}
            />
            <br />
            <br />

            <p aria-live="polite">{error}</p>
            <br />
            
            <button type="submit" disabled={isPending}>Register</button>
            </form>
    
        </main>
    )
}