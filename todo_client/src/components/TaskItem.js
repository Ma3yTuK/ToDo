"use client"

import { React, useTransition } from "react";
import { usePathname } from "next/navigation";
import deleteTaskAction from "@/actions/tasks/deleteTaskAction";
import Link from "next/link";
import PropTypes from "prop-types";

export default function TaskItem({ task, updateTasks }) {
    const pathname = usePathname();
    const [isDeletePending, startDeleteTransition] = useTransition();

    function handleDelete(event) {
        event.preventDefault();
        startDeleteTransition(async () => {
            await deleteTaskAction(task.id);
            updateTasks();
        });
    }
    
    return (
        <div>
            <h1>{task.title}</h1>
            <p>{task.description}</p>
            <p>{new Date(task.due.getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 16)}</p>
            <p>{task.status ? "finished" : "not finished"}</p>
            <button disabled={isDeletePending} onClick={handleDelete}>Delete</button>
            <Link href={{
                pathname: `/tasks/update/${task.id}`,
                query: {
                    from: pathname
                }
            }}>
                <button>Update</button>
            </Link>
        </div>
    )
}
TaskItem.propTypes = {
    task: PropTypes.shape({
        id: PropTypes.number,
        title: PropTypes.string,
        description: PropTypes.string,
        due: PropTypes.shape({
            getTime: PropTypes.func
        }),
        status: PropTypes.bool,
    }),
    updateTasks: PropTypes.func
}