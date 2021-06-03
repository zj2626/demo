# JVM参数

[ java -Xmx3550m -Xms3550m -Xss128k -XX:NewRatio=4 -XX:SurvivorRatio=4 -XX:MaxPermSize=16m -XX:MaxTenuringThreshold=0 ]

### 堆设置

> -Xmx3550m：最大堆大小。
 
> -Xms3550m：初始堆大小。此值可以设置与-Xmx相同，以避免每次垃圾回收完成后JVM重新分配内存。

> -Xmn2g：设置年轻代大小为2G。整个JVM内存大小=年轻代大小 + 年老代大小 + 持久代大小。持久代一般固定大小为64m，所以增大年轻代后，将会减小年老代大小。此值对系统性能影响较大，Sun官方推荐配置为整个堆的3/8。

> -Xss128k：设置每个线程的堆栈大小。JDK5.0以后每个线程堆栈大小为1M，以前每个线程堆栈大小为256K。更具应用的线程所需内存大小进行调整。在相同物理内存下，减小这个值能生成更多的线程。但是操作系统对一个进程内的线程数还是有限制的，不能无限生成，经验值在3000~5000左右。
 
> -XX:NewRatio=4:设置年轻代（包括Eden和两个Survivor区）与年老代的比值（除去持久代）。设置为4，则年轻代与年老代所占比值为1：4，年轻代占整个堆栈的1/5

> -XX:SurvivorRatio=4：设置年轻代中Eden区与Survivor区的大小比值。设置为4，则两个Survivor区与一个Eden区的比值为2:4，一个Survivor区占整个年轻代的1/6

> -XX:MaxTenuringThreshold=0：设置垃圾最大年龄(转入老年代的存活次数)。如果设置为0的话，则年轻代对象不经过Survivor区，直接进入年老代。对于年老代比较多的应用，可以提高效率。如果将此值设置为一个较大值，则年轻代对象会在Survivor区进行多次复制，这样可以增加对象再年轻代的存活时间，增加在年轻代即被回收的概论。

> -XX:PermSize=16m | -XX:MaxPermSize=16m:设置永久代最小大小|最大大小。（Java8以前）
 
> -XX:MetaspaceSize=16m | -XX:MaxMetaspaceSize=16m:设置元空间最小大小|最大大小。（Java8以后）

### 垃圾回收统计信息

> -XX:+PrintGC

> -XX:+PrintGCDetails

> -XX:+PrintGCTimeStamps

> -Xloggc:filename


### 收集器选择

> XX:+UseSerialGC:设置串行收集器  TODO

> -XX:+UseParallelGC:设置并行收集器

> -XX:+UseParalledlOldGC:设置并行年老代收集器

> -XX:+UseConcMarkSweepGC:设置并发收集器

### 收集器设置

> -XX:ParallelGCThreads=n:设置并行收集器收集时使用的CPU数。并行收集线程数。

> -XX:MaxGCPauseMillis=n:设置并行收集最大暂停时间

> -XX:GCTimeRatio=n:设置垃圾回收时间占程序运行时间的百分比。公式为1/(1+n)

> -XX:+CMSIncrementalMode:设置为增量模式。适用于单CPU情况。

> -XX:ParallelGCThreads=n:设置并发收集器年轻代收集方式为并行收集时，使用的CPU数。并行收集线程数。

> -XX:+UseCMSCompactAtFullCollection:使用并发收集器时，开启对年老代的压缩。

> -XX:CMSFullGCsBeforeCompaction=0:上面配置开启的情况下，这里设置多少次Full GC后，对年老代进行压缩