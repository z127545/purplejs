package io.purplejs.core.registry

import io.purplejs.core.internal.resource.ResourceTestSupport

import java.util.function.Supplier

class RegistryTest
    extends ResourceTestSupport
{
    public class MyObject1
    {}

    public class MyObject2
    {}

    public class MyObject3
    {}

    private Registry registry;

    def setup()
    {
        this.registry = RegistryBuilder.newBuilder().
            instance( MyObject1.class, new MyObject1() ).
            provider( MyObject2.class, new Supplier<MyObject2>() {
                @Override
                MyObject2 get()
                {
                    return new MyObject2();
                }
            } ).
            build();
    }

    def "getInstance"()
    {
        when:
        def instance1 = this.registry.getInstance( MyObject1.class );

        then:
        instance1 != null;

        when:
        def instance2 = this.registry.getInstance( MyObject1.class );

        then:
        instance1 == instance2;

        when:
        def instance3 = this.registry.getInstance( MyObject2.class );

        then:
        instance3 != null;

        when:
        def instance4 = this.registry.getInstance( MyObject2.class );

        then:
        instance3 != instance4;
    }

    def "getProvider"()
    {
        when:
        def supplier1 = this.registry.getProvider( MyObject1.class );

        then:
        supplier1 != null;
        supplier1.get() != null;
        supplier1.get() == supplier1.get();

        when:
        def supplier2 = this.registry.getProvider( MyObject1.class );

        then:
        supplier2 != null;
        supplier1 == supplier2;

        when:
        def supplier3 = this.registry.getProvider( MyObject2.class );

        then:
        supplier3 != null;
        supplier3.get() != null;
        supplier3.get() != supplier3.get();

        when:
        def supplier4 = this.registry.getProvider( MyObject2.class );

        then:
        supplier4 != null;
        supplier3 == supplier4;
    }

    def "getInstanceOrNull"()
    {
        when:
        def instance1 = this.registry.getInstanceOrNull( MyObject1.class );

        then:
        instance1 != null;

        when:
        def instance2 = this.registry.getInstanceOrNull( MyObject2.class );

        then:
        instance2 != null;

        when:
        def instance3 = this.registry.getInstanceOrNull( MyObject3.class );

        then:
        instance3 == null;
    }

    def "getInstance not found"()
    {
        when:
        this.registry.getInstance( MyObject3.class );

        then:
        thrown IllegalArgumentException;
    }

    def "getProvider not found"()
    {
        when:
        this.registry.getProvider( MyObject3.class );

        then:
        thrown IllegalArgumentException;
    }
}
