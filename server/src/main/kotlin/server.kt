import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

import org.litote.kmongo.*
import org.litote.kmongo.getCollection as kmongoGetCollection

data class NoteData(
    val _id: String,
    val name: String,
    val text: String
)

fun Application.main() {
    routing {

        static("/") {
            resources("/")
            default("client/index.html")
        }

        val client = KMongo.createClient("mongodb+srv://public-notes:XXX") //get com.mongodb.MongoClient new instance
        val database = client.getDatabase("test") //normal java driver usage
        val col = database.kmongoGetCollection<NoteData>() //KMongo extension method

        get("/content") {
            call.respondText(col.find().toList().json)
        }

        post("/delete") {
            val recieved_id = call.parameters["id"]!!
            col.deleteOne(NoteData::_id eq recieved_id)
            call.respond(HttpStatusCode.OK)
        }

        post("/upload") {
            val recieved_name = call.parameters["name"]!!
            val recieved_text = call.parameters["text"]!!
            val new_id = if (col.find().toList().json == "[]") "1" else ((col.find().last()._id).toInt() + 1).toString()
            col.insertOne(NoteData(new_id, recieved_name, recieved_text))
            call.respond(HttpStatusCode.OK)
        }
    }
}
