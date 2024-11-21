"use client";

import { React, useState, useActionState } from "react";
import registerAction from "@/actions/registerAction";

export default function Page() {
    const [state, action] = useActionState(registerAction, null);
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });

    return (
        <main style={{ padding: '50px' }}>
            <h1>Registration </h1>
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

            <p aria-live="polite">{state}</p>
            <br />
            
            <button type="submit">Register</button>
            </form>
    
        </main>
    )
}