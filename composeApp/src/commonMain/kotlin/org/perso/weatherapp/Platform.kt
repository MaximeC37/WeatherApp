package org.perso.weatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform