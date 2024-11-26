"use client";

import { React, useTransition, useState } from "react";
import registerAction from "@/actions/registerAction";
import styles from "./page.module.css";
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
        <main className={styles.registrationContainer}>
            <div className={styles.formWrapper}>
                <h1 className={styles.formTitle}>Registration</h1>

                <form onSubmit={handleSubmit} className={styles.registrationForm}>
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

                    <div className={styles.inputGroup}>
                        <input
                            value={formData.repeat_password}
                            type="password"
                            placeholder="Repeat password"
                            name="repeat_password"
                            onChange={(e) => setFormData({ ...formData, repeat_password: e.target.value })}
                            className={styles.inputField}
                        />
                    </div>

                    {error && <p className={styles.errorMessage} aria-live="polite">{error}</p>}

                    <button type="submit" disabled={isPending} className={styles.btnRegister}>
                        {isPending ? 'Registering...' : 'Register'}
                    </button>
                </form>
            </div>
        </main>
    )
}