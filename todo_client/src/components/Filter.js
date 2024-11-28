"use client";

import { React, useState } from "react";
import Image from "next/image";
import { useSearchParams } from 'next/navigation'
import PropTypes from "prop-types";
import styles from './styles/Filter.module.css';
import upArrow from "@/icons/up_arrow.png";
import downArrow from "@/icons/down_arrow.png";


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


export default function Filter({ addSearchParam, resetSearchParams }) {
    const [filters, setFilters] = useState(getFilters(useSearchParams()));

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
        <div className={styles.filter}>
            <div className={styles.filters}>
                <input
                    type="text"
                    value={filters.titleLikeFilter}
                    onChange={handleTitleLikeFilter}
                    placeholder="Search by title"
                    className={styles.inputField}
                />

                <Image className={styles.icon} src={upArrow} alt="up arrow" />
                <input
                    type="datetime-local"
                    value={filters.dueGreaterThanFilter}
                    onChange={handleDueGreaterThanFilter}
                    className={styles.inputField}
                />

                <Image className={styles.icon} src={downArrow} alt="down arrow" />
                <input
                    type="datetime-local"
                    value={filters.dueLessThanFilter}
                    onChange={handleDueLessThanFilter}
                    className={styles.inputField}
                />

                <select value={filters.isStatusFilter} onChange={handleIsStatusFilter} className={styles.select}>
                    <option disabled value="">
                        Select status
                    </option>
                    <option>finished</option>
                    <option>not finished</option>
                </select>

                <button onClick={handleReset} className={styles.resetButton}>
                    Reset
                </button>
            </div>
        </div>
    )
}
Filter.propTypes = {
    addSearchParam: PropTypes.func,
    resetSearchParams: PropTypes.func
}