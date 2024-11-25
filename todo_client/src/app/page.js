"use client"

import styles from "./page.module.css";
import TaskItem from "@/components/TaskItem";
import HomePageHeader from "@/components/HomePageHeader";
import getTasksAction from "@/actions/tasks/getTasksAction";
import { React, useEffect, useState } from "react";
import { useSearchParams, useRouter, usePathname } from "next/navigation";

export default function Home() {
    const [error, setError] = useState("Loading data...");
    const [searchParams, setSearchParams] = useState(useSearchParams());
    const [tasks, setTasks] = useState([]);
    const pathname = usePathname();
    const router = useRouter();
    const [updateSwitcher, setUpdateSwitcher] = useState(false);
    const [isAcive, setIsActive] = useState(false); // Tasks should not be shown when request is processing

    function switcher() { // Starts useEffect
        setUpdateSwitcher(!updateSwitcher);
    }

    function addSearchParam(name, value) {
        let newSearchParams = new URLSearchParams(searchParams.toString());
        newSearchParams.set(name, value);
        setSearchParams(newSearchParams);
    }

    function resetSearchParams() {
        setSearchParams(new URLSearchParams());
    }

    useEffect(() => {
        router.push(`${pathname}?${searchParams.toString()}`, { shallow: true }); // set browser url

        let isCurrentAcive = true; // Individual for every request to cancel it if needed
        setIsActive(true);

        getTasksAction(searchParams.toString()).then(async ([newError, newTasks]) => {
            if (isCurrentAcive) {
                setIsActive(false);
                setError(newError);
                if (!newError)
                    setTasks(newTasks.map(task => {
                        return {...task, due: new Date(task.due)};
                    }));
            }
        });

        return () => {
            isCurrentAcive = false;
        }
    }, [searchParams, updateSwitcher]);

    if (error)
        return error;

    let taskItems;
    if (isAcive)
        taskItems = [];
    else
        taskItems = tasks.map(task => <TaskItem key={task.id} task={task} updateTasks={switcher} />);

    return (
        <>
            {error}
            <HomePageHeader addSearchParam={addSearchParam} resetSearchParams={resetSearchParams} />
            <div className={styles.container}>
                {taskItems}
            </div>
        </>
    );
}