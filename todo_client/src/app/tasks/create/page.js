"use client";

import { React, useTransition, useState } from "react";
import addTaskAction from "@/actions/tasks/addTaskAction";
import { useSearchParams } from 'next/navigation';

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
        <main style={{ padding: '50px' }}>
            <h1>Add note </h1>
            <br />
    
            <form onSubmit={handleSubmit}>
            <input
                value={formData.title}
                type="text"
                placeholder="Title"
                name="title"
                onChange={(e) => setFormData({...formData, title: e.target.value})}
            />
            <br />
            <br />
    
            <textarea
                value={formData.description}
                placeholder="Description"
                name="description"
                onChange={(e) => setFormData({...formData, description: e.target.value})}
            />
            <br />
            <br />

            <input
                value={new Date(formData.due.getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 16)}
                type="datetime-local"
                name="due"
                onChange={(e) => setFormData({...formData, due: new Date(e.target.value)})}
            />
            <br />
            <br />

            <input
                value={formData.status}
                type="checkbox"
                name="status"
                onChange={(e) => setFormData({...formData, status: e.target.checked})}
            />
            <br />
            <br />

            <p aria-live="polite">{error}</p>
            <br />
            
            <button type="submit" disabled={isPending}>Create</button>
            </form>
    
        </main>
    )
}