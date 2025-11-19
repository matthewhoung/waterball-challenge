import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Sidebar from "@/components/Sidebar";
import Header from "@/components/Header";
import Footer from "@/components/Footer";
import { CourseProvider } from "@/contexts/CourseContext";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "水球軟體學院 | Waterball School",
  description: "Release 1 Mock",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="zh-TW">
      <body className={`${inter.className} bg-wb-dark text-wb-text`}>
        <CourseProvider>
        <div className="flex min-h-screen">
          {/* 左側固定 Sidebar */}
          <Sidebar />

          {/* 右側主要內容區 */}
          <div className="flex-1 pl-64 w-full min-h-screen flex flex-col bg-[#121212]"> {/* 確保背景色一致 */}

            {/* 頂部 Header */}
            <Header />

            {/* 頁面內容 (會自動撐開高度) */}
            <main className="flex-1 p-8 max-w-7xl mx-auto w-full animate-fade-in">
              {children}
            </main>

            {/* 底部 Footer */}
            <Footer />
          </div>
        </div>
        </CourseProvider>
      </body>
    </html>
  );
}