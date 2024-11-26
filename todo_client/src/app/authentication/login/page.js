"use client";

import styles from "./page.module.css";
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
        <main className={styles.loginContainer}>
            <div className={styles.formWrapper}>
                <h1 className={styles.formTitle}>Login</h1>

                <form onSubmit={handleSubmit} className={styles.loginForm}>
                    <div className={styles.inputGroup}>
                        <input
                            value={formData.username}
                            type="text"
                            placeholder="Username"
                            name="username"
                            onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                            className={styles.inputField}
                        />
                    </div>

                    <div className={styles.inputGroup}>
                        <input
                            value={formData.password}
                            type="password"
                            placeholder="Password"
                            name="password"
                            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                            className={styles.inputField}
                        />
                    </div>

                    {error && <p className={styles.errorMessage} aria-live="polite">{error}</p>}

                    <button type="submit" disabled={isPending} className={styles.btnLogin}>
                        {isPending ? 'Logging in...' : 'Login'}
                    </button>

                    <Link
                        href={{
                            pathname: '/authentication/registration',
                            query: searchParams,
                        }}
                    >
                        <button type="button" className={styles.btnRegister}>Register</button>
                    </Link>
                </form>
            </div>
        </main>
    )
}