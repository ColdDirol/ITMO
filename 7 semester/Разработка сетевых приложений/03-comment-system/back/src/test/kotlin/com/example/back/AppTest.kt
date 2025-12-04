package com.example.back

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.internal.function.text.Length
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(CommentController::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AppTest {

    @Autowired
    private var mvc: MockMvc? = null

    @Test
    @Throws(Exception::class)
    fun `{CHECK STATUS} **addComment** positive way (status should be OK (200 code))`() {
        mvc!!.perform(
            MockMvcRequestBuilders
                .post("/addComment")
                .content(asJsonString(CommentDto("Taste", "lastName4")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    @Throws(Exception::class)
    fun `{CHECK STATUS} **addComment** negative way {without comment} (status should be BAD REQUEST (400 code))`() {
        val comment = CommentDto("author", "")
        mvc!!.perform(
            MockMvcRequestBuilders
                .post("/addComment")
                .content(asJsonString(comment))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)

        mvc!!.perform(
            MockMvcRequestBuilders
                .get("/allComments")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
    }

    @Test
    @Throws(Exception::class)
    fun `{CHECK SCENARIO} **allComments** positive way (initial array is empty)`() {
        mvc!!.perform(
            MockMvcRequestBuilders
                .get("/allComments")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty)
    }



    fun asJsonString(obj: Any): String {
        return try {
            ObjectMapper().writeValueAsString(obj)
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }
}