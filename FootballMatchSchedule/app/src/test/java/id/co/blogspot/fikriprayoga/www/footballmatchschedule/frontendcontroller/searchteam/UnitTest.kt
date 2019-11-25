package id.co.blogspot.fikriprayoga.www.footballmatchschedule.frontendcontroller.searchteam

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import android.os.Bundle
import org.mockito.Mockito
import com.nhaarman.mockito_kotlin.argumentCaptor
import org.junit.Assert
import org.mockito.Mockito.*
import retrofit2.Call


class UnitTest {
    @Test
    fun testDoRequest() {
        val searchTeamPresenter = mock(SearchTeamPresenter::class.java)
        val searchTeamView = mock(SearchTeamView::class.java)

        val bundle = mock(Bundle::class.java)
        bundle.putString("Data", "Unit Test")
        val searchTeamModel = SearchTeamModel("Data", bundle)
        `when`(searchTeamPresenter.getPlayer(1432, bundle)).then { searchTeamView.onResponse(searchTeamModel) }

        val mValue = searchTeamPresenter.getPlayer(1432, bundle)
        Assert.assertEquals(searchTeamView.onResponse(searchTeamModel), mValue)


    }

}
