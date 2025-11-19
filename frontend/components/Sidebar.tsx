"use client";

import Image from "next/image";
import Link from "next/link";
import { usePathname } from "next/navigation";
import { Sword, Trophy, Map, BookOpen, User, Home, FileText } from "lucide-react";
import { clsx } from "clsx";

const MENU_ITEMS = [
    { name: "首頁", href: "/", icon: Home },
    { name: "課程", href: "/courses", icon: BookOpen },
    { name: "個人檔案", href: "/profile", icon: User, withDivider: true },
    { name: "排行榜", href: "/ranking", icon: Trophy },
    { name: "獎勵任務", href: "/missions", icon: Sword },
    { name: "挑戰歷程", href: "/history", icon: FileText, withDivider: true },
    { name: "所有單元", href: "/lessons", icon: BookOpen },
    { name: "挑戰地圖", href: "/map", icon: Map },
    { name: "SOP 寶典", href: "/sop", icon: BookOpen },
];

export default function Sidebar() {
    const pathname = usePathname();

    return (
        <aside className="w-64 h-screen bg-wb-gray border-r border-gray-800 flex flex-col fixed left-0 top-0 overflow-y-auto z-50">
            {/* --- 1. Logo 區塊 (視覺裁切術) --- */}
            <div className="p-6 pt-8 pb-6">
                <Link href="/" className="flex items-center gap-3 group">
                    {/* Logo Icon 容器：
               設定 w-10 h-10 (正方形) 且 overflow-hidden。
               這樣原本長條形的 logo.png，右邊的文字就會被切掉。
                */}
                    <div className="relative w-10 h-10 flex-shrink-0 overflow-hidden">
                        <Image
                            src="/logo.png"
                            alt="Logo"
                            fill
                            // object-cover: 讓圖片填滿容器
                            // object-right: 強制靠右對齊 (只露出右邊的圖案)
                            className="object-cover object-right"
                        />
                    </div>

                    {/* 文字部分：用程式碼刻出來，確保清晰度 */}
                    <div className="flex flex-col justify-center">
                        <h1 className="text-gray-100 font-bold text-[15px] tracking-wider group-hover:text-white transition leading-tight">
                            水球軟體學院
                        </h1>
                        <p className="text-[10px] font-bold text-blue-400 tracking-widest font-mono mt-0.5">
                            WATERBALLSA.TW
                        </p>
                    </div>
                </Link>
            </div>

            {/* --- 2. 導覽選單 --- */}
            <nav className="flex-1 px-4 space-y-1">
                {MENU_ITEMS.map((item) => {
                    const isActive = pathname === item.href;

                    return (
                        <div key={item.href}>
                            <Link
                                href={item.href}
                                className={clsx(
                                    "flex items-center gap-4 px-4 py-3 rounded-lg transition-all duration-200 group mb-1",
                                    isActive
                                        ? "bg-wb-yellow text-black font-bold shadow-[0_0_15px_rgba(255,215,0,0.2)]"
                                        : "text-gray-400 hover:text-gray-100"
                                )}
                            >
                                {/* Icon 調整 */}
                                <item.icon
                                    size={22}
                                    strokeWidth={isActive ? 2.5 : 1.5}
                                    className={clsx(isActive ? "text-black" : "text-gray-500 group-hover:text-gray-300")}
                                />
                                <span className="text-[15px] tracking-wide">{item.name}</span>
                            </Link>

                            {/* --- 分隔線 (修正為藍灰色調) --- */}
                            {item.withDivider && (
                                <div className="my-4 border-b border-gray-700/50 mx-2" />
                            )}
                        </div>
                    );
                })}
            </nav>
        </aside>
    );
}