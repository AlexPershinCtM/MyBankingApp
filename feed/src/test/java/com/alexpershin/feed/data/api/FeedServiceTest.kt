package com.alexpershin.feed.data.api

import com.alexpershin.feed.data.model.FeedResponse
import com.alexpershin.test.BaseServiceTest
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import retrofit2.Response

class FeedServiceTest : BaseServiceTest<FeedService>() {
    override val serviceClass: Class<FeedService>
        get() = FeedService::class.java

    @Test
    fun `GIVEN server returns successful response, WHEN fetchFeed is called, THEN assert response is success`() = runTest {
        // given
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(successResponseJson)
        )
        // when
        val result: Response<FeedResponse> = sut.fetchFeed(
            "accountUid",
            "categoryId",
        )

        // then
        assert(result.isSuccessful)
    }

    @Test
    fun `GIVEN server returns successful response, WHEN fetchFeed is called, THEN assert response is mapped correctly`() = runTest {
        // given
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(successResponseJson)
        )

        // when
        val result: Response<FeedResponse> = sut.fetchFeed(
            "accountUid",
            "categoryId",
        )

        // then
        val expected = Gson().fromJson(successResponseJson, FeedResponse::class.java)
        val actual = result.body()

        assert(result.isSuccessful)
        assertEquals(expected, actual)
    }

    @Test
    fun `GIVEN server returns error code, WHEN fetchFeed is called, THEN assert response is error`() = runTest {
        // given
        server.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody(successResponseJson)
        )
        // when
        val result: Response<FeedResponse> = sut.fetchFeed(
            "accountUid",
            "categoryId",
        )

        // then
        assertFalse(result.isSuccessful)
        assertEquals( 500, result.code())
    }

    private companion object {
        private const val successResponseJson = "{\n" + // TODO move to separate json file if have time
                "    \"feedItems\": [\n" +
                "        {\n" +
                "            \"feedItemUid\": \"fb66ddc6-96b5-41f9-a8c0-29317c15fb8e\",\n" +
                "            \"categoryUid\": \"fa1e6dcd-ad06-49ed-a39d-608510b35592\",\n" +
                "            \"amount\": {\n" +
                "                \"currency\": \"GBP\",\n" +
                "                \"minorUnits\": 18654\n" +
                "            },\n" +
                "            \"sourceAmount\": {\n" +
                "                \"currency\": \"GBP\",\n" +
                "                \"minorUnits\": 18654\n" +
                "            },\n" +
                "            \"direction\": \"OUT\",\n" +
                "            \"updatedAt\": \"2024-01-29T00:06:02.086Z\",\n" +
                "            \"transactionTime\": \"2024-01-29T00:06:01.890Z\",\n" +
                "            \"settlementTime\": \"2024-01-29T00:06:01.890Z\",\n" +
                "            \"source\": \"INTERNAL_TRANSFER\",\n" +
                "            \"status\": \"SETTLED\",\n" +
                "            \"transactingApplicationUserUid\": \"fa1ec9fa-9168-4ce9-862a-b4d80fcf1ed2\",\n" +
                "            \"counterPartyType\": \"CATEGORY\",\n" +
                "            \"counterPartyUid\": \"fa8e07cf-b744-41e7-a057-a890571120e2\",\n" +
                "            \"counterPartyName\": \"Family trip to Disney World\",\n" +
                "            \"country\": \"GB\",\n" +
                "            \"spendingCategory\": \"SAVING\",\n" +
                "            \"hasAttachment\": false,\n" +
                "            \"hasReceipt\": false,\n" +
                "            \"batchPaymentDetails\": null\n" +
                "        }\n" +
                "    ]\n" +
                "}"
    }
}