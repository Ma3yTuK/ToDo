"use client"

import { React, useTransition } from "react";
import styles from "@/components/styles/Header.module.css";
import properties from "@/properties";
import logoutAction from "@/actions/logoutAction";
import logoutIcon from "@/icons/logout.webp";
import Image from "next/image";
import Link from "next/link";
import { usePathname } from "next/navigation";

export default function Header() {
    const pathname = usePathname();
    const [isLogoutPending, startLogoutTransition] = useTransition();

    function handleLogout(event) {
        event.preventDefault();
        startLogoutTransition(async () => {
            await logoutAction();
        })
    }

    return (
        <div className={styles['header']}>
            <Link href={properties.routes.home} className={styles['logo']}>ToDo</Link>
            <Link href={properties.routes.home} className={`${pathname == properties.routes.home && styles['active']}`}>Home</Link>
            <div className={styles['header-right']}>
                <button onClick={handleLogout} disabled={isLogoutPending}>
                    Logout
                    <Image src={logoutIcon} className={styles['icon']} alt="Logout icon" />
                </button>
            </div>
        </div> 
    )
}