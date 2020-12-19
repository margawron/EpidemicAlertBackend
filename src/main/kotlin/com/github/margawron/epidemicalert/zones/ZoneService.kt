package com.github.margawron.epidemicalert.zones

import com.gargoylesoftware.htmlunit.WebClient
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class ZoneService(
    private val webClient: WebClient
) {
    companion object{
        val log = LoggerFactory.getLogger(ZoneService::class.java)
        val removePunctuationPattern: Regex = Regex("[;|.|,]")
        val countyPattern: Regex = Regex("(powiat \\p{L}*)|(, \\p{L}*)|(oraz \\p{L}+)")
        val joinPattern: Regex = Regex("(powiat)|(oraz)")
    }

    val redZones:List<String> by lazy {
        fetchRedZones()
    }

    // TODO lazy fetch all to main container then split to red/yellow
    private fun fetchRedZones(): List<String> {
        val url = "https://rcb.gov.pl/alert-rcb-powiaty-zoltej-i-czerwonej-strefy-epidemicznej/"
        val page = Jsoup.connect(url).get()
        val zonesUnformatted = page.select(".editor-content > p").eachText().filter { it.contains("województwo") }
        val zones = mutableListOf<String>()
        for (single in zonesUnformatted) {
            val splitter = single.split(":")
            val removePunctuation = splitter[1].replace(removePunctuationPattern, "")
            val singleZones = countyPattern.findAll(removePunctuation).map { it.value }

            val removedJoins = singleZones.map {
                it.replace(joinPattern, "")
            }
            val deletedSpaces = removedJoins.map {
                it.replace(" ", "")
            }
            val formattedCounties = deletedSpaces.map{
                if(!it[0].isUpperCase()) "powiat $it"
                else it
            }
            zones.addAll(formattedCounties)
        }
        return zones
    }
}