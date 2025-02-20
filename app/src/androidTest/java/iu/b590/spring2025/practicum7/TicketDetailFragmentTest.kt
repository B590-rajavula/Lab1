package iu.b590.spring2025.practicum7

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicketDetailFragmentTest{
    @Test
    fun checktextandBoxupdate() {
        val scenario = launchFragmentInContainer<TicketDetailFragment>()

        onView(withId(R.id.ticket_title))
            .perform(typeText("test_ticket"))
            .check(matches(withText("test_ticket")))

        onView(withId(R.id.ticket_solved))
            .perform(click())
            .check(matches(isChecked()))

        scenario.onFragment { fragment ->
            assert(fragment.ticket.title == "test_ticket")
            assert(fragment.ticket.isSolved)
        }
    }
}