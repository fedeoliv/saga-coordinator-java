module com.sample.coordinator.jlink {
    // requires spring.beans;
    // requires spring.boot;
    // requires spring.boot.autoconfigure;
    // requires spring.context;
    // requires spring.core;
    // requires jdk.unsupported;

    requires spring.boot;
    requires spring.boot.autoconfigure;
    
    exports com.sample.coordinator;
    // export com.sample.app to com.specific.package;
}
