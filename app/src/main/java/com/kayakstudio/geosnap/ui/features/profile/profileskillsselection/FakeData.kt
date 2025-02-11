package com.kayakstudio.geosnap.ui.features.profile.profileskillsselection

object FakeData {
    fun getChips(type: ProfileSkillSelectionType): List<String> {
       return when (type) {
            ProfileSkillSelectionType.TARGET_JOBS -> listOf(
                "Pharmacien",
                "Préparateur",
                "Inventoriste",
                "Gestion du back-office",
                "Services"
            )

            ProfileSkillSelectionType.LANGUAGES -> listOf(
                "English",
                "German",
                "Spanish",
                "Mandarin",
                "Italian"
            )

            ProfileSkillSelectionType.SOFTWARE -> listOf(
                "Words",
                "Excels"
            )

            ProfileSkillSelectionType.SERVICES -> listOf(
                "Réception de commandes",
                "Réapprovisionnement",
                "Livraison ",
                "Manutention",
                "Recouvrement tiers-payant",
                "Merchandising"
            )
        }
    }
}