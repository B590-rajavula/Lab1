package iu.b590.spring2025.practicum7

import android.app.Application

class TicketApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TicketRepository.initialize(this)
    }
}