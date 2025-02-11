package com.kayakstudio.geosnap.tools.extensions


fun <T> MutableList<T>.upsert(
    itemsToUpsert: List<T>,
    compare: (old: T, new: T) -> Boolean,
) {
    itemsToUpsert.forEach { newItem ->
        val index = this.indexOfFirst { compare(it, newItem) }
        if (index >= 0) {
            this[index] = newItem
        } else {
            this.add(newItem)
        }
    }
}
