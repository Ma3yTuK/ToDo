"use client";

import { React, useState, useTransition } from "react";
import logoutAction from "@/actions/logoutAction";
import Link from "next/link";
import { usePathname, useSearchParams } from 'next/navigation'
import PropTypes from "prop-types";


function getFilters(searchParams) {

    let titleLikeFilter;

    if (searchParams.has("titleLikeFilter"))
        titleLikeFilter = searchParams.get("titleLikeFilter");
    else
        titleLikeFilter = ""

    let dueGreaterThanFilter;

    if (searchParams.has("dueGreaterThanFilter"))
        dueGreaterThanFilter = new Date(new Date(searchParams.get("dueGreaterThanFilter")).getTime() - new Date().getTimezoneOffset() * 60000 || 0).toISOString().slice(0, 16);
    else
        dueGreaterThanFilter = "";

    let dueLessThanFilter;

    if (searchParams.has("dueLessThanFilter"))
        dueLessThanFilter = new Date(new Date(searchParams.get("dueLessThanFilter")).getTime() - new Date().getTimezoneOffset() * 60000 || 0).toISOString().slice(0, 16);
    else
        dueLessThanFilter = ""; 

    let isStatusFilter;

    if (searchParams.has("isStatusFilter"))
    {
        if (JSON.parse(searchParams.get("isStatusFilter"))) {
            isStatusFilter = "finished";
        } else {
            isStatusFilter = "not finished";
        }
    } else {
        isStatusFilter = "";
    }

    return {
        titleLikeFilter: titleLikeFilter,
        dueGreaterThanFilter: dueGreaterThanFilter,
        dueLessThanFilter: dueLessThanFilter,
        isStatusFilter: isStatusFilter
    }
}


export default function HomePageHeader({ addSearchParam, resetSearchParams }) {
    const pathname = usePathname();
    const [isLogoutPending, startLogoutTransition] = useTransition();
    const [filters, setFilters] = useState(getFilters(useSearchParams()));

    function handleLogout(event) {
        event.preventDefault();
        startLogoutTransition(async () => {
            await logoutAction();
        })
    }

    function handleTitleLikeFilter(event) {
        event.preventDefault();
        let value = event.target.value;
        setFilters({ ...filters, titleLikeFilter: value });
        addSearchParam("titleLikeFilter", value);
    }

    function handleDueGreaterThanFilter(event) {
        event.preventDefault();
        let value = event.target.value;
        setFilters({ ...filters, dueGreaterThanFilter: new Date(new Date(value).getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 16) });
        addSearchParam("dueGreaterThanFilter", new Date(value).toISOString());
    }

    function handleDueLessThanFilter(event) {
        event.preventDefault();
        let value = event.target.value;
        setFilters({ ...filters, dueLessThanFilter: new Date(new Date(value).getTime() - new Date().getTimezoneOffset() * 60000).toISOString().slice(0, 16) });
        addSearchParam("dueLessThanFilter", new Date(value).toISOString());
    }

    function handleIsStatusFilter(event) {
        event.preventDefault();
        
        let value = event.target.value;
        setFilters({ ...filters, isStatusFilter: value });

        if (value === "finished")
            addSearchParam("isStatusFilter", true);
        else
            addSearchParam("isStatusFilter", false);
    }

    function handleReset() {
        resetSearchParams();
        setFilters(getFilters(new URLSearchParams()));
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

            <div>
                <input type="text" value={filters.titleLikeFilter} onChange={handleTitleLikeFilter} />
                <input type="datetime-local" value={filters.dueGreaterThanFilter} onChange={handleDueGreaterThanFilter} />
                <input type="datetime-local" value={filters.dueLessThanFilter} onChange={handleDueLessThanFilter} />

                <select value={filters.isStatusFilter} onChange={handleIsStatusFilter}>
                    <option disabled style={{display: 'none'}}></option>
                    <option>finished</option>
                    <option>not finished</option>
                </select>

                <button onClick={handleReset}>Reset</button>
            </div>
        </div>
    )
}
HomePageHeader.propTypes = {
    searchParams: PropTypes.shape({
        get: PropTypes.func
    }),
    setSearchParams: PropTypes.func
}