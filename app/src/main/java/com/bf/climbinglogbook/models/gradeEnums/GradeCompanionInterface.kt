package com.bf.climbinglogbook.models.gradeEnums


interface GradeCompanionInterface<E> {

    fun gradeToNumber(grade: E, hard: Boolean = false): Int
    fun numberToGrade(number: Int, hard: Boolean = false): E?
    fun getList(): List<String>
}