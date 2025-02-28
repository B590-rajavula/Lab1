package iu.b590.spring2025.lab7


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TicketDetailFragmentTest {

    @Test
    fun updateTicketProperties() {
        val scenario = launchFragmentInContainer<TicketDetailFragment>()

        // Test updating the title
        onView(withId(R.id.ticket_title))
            .perform(typeText("New Ticket Title"))
            .check(matches(withText("New Ticket Title")))

        // Test updating the solved status
        onView(withId(R.id.ticket_solved))
            .perform(click())
            .check(matches(isChecked()))

        // Verify that the Ticket object is updated
//        scenario.onFragment { fragment ->
//            assert(fragment.ticket.title == "New Ticket Title")
//            assert(fragment.ticket.isSolved)
//        }
    }
}
