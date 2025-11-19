"use client";

import { useState, useEffect } from "react";
import { BookOpen, Trophy, Play, Lock } from "lucide-react";
import { clsx } from "clsx";
import { useCourse } from "@/contexts/CourseContext";

// --- 資料結構 ---
interface LessonSummary {
    id: string;
    title: string;
    isChallenge: boolean;
    orderIndex: number;
    preview: boolean;
}

interface CourseDetail {
    id: string;
    title: string;
    description: string;
    thumbnailUrl: string;
    price: number;
    purchaseRequired: boolean;
    lessons: LessonSummary[];
}

export default function LessonsPage() {
    const { selectedCourseId, loading: courseLoading } = useCourse();
    const [courseDetail, setCourseDetail] = useState<CourseDetail | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        async function fetchCourseDetail() {
            if (!selectedCourseId) {
                setLoading(false);
                return;
            }

            try {
                setLoading(true);
                const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}api/courses/${selectedCourseId}`);
                if (!res.ok) throw new Error("Failed to fetch course detail");
                const response = await res.json();
                setCourseDetail(response.data || null);
            } catch (error) {
                console.error("Error loading course detail:", error);
            } finally {
                setLoading(false);
            }
        }
        fetchCourseDetail();
    }, [selectedCourseId]);

    if (courseLoading || loading) {
        return (
            <div className="flex items-center justify-center py-20 text-gray-500">
                載入中...
            </div>
        );
    }

    if (!selectedCourseId) {
        return (
            <div className="flex flex-col items-center justify-center py-20 text-gray-500">
                <div className="text-4xl mb-4">📚</div>
                <p>請先從上方選擇課程</p>
            </div>
        );
    }

    if (!courseDetail) {
        return (
            <div className="flex flex-col items-center justify-center py-20 text-gray-500">
                <div className="text-4xl mb-4">❌</div>
                <p>無法載入課程資料</p>
            </div>
        );
    }

    // 根據 orderIndex 排序課程
    const sortedLessons = [...courseDetail.lessons].sort((a, b) => a.orderIndex - b.orderIndex);

    return (
        <div className="space-y-8 pb-12">
            {/* 課程標題區 */}
            <div className="flex flex-col items-center space-y-4 mb-8">
                <h1 className="text-3xl font-bold text-white tracking-widest">{courseDetail.title}</h1>
                <p className="text-gray-400 text-center max-w-2xl">{courseDetail.description}</p>
            </div>

            {/* 課程資訊 */}
            <div className="bg-[#161b22] border border-gray-700 rounded-xl p-6 mb-8">
                <div className="flex items-center justify-between">
                    <div className="flex items-center gap-4">
                        <span className="text-gray-400">共 {courseDetail.lessons.length} 個單元</span>
                        <span className="text-gray-600">|</span>
                        <span className="text-gray-400">
                            {courseDetail.lessons.filter(l => l.isChallenge).length} 個挑戰
                        </span>
                    </div>
                    {courseDetail.purchaseRequired && (
                        <span className="text-wb-yellow font-bold">NT$ {courseDetail.price}</span>
                    )}
                </div>
            </div>

            {/* 課程單元列表 */}
            <div className="space-y-4">
                {sortedLessons.map((lesson, index) => (
                    <LessonCard
                        key={lesson.id}
                        lesson={lesson}
                        index={index + 1}
                        purchaseRequired={courseDetail.purchaseRequired}
                    />
                ))}

                {sortedLessons.length === 0 && (
                    <div className="flex flex-col items-center justify-center py-20 text-gray-500 border-2 border-dashed border-gray-800 rounded-2xl">
                        <div className="text-4xl mb-4">🍃</div>
                        <p>此課程尚無單元</p>
                    </div>
                )}
            </div>
        </div>
    );
}

// --- 子元件：課程單元卡片 ---
function LessonCard({ lesson, index, purchaseRequired }: {
    lesson: LessonSummary;
    index: number;
    purchaseRequired: boolean;
}) {
    const isLocked = purchaseRequired && !lesson.preview;

    return (
        <div className={clsx(
            "flex items-center gap-4 p-4 rounded-xl border transition-all duration-300",
            isLocked
                ? "bg-[#0d1117] border-gray-800 opacity-60"
                : "bg-[#161b22] border-gray-700 hover:border-wb-yellow/50 cursor-pointer hover:-translate-y-0.5"
        )}>
            {/* 序號 */}
            <div className={clsx(
                "w-10 h-10 rounded-lg flex items-center justify-center font-bold text-sm",
                lesson.isChallenge
                    ? "bg-purple-500/20 text-purple-400"
                    : "bg-gray-700 text-gray-300"
            )}>
                {index}
            </div>

            {/* 圖標 */}
            <div className={clsx(
                "p-2 rounded-lg",
                lesson.isChallenge
                    ? "bg-purple-500/20 text-purple-400"
                    : "bg-wb-yellow/20 text-wb-yellow"
            )}>
                {lesson.isChallenge ? <Trophy size={20} /> : <BookOpen size={20} />}
            </div>

            {/* 標題 */}
            <div className="flex-1">
                <h3 className={clsx(
                    "font-medium",
                    isLocked ? "text-gray-500" : "text-white"
                )}>
                    {lesson.title}
                </h3>
                <div className="flex items-center gap-2 mt-1">
                    {lesson.isChallenge && (
                        <span className="text-[10px] px-2 py-0.5 rounded bg-purple-900/30 text-purple-400 border border-purple-800">
                            挑戰
                        </span>
                    )}
                    {lesson.preview && (
                        <span className="text-[10px] px-2 py-0.5 rounded bg-green-900/30 text-green-400 border border-green-800">
                            預覽
                        </span>
                    )}
                </div>
            </div>

            {/* 操作按鈕 */}
            <div>
                {isLocked ? (
                    <Lock size={18} className="text-gray-600" />
                ) : (
                    <Play size={18} className="text-wb-yellow" />
                )}
            </div>
        </div>
    );
}
