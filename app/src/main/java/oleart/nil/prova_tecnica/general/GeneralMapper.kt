package oleart.nil.prova_tecnica.general

abstract class GeneralMapper<I, O> {
    abstract fun map(value: I): O
    abstract fun reverseMap(value: O): I

    fun mapList(values: List<I>): List<O> =
            values.map { map(it) }

    fun reverseMapList(values: List<O>): List<I> =
            values.map { reverseMap(it) }

    fun mapListNullValues(values: List<I?>): List<O?> =
            values.map { if(it == null) null else map(it) }

    fun reverseMapListNulLValues(values: List<O?>): List<I?> =
            values.map { if(it == null) null else reverseMap(it) }
}