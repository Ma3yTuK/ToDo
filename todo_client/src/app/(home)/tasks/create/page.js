import TaskSaving from "@/components/TaskSaving";
import addTaskAction from "@/actions/tasks/addTaskAction";

export default function Page() {
    const task = {
        title: '',
        description: '',
        due: new Date().toISOString(),
        status: false
    };

    return (
        <TaskSaving task={task} message={"Add note"} action={addTaskAction} />
    )
}