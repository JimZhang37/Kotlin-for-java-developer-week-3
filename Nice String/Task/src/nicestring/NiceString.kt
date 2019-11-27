package nicestring
import kotlin.collections.listOf

fun String.isNice(): Boolean {


    if(this.isEmpty()) return false
    val a = this.zip(this?.substring(1,this.length)).none{it.first == 'b' && (it.second == 'u' || it.second == 'a' || it.second == 'e')}
    val b = this.zip(this?.substring(1,this.length)).any{it.first ==it.second}
    val mCount = this.count{when(it){
        'a','e','i','o','u' -> true
        else ->false
    }}
    val c = if(mCount>=3)true else false
    val l = if (listOf(a,b,c).count{it == true}>=2) true else false
    return l
}