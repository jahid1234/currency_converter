package com.example.assaignment_currency_converter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class MainActivityTest{

    @get:Rule
    val activity = activityScenarioRule<MainActivity>()

    @Test
    fun testGridRecyclerItemClick(){
        onView(withId(R.id.ediTextAmount)).perform(typeText("2"))

        onView(withId(R.id.currency_convert_grid)).perform(click())

        onView(withId(R.id.conversionTextView)).check(matches(withText("2 AED = 2.00 AED")))
    }
}