package com.gritbus.hipchon.domain.model

import com.gritbus.hipchon.R

enum class Keyword(val keywordAll: Any) {
    FACILITY(
        KeywordFacility.values()
    ),
    MOOD(
        KeywordMood.values()
    ),
    SATISFACTION(
        KeywordSatisfaction.values()
    )
}

enum class KeywordFacility(val iconDrawable: Int, val contentString: Int) {
    TOILET(R.drawable.ic_facility_toilet, R.string.keyword_facility_toilet),
    PARKING(R.drawable.ic_facility_parking, R.string.keyword_facility_parking),
    SPACE(R.drawable.ic_facility_space, R.string.keyword_facility_space),
    COMFORTABLE(R.drawable.ic_facility_comfortable, R.string.keyword_facility_comfortable),
    INTERNET(R.drawable.ic_facility_internet, R.string.keyword_facility_internet)
}

enum class KeywordMood(val iconDrawable: Int, val contentString: Int) {
    INTERIOR(R.drawable.ic_mood_interior, R.string.keyword_mood_interior),
    LANDSCAPE(R.drawable.ic_mood_landscape, R.string.keyword_mood_landscape),
    PHOTO(R.drawable.ic_mood_photo, R.string.keyword_mood_photo),
    GROUP(R.drawable.ic_mood_group, R.string.keyword_mood_group),
    WORK(R.drawable.ic_mood_work, R.string.keyword_mood_work)
}

enum class KeywordSatisfaction(val iconDrawable: Int, val contentString: Int) {
    STAFF(R.drawable.ic_satisfaction_staff, R.string.keyword_satisfaction_staff),
    FAVOR(R.drawable.ic_satisfaction_favor, R.string.keyword_satisfaction_favor),
    ACTIVITY(R.drawable.ic_satisfaction_activity, R.string.keyword_satisfaction_activity),
    KID(R.drawable.ic_satisfaction_kid, R.string.keyword_satisfaction_kid),
    FRIEND(R.drawable.ic_satisfaction_friend, R.string.keyword_satisfaction_friend),
}
