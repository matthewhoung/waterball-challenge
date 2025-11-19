"use client";

import { createContext, useContext, useState, useEffect, ReactNode } from "react";

interface Course {
    id: string;
    title: string;
    description: string;
    thumbnailUrl: string;
    price: number;
    purchaseRequired: boolean;
    introCourse: boolean;
    discountPercentage: number;
}

interface CourseContextType {
    courses: Course[];
    selectedCourseId: string | null;
    setSelectedCourseId: (id: string) => void;
    loading: boolean;
}

const CourseContext = createContext<CourseContextType | undefined>(undefined);

export function CourseProvider({ children }: { children: ReactNode }) {
    const [courses, setCourses] = useState<Course[]>([]);
    const [selectedCourseId, setSelectedCourseId] = useState<string | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function fetchCourses() {
            try {
                const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}api/courses`);
                if (!res.ok) throw new Error("Failed to fetch courses");
                const response = await res.json();
                const data = response.data || [];
                setCourses(data);
                // Auto-select first course if available
                if (data.length > 0 && !selectedCourseId) {
                    setSelectedCourseId(data[0].id);
                }
            } catch (error) {
                console.error("Error loading courses:", error);
            } finally {
                setLoading(false);
            }
        }
        fetchCourses();
    }, []);

    return (
        <CourseContext.Provider value={{ courses, selectedCourseId, setSelectedCourseId, loading }}>
            {children}
        </CourseContext.Provider>
    );
}

export function useCourse() {
    const context = useContext(CourseContext);
    if (context === undefined) {
        throw new Error("useCourse must be used within a CourseProvider");
    }
    return context;
}
