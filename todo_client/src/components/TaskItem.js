"use client"

import { React, useTransition } from "react";
import { usePathname } from "next/navigation";
import deleteTaskAction from "@/actions/tasks/deleteTaskAction";
import Link from "next/link";
import PropTypes from "prop-types";
import styles from './styles/TaskItem.module.css';

export default function TaskItem({ task, updateTasks }) {
    const pathname = usePathname();
    const [isDeletePending, startDeleteTransition] = useTransition();
    
    const formattedDate = new Date(task.due).toLocaleString('en-US', {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
    });

    function handleDelete(event) {
        event.preventDefault();
        startDeleteTransition(async () => {
            await deleteTaskAction(task.id);
            updateTasks();
        });
    }
    
    return (
        <div className={styles.taskItem}>
            <div className={styles.content}>
                <h1 className={styles.title}>{task.title}</h1>
                <p className={styles.label}>
                    <strong>Description:</strong>
                    <span className={styles.description}>{task.description}</span>
                </p>
                <p className={styles.label}>
                    <strong>Due Date:</strong>
                    <span className={styles.dueDate}>{formattedDate}</span>
                </p>
                <p className={styles.label}>
                    <strong>Status:</strong>
                    <span
                        className={
                            task.status
                                ? `${styles.status} ${styles.finished}`
                                : `${styles.status} ${styles.notFinished}`
                        }
                    >
                        {task.status ? 'Finished' : 'Not Finished'}
                    </span>
                </p>
            </div>
            <div className={styles.actions}>
                <button
                    disabled={isDeletePending}
                    onClick={handleDelete}
                    className={styles.deleteButton}
                >
                    Delete
                </button>
                <Link href={{ pathname: `/tasks/update/${task.id}`, query: { from: pathname } }}>
                    <button className={styles.updateButton}>Update</button>
                </Link>
            </div>
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