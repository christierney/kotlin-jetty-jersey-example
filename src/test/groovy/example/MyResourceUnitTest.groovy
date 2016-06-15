package example

class MyResourceUnitTest extends spock.lang.Specification {
    MyResource resource = new MyResource()

    def "getIt"() {
        when:
        MyData data = resource.getIt()

        then:
        data.id == 1
        data.msg == "Got it!"
        data.date instanceof Date
    }

    def "reverse"() {
        expect:
        resource.reverse(new MyData(42, "panama", new Date(2000))) == new MyData(42, "amanap", new Date(2000))
    }
}
