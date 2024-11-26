"use client";

import { React, useTransition, useState } from "react";
import addTaskAction from "@/actions/tasks/addTaskAction";
import { useSearchParams } from 'next/navigation';
import styles from "./page.module.css";

export default function Page() {
    const [isPending, startTransition] = useTransition();
    const searchParams = useSearchParams();
    const [error, setError] = useState(null);
    const [formData, setFormData] = useState({
        title: '',
        description: '',
        due: new Date(),
        status: false
    });

    function handleSubmit(event) {
        event.preventDefault();
        startTransition(async () => {
            setError(await addTaskAction(formData, searchParams.get('from') || '/'));
        })
    }

    return (
        <main className={styles.main}>
            <h1 className={styles.header}>Add Note</h1>

            <form onSubmit={handleSubmit} className={styles.form}>
                <label htmlFor="title" className={styles.label}>
                    Title:
                </label>
                <input
                    id="title"
                    value={formData.title}
                    type="text"
                    placeholder="Enter title"
                    name="title"
                    onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                    className={styles.input}
                />

                <label htmlFor="description" className={styles.label}>
                    Description:
                </label>
                <textarea
                    id="description"
                    value={formData.description}
                    placeholder="Enter description"
                    name="description"
                    onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                    className={styles.textarea}
                />

                <label htmlFor="due" className={styles.label}>
                    Due Date:
                </label>
                <input
                    id="due"
                    value={new Date(
                        formData.due.getTime() - new Date().getTimezoneOffset() * 60000
                    )
                        .toISOString()
                        .slice(0, 16)}
                    type="datetime-local"
                    name="due"
                    onChange={(e) =>
                        setFormData({ ...formData, due: new Date(e.target.value) })
                    }
                    className={styles.input}
                />

                <div className={styles.statusWrapper}>
                    <label htmlFor="status" className={styles.label}>
                        Status:
                    </label>
                    <input
                        id="status"
                        type="checkbox"
                        name="status"
                        checked={formData.status}
                        onChange={(e) => setFormData({ ...formData, status: e.target.checked })}
                        className={styles.checkbox}
                    />
                    <span className={styles.statusText}>
                        {formData.status ? 'Finished' : 'Not Finished'}
                    </span>
                </div>

                {error && <p aria-live="polite" className={styles.error}>{error}</p>}

                <button type="submit" disabled={isPending} className={styles.button}>
                    Create
                </button>
            </form>
        </main>
    )
}