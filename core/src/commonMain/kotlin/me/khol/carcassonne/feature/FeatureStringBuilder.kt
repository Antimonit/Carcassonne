package me.khol.carcassonne.feature

interface FeatureStringBuilder {
    fun <T> withField(name: String, collection: Collection<T>): FeatureStringBuilder
    fun withField(name: String, any: Any): FeatureStringBuilder
}

private class FeatureStringBuilderImpl : FeatureStringBuilder {

    private val fields = mutableListOf<String>()

    override fun <T> withField(name: String, collection: Collection<T>): FeatureStringBuilder = apply {
        val array = if (collection.isEmpty()) {
            "[]"
        } else {
            collection.joinToString("\n", prefix = "[\n", postfix = "\n]") { "    $it," }
        }
        fields += "$name = $array"
    }

    override fun withField(name: String, any: Any): FeatureStringBuilder = apply {
        fields += "$name = $any"
    }

    override fun toString(): String = fields.joinToString(separator = ",\n", postfix = ",")
}

fun buildFeatureString(name: String, builderAction: FeatureStringBuilder.() -> Unit): String = buildString {
    append(name)
    appendLine("(")
    appendLine(FeatureStringBuilderImpl().apply(builderAction).toString().prependIndent("    "))
    append(")")
}
