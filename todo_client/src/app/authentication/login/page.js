"use client";

import styles from "./page.module.css";
import { React, useTransition, useState } from "react";
import loginAction from "@/actions/loginAction";
import { useSearchParams } from 'next/navigation';
import Link from "next/link";
import properties from "@/properties";


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
            setError(await loginAction(formData, searchParams.get('from') || properties.routes.home));
        })
    }

    return (
        <main className={"auth-container"}>
            <div className={"form-wrapper"}>
                <h1 className={"form-title"}>Login</h1>

                <form onSubmit={handleSubmit}>
                    <div className={"input-group"}>
                        <input
                            value={formData.username}
                            type="text"
                            placeholder="Username"
                            name="username"
                            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                            className={"input-field"}
                        />
                    </div>

                    <div className={"input-group"}>
                        <input
                            value={formData.password}
                            type="password"
                            placeholder="Password"
                            name="password"
                            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                            className={"input-field"}
                        />
                    </div>

                    {error && <p className={"error-message"} aria-live="polite">{error}</p>}

                    <button type="submit" disabled={isPending} className={`${styles.btnLogin} auth-button`}>
                        {isPending ? 'Logging in...' : 'Login'}
                    </button>

                    <Link
                        href={{
                            pathname: properties.routes.register,
                            query: searchParams,
                        }}
                    >
                        <button type="button" className={`${styles.btnRegister} auth-button`}>Register</button>
                    </Link>
                </form>
            </div>
        </main>
    )
}