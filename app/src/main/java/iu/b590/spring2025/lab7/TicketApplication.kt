package iu.b590.spring2025.lab7

import android.app.Application

class TicketApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        TicketRepository.initialize(this)
    }
}