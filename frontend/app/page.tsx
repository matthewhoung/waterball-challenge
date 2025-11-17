import Image from "next/image";
import { CheckCircle, Play } from "lucide-react";

export default function Home() {
  return (
    <div className="space-y-12 pb-20">

      {/* 1. 黃色通告欄 */}
      <div className="w-full p-4 bg-[#202026] border border-gray-700 rounded-lg flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <p className="text-gray-200 text-sm leading-6">
          將軟體設計精通之旅體驗課程的全部影片看完就可以獲得 <span className="text-wb-yellow font-bold">3000 元課程折價券！</span>
        </p>
        <button className="flex-shrink-0 px-6 py-2 bg-wb-yellow text-black font-bold rounded hover:bg-yellow-400 transition text-sm">
          前往
        </button>
      </div>

      {/* 2. 歡迎標題區 */}
      <div className="space-y-6">
        <h1 className="text-3xl font-bold text-white tracking-wide">歡迎來到水球軟體學院</h1>
        <p className="text-gray-400 max-w-4xl leading-relaxed text-sm md:text-base">
          水球軟體學院提供最先進的軟體設計思路教材，並透過線上 Code Review 來帶你掌握進階軟體架構能力。
          <br />
          只要每週投資 5 小時，就能打造不平等的優勢，成為硬核的 Coding 實戰高手。
        </p>
      </div>

      {/* 3. 課程卡片區 */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">

        {/* Card A: 軟體設計模式 */}
        <div className="bg-[#1e1e26] rounded-xl overflow-hidden border border-gray-800 hover:border-wb-yellow/50 transition group flex flex-col">
          <div className="aspect-video relative bg-black">
            <Image
              src="/course_0.png"
              alt="軟體設計模式"
              fill
              className="object-cover opacity-90 group-hover:opacity-100 transition"
            />
          </div>
          <div className="p-6 flex flex-col flex-1 space-y-4">
            <div>
              <h3 className="text-xl font-bold text-blue-400 mb-1">軟體設計模式精通之旅</h3>
              <p className="text-xs text-gray-500">水球潘</p>
            </div>
            <p className="text-gray-300 text-sm flex-1">用一趟旅程的時間，成為硬核的 Coding 實戰高手</p>
            <p className="text-wb-yellow text-xs">看完課程介紹，立刻折價 3,000 元</p>
            <button className="w-full py-3 border border-wb-yellow text-wb-yellow rounded font-bold hover:bg-wb-yellow hover:text-black transition text-sm">
              立刻體驗
            </button>
          </div>
        </div>

        {/* Card B: AI x BDD*/}
        <div className="bg-[#1e1e26] rounded-xl overflow-hidden border border-gray-800 hover:border-wb-yellow/50 transition group flex flex-col">
          <div className="aspect-video relative bg-black">
            <Image
              src="/course_4.png"
              alt="AI x BDD"
              fill
              className="object-cover opacity-90 group-hover:opacity-100 transition"
            />
          </div>
          <div className="p-6 flex flex-col flex-1 space-y-4">
            <div>
              <h3 className="text-xl font-bold text-white mb-1">AI x BDD : 規格驅動全自動開發術</h3>
              <p className="text-xs text-gray-500">水球潘</p>
            </div>
            <p className="text-gray-300 text-sm flex-1">AI Top 1% 工程師必修課，掌握規格驅動的全自動化開發</p>
            <div className="h-4"></div> {/* spacer */}
            <button className="w-full py-3 bg-wb-yellow text-black rounded font-bold hover:bg-yellow-400 transition text-sm">
              立刻購買
            </button>
          </div>
        </div>

      </div>

      {/* 4. 講師介紹區 */}
      <div className="bg-[#1e1e26] rounded-xl p-8 border border-gray-800 flex flex-col md:flex-row gap-8">
        <div className="flex-shrink-0 mx-auto md:mx-0">
          <div className="w-32 h-32 md:w-40 md:h-40 rounded-full overflow-hidden border-4 border-gray-700">
            <Image
              src="/avatar.webp"
              alt="水球潘"
              width={160}
              height={160}
              className="object-cover"
            />
          </div>
        </div>
        <div className="flex-1 space-y-4">
          <div>
            <h2 className="text-xl font-bold text-white">水球潘</h2>
          </div>
          <ul className="space-y-2">
            {[
              "主修 Christopher Alexander 設計模式、軟體架構",
              "過去 40 多場 Talk 平均 93 位觀眾參與",
              "主辦的學院社群一年內成長超過 6000 位成員",
            ].map((text, index) => (
              <li key={index} className="flex items-start gap-3 text-gray-300 text-sm">
                <CheckCircle className="text-wb-yellow flex-shrink-0 mt-0.5" size={16} />
                <span>{text}</span>
              </li>
            ))}
          </ul>
          <div className="pt-4">
            <button className="px-6 py-2 bg-wb-yellow text-black font-bold rounded text-sm hover:bg-yellow-400">
              水球潘的部落格
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}