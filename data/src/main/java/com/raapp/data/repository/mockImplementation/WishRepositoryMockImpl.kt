package com.raapp.data.repository.mockImplementation

import com.raapp.data.models.Wish
import com.raapp.data.repository.WishRepository

class WishRepositoryMockImpl : WishRepository {
    override fun addNewWishLocal(wish: Wish) {
        // do nothing
    }

    override fun getAllWishesLocal(): List<Wish> {
        return listOf(
            Wish(
                title = "Title 1",
                description = "Description 1",
                privacy = 0
            ),
            Wish(
                title = "Title 2",
                description = "Description 1",
                privacy = 1
            ),
            Wish(
                title = "Title 3",
                description = "Description 1",
                privacy = 2
            ),
            Wish(
                title = "Title 4",
                description = "Description 1",
                privacy = 3
            ),
            Wish(
                title = "Title 5",
                description = "Description 1",
                privacy = 1
            ),
            Wish(
                title = "Title 1, title  title  title  title  title  title  title  title  title  title  title  title  title  title  title  title  title ",
                description = "Description? werrrrrry long dexcription   text text text text text text text text text text text text text text text text text text text text text ",
                privacy = -1
            ),
            Wish(
                title = "Title 1",
                description = "Description 1"
            ),
            Wish(
                title = "Title 2",
                description = "Description 1",
                privacy = 0
            ),
            Wish(
                title = "Title 3",
                description = "Description 1",
                privacy = 0
            )
        )

    }
}