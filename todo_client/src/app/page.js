"use client"

import styles from "./page.module.css";
import TaskItem from "@/components/TaskItem";
import HomePageHeader from "@/components/HomePageHeader";
import getTasksAction from "@/actions/tasks/getTasksAction";
import { React, useEffect, useState } from "react";

export default function Home() {
    const [error, setError] = useState("Loading data...");
    const [tasks, setTasks] = useState([]);

    async function updateTasks() {
        getTasksAction().then(async ([newError, newTasks]) => {
            setError(newError);
            if (!newError)
                setTasks(newTasks.map(task => {
                    return {...task, due: new Date(task.due)};
                }));
        });
    }

    useEffect(() => {
        updateTasks();
    }, []);

    if (error)
        return error;

    let taskItems = tasks.map(task => <TaskItem key={task.id} task={task} updateTasks={updateTasks} />);

    return (
        <>
            {error}
            <HomePageHeader updateTasks={updateTasks} />
            <div className={styles.container}>
                {taskItems}
            </div>
        </>
    );
}