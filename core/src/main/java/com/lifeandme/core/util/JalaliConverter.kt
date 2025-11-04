package com.lifeandme.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

/**
 * Jalali (Persian) to Gregorian converter
 *
 * This implementation uses the robust mathematical algorithm based on Julian Day Number (JDN)
 * for precise and accurate conversion.
 */
object JalaliConverter {

    //Julian Day Number (JDN) of the Jalali Epoch (Farvardin 1, 1 SH = March 19, 622 AD)
    private const val JDN_OF_JALALI_EPOCH =
        1948321 // The standard JDN for the start of the Jalali calendar

    /**
     * Convert Jalali date (jalaliYear: Int, jalaliMonth: Int, jalaliDay: Int) to Gregorian local date
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun jalaliToGregorian(jalaliYear: Int, jalaliMonth: Int, jalaliDay: Int): LocalDate {
        // Jalali date to Julian Day Number (JDN)
        val jdn = jalaliToJDN(jalaliYear, jalaliMonth, jalaliDay)

        //  JDN back to Gregorian Date
        val gregorianDate = jdnToGregorian(jdn)

        // The array contains [year, month, day]
        return LocalDate.of(gregorianDate[0], gregorianDate[1], gregorianDate[2])
    }


    /**
     * Converts a Jalali date to its Julian Day Number (JDN).
     */
    private fun jalaliToJDN(jy: Int, jm: Int, jd: Int): Int {
        var dayNumber = 0

        // Days passed in full years before the current year
        val numYears = jy - 1
        dayNumber += numYears * 365

        // Add leap days passed in those full years (simplified 33-year cycle)
        val leapYears = (numYears * 8 + 2) / 33
        dayNumber += leapYears

        // Add days for full months passed
        dayNumber += if (jm <= 6) (jm - 1) * 31 else 186 + (jm - 7) * 30

        // Add days for the current month
        dayNumber += jd

        // Subtract 1 because the calculation of years/months assumes day 1
        dayNumber -= 1

        // Add the JDN of the Jalali Epoch to get the final JDN
        return dayNumber + JDN_OF_JALALI_EPOCH
    }

    /**
     * Converts a Julian Day Number (JDN) to a Gregorian date [year, month, day].
     * Uses the standard inverse Julian Day Number algorithm.
     */
    private fun jdnToGregorian(jdn: Int): IntArray {
        var modifiedJdn = jdn + 68569
        val centuryComponent = 4 * modifiedJdn / 146097
        modifiedJdn -= (146097 * centuryComponent + 3) / 4
        val yearComponent = 4000 * (modifiedJdn + 1) / 1461001
        modifiedJdn = modifiedJdn - 1461 * yearComponent / 4 + 31
        val monthComponentBase = 80 * modifiedJdn / 2447
        val dayOfMonth = modifiedJdn - 2447 * monthComponentBase / 80
        val monthIndexAdjustment = monthComponentBase / 11
        val gregorianMonth = monthComponentBase + 2 - 12 * monthIndexAdjustment
        val gregorianYear = 100 * (centuryComponent - 49) + yearComponent + monthIndexAdjustment

        if (gregorianMonth == 13) return intArrayOf(gregorianYear + 1, 1, dayOfMonth)

        return intArrayOf(gregorianYear, gregorianMonth, dayOfMonth) // Y: Year, D: Month, K: Day
    }

    fun getGregorianMonthName(gregorianIndex: Int, locale: Locale): String {
        val calendarMonthIndex = gregorianIndex - 1
        if (calendarMonthIndex !in 0..11) {
            return "---"
        }
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, calendarMonthIndex)
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val formatter = SimpleDateFormat("MMMM", locale)
        return formatter.format(calendar.time)
    }
}