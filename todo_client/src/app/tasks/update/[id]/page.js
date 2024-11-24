"use client"

import { React, useEffect, useTransition, useState } from "react";
import getTaskAction from "@/actions/tasks/getTaskAction";
import { useSearchParams } from "next/navigation";
import updateTaskAction from "@/actions/tasks/updateTaskAction";
import PropTypes from "prop-types";

export default function Page({ params }) {
    const [isPending, startTransition] = useTransition();
    const searchParams = useSearchParams();
    const [error, setError] = useState("Loading data...");
    const [formData, setFormData] = useState({});

    useEffect(() => {
        params.then(async (queryParams) => {
            let [newError, newTask] = await getTaskAction(queryParams.id);
            newTask.due = new Date(newTask.due);
            setError(newError);
            setFormData(newTask);
        })
    }, []);

    function handleSubmit(event) {
        event.preventDefault();
        startTransition(async () => {
            setError(await updateTaskAction(formData, searchParams.get('from') || '/'));
        })
    }

    if (error)
        return error;

    return (
        <main style={{ padding: '50px' }}>
            <h1>Update note </h1>
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
                checked={formData.status}
                type="checkbox"
                name="status"
                onChange={(e) => setFormData({...formData, status: e.target.checked})}
            />
            <br />
            <br />

            <p aria-live="polite">{error}</p>
            <br />
            
            <button type="submit" disabled={isPending}>Update</button>
            </form>
    
        </main>
    )
}
Page.propTypes = {
    padding: PropTypes.shape({
        then: PropTypes.func
    })
}