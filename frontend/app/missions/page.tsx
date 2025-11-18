"use client";

import { useState, useEffect } from "react";
import { Sword, Clock, Calendar, Lock, CheckCircle, AlertCircle, ArrowRight } from "lucide-react";
import { clsx } from "clsx";

// --- 1. 定義資料結構 ---
type MissionStatus = "AVAILABLE" | "LOCKED" | "ONGOING" | "COMPLETED" | "CLAIMABLE";

interface Mission {
    id: string;
    title: string;
    condition: string;     // 開啟條件
    timeLimit: string;     // 時限
    reward: string;        // 獎勵
    status: MissionStatus;
    type: "WHITE" | "BLACK" | "NEWBIE"; // 任務類型
}

export default function MissionsPage() {
    // Tab 狀態: available (可接) | ongoing (進行中) | past (過去)
    const [activeTab, setActiveTab] = useState<"available" | "ongoing" | "past">("available");
    const [missions, setMissions] = useState<Mission[]>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        async function fetchMissions() {
            try {
                const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}api/missions`);
                if (!res.ok) throw new Error("Failed to fetch missions");
                const data = await res.json();
                setMissions(data);
            } catch (error) {
                console.error("Error loading missions:", error);
            } finally {
                setLoading(false);
            }
        }
        fetchMissions();
    }, []);

    // 根據 Tab 篩選顯示的任務
    const displayedMissions = missions.filter((m) => {
        if (activeTab === "available") {
            // "可接任務" 顯示：可接取、鎖定中、可領獎
            return ["AVAILABLE", "LOCKED", "CLAIMABLE"].includes(m.status);
        }
        if (activeTab === "ongoing") return m.status === "ONGOING";
        if (activeTab === "past") return m.status === "COMPLETED";
        return false;
    });

    return (
        <div className="space-y-8 pb-12">

            {/* 頁面標題區 */}
            <div className="flex flex-col items-center space-y-4 mb-8">
                <h1 className="text-3xl font-bold text-white tracking-widest">獎勵任務</h1>

                {/* Tabs 切換器 */}
                <div className="bg-[#161b22] p-1 rounded-xl inline-flex border border-gray-800 shadow-lg">
                    <TabButton
                        label="可接任務"
                        isActive={activeTab === "available"}
                        onClick={() => setActiveTab("available")}
                    />
                    <TabButton
                        label="進行中的任務"
                        isActive={activeTab === "ongoing"}
                        onClick={() => setActiveTab("ongoing")}
                    />
                    <TabButton
                        label="過去的任務"
                        isActive={activeTab === "past"}
                        onClick={() => setActiveTab("past")}
                    />
                </div>
            </div>

            {/* 任務卡片列表 (Grid) */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {displayedMissions.map((mission) => (
                    <MissionCard key={mission.id} mission={mission} />
                ))}

                {/* 空狀態處理 */}
                {displayedMissions.length === 0 && (
                    <div className="col-span-full flex flex-col items-center justify-center py-20 text-gray-500 border-2 border-dashed border-gray-800 rounded-2xl">
                        <div className="text-4xl mb-4">🍃</div>
                        <p>目前這裡空空如也</p>
                    </div>
                )}
            </div>
        </div>
    );
}

// --- 子元件：Tab 按鈕 ---
function TabButton({ label, isActive, onClick }: { label: string, isActive: boolean, onClick: () => void }) {
    return (
        <button
            onClick={onClick}
            className={clsx(
                "px-6 py-2.5 rounded-lg text-sm font-bold transition-all duration-300",
                isActive
                    ? "bg-wb-yellow text-black shadow-[0_0_10px_rgba(255,215,0,0.3)] scale-105"
                    : "text-gray-400 hover:text-white hover:bg-gray-700/50"
            )}
        >
            {label}
        </button>
    );
}

// --- 子元件：任務卡片 ---
function MissionCard({ mission }: { mission: Mission }) {
    const isLocked = mission.status === "LOCKED";
    const isClaimable = mission.status === "CLAIMABLE";
    const isBlack = mission.type === "BLACK"; // 黑段任務樣式

    return (
        <div className={clsx(
            "relative rounded-xl p-6 border flex flex-col transition-all duration-300 group",
            // 鎖定狀態 vs 一般狀態 vs 黑段
            isLocked
                ? "bg-[#0d1117] border-gray-800 opacity-60 grayscale-[0.5]"
                : "bg-[#161b22] border-gray-700 hover:-translate-y-1 hover:shadow-2xl",
            !isLocked && !isBlack && "hover:border-wb-yellow/50", // 白段 Hover
            !isLocked && isBlack && "hover:border-purple-500/50 bg-gradient-to-br from-[#161b22] to-[#0d0d10]" // 黑段 Hover
        )}>

            {/* 任務類型標籤 (右上角) */}
            <div className="absolute top-4 right-4">
                {isBlack ? (
                    <span className="text-[10px] px-2 py-1 rounded bg-purple-900/30 text-purple-400 border border-purple-800">黑段</span>
                ) : (
                    <span className="text-[10px] px-2 py-1 rounded bg-blue-900/30 text-blue-400 border border-blue-800">白段</span>
                )}
            </div>

            {/* 標題區 */}
            <div className="flex items-start gap-3 mb-6 pr-8">
                <div className={clsx(
                    "p-2 rounded-lg",
                    isLocked ? "bg-gray-800 text-gray-500" : (isBlack ? "bg-purple-500/20 text-purple-400" : "bg-wb-yellow/20 text-wb-yellow")
                )}>
                    <Sword size={20} />
                </div>
                <div>
                    <h3 className={clsx("text-lg font-bold", isLocked ? "text-gray-500" : "text-white")}>
                        {mission.title}
                    </h3>
                    <p className="text-xs text-gray-500 mt-1">ID: {mission.id}</p>
                </div>
            </div>

            {/* 資訊列表 */}
            <div className="space-y-4 mb-8 flex-1">
                <InfoRow icon={Lock} label="開啟條件" value={mission.condition} isLocked={isLocked} highlight={mission.condition !== "無"} />
                <InfoRow icon={Clock} label="時限" value={mission.timeLimit} isLocked={isLocked} />
                <InfoRow icon={Calendar} label="獎勵" value={mission.reward} isLocked={isLocked} />
            </div>

            {/* 按鈕區 */}
            <div className="mt-auto">
                {mission.status === "AVAILABLE" && (
                    <button className="w-full py-3 bg-wb-yellow text-black font-bold rounded hover:bg-yellow-400 transition flex items-center justify-center gap-2 group-hover:shadow-[0_0_15px_rgba(255,215,0,0.3)]">
                        接受任務 <ArrowRight size={16} />
                    </button>
                )}
                {isClaimable && (
                    <button className="w-full py-3 bg-green-600 text-white font-bold rounded hover:bg-green-500 transition animate-pulse flex items-center justify-center gap-2 shadow-[0_0_15px_rgba(34,197,94,0.4)]">
                        <CheckCircle size={18} /> 領取獎勵
                    </button>
                )}
                {mission.status === "ONGOING" && (
                    <div className="w-full py-3 bg-blue-900/20 border border-blue-500 text-blue-400 font-bold rounded text-center flex items-center justify-center gap-2">
                        <Clock size={16} className="animate-spin-slow" /> 進行中 (0%)
                    </div>
                )}
                {mission.status === "COMPLETED" && (
                    <div className="w-full py-3 bg-gray-800/50 text-gray-500 font-bold rounded text-center cursor-default">
                        已完成
                    </div>
                )}
                {isLocked && (
                    <button disabled className="w-full py-3 bg-[#0d1117] text-gray-600 font-bold rounded cursor-not-allowed border border-gray-800 flex items-center justify-center gap-2">
                        <Lock size={14} /> 尚未達成開啟條件
                    </button>
                )}
            </div>
        </div>
    );
}

// --- Helper: 資訊列 ---
function InfoRow({ icon: Icon, label, value, isLocked, highlight }: any) {
    return (
        <div className="flex items-center gap-3 text-sm border-b border-gray-800/50 pb-2 last:border-0">
            <span className="text-gray-500 min-w-[70px] flex items-center gap-1">
                {label}
            </span>
            <div className="flex items-center gap-2 ml-auto">
                <span className={clsx(
                    "font-mono",
                    isLocked ? "text-gray-600" : (highlight ? "text-white" : "text-gray-400")
                )}>
                    {value}
                </span>
            </div>
        </div>
    )
}