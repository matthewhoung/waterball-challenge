"use client";

import { useState, useEffect } from "react";
import { Sword, Clock, Lock, ArrowRight } from "lucide-react";
import { clsx } from "clsx";

// --- 1. 定義資料結構 ---
interface Challenge {
    id: string;
    title: string;
    description: string;
    prerequisites: string;
    timeLimitDays: number;
}

export default function MissionsPage() {
    const [challenges, setChallenges] = useState<Challenge[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        async function fetchChallenges() {
            try {
                const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}api/challenges`);
                if (!res.ok) throw new Error("Failed to fetch challenges");
                const response = await res.json();
                setChallenges(response.data || []);
            } catch (error) {
                console.error("Error loading challenges:", error);
            } finally {
                setLoading(false);
            }
        }
        fetchChallenges();
    }, []);

    return (
        <div className="space-y-8 pb-12">

            {/* 頁面標題區 */}
            <div className="flex flex-col items-center space-y-4 mb-8">
                <h1 className="text-3xl font-bold text-white tracking-widest">獎勵任務</h1>
            </div>

            {/* 任務卡片列表 (Grid) */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {loading ? (
                    <div className="col-span-full text-center py-20 text-gray-500">
                        載入中...
                    </div>
                ) : (
                    <>
                        {challenges.map((challenge) => (
                            <ChallengeCard key={challenge.id} challenge={challenge} />
                        ))}

                        {/* 空狀態處理 */}
                        {challenges.length === 0 && (
                            <div className="col-span-full flex flex-col items-center justify-center py-20 text-gray-500 border-2 border-dashed border-gray-800 rounded-2xl">
                                <div className="text-4xl mb-4">🍃</div>
                                <p>目前這裡空空如也</p>
                            </div>
                        )}
                    </>
                )}
            </div>
        </div>
    );
}

// --- 子元件：挑戰卡片 ---
function ChallengeCard({ challenge }: { challenge: Challenge }) {
    const hasPrerequisites = !!(challenge.prerequisites && challenge.prerequisites !== "無");

    return (
        <div className={clsx(
            "relative rounded-xl p-6 border flex flex-col transition-all duration-300 group",
            "bg-[#161b22] border-gray-700 hover:-translate-y-1 hover:shadow-2xl hover:border-wb-yellow/50"
        )}>

            {/* 任務類型標籤 (右上角) */}
            <div className="absolute top-4 right-4">
                <span className="text-[10px] px-2 py-1 rounded bg-blue-900/30 text-blue-400 border border-blue-800">挑戰</span>
            </div>

            {/* 標題區 */}
            <div className="flex items-start gap-3 mb-6 pr-8">
                <div className="p-2 rounded-lg bg-wb-yellow/20 text-wb-yellow">
                    <Sword size={20} />
                </div>
                <div>
                    <h3 className="text-lg font-bold text-white">
                        {challenge.title}
                    </h3>
                    <p className="text-xs text-gray-500 mt-1">{challenge.description}</p>
                </div>
            </div>

            {/* 資訊列表 */}
            <div className="space-y-4 mb-8 flex-1">
                <InfoRow
                    icon={Lock}
                    label="前置條件"
                    value={challenge.prerequisites || "無"}
                    highlight={hasPrerequisites}
                />
                <InfoRow
                    icon={Clock}
                    label="時限"
                    value={`${challenge.timeLimitDays} 天`}
                />
            </div>

            {/* 按鈕區 */}
            <div className="mt-auto">
                <button className="w-full py-3 bg-wb-yellow text-black font-bold rounded hover:bg-yellow-400 transition flex items-center justify-center gap-2 group-hover:shadow-[0_0_15px_rgba(255,215,0,0.3)]">
                    接受任務 <ArrowRight size={16} />
                </button>
            </div>
        </div>
    );
}

// --- Helper: 資訊列 ---
function InfoRow({ label, value, highlight }: {
    icon: React.ElementType;
    label: string;
    value: string;
    highlight?: boolean;
}) {
    return (
        <div className="flex items-center gap-3 text-sm border-b border-gray-800/50 pb-2 last:border-0">
            <span className="text-gray-500 min-w-[70px] flex items-center gap-1">
                {label}
            </span>
            <div className="flex items-center gap-2 ml-auto">
                <span className={clsx(
                    "font-mono",
                    highlight ? "text-white" : "text-gray-400"
                )}>
                    {value}
                </span>
            </div>
        </div>
    );
}
