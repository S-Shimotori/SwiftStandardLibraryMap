package net.terminal_end.scraper.model


sealed class PageKind {

    /**
     * top page
     */
    object Framework: PageKind()

    /**
     * category page
     */
    object Topic: PageKind()

    /**
     * `typealias T0 = T1 && T2`
     */
    object TypeAlias: PageKind()

    /**
     * `var something: T0`
     * `let another: T1`
     */
    object InstanceProperty: PageKind()

    /**
     * `case something(T0)`
     */
    object EnumerationCase: PageKind()

    sealed class Type: PageKind() {

        /**
         * `struct T0 {}`
         */
        object Structure : Type()

        /**
         * `class T0 {}`
         */
        object Class : Type()

        /**
         * `protocol T0 {}`
         */
        object Protocol : Type()

        /**
         * `enum T0 {}`
         */
        object Enumeration : Type()

    }

    sealed class Function: PageKind() {

        /**
         * `init() {}`
         */
        object Initializer: PageKind.Function()

        /**
         * `func something(value: T0) -> T1`
         */
        object InstanceMethod: PageKind.Function()

        /**
         * `func something(value: T0) -> T1`
         */
        object Function: PageKind.Function()

        /**
         * `xxfix operator`
         */
        object Operator: PageKind.Function()
    }
}
