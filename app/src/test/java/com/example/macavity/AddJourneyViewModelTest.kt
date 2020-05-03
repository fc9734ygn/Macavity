package com.example.macavity

import com.example.macavity.data.SharedPreferencesManager
import com.example.macavity.data.models.local.Location
import com.example.macavity.data.models.local.User
import com.example.macavity.data.repositories.journey.JourneyRepository
import com.example.macavity.data.repositories.user.UserRepository
import com.example.macavity.ui.addJourney.AddJourneyViewModel
import com.example.macavity.utils.secondsToMillis
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class AddJourneyViewModelTest : BaseTest() {

    @MockK
    lateinit var userRepoMock: UserRepository

    @MockK
    lateinit var journeyRepoMock: JourneyRepository

    @MockK
    lateinit var sharedPrefManagerMock: SharedPreferencesManager

    @MockK
    lateinit var userMock: User

    @MockK
    lateinit var locationMock: Location

    @MockK
    lateinit var dateTimeMock: LocalDateTime

    @MockK
    lateinit var zonedDateTimeMock: ZonedDateTime

    @MockK
    lateinit var zoneIdMock: ZoneId

    private lateinit var vm: AddJourneyViewModel

    @Before
    fun init() {

        // Initializing static mocks
        mockkStatic("com.example.macavity.utils.DateUtils")
        mockkStatic("org.threeten.bp.ZoneId")

        // Initializing class we're testing
        vm = AddJourneyViewModel(sharedPrefManagerMock, journeyRepoMock, userRepoMock)
        vm.dateTime = dateTimeMock
        vm.startingLocation = locationMock
        vm.destination = locationMock


        // Defining the behaviour of our mocked objects
        every { dateTimeMock.atZone(any()) } returns zonedDateTimeMock
        every { ZoneId.systemDefault() } returns zoneIdMock
        every { zonedDateTimeMock.toEpochSecond() } returns 0
        every { secondsToMillis(any()) } returns 0
        every { userMock.groupId } returns ""
        every { userMock.id } returns ""
        every { userMock.avatarUrl } returns ""
        every { userMock.groupId } returns ""
        every { userMock.driverStat } returns 0
        every { userRepoMock.fetchUserSingle(any()) } answers { Single.just(userMock) }
        every {
            journeyRepoMock.createJourney(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } answers { Completable.complete() }
    }

    @Test
    fun fetchUserSuccess() {
        //Defining mock behaviour
        val fakeUserId = "fakeUserId"
        every { sharedPrefManagerMock.getCurrentUserId() } answers { Single.just(fakeUserId) }

        //Using the function which is being tested
        vm.fetchUser()

        //Verifying outcomes
        verify { sharedPrefManagerMock.getCurrentUserId() }
        verify { userRepoMock.fetchUserSingle(fakeUserId) }
        assert(vm.currentUser.value == userMock)
        confirmVerified(sharedPrefManagerMock)
        confirmVerified(userRepoMock)
    }

    @Test
    fun createJourneyWithNoteSuccess() {
        vm.currentUser.value = userMock
        vm.createJourney(0, null)

        assert(vm.journeyCreatedSuccess.value == true)
        verify {
            journeyRepoMock.createJourney(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
        confirmVerified(journeyRepoMock)
    }

    @Test
    fun createJourneyWithoutNoteSuccess() {
        vm.currentUser.value = userMock
        vm.createJourney(0, "note")

        assert(vm.journeyCreatedSuccess.value == true)
        verify {
            journeyRepoMock.createJourney(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
            )
        }
        confirmVerified(journeyRepoMock)
    }

}