package god.dictdemo.model.bottombar

data class BottomBar(
    val activeColor: String,
    val inActiveColor: String,
    val selectTab: Int,
    val tabs: List<Tabs>
)

data class Tabs(
    val size: Int,
    val enable: Boolean,
    val index: Int,
    val pageUrl: String,
    val title: String
)