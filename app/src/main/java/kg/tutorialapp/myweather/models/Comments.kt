package kg.tutorialapp.myweather.models

data class Comments(
    var userId:Int?=null,
    var id:Int?=null,
    var name :String?=null,
    var email :String?=null,
    var body: String?=null
)
