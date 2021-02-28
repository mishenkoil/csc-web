import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.*
import org.w3c.dom.*
import org.w3c.xhr.XMLHttpRequest
import kotlinx.serialization.*
import kotlinx.serialization.json.*

class Note(
    private val _id: String,
    private val name: String,
    private val text: String
) {
    fun TagConsumer<HTMLElement>.render() {
        article(classes = "notes__note notes__note_default") {
            div(classes = "notes_text") {
                h2 { +name }
                p { +text }
            }
            div(classes = "notes__buttons") {
                div(classes = "notes__buttons-right") {
                    button(classes = "notes__button-delete") {
                        id = _id
                        img(src = "img/delete.svg", alt = "delete") { }
                    }
                }
            }
        }
    }
}

@Serializable
data class NoteData(
    val _id: String,
    val name: String,
    val text: String
)

fun main() {
    window.onload = {
        val http = XMLHttpRequest()
        http.open("get", "/content")
        http.onload = {
            if (http.status in 200..399) {
                val parsedJson = Json.decodeFromString<List<NoteData>>(http.responseText)

                for (note in parsedJson) {
                    document.querySelector(".notes")!!.prepend {
                        Note(
                            _id = note._id,
                            name = note.name,
                            text = note.text
                        ).run { render() }
                    }
                }

                val remove = document.getElementsByClassName("notes__button-delete");
                for (i in 0 until remove.length) {
                    (remove[i] as HTMLButtonElement).addEventListener("click", {
                        val id = (remove[i] as HTMLElement).id
                        val httpPassive = XMLHttpRequest()
                        httpPassive.open("post", "/delete?id=${id}")
                        httpPassive.onload = {
                            if (httpPassive.status in 200..399) {
//                                window.alert("сейчас улетает запрос на удаление")
                                window.location.reload()
                            }
                        }
                        httpPassive.send()
                    })
                }
            }
        }
        http.send()

        (document.querySelector(".note-maker__button-submit") as HTMLButtonElement).addEventListener(
            "click", {

                val input_name = (document.querySelector(".note-maker__title") as HTMLTextAreaElement).value;
                val input_text = (document.querySelector(".note-maker__text") as HTMLTextAreaElement).value;
                if (input_name.isEmpty() || input_text.isEmpty()) {
                    window.alert("You must write something!");
                } else {
                    val httpPassive = XMLHttpRequest()
                    httpPassive.open("post", "/upload?name=${input_name}&text=${input_text}")

                    httpPassive.onload = {
                        if (httpPassive.status in 200..399) {
//                            window.alert("сейчас улетает запрос с новой заметкой")
                            window.location.reload()
                        }
                    }
                    httpPassive.send()
                }
            })

        (document.querySelector(".note-maker__button-cancel") as HTMLButtonElement).addEventListener(
            "click", {

                (document.querySelector(".note-maker__title") as HTMLTextAreaElement).value = ""
                (document.querySelector(".note-maker__text") as HTMLTextAreaElement).value = ""
            })


    }
}
