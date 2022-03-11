package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun solution(region: Int, num: Int, info: Array<IntArray>): IntArray {
        val result = IntArray(num + 1)
        var count = 0

        for (i in info.indices) {
            result[count] = i
            for (j in count - 1 downTo 0) {
                if (isPriority(result[j + 1], result[j], region, info)) {
                    val temp = result[j + 1]
                    result[j + 1] = result[j]
                    result[j] = temp
                } else {
                    break
                }
            }
            if (count < num) {
                count++
            }
        }
        val answer = IntArray(info.size){-1};
        Arrays.fill(answer, -1)
        for (i in 0 until count) {
            val index = result[i]
            answer[index] = i + 1
        }

        return answer
    }

    fun isPriority(index_a: Int, index_b: Int, region: Int, info: Array<IntArray>): Boolean { //index a가 더 우선순위 높은지 구하는것.
        val a_region = info[index_a][REGION]
        val b_region = info[index_b][REGION]
        val a_score = (info[index_a][NO_HOUSE] + 1) * 2 + (info[index_a][REGISTER] + 2) + (info[index_a][FAMILY] + 1) * 5
        val b_score = (info[index_b][NO_HOUSE] + 1) * 2 + (info[index_b][REGISTER] + 2) + (info[index_b][FAMILY] + 1) * 5
        val priorityForRegionNum = priorityForRegion(a_region, b_region, region)
        if (priorityForRegionNum == 0) {
            return true
        } else if (priorityForRegionNum == 1) {
            return false
        } else {
            if (a_score > b_score) {
                return true
            } else if (a_score == b_score) {
                if (index_a < index_b) {
                    return true
                }
            }
        }
        return false
    }

    fun priorityForRegion(a_region: Int, b_region: Int, region: Int): Int {
        return if (a_region == region && b_region != region) {
            0
        } else if (a_region != region && b_region == region) {
            1
        } else {
            2
        }
    }

    private val REGION = 0
    private val NO_HOUSE = 1
    private val REGISTER = 2
    private val FAMILY = 3



    class Solution {
        fun solution(grades: Array<String>): Array<String> {
            val gradeHashMap = HashMap<String, Grade?>()
            var count = 0

            for (i in 0..grades.size-1) {
                val name: String = grades.get(i).substring(0, 6)
                val grade: String = grades.get(i).substring(7, grades.get(i).length)
                if (gradeHashMap.containsKey(name)) {
                    val originGrade = gradeHashMap[name]
                    if (isPriority(originGrade!!.grade, grade)) {
                        originGrade.index = i
                        originGrade.grade = grade
                        originGrade.fullName = grades[i]
                        gradeHashMap.put(name,originGrade);
                    }
                } else {
                    val newGrade = Grade()
                    newGrade.grade = grade
                    newGrade.index = i
                    newGrade.fullName = grades[i]
                    gradeHashMap.put(name,newGrade);
                    count++
                }
            }
            val answer = Array(count, {""});
            val set: Set<String> = gradeHashMap.keys
            val i = set.iterator()
            var cursor = 0
            while (i.hasNext()) {
                val key = i.next()
                val nowGrade = gradeHashMap[key]
                answer[cursor] = nowGrade!!.fullName
                var j =0;
                for (j in (cursor-1) downTo 0){
                    val name_b: String = answer.get(j).substring(0, 6)
                    val name_a: String = answer.get(j + 1).substring(0, 6)
                    val a = gradeHashMap[name_a]
                    val b = gradeHashMap[name_b]
                    if (isPriorityForGrade(a, b)) {
                        val temp = answer[j + 1]
                        answer[j + 1] = answer[j]
                        answer[j] = temp
                    } else {
                        break
                    }
                }
                cursor++
            }
            return answer;
        }

        fun isPriorityForGrade(a: Grade?, b: Grade?): Boolean { //a가우선순위인지 찾는것
            if (convertToNumForGrade(a!!.grade) < convertToNumForGrade(b!!.grade)) {
                return true
            }
            if (convertToNumForGrade(a.grade) == convertToNumForGrade(b.grade)) {
                if (a.index < b.index) {
                    return true
                }
            }
            return false
        }

        class Grade {
            var grade = ""
            var index = -1
            var fullName = ""
        }
        fun isPriority(oldValue: String, newValue: String): Boolean {
             if (convertToNumForGrade(oldValue) > convertToNumForGrade(newValue)) {
                return true
            } else {
                return false
            }
        }

        fun convertToNumForGrade(value: String): Int {
            if (value[0] == 'A') {
                if (value[1] == '+') {
                    return 0
                } else if (value[1] == '0') {
                    return 1
                } else {
                    return 2
                }
            } else if (value[0] == 'B') {
                if (value[1] == '+') {
                    return 3
                } else if (value[1] == '0') {
                    return 4
                } else {
                    return 5
                }
            } else if (value[0] == 'C') {
                if (value[1] == '+') {
                    return 6
                } else if (value[1] == '0') {
                    return 7
                } else {
                    return 8
                }
            } else if (value[0] == 'D') {
                if (value[1] == '+') {
                    return 9
                } else if (value[1] == '0') {
                    return 10
                } else {
                    return 11
                }
            } else {
                return 12
            }
        }
    }
}