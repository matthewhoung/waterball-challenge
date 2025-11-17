import { Facebook, Instagram, Youtube, Share2, Mail } from "lucide-react";
import Image from "next/image";

export default function Footer() {
    return (
        <footer className="w-full bg-[#121212] border-t border-gray-800 pt-12 pb-8 px-8 mt-auto">
            <div className="max-w-7xl mx-auto flex flex-col md:flex-row justify-between items-start md:items-end gap-8">

                {/* 左側：社群與連結 */}
                <div className="space-y-6">
                    {/* Social Icons */}
                    <div className="flex gap-4">
                        <a href="#" className="text-gray-400 hover:text-white transition"><Share2 size={24} /></a> {/* Line Mock */}
                        <a href="#" className="text-gray-400 hover:text-white transition"><Facebook size={24} /></a>
                        <a href="#" className="text-gray-400 hover:text-white transition"><Instagram size={24} /></a>
                        <a href="#" className="text-gray-400 hover:text-white transition"><Youtube size={24} /></a>
                        <a href="#" className="text-gray-400 hover:text-white transition"><Share2 size={24} /></a>
                    </div>

                    {/* Links */}
                    <div className="flex gap-6 text-sm text-gray-400">
                        <a href="#" className="hover:text-white transition">隱私權政策</a>
                        <a href="#" className="hover:text-white transition">服務條款</a>
                    </div>

                    {/* Contact */}
                    <div className="flex items-center gap-2 text-sm text-gray-400">
                        <Mail size={16} />
                        <span>客服信箱: support@waterballsa.tw</span>
                    </div>
                </div>

                {/* 右側：Logo 與 Copyright */}
                <div className="flex flex-col items-end gap-4">
                    {/* 這裡再次使用 Logo，根據截圖右下角有 Logo */}
                    <div className="relative w-32 h-10 opacity-80">
                        <Image
                            src="/logo.png"
                            alt="Waterball Logo"
                            fill
                            className="object-contain"
                        />
                    </div>
                    <p className="text-xs text-gray-500">© 2025 水球球特務有限公司</p>
                </div>
            </div>
        </footer>
    );
}