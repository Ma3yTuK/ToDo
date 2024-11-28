"use client"

import { React, useTransition } from "react";
import { usePathname, useRouter } from "next/navigation";
import deleteTaskAction from "@/actions/tasks/deleteTaskAction";
import Image from "next/image";
import PropTypes from "prop-types";
import styles from './styles/TaskItem.module.css';
import properties from "@/properties";
import deleteTaskIcon from "@/icons/delete_task.png";
import comletedIcon from "@/icons/completed.webp";
import Link from "next/link";

export default function TaskItem({ task, updateTasks }) {
    const pathname = usePathname();
    const router = useRouter();
    const [isDeletePending, startDeleteTransition] = useTransition();
    
    const formattedDate = new Date(task.due).toLocaleString('en-GB', {
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
                <Link href={{ pathname: properties.routes.update_task + task.id, query: { from: pathname } }} className={styles.description}>
                    {task.description}
                </Link>
            </div>
            <div className={styles.itemFooter}>
                <p className={task.status ? styles.nothing : styles.dueDate} disabled>
                    {formattedDate}
                </p>
                <Image src={comletedIcon} alt="Task completed" className={task.status ? styles.comletedIcon : styles.nothing} />
                <Image src={deleteTaskIcon} alt="Delete Task" className={styles.deleteIcon} onClick={isDeletePending ? null : handleDelete} />
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