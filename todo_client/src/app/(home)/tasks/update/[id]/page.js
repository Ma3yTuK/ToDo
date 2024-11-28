import TaskSaving from "@/components/TaskSaving";
import { React } from "react";
import getTaskAction from "@/actions/tasks/getTaskAction";
import PropTypes from "prop-types";
import updateTaskAction from "@/actions/tasks/updateTaskAction";

export default async function Page({ params }) {
    let [error, task] = await getTaskAction((await params).id);

    if (error)
        return error;

    return (
        <TaskSaving task={task} message={"Update note"} action={updateTaskAction} />
    )
}
Page.propTypes = {
    params: PropTypes.object,
}