package example

import org.jetbrains.spek.api.Spek
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MyResourceSpec : Spek({
    describe("MyResource") {
        val resource = MyResource()

        it("should return a MyData with an id, message, and date") {
            val data = resource.getIt()
            assertEquals(1, data.id)
            assertEquals("Got it!", data.msg)
            assertTrue { data.date is Date }
        }

        it("should return a MyData with msg reversed and id and date the same") {
            val data = resource.reverse(MyData(42, "panama", Date(2000)))
            assertEquals(MyData(42, "amanap", Date(2000)), data)
        }
    }
})