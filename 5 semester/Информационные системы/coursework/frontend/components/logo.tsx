"use client";

import {useTheme} from "next-themes";

export default function Logo() {
    const { theme } = useTheme();

    return (
        <>
            {theme === "light" ? (
                <img src="/black-logo.svg" alt="logo" className="w-32 h-auto" />
            ) : (
                <img src="/white-logo.svg" alt="logo" className="w-32 h-auto" />
            )}
        </>
    );
}