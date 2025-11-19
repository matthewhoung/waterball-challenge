"use client";

import { useState } from "react";
import { Bell, ChevronDown, LogOut, User, Moon, Users, BookOpen } from "lucide-react";
import { clsx } from "clsx";
import Link from "next/link";
import { useCourse } from "@/contexts/CourseContext";

export default function Header() {
    // 控制三個選單的開關狀態
    const [isCourseOpen, setIsCourseOpen] = useState(false);
    const [isNotifOpen, setIsNotifOpen] = useState(false);
    const [isUserOpen, setIsUserOpen] = useState(false);

    // 從 Context 取得課程資料
    const { courses, selectedCourseId, setSelectedCourseId, loading } = useCourse();
    const selectedCourse = courses.find(c => c.id === selectedCourseId);

    // 點擊外部關閉選單的邏輯
    const toggleCourse = () => { setIsCourseOpen(!isCourseOpen); setIsNotifOpen(false); setIsUserOpen(false); };
    const toggleNotif = () => { setIsNotifOpen(!isNotifOpen); setIsCourseOpen(false); setIsUserOpen(false); };
    const toggleUser = () => { setIsUserOpen(!isUserOpen); setIsCourseOpen(false); setIsNotifOpen(false); };

    const handleCourseSelect = (courseId: string) => {
        setSelectedCourseId(courseId);
        setIsCourseOpen(false);
    };

    return (
        // 修改背景色以符合截圖
        <header className="h-16 w-full bg-wb-gray flex items-center justify-between px-6 sticky top-0 z-40 border-b border-gray-800">
            {/* --- 1. 左側：課程切換下拉選單 --- */}
            <div className="relative">
                <button
                    onClick={toggleCourse}
                    className="flex items-center gap-3 px-4 py-2 bg-[#202026] border border-gray-700 rounded text-gray-200 hover:border-wb-yellow transition text-sm min-w-[240px] justify-between"
                >
                    <span className="font-medium">
                        {loading ? "載入中..." : (selectedCourse?.title || "選擇課程")}
                    </span>
                    <ChevronDown size={16} className={clsx("text-gray-400 transition", isCourseOpen && "rotate-180")} />
                </button>

                {/* Dropdown Content */}
                {isCourseOpen && (
                    <div className="absolute top-full left-0 mt-2 w-full bg-[#202026] border border-gray-700 rounded shadow-xl py-1 animate-fade-in">
                        {courses.map((course) => (
                            <div
                                key={course.id}
                                onClick={() => handleCourseSelect(course.id)}
                                className={clsx(
                                    "px-4 py-2 hover:bg-gray-700 cursor-pointer text-sm flex items-center gap-2",
                                    course.id === selectedCourseId ? "text-gray-200" : "text-gray-400 pl-9"
                                )}
                            >
                                {course.id === selectedCourseId && <span className="text-wb-yellow">✓</span>}
                                {course.title}
                            </div>
                        ))}
                        {courses.length === 0 && !loading && (
                            <div className="px-4 py-2 text-sm text-gray-500">暫無課程</div>
                        )}
                    </div>
                )}
            </div>

            {/* --- 右側區塊 --- */}
            <div className="flex items-center gap-4">
                <button className="hidden md:block px-4 py-1.5 text-blue-400 border border-blue-400 rounded hover:bg-blue-400/10 transition text-sm font-bold">
                    <BookOpen size={16} className="inline mr-2" />
                    前往挑戰
                </button>

                {/* --- 2. 通知鈴鐺 --- */}
                <div className="relative">
                    <button
                        onClick={toggleNotif}
                        className={clsx(
                            "relative p-2 rounded-full border transition",
                            isNotifOpen ? "bg-wb-yellow text-black border-wb-yellow" : "text-wb-yellow border-wb-yellow hover:bg-wb-yellow/10"
                        )}
                    >
                        <Bell size={20} />
                        <span className="absolute top-0 right-0 w-2.5 h-2.5 bg-red-500 rounded-full border-2 border-[#121212]"></span>
                    </button>

                    {/* Notification Dropdown */}
                    {isNotifOpen && (
                        <div className="absolute top-full right-0 mt-3 w-80 bg-[#1e1e26] border border-gray-700 rounded-lg shadow-2xl overflow-hidden z-50">
                            <div className="p-4 border-b border-gray-700">
                                <h3 className="font-bold text-white">Notifications</h3>
                                <div className="flex gap-4 mt-2 text-sm">
                                    <span className="text-white border-b-2 border-white pb-1">Unread</span>
                                </div>
                            </div>
                            <div className="p-8 flex flex-col items-center justify-center text-gray-400 min-h-[200px]">
                                <span className="text-2xl mb-2">👏</span>
                                <p className="text-sm">Nice - that&apos;s all for now.</p>
                            </div>
                        </div>
                    )}
                </div>

                {/* --- 3. 使用者選單 --- */}
                <div className="relative">
                    <button onClick={toggleUser} className="block outline-none">
                        <div className="w-10 h-10 rounded-full overflow-hidden border-2 border-gray-700 cursor-pointer hover:border-wb-yellow transition">
                            {/* Avatar */}
                            <div className="w-full h-full bg-gray-600 flex items-center justify-center text-white font-bold text-sm">
                                MH
                            </div>
                        </div>
                    </button>

                    {/* User Dropdown Profile */}
                    {isUserOpen && (
                        <div className="absolute top-full right-0 mt-3 w-72 bg-[#1e1e26] border border-gray-700 rounded-lg shadow-2xl overflow-hidden z-50">
                            {/* User Info Header */}
                            <div className="p-5 border-b border-gray-700 bg-[#1e1e26]">
                                <p className="text-white font-bold text-lg">Matthew Hong <span className="text-gray-500 text-sm">#3082</span></p>

                                <div className="flex justify-between items-end mt-3 mb-1 text-white">
                                    <span className="font-bold">Lv. 1</span>
                                    <span className="text-xs text-gray-400">(0/200)</span>
                                </div>

                                {/* Progress Bar */}
                                <div className="w-full h-2 bg-gray-700 rounded-full overflow-hidden">
                                    <div className="h-full bg-wb-yellow w-[0%]"></div>
                                </div>
                            </div>

                            {/* Menu Items */}
                            <div className="py-2">
                                <Link href="/profile" className="flex items-center gap-3 px-5 py-3 text-gray-300 hover:bg-gray-700/50 transition">
                                    <User size={18} />
                                    <span className="text-sm">個人檔案</span>
                                </Link>
                                <button className="w-full flex items-center gap-3 px-5 py-3 text-gray-300 hover:bg-gray-700/50 transition text-left">
                                    <Moon size={18} />
                                    <span className="text-sm">切換主題</span>
                                </button>
                                <button className="w-full flex items-center gap-3 px-5 py-3 text-gray-300 hover:bg-gray-700/50 transition text-left">
                                    <Users size={18} />
                                    <span className="text-sm">邀請好友</span>
                                </button>
                            </div>

                            <div className="border-t border-gray-700 py-2">
                                <button className="w-full flex items-center gap-3 px-5 py-3 text-gray-300 hover:bg-gray-700/50 transition text-left">
                                    <LogOut size={18} />
                                    <span className="text-sm">登出</span>
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </header>
    );
}