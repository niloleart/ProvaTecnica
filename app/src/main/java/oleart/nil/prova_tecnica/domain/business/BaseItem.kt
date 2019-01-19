package oleart.nil.prova_tecnica.domain.business

import java.io.Serializable

open class BaseItem() : Serializable {
    //TODO change type object
    constructor(type : Int) : this(){
        this.type = type
    }

    //Type is optional value
    var type : Int = 0
}