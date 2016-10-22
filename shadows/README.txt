# JDBC Shadow classes

Shadow classes for various JDBC drivers.

The purpose of a shadow class is to be able to deliver a library which uses vendor specific JDBC extensions, but without actually having the library depend on the vendor's JDBC driver being available at compile time. The shadow class project is then used as an [optional dependency](https://maven.apache.org/guides/introduction/introduction-to-optional-and-excludes-dependencies.html) in the library project.

A shadow class need not mimic all of the methods of the class it shadows. It only needs to mimic what is actually used by the library. Also the only thing which is important, is to match the signature of the method, it is irrelevant what the method in the shadow class actually returns. This is because the shadow class will never actually be used at runtime.

