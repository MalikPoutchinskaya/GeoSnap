package com.kayakstudio.geosnap.data.stubs.scenario

import com.kayakstudio.geosnap.data.player.picture.LocationDto
import com.kayakstudio.geosnap.data.stubs.databases.StubRemoteDatabase
import com.kayakstudio.geosnap.data.stubs.samples.SampleDto
import com.kayakstudio.geosnap.data.stubs.samples.SampleDto.playerPicture
import com.raedghazal.kotlinx_datetime_ext.minus
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime

const val USER_ID = "USER_ID"

fun buildScenario() = run {
    val user = SampleDto.user.copy(
        id = USER_ID,
        displayName = "Denis Brogniart",
        aboutMe = "Hiking, climbing, and chasing sunsets \uD83C\uDFD4\uFE0F",
        email = "test@test",
        firstName = "Denis",
        lastName = "Brogniart",
        phoneNumber = "+33612345678",
        imageUrl = "https://www.lequipe.fr/_medias/img-photo-jpg/denis-brogniart-photo-julien-faure-l-equipe/1500000001337636/37:471,1921:2355-828-828-75/89c9b",
    )
    StubRemoteDatabase.users.add(user)

    // competitor pictures
    StubRemoteDatabase.playerPictures.addAll(
        listOf(
            playerPicture.copy(
                id = "eiffel_tour",
                playerId = "maximus",
                url = "https://media.istockphoto.com/id/530410722/photo/attractive-woman-taking-selfie-in-front-of-the-eiffel-tower.jpg?s=612x612&w=0&k=20&c=qNKimtW08Vl2eucZpTUv7Gf0j1PT9B6TStLb2gI41EU=",
                location = LocationDto(48.8559324, 2.2932334)
            ),
            playerPicture.copy(
                id = "notre_dame",
                playerId = "omittantur",
                url = "https://as2.ftcdn.net/v2/jpg/01/30/40/69/1000_F_130406928_Hfb6BouMMbbcIm4lyMKNW3ROWDnUtTR0.jpg",
                location = LocationDto(48.8523647, 2.3474993)
            ),
            playerPicture.copy(
                id = "nantes",
                playerId = "iusto",
                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsK46uG_kPEnNHFjS9ZwhC4WhvDE5-JhJ6AA&s",
                location = LocationDto(47.2061616, -1.5670233)
            ),
            playerPicture.copy(
                id = "marseille",
                playerId = "quisque",
                url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXK1-z7SPxN2NjWprmvQc9LW4yv5_XniYwfA&s",
                location = LocationDto(43.297073, 5.3564062)
            )
        )
    )

    // user pictures
    StubRemoteDatabase.playerPictures.addAll(
        listOf(
            playerPicture.copy(
                id = "denis1",
                playerId = USER_ID,
                url = "https://pbs.twimg.com/media/D4m1BR8UIAAJhBa.jpg",
                dateTime = LocalDateTime.now().minus(1, DateTimeUnit.DAY),
            ),
            playerPicture.copy(
                id = "denis2",
                playerId = USER_ID,
                url = "https://www.programme-tv.net/imgre/fit/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2Ftel.2F2018.2F08.2F16.2F59ef7bba-ffcd-443a-9f4f-8934fbead918.2Ejpeg/720x405/crop-from/top/quality/80/koh-lanta-le-cap-ou-pas-cap-de-denis-brogniart.jpg",
                dateTime = LocalDateTime.now().minus(2, DateTimeUnit.DAY)
            ),
            playerPicture.copy(
                id = "denis3",
                playerId = USER_ID,
                url = "https://www.programme-tv.net/imgre/fit/~1~tel~2022~09~26~fd9bac5d-6ac5-411e-b73c-d142c8287629.jpeg/720x405/crop-from/top/quality/80/focus-point/704,337/denis-brogniart-sa-vie-privee.jpg",
                dateTime = LocalDateTime.now().minus(3, DateTimeUnit.DAY)
            ),
            playerPicture.copy(
                id = "denis4",
                playerId = USER_ID,
                url = "https://www.nextplz.fr/wp-content/uploads/nextplz/2022/08/jai-envie-de-toi-denis-denis-brogniart-drague-dans-koh-lanta-il-balance.jpg",
                dateTime = LocalDateTime.now()
            ),
            playerPicture.copy(
                id = "denis5",
                playerId = USER_ID,
                url = "https://www.nextplz.fr/wp-content/uploads/nextplz/2022/02/denis-brogniart-loin-des-siens-durant-le-tournage-de-koh-lanta-il-se-confie-365x200.png",
                dateTime = LocalDateTime.now().minus(4, DateTimeUnit.DAY)
            ),
            playerPicture.copy(
                id = "denis6",
                playerId = USER_ID,
                url = "https://www.programme.tv/imgre/fit/~1~tls~2022~02~22~89ebb742-74db-4d48-b2ba-5188edd862e8.jpeg/660x370/crop-from/top/quality/80/focus-point/841,386/koh-lanta-le-totem-maudit.jpg",
                dateTime = LocalDateTime.now().minus(5, DateTimeUnit.DAY)
            ),
        )
    )


    StubRemoteDatabase.players.addAll(
        listOf(
            SampleDto.player.copy(
                id = user.id,
                displayName = user.displayName,
                imageUrl = user.imageUrl,
                aboutMe = user.aboutMe,
                points = 4560
            ),
            SampleDto.player.copy(
                id = "maximus",
                displayName = "Brenda Christensen",
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZnTDdQyQOVzuAXcIPQ2UHXLfAXR8iY3ACcA&s",
                points = 45,
                aboutMe = "Adventure seeker \uD83C\uDF0D Always on the move!"
            ),
            SampleDto.player.copy(
                id = "omittantur",
                displayName = "Cassie Gibson",
                imageUrl = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                aboutMe = "Photographing life, one snapshot at a time \uD83D\uDCF8",
                points = 234
            ),
            SampleDto.player.copy(
                id = "iusto",
                displayName = "Alejandro Maxwell",
                imageUrl = "https://www.shutterstock.com/shutterstock/photos/250543117/display_1500/stock-photo-selfie-portrait-of-young-man-outdoors-250543117.jpg",
                aboutMe = "Food lover \uD83C\uDF55 Exploring the world bite by bite.",
                points = 78
            ),
            SampleDto.player.copy(
                id = "quisque",
                displayName = "Mike Nicholson",
                imageUrl = "https://media.istockphoto.com/id/1286279873/fr/photo/homme-selfie-portrait-avec-des-lunettes-de-soleil.jpg?s=170667a&w=0&k=20&c=pfC-HoW7Gfjsp8w4C4VK-EyekTgsd5LgVCzXBMqX21c=",
                aboutMe = "Curious mind, always learning something new! \uD83D\uDCDA",
                points = 166
            )
        )
    )
}