import Logo from "@/components/logo";
import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {LogInIcon, SearchIcon, ShellIcon} from "lucide-react";
import {ThemeToggle} from "@/components/theme-toggle";

export default function Header() {
    return (
        <>
            <header className="flex  p-4">
                <div className="text-lg font-bold">
                    <Logo/>
                </div>
                <div className="flex-1 flex justify-center items-center space-x-4 mx-4">
                    <Input type="email" placeholder="Search..." className="w-full max-w-xs p-2 border rounded-md"/>
                    <Button variant="outline" size="default">
                        <SearchIcon className="h-6 w-auto"/>
                    </Button>
                </div>
                <nav className="flex space-x-4">
                    <ThemeToggle/>
                    <Button variant="outline" size="default">
                        <LogInIcon/> Sign Up
                    </Button>
                    <Button variant="outline" size="default">
                        <ShellIcon/> Admin
                    </Button>
                </nav>
            </header>
        </>
    );
}