1. aidl机制去bind服务端进程的service，会激活服务端进程

2. aidl机制的内部流程：客户端通过AIDLService.Stub.asInterface()，拿到Proxy对象（Proxy对象实现了
AIDLService接口），调用Proxy对象的方法，然后调用transact()方法，transact()方法会调用服务端（
自己创建了一个类，这个类继承自Stub）的onTransact()，onTransact()会根据code去调用不同的方法