import sun.jvm.hotspot.utilities.Observable

class ObservableList<T>(private val wrapped: MutableList<T>): MutableList<T> by wrapped, Observable() {
    val wrappedList: MutableList<T>
        get() = wrapped
    override fun add(element: T): Boolean {
        if (wrapped.add(element)) {
            setChanged()
            notifyObservers()
            return true
        }
        return false
    }
}