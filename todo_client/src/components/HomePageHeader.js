"use client";

import { React, useTransition, useState } from "react";
import logoutAction from "@/actions/logoutAction";
import Link from "next/link";
import { usePathname } from 'next/navigation'

export default function HomePageHeader({ updateTasks }) {
    const pathname = usePathname();
    const [isLogoutPending, startLogoutTransition] = useTransition();

    function handleLogout(event) {
        event.preventDefault();
        startLogoutTransition(async () => {
            await logoutAction();
        })
    }

    return (
        <div>
            <button disabled={isLogoutPending} onClick={handleLogout}>Logout</button>
            <Link href={{
                pathname: "/tasks/create",
                query: {
                    from: pathname
                }
            }}>
                <button>Add</button>
            </Link>
        </div>
    )
}