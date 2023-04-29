package com.bigbratan.emulair.fragmentHome

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.carousel
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerInteraction.GameInteractor
import com.bigbratan.emulair.utils.home.withModelsFrom
import com.bigbratan.emulair.BuildConfig
import com.bigbratan.emulair.managerCovers.CoverLoader
import com.bigbratan.emulair.managerSettings.SettingsInteractor
import com.bigbratan.emulair.common.utils.kotlin.lazySequenceOf
import com.bigbratan.emulair.common.metadataRetrograde.db.entity.Game

class EpoxyHomeController(
    private val gameInteractor: GameInteractor,
    private val settingsInteractor: SettingsInteractor,
    private val coverLoader: CoverLoader,
    private val home: HomeFragment
) : AsyncEpoxyController() {

    private var uiState = HomeViewModel.UIState()

    fun update(viewState: HomeViewModel.UIState) {
        uiState = viewState
        requestModelBuild()
    }

    override fun buildModels() {
        if (displayJump()) {
            addCardJumpBackInGame("section_jump", R.string.section_jump, uiState.jumpGame)
        }

        if (displayRecents()) {
            addCarouselRecentGames("section_recent", R.string.section_recent, uiState.recentGames)
        }

        if (displayFavorites()) {
            addCarouselFavoriteGames("section_favorites", R.string.section_favorites, uiState.favoriteGames)
        }

        if (displayAll()) {
            addCarouselAllGames("section_all_games", R.string.section_all_games, uiState.allGames)
        }

        /*
        if (displayNotice()) {
            addNoticeView()
        }
        */

        if (displayEmptyView()) {
            addNoDirectoryView()
        }
    }

    /*
    private fun displayNotice(): Boolean {
        val conditions = lazySequenceOf(
            { uiState.jumpGame.isEmpty() },
            { uiState.recentGames.isEmpty() },
            { uiState.favoriteGames.isEmpty() },
            { uiState.allGames.isNotEmpty() },
        )
        return conditions.all { it }
    }
    */

    private fun displayJump() = uiState.jumpGame.isNotEmpty()

    private fun displayRecents() = uiState.recentGames.isNotEmpty()

    private fun displayFavorites() = uiState.favoriteGames.isNotEmpty()

    private fun displayAll() = uiState.allGames.isNotEmpty()

    private fun displayEmptyView(): Boolean {
        val conditions = lazySequenceOf(
            { uiState.loading.not() },
            { uiState.jumpGame.isEmpty() },
            { uiState.recentGames.isEmpty() },
            { uiState.favoriteGames.isEmpty() },
            { uiState.allGames.isEmpty() },
        )
        return conditions.all { it }
    }

    private fun addCarouselRecentGames(id: String, titleId: Int, games: List<Game>) {
        epoxySectionView {
            id("section_$id")
            title(titleId)
        }
        carousel {
            id("carousel_$id")
            // padding(Carousel.Padding.dp(0, 0, 0, 24, 12))
            padding(Carousel.Padding.dp(24, 0, 24, 12, 12))
            withModelsFrom(games) { item ->
                EpoxyGameSmallView_()
                    .id(item.id)
                    .game(item)
                    .gameInteractor(this@EpoxyHomeController.gameInteractor)
                    .coverLoader(this@EpoxyHomeController.coverLoader)
            }
        }
    }

    private fun addCarouselAllGames(id: String, titleId: Int, games: List<Game>) {
        epoxyClickableSectionView {
            id("section_$id")
            title(titleId)
            onClick { this@EpoxyHomeController.home.navigateToAllGames() }
        }
        carousel {
            id("carousel_$id")
            // padding(Carousel.Padding.dp(0, 0, 0, 24, 12))
            padding(Carousel.Padding.dp(24, 0, 24, 12, 12))
            withModelsFrom(games) { item ->
                EpoxyGameSmallView_()
                    .id(item.id)
                    .game(item)
                    .gameInteractor(this@EpoxyHomeController.gameInteractor)
                    .coverLoader(this@EpoxyHomeController.coverLoader)
            }
        }
    }

    private fun addCarouselFavoriteGames(id: String, titleId: Int, games: List<Game>) {
        epoxyClickableSectionView {
            id("section_$id")
            title(titleId)
            onClick { this@EpoxyHomeController.home.navigateToFavoriteGames() }
        }
        carousel {
            id("carousel_$id")
            // padding(Carousel.Padding.dp(0, 0, 0, 24, 12))
            padding(Carousel.Padding.dp(24, 0, 24, 12, 12))
            // padding(Carousel.Padding.dp(24, 8, 24, 24, 12))
            withModelsFrom(games) { item ->
                EpoxyGameSmallView_()
                    .id(item.id)
                    .game(item)
                    .gameInteractor(this@EpoxyHomeController.gameInteractor)
                    .coverLoader(this@EpoxyHomeController.coverLoader)
            }
        }
    }

    private fun addCardJumpBackInGame(id: String, titleId: Int, games: List<Game>) {
        epoxySectionView {
            id("section_$id")
            title(titleId)
        }
        games.forEach { item ->
            epoxyGameBigView {
                id(item.id)
                game(item)
                gameInteractor(this@EpoxyHomeController.gameInteractor)
                coverLoader(this@EpoxyHomeController.coverLoader)
            }
        }
    }

    /*
    private fun addNoticeView() {
        epoxyNoticeView {
            id("empty_categories")
                .text(R.string.home_notice_message)
        }
    }
    */

    private fun addNoDirectoryView() {
        epoxyNoDirectoryView {
            id("empty_home")
                .title(R.string.home_empty_title)
                .message(R.string.home_empty_message)
                .action(R.string.home_empty_action)
                .actionEnabled(!this@EpoxyHomeController.uiState.indexInProgress)
                .onClick { this@EpoxyHomeController.settingsInteractor.changeLocalStorageFolder() }
        }
    }

    init {
        if (BuildConfig.DEBUG) {
            isDebugLoggingEnabled = true
        }
    }

    override fun onExceptionSwallowed(exception: RuntimeException) {
        throw exception
    }
}
