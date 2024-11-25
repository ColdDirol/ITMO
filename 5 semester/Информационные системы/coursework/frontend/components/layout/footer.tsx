import {Button} from "@/components/ui/button";
import {HomeIcon, NotebookIcon, UserIcon, WalletIcon} from "lucide-react";

export default function Footer() {
    return (
        <>
            <footer className="flex justify-center items-center p-4 space-x-6">
                <Button variant="outline" size="lg">
                    <HomeIcon className="h-6 w-6"/>
                </Button>

                <Button variant="outline" size="lg">
                    <WalletIcon className="h-6 w-6"/>
                </Button>

                <Button variant="outline" size="lg">
                    <NotebookIcon className="h-6 w-6"/>
                </Button>

                <Button variant="outline" size="lg">
                    <UserIcon className="h-6 w-6"/>
                </Button>
            </footer>
        </>
    );
}