"use client";

import { React, useTransition, useState } from "react";
import registerAction from "@/actions/registerAction";
import styles from "./page.module.css";
import { useSearchParams } from 'next/navigation';
import properties from "@/properties";

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
            setError(await registerAction(formData, searchParams.get('from') || properties.routes.home));
        })
    }

    return (
        <main className={"auth-container"}>
            <div className={"form-wrapper"}>
                <h1 className={"form-title"}>Registration</h1>

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

                    <div className={"input-group"}>
                        <input
                            value={formData.repeat_password}
                            type="password"
                            placeholder="Repeat password"
                            name="repeat_password"
                            onChange={(e) => setFormData({ ...formData, repeat_password: e.target.value })}
                            className={"input-field"}
                        />
                    </div>

                    {error && <p className={"error-message"} aria-live="polite">{error}</p>}

                    <button type="submit" disabled={isPending} className={`${styles.btnRegister} auth-button`}>
                        {isPending ? 'Registering...' : 'Register'}
                    </button>
                </form>
            </div>
        </main>
    )
}