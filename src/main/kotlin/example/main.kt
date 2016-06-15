package example

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.eclipse.jetty.server.Server
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.jetty.JettyHttpContainerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.UriBuilder
import javax.ws.rs.ext.ContextResolver
import javax.ws.rs.ext.Provider

/**
 * Launch an embedded Jetty server, configured to use Jersey and Jackson.
 */
fun main(args: Array<String>) {
    println("Starting up")

    val baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build()
    val config = ResourceConfig()
            .register(JacksonFeature::class.java) // enable Jackson JSON provider
            .register(ObjectMapperProvider::class.java) // use our ObjectMapper rather than the default
            .register(MyResource())

    JettyHttpContainerFactory.createServer(baseUri, config).use { server ->
        server.join()
    }
}

/**
 * Provide a custom ObjectMapper for JSON ser/deser.
 *
 * It is only necessary to write a provider if you want to configure your ObjectMapper; otherwise a default is used.
 */
@Provider
class ObjectMapperProvider : ContextResolver<ObjectMapper> {
    val objectMapper = ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT) // pretty-print
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // use iso-8601 for dates
        .registerModule(KotlinModule()) // this doesn't seem to be required to serialize our data class, but it is required to deserialize

    override fun getContext(type: Class<*>?): ObjectMapper? = objectMapper
}


/**
 * Executes the given [block] function on this Server and then destroys it.
 *
 * This Mimic's the kotlin stdlib's Closeable::use.
 *
 * @param block a function to process this Server.
 */
inline fun Server.use(block: (Server) -> Unit) {
    try {
        block(this)
    } finally {
        this.destroy()
    }
}

data class MyData(val id: Long, val msg: String, val date: Date)

@Path("myresource")
class MyResource {
    val ids = AtomicLong()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getIt(): MyData {
        return MyData(ids.incrementAndGet(), "Got it!", Date())
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("reverse")
    fun reverse(r: MyData): MyData {
        return MyData(r.id, r.msg.reversed(), r.date)
    }
}