package com.gritbus.hipchon.data.model.place

data class LocalHipsterDetailData(
    val hipsterId: Int,
    val postList: List<LocalHipsterPost>,
    val title: String
)

data class LocalHipsterPost(
    val detail: String,
    val hipsterPostId: Int,
    val imageList: List<String>,
    val place: LocalHipsterPlace,
    val title: String
)

data class LocalHipsterPlace(
    val address: String,
    val category: String,
    val homepage: String,
    val isMyplace: Boolean,
    val name: String,
    val placeId: Int
)
